package CategoryGenerator;




import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.*;

import net.didion.jwnl.JWNLException;

import org.apache.ivy.plugins.resolver.util.URLLister;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldSelectorResult;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Version;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;

import dao.DAO_Model;
import dominio.DbPedia;
import encoders.Encode;


import Analizer.BlogSpaAnalyzer;
import Analizer.SpanishAnalyzer;
import GetBlogText.ExtractText;
import GetBlogText.WikipediaText;
import Language.Synonym;
import Language.Traductor;


public class CategoryGenerator {


	private static String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
	private static 	String rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns/#";
	private static String thing="http://www.w3.org/2002/07/owl#Thing";
	public static Hashtable<Categories, List<String>> listToEvaluate = new Hashtable<Categories, List<String>>();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("EMPIEZA: coge categorias");
		Model ontology=DAO_Model.generateMainModel();
		ArrayList<Resource> urlCategorias=getFatherCategoriesInternet();
		//System.out.println("Fin categorias");


		SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_30, new File (".\\resources\\stopwords\\spanishSmart.txt"));
		//Property type=ontology.createProperty(rdf,"type");

		
		
		File fDBPediaDirectory=new File(".\\resources\\DBPediaIndex");
		if(fDBPediaDirectory.exists()){
			fDBPediaDirectory.delete();
		}
		Directory dDBPediaIndexDirectory = FSDirectory.open(fDBPediaDirectory,new NoLockFactory());
		IndexWriter iDBPediaWriter = new IndexWriter(dDBPediaIndexDirectory, analyzer, true, new IndexWriter.MaxFieldLength(25000));

		File fWikiDirectory=new File(".\\resources\\WikiIndex");
		if(fWikiDirectory.exists()){
			fWikiDirectory.delete();
		}
		Directory dWikiIndexDirectory = FSDirectory.open(fWikiDirectory,new NoLockFactory());
		IndexWriter iWikiWriter = new IndexWriter(dWikiIndexDirectory, analyzer, true, new IndexWriter.MaxFieldLength(25000));

		File fListWebsDirectory=new File(".\\resources\\ListWebsIndex");
		if(fListWebsDirectory.exists()){
			fListWebsDirectory.delete();
		}
		Directory dListWebsIndexDirectory = FSDirectory.open(fListWebsDirectory,new NoLockFactory());
		IndexWriter iListWebsWriter = new IndexWriter(dListWebsIndexDirectory, analyzer, true, new IndexWriter.MaxFieldLength(25000));


		String nameResource = "";	
		String sTextResources = "";
		String sTotalTextSynonym = "";
		//int i = 0; i< 20; i++
		for(Resource category : urlCategorias){

			//Resource category = urlCategorias.get(i);
			////PRIMERA PARTE-->>Recursos de DbPedia
			getResourcesCategory(analyzer, iDBPediaWriter, sTextResources, category);

			///SEGUNDA PARTE--->Texto de WIkipedia
			System.out.println("\n\nSALIDA DE LA SEGUNDA FASE: ");
			
			getTextFromWikipedia(analyzer, iWikiWriter, sTotalTextSynonym, category);
			
			System.out.println("\n\nSALIDA DE LA TERCERA FASE: ");
			//getTextFromUrls(iListWebsWriter, category);


		}
		

		iDBPediaWriter.optimize();
		iWikiWriter.optimize();
		iListWebsWriter.optimize();
		iDBPediaWriter.close();
		iWikiWriter.close();
		iListWebsWriter.close();


	}

	private static void getTextFromUrls(IndexWriter iListWebsWriter, Resource category)
			throws MalformedURLException, IOException {
		List<String> sUrls;
		
		for(Categories oCategory: Categories.allCategories){
			if(category.getLocalName().equals(oCategory.toString())){
				String sTextUrls = "";
				for(UrlByCategory oUrlByCategory : oCategory.getLUrlList()){
					sUrls = Spider.GetSubUrls.SpiderUrl(oUrlByCategory.sMainUrl, oUrlByCategory.sRestUrl, oUrlByCategory.sSuffixFilter);
					int iMaxToText =(int) (sUrls.size() * 0.8);
					List<String> sUrlsSubList =  sUrls.subList(iMaxToText, sUrls.size());
					listToEvaluate.put(oCategory, sUrlsSubList);
					
					sTextUrls = getTextFromUrls(sUrls, iMaxToText) + " ";
					
				}
				AddDocument(iListWebsWriter, category, sTextUrls.trim(), 3);
			}
		}
	}

	private static String getTextFromUrls(List<String> sUrls, int maxToText) throws IOException,
			MalformedURLException {
		String sText="";
		for(int i = 0; i<maxToText;i++){
			String sUrlSub = sUrls.get(i);
			sText += ExtractText.GetBlogText(sUrlSub) + " ";
		}
		return sText;
	}

	private static void getTextFromWikipedia(SpanishAnalyzer analyzer,
			IndexWriter iWikiWriter, String sTotalTextSynonym, Resource category)
			throws Exception, IOException, JWNLException, CorruptIndexException {
		String sText;
		WikipediaText oWikipediaText = new WikipediaText();
		String sTextWikipedia= oWikipediaText.GetTextFromWikipedia(category.getLocalName(), true);


		sText = showTextAnalized(sTextWikipedia, analyzer);

		System.out.println(sText);

		//AddDocument(iWikiWriter, category, sTextWikipedia, 2);
		String sTextWikipediaSynonym = "";
		Set<String> oSyn = Synonym.lookupSynonyms(category.getLocalName());
		

		System.out.println("num sinonimos: " + oSyn.size());
		for(String sWord:oSyn){

			try{
				System.out.println("\n\nSALIDA DE LA TERCERA FASE: ");
				sTextWikipediaSynonym = oWikipediaText.GetTextFromWikipedia(sWord, true);

				sText = showTextAnalized(sTextWikipediaSynonym, analyzer);

				System.out.println(sText);
				
				sTotalTextSynonym += " " + sTextWikipediaSynonym;
			}
			catch(Exception e){


			}
		}
		
		String sTotalTextWikipedia =  sTextWikipedia + " "+ sTextWikipediaSynonym;


		sText = showTextAnalized(sTotalTextWikipedia, analyzer);

		AddDocument(iWikiWriter, category, sText, 3);
	}

	private static void getResourcesCategory(SpanishAnalyzer analyzer,
			IndexWriter iDBPediaWriter, String sTextResources,
			Resource category) throws Exception,
			IOException, CorruptIndexException {
		String pathCategory=category.getURI();

		List<Resource> lis=DbPedia.getResourcesOfType(pathCategory);
		
		System.out.println("\n\nSALIDA DE LA PRIMERA FASE: ");
		String labelResource=null;
		System.out.println("\nCATEGORIA = " +category.getLocalName() + "\n");
		System.out.println("Tiene "+lis.size()+" resources");
		for(int i=0; i<20 && i<lis.size();i++){
			Resource resource =lis.get(i);
			//if(resource.getLocalName()!=null){
			// labelResource=resource.getLocalName();
			//}else{
			labelResource=DbPedia.getLabel(resource.getURI());
			//	labelResource =Encode.getNameFromUrl(resource.getURI());
			//}

			//System.out.print(labelResource+" ");
			if(labelResource.endsWith("@en")){
				labelResource=labelResource.replace("@en","");
				labelResource=Traductor.Translate(labelResource);			
			}else{
				labelResource=labelResource.replace("@es","");
			}
			sTextResources=labelResource+" "+sTextResources;
		}

		
		String sText = showTextAnalized(sTextResources, analyzer);

		System.out.println(sText);

		
		AddDocument(iDBPediaWriter, category, sText, 1);
	}

	private static String showTextAnalized(String sTextResources, Analyzer oAnalyzer)
			throws IOException {
		String sText = "";
		if(sTextResources.equals("")){
			//clean stopwords
			Reader stringReader = new StringReader(sTextResources); 
			TokenStream tokenStream = oAnalyzer.tokenStream("defaultFieldName", stringReader);
			sText =  (new Analizer.test.TermAnalyzerView()).GetView(tokenStream, 0).trim();
		}
		return sText;
	}

	private static void AddDocument(IndexWriter iwriter, Resource category,
			String sTotalText, int iPriority) throws CorruptIndexException, IOException {
		Document categoria = new Document();


		categoria.add( new Field("CategoryName", category.getLocalName(), Field.Store.YES,Field.Index.NO));
		Field textoField= new Field("Text", sTotalText, Field.Store.YES,Field.Index.ANALYZED,Field.TermVector.YES);
		categoria.add(textoField ); 
		Field priorityField= new Field("Priority", String.valueOf(iPriority), Field.Store.YES,Field.Index.NO);
		categoria.add(priorityField );


		iwriter.addDocument(categoria );

	}

	private static String getSubClassesCategorias(
			String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getResourcesCategorias(
			String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private static ArrayList<Resource> getFatherCategoriesInternet() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		ArrayList <Resource> fatherCategorias=DbPedia.getFatherCategories();


		return fatherCategorias;
	}
	private static ArrayList<Resource> getFatherCategories(Model model) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		Property subClassOf = model.createProperty(rdfs,"subClassOf");
		//  po: http://purl.org/ontology/po/
		//	http://www.w3.org/2002/07/owl# rdf: http://www.w3.org/1999/02/22-rdf-syntax-ns/#
		// skos http://www.w3.org/TR/skos-reference/skos.html# rdfs http://www.w3.org/2000/01/rdf-schema#


		Property label = model.createProperty(rdfs,"label");
		Property type=model.createProperty(rdf,"type");

		System.out.println("\n\n\nEntra objects categorias");
		NodeIterator ri=model.listObjectsOfProperty(subClassOf);

		//NodeIterator moreParents = model.listObjectsOfProperty(subClassOf);
		ArrayList <Resource> allCategorias=new ArrayList<Resource>();
		ArrayList <Resource> topCategorias=new ArrayList<Resource>();

		//		ArrayList<Resource> urlCategorias=new ArrayList<Resource>();
		//boolean found=false;
		//&& !found

		while (ri.hasNext() ) {
			Resource node=(Resource) ri.next();
			System.out.println("All Cat "+node.getLocalName());
			allCategorias.add(node);

			if(node.getURI().equals(thing)){
				System.out.println("entra");
				Resource thingRes =node;
				//found =true;
				NodeIterator ni=model.listObjectsOfProperty(thingRes, subClassOf);
				while(ni.hasNext()){
					RDFNode parentCat=ni.next();
					if(parentCat.isResource()){
						Resource cat=(Resource) parentCat;
						topCategorias.add(cat);
						System.out.println("Cat "+cat.getLocalName());
					}
				}
			}
		}


		return allCategorias;
	}
}