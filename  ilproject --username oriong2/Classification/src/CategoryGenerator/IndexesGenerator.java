package CategoryGenerator;



import Classificator.Classificator;
import Classificator.Evaluator;
import Classificator.IndexCategScore;

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
import java.util.Map.Entry;

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

import dao.DAO;
import dao.DAO_Model;
import dominio.Category;
import dominio.DbPedia;
import dominio.StringToCategories;
import dominio.UrlForFiltering;
import encoders.Encode;


import Analizer.BlogSpaAnalyzer;
import Analizer.SpanishAnalyzer;
import CategoryGenerator.IndexesWriter.IndexType;

import DBLayer.DAOUrlsClassified;
import DBLayer.DAOUrlsRastreated;
import DBLayer.DAOUrlsRastreated.State;
import DBLayer.DAOWebsClassified;
import GetText.ExtractText;
import GetText.WikipediaText;
import Language.Synonym;
import Language.Traductor;


public class IndexesGenerator {
	
	
	public enum SourceOfData{
		Internet,
		DB
	}
	

	private static SourceOfData getSourceOfData = SourceOfData.DB;
	private static String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
	private static 	String rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns/#";
	private static String thing="http://www.w3.org/2002/07/owl#Thing";
	public static Hashtable<Category, List<String>> listToEvaluate = new Hashtable<Category, List<String>>();
	public static Hashtable<Category, List<String>> listToClassify=new Hashtable<Category, List<String>>();
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("EMPIEZA: coge categorias");
		Model ontology=DAO_Model.generateMainModel();
		ArrayList<Resource> urlCategorias=getFatherCategoriesInternet();
		
		SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_30, new File (".\\resources\\stopwords\\spanishSmart.txt"));

		////LIST OF INDEXES THAT WILL BE CREATED---->CHANGE
		IndexType[] oListofIdexes = new IndexType[]{
				/*IndexType.DBPedia,*/
				IndexType.WikipediaIndex
				//IndexType.ListWebsIndex
		};
		IndexesWriter.CreateIndexes(oListofIdexes, analyzer);


		
		for(Resource category : urlCategorias){

			////PRIMERA PARTE-->>Generar indice de Recursos de DbPedia
			addResourcesCategory(analyzer, IndexesWriter.getIndex(IndexType.DBPediaIndex), category);

			///SEGUNDA PARTE--->Generar indice de Texto de WIkipedia
			addTextFromWikipedia(analyzer, IndexesWriter.getIndex(IndexType.WikipediaIndex), category);

			////TERCERA FASE: ");---->Generar indice de Noticias diarias
			addTextFromUrls(IndexesWriter.getIndex(IndexType.ListWebsIndex), category);

		}

		////The list for evaluating  with 20%of the webs is tranferred to DB WEB_CAT, 
		//Evaluator will look for them
			
		//saveUrlsToEvaluate(listToEvaluate);
	
		
		/// Not necesary ---  Evaluator.evaluate(listToEvaluate);

	}

	



//////PRIMERA FASE
	private static void addResourcesCategory(SpanishAnalyzer analyzer,IndexWriter iDBPediaWriter, Resource category) throws Exception,
			IOException, CorruptIndexException {
		String pathCategory=category.getURI();

		List<Resource> lis=DbPedia.getResourcesOfType(pathCategory);

		System.out.println("\n\nSALIDA DE LA PRIMERA FASE: ");
		String labelResource=null;
		System.out.println("\nCATEGORIA = " +category.getLocalName() + "\n");
		System.out.println("Tiene "+lis.size()+" resources");
		
		String sTextResources = "";
		
		for(int i=0; i<100 && i<lis.size();i++){
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


		AddDocument(iDBPediaWriter, category, sText);
	}
	
//////SEGUNDA FASE
	private static void addTextFromWikipedia(SpanishAnalyzer analyzer,IndexWriter iWikiWriter, Resource category)
	throws Exception, IOException, JWNLException, CorruptIndexException {
		String sText;
		WikipediaText oWikipediaText = new WikipediaText();
		String sTextWikipedia= oWikipediaText.GetTextFromWikipedia(category.getLocalName(), true);
		String sTotalTextSynonym = "";

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

		AddDocument(iWikiWriter, category, sText);
	}

	
	
//////TERCERA FASE
	private static void addTextFromUrls(IndexWriter iListWebsWriter, Resource category)	throws MalformedURLException, IOException {
	
		
		//Obtener objeto category
		Category oCategory = StringToCategory.getCategory(category.getLocalName());
		
			
		List<String> listUrls = new ArrayList<String>();
		//Esto es para o sacar las urls de internet rastreando con el spider
		//o cogerlas de la base de datos donde previamente las hemos guardado con StoreAllUrls
		
		///POR DEFECTO DB
		
		listUrls = DAOUrlsRastreated.getInstance().selectUrls(State.ToIndex);
	
		String sTextUrls = "";		
		
		//RECOGE TODO EL TEXTO

		for(int i = 0; i<listUrls.size();i++){
			String sUrlSub = listUrls.get(i);
			sTextUrls += ExtractText.GetBlogText(sUrlSub) + " ";
			DAOUrlsRastreated.getInstance().updateUrlState(sUrlSub, State.Indexed);
		}
		AddDocument(iListWebsWriter, category, sTextUrls.trim());
		
	}

	
	
	
	///METODOS COMPLEMENTARIOS
	

	private static String showTextAnalized(String sTextResources, Analyzer oAnalyzer)
	throws IOException {
		String sText = "";
		if(!sTextResources.equals("")){
			//clean stopwords
			Reader stringReader = new StringReader(sTextResources); 
			TokenStream tokenStream = oAnalyzer.tokenStream("defaultFieldName", stringReader);
			sText =  (new Analizer.test.TermAnalyzerView()).GetView(tokenStream, 0).trim();
		}
		return sText;
	}

	private static void AddDocument(IndexWriter iwriter, Resource category,
			String sTotalText) throws CorruptIndexException, IOException {
		if(iwriter != null){
			Document categoria = new Document();


			categoria.add( new Field("CategoryName", category.getLocalName(), Field.Store.YES,Field.Index.NO));
			Field textoField= new Field("Text", sTotalText, Field.Store.YES,Field.Index.ANALYZED,Field.TermVector.YES);
			categoria.add(textoField ); 


			iwriter.addDocument(categoria );
		}

	}



	public static ArrayList<Resource> getFatherCategoriesInternet() throws SQLException, ClassNotFoundException {
		
		ArrayList <Resource> fatherCategorias=DbPedia.getFatherCategories();


		return fatherCategorias;
	}
	
	


	////METODOS NO USADOS
@Deprecated
	private static void saveUrlsToEvaluate(Hashtable<Category, List<String>> listToEvaluate2) {
		
		for(Entry<Category,List<String>> oEntry:listToEvaluate.entrySet()){
			Category oCategories = oEntry.getKey();
		
			for(String sUrl: oEntry.getValue()){
				//DAO.storeWebCat(sUrl, oCategories.toString());
			}
		}
			
		}

		

@Deprecated
		private static String getTextFromUrls(List<String> sUrls, int maxToText) throws IOException,
		MalformedURLException {
			String sText="";
			for(int i = 0; i<maxToText;i++){
				String sUrlSub = sUrls.get(i);
				sText += ExtractText.GetBlogText(sUrlSub) + " ";
			}
			return sText;
		}

	private static void getTextFromUrlsFromOneCat( Resource category)
	throws MalformedURLException, IOException {
		List<String> sUrls = new java.util.ArrayList<String>();

		Category oCategory = Category.FictionalCharacter;
		if(category.getLocalName().equals(oCategory.toString())){
			String sTextUrls = "";
			List<String> sUrlsSubListToEval = new java.util.ArrayList<String>();
			for(UrlForFiltering oUrlByCategory : oCategory.getLUrlList()){
				sUrls.addAll(Spider.GetSubUrls.SpiderUrl(oUrlByCategory.sMainUrl, oUrlByCategory.sRestUrl, 1,3,0, oUrlByCategory.sSuffixFilter));


			}

			int iMaxToText =(int) (sUrls.size() * 0.8);

			sUrlsSubListToEval.addAll(sUrls.subList(iMaxToText, sUrls.size()));
			listToEvaluate.put(oCategory, sUrlsSubListToEval);
		}
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