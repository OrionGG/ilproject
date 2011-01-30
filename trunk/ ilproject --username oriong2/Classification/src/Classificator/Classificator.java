
package Classificator;
import Classificator.Evaluator;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NoLockFactory;
import org.apache.lucene.util.Version;

import com.hp.hpl.jena.rdf.model.Resource;

import dao.DAO_MySQL;


import Analizer.SpanishAnalyzer;
import CategoryGenerator.Categories;
import CategoryGenerator.IndexesGenerator;
import CategoryGenerator.IndexesWriter;
import CategoryGenerator.StringToCategories;
import CategoryGenerator.UrlByCategory;
import CategoryGenerator.IndexesWriter.IndexType;
import DBLayer.DAOScoresIntermediate;
import DBLayer.DAOUrlsRastreated;
import DBLayer.DAOWebsClassified;
import DBLayer.DAOUrl;
import DBLayer.DAOUrlsRastreated.State;
import GetText.ExtractText;
import Spider.GetSubUrls;

public class Classificator {


	public static void main(String[] args) throws Exception {
		
		if (args.length > 1){
			String sOption = args[0].toString();
			String sDomainUrl = args[1].toString();
			
			if(sOption.equals("-u")){
				classificate(sDomainUrl);
	
			}
			else if(sOption.equals("-l")){
				String sUrl = args[2].toString();
				List<String> oList = GetSubUrls.DefaultSpiderUrl(sDomainUrl, sUrl);
				for(String sSubUrl : oList){
			
					classificate(sSubUrl);
				}
			}else{
				
				////GETTING THE URLS FROM THE CATEGORY GENERATOR
				
					List<String> urls=DAOUrlsRastreated.getInstance().selectUrls(State.ToIndex);
					for(String url : urls){
						classificate(url);
						
					}
								
				

				}
				
	
			}
		}

	
	public static void classificate(String sDomainUrl){
		
		List<IndexCategScore> listTopDocs = new ArrayList<IndexCategScore>();
		String sText = "";
		try {
			sText = ExtractText.GetBlogText(sDomainUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			listTopDocs = getListIndex(sDomainUrl, sText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		TreeMap<Double, Categories> oTreeMap = FinalScoreCalculator.indexShortedCross(listTopDocs);
		System.out.println("");
		//	System.out.println("URL: "+ sUrl);
		System.out.println("");
	
		FinalScoreCalculator.showFinalResults(oTreeMap);
	}



	public static List<IndexCategScore> getScoresCat(String sUrl) {
		List<IndexCategScore> oResult = new java.util.ArrayList<IndexCategScore>();
		String sText = "";
		try {
			sText = ExtractText.GetBlogText(sUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oResult = getListIndex(sUrl, sText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oResult;
	}

	public static List<IndexCategScore>  getListIndex(String sDomainUrl, String sText) throws CorruptIndexException, IOException, ParseException, SQLException, ClassNotFoundException{
		List<IndexCategScore> lResult = new ArrayList<IndexCategScore>();
		Map<IndexSearcher, Directory> lIndex = new Hashtable<IndexSearcher, Directory>();
		
		lIndex=generateIndexesReader(lIndex);
		// Parse a simple query that searches for "text":
		Analyzer analyzer = new SpanishAnalyzer(Version.LUCENE_30, new File (".\\resources\\stopwords\\spanishSmart.txt"));

		//Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
		QueryParser parser = new QueryParser(Version.LUCENE_30,"Text", analyzer);
		// Term term=new Term("baile");
		BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
		parser.setAllowLeadingWildcard(false);

		Reader stringReader = new StringReader(sText);
		
		TokenStream tokenStream = (analyzer).tokenStream("defaultFieldName", stringReader);
		sText =  (new Analizer.test.TermAnalyzerView()).GetView(tokenStream, 0).trim();
		
		System.out.println("Texto parseado: " + sText);

		Query query = parser.parse(sText);
		
		//Generate a list of top docs archived
		List<IndexTopDoc> lIndexTopDoc = new ArrayList<IndexTopDoc>(); 
		//For each index
		for(Entry<IndexSearcher, Directory> oIndex: lIndex.entrySet()){
			IndexSearcher oIndexSearcher = oIndex.getKey();
			Directory oDirectory = oIndex.getValue();
			IndexTopDoc oIndexTopDoc = hitDocsByIndex(oIndexSearcher, oDirectory, query,sDomainUrl);
			lIndexTopDoc.add(oIndexTopDoc);
		}
		

		int i =0;
		for(IndexTopDoc oIndexTopDoc : lIndexTopDoc){
			IndexCategScore oIndexCategScore = PrepareIndexToCross(sDomainUrl, i, oIndexTopDoc);
			lResult.add(oIndexCategScore);
			i++;
		}
		

		//For each index
		for(Entry<IndexSearcher, Directory> oIndex: lIndex.entrySet()){
			IndexSearcher oIndexSearcher = oIndex.getKey();
			Directory oDirectory = oIndex.getValue();
			oIndexSearcher.close();
			oDirectory.close();
		}
		

		return lResult;
	}
	

	private static Map<IndexSearcher, Directory> generateIndexesReader(Map<IndexSearcher, Directory> lIndex) throws IOException {
		// TODO Auto-generated method stub
		
		File fDBPediaDirectory=new File(".\\resources\\DBPediaIndex");
		Directory dDBPediaIndexDirectory = FSDirectory.open(fDBPediaDirectory,new NoLockFactory());
		
		// Now search the index:
		IndexSearcher iDBPediaSearcher = new IndexSearcher(dDBPediaIndexDirectory, true); // read-only=true
		lIndex.put(iDBPediaSearcher, dDBPediaIndexDirectory);

		File fWikiDirectory=new File(".\\resources\\WikiIndex");
		Directory dWikiIndexDirectory = FSDirectory.open(fWikiDirectory,new NoLockFactory());
		// Now search the index:
		IndexSearcher iWikiSearcher = new IndexSearcher(dWikiIndexDirectory, true); // read-only=true
		lIndex.put(iWikiSearcher, dWikiIndexDirectory);

		File fListWebsDirectory=new File(".\\resources\\ListWebsIndex");
		Directory dListWebsIndexDirectory = FSDirectory.open(fListWebsDirectory,new NoLockFactory());
		// Now search the index:
		IndexSearcher iListWebsSearcher = new IndexSearcher(dListWebsIndexDirectory, true); // read-only=true
		lIndex.put(iListWebsSearcher, dListWebsIndexDirectory);

		return lIndex;
	}


	private static IndexTopDoc hitDocsByIndex(IndexSearcher oIndexSearcher, Directory oDirectory,
			Query query,String sDomainUrl) throws IOException, CorruptIndexException, SQLException {

		TopDocs hits = oIndexSearcher.search(query, 1000); 
		IndexTopDoc oResult = new IndexTopDoc(oIndexSearcher, hits);
		return oResult;
	}


	private static IndexCategScore PrepareIndexToCross(String sDomainUrl, int i, IndexTopDoc oIndexTopDoc)
			throws CorruptIndexException, IOException, SQLException, ClassNotFoundException {
		IndexSearcher oIndexSearcher = oIndexTopDoc.oIndexSearcher;
		TopDocs hits = oIndexTopDoc.oTopDocs;
		//////FUNCIONALIDADES POSIBLES PARA LEER EL INDICE
		//System.out.println("El termino -"+term+"- aparece "+isearcher.docFreq(term)+" veces");
		System.out.println("Tras ejecutar la query " + hits.scoreDocs.length + " encontrados para");
		// Iterate through the results:
		System.out.println("Scores del articulo por categorias: "); 
		IndexCategScore oResult = new IndexCategScore(oIndexSearcher);
		for(ScoreDoc oScoreDoc : hits.scoreDocs)
		{
			Document hitDoc = oIndexSearcher.doc(oScoreDoc.doc); 
			String sCategoryName = hitDoc.getField("CategoryName").stringValue();

			////SAVING TO DB TEMPORARY SCORES
			DAOScoresIntermediate.getInstance().saveUrl(sDomainUrl,i, oScoreDoc.score);
			oResult.hCategScore.put(sCategoryName, oScoreDoc.score);
		
			System.out.println("     "+hitDoc.getField("CategoryName").stringValue() + " "+ oScoreDoc.score+"->ALMACENADO");
			//assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
			
		}
		return oResult;
	}


}
