
package Analizer;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
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


import DBLayer.DAOUrl;
import GetBlogText.ExtractText;
import Spider.GetSubUrls;

public class Categorizator {


	public static void main(String[] args) throws Exception {
		if (args.length > 1){
			String sOption = args[0].toString();
			String sDomainUrl = args[1].toString();
			if(sOption.equals("-u")){
				String sText = ExtractText.GetBlogText(sDomainUrl);
				//DAOUrl oDAOUrl = new DAOUrl();
				//oDAOUrl.insertOrUpdateUrl(sUrl, sText);
				//String sText = "Impotencia. Rabia. Llanto. Indignación e insultos. Esos fueron los prolegómenos del concierto de ayer de Lady Gaga, la nueva reina del pop del siglo XIX, que en Madrid estuvo marcado por la venta de entradas falsas. Ese fue el motivo de que cientos de fans, venidos de toda España y de otros países, y que llevaban varios días a la intemperie, con el fin de ser los primeros en ver y oír a su musa";
				similarityFunction(sText);
			}
			else if(sOption.equals("-l")){
				String sUrl = args[2].toString();
				List<String> oList = GetSubUrls.DefaultSpiderUrl(sDomainUrl, sUrl);
				for(String sSubUrl : oList){
					String sText = ExtractText.GetBlogText(sSubUrl);
					DAOUrl oDAOUrl = new DAOUrl();
					oDAOUrl.insertOrUpdateUrl(sSubUrl, sText);
					similarityFunction(sText);

				}
			}
		}

	}


	public static void similarityFunction(String sText) throws CorruptIndexException, IOException, ParseException{

		Map<IndexSearcher, Directory> lIndex = new Hashtable<IndexSearcher, Directory>();
		
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
		lIndex.put(iWikiSearcher, dWikiIndexDirectory);

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
		ArrayList<TopDocs> oListHits = new ArrayList<TopDocs>();
		for(Entry<IndexSearcher, Directory> oIndex: lIndex.entrySet()){
			IndexSearcher oIndexSearcher = oIndex.getKey();
			Directory oDirectory = oIndex.getValue();
			TopDocs oHits = hitDosByIndex(sText, oIndexSearcher, oDirectory, query);
			oListHits.add(oHits);
		}
	}


	private static TopDocs hitDosByIndex(String sText,
			IndexSearcher oIndexSearcher, Directory oDirectory,
			Query query) throws IOException, CorruptIndexException {
		TopDocs hits = oIndexSearcher.search(query, 1000); 

		//////FUNCIONALIDADES POSIBLES PARA LEER EL INDICE
		//System.out.println("El termino -"+term+"- aparece "+isearcher.docFreq(term)+" veces");
		System.out.println("Tras ejecutar la query " + hits.scoreDocs.length + " encontrados para");
		// Iterate through the results:
		System.out.println(sText);
		for(ScoreDoc oScoreDoc : hits.scoreDocs)
		{
			Document hitDoc = oIndexSearcher.doc(oScoreDoc.doc);
			System.out.println("Esta en Documento "+hitDoc.hashCode()+" "+hitDoc.getField("CategoryName") + " "+ oScoreDoc.score);
			// System.out.println(hitDoc.toString());

			//assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
		}
		oIndexSearcher.close();
		oDirectory.close();
		return hits;
	}

}