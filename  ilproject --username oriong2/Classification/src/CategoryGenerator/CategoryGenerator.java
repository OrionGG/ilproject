package CategoryGenerator;




import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
import GetBlogText.WikipediaText;
import Language.Synonym;
import Language.Traductor;


public class CategoryGenerator {


	private static String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
	private static 	String rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns/#";
	private static String thing="http://www.w3.org/2002/07/owl#Thing";

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("EMPIEZA: coge categorias");
		Model ontology=DAO_Model.generateMainModel();
		ArrayList<Resource> urlCategorias=getFatherCategoriesInternet();
		//System.out.println("Fin categorias");


		SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_30, new File (".\\resources\\stopwords\\spanishSmart.txt"));
		//Property type=ontology.createProperty(rdf,"type");

		File fDBPediaDirectory=new File(".\\resources\\DBPediaIndex");
		Directory dDBPediaIndexDirectory = FSDirectory.open(fDBPediaDirectory,new NoLockFactory());
		IndexWriter iDBPediaWriter = new IndexWriter(dDBPediaIndexDirectory, analyzer, true, new IndexWriter.MaxFieldLength(25000));

		File fWikiDirectory=new File(".\\resources\\WikiIndex");
		Directory dWikiIndexDirectory = FSDirectory.open(fWikiDirectory,new NoLockFactory());
		IndexWriter iWikiWriter = new IndexWriter(dWikiIndexDirectory, analyzer, true, new IndexWriter.MaxFieldLength(25000));

		File fListWebsDirectory=new File(".\\resources\\ListWebsIndex");
		Directory dListWebsIndexDirectory = FSDirectory.open(fListWebsDirectory,new NoLockFactory());
		IndexWriter iListWebsWriter = new IndexWriter(dListWebsIndexDirectory, analyzer, true, new IndexWriter.MaxFieldLength(25000));


		String nameResource = null;	
		String sTextResources = null;
		String sTotalTextSynonym = null;
		//int i = 0; i< 20; i++
		for(Resource category : urlCategorias){

			//Resource category = urlCategorias.get(i);
			////PRIMERA PARTE-->>Recursos de DbPedia
			String pathCategory=category.getURI();

			List<Resource> lis=DbPedia.getResourcesOfType(pathCategory);


			System.out.println("\n\nSALIDA DE LA PRIMERA FASE: ");
			String labelResource=null;
			System.out.println("\nCATEGORIA = " +category.getLocalName()+"\n");
			System.out.println("Tiene "+lis.size()+" resources");
			for(int i=0; i<9 && i<lis.size();i++){
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
				//eliminar tag @es y @en
				System.out.print(labelResource+" |");



				sTextResources=labelResource+" "+sTextResources;
			}

			AddDocument(iDBPediaWriter, category, sTextResources, 1);

			///SEGUNDA PARTE--->Texto de WIkipedia
			System.out.println("\n\nSALIDA DE LA SEGUNDA FASE: ");
			WikipediaText oWikipediaText = new WikipediaText();
			String sTextWikipedia= oWikipediaText.GetTextFromWikipedia(category.getLocalName(), true);


			//AddDocument(iWikiWriter, category, sTextWikipedia, 2);
			String sTextWikipediaSynonym = "";
			Set<String> oSyn = Synonym.lookupSynonyms(category.getLocalName());
			for(String sWord:oSyn){

				try{
					System.out.println("\n\nSALIDA DE LA TERCERA FASE: ");
					sTextWikipediaSynonym = oWikipediaText.GetTextFromWikipedia(sWord, true);

					sTotalTextSynonym += " " + sTextWikipediaSynonym;
				}
				catch(Exception e){


				}
			}
			
			String sTotalTextWikipedia =  sTextWikipedia + " "+ sTextWikipediaSynonym;
			
			AddDocument(iWikiWriter, category, sTotalTextWikipedia, 3);



		}
		iDBPediaWriter.close();
		iWikiWriter.close();
		iListWebsWriter.close();


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

		iwriter.optimize();
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