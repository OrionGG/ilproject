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
import GetBlogText.WikipediaText;


public class CategoryGerenator {


	private static String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
	private static 	String rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns/#";
	private static String thing="http://www.w3.org/2002/07/owl#Thing";
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub


		Model ontology=DAO_Model.generateMainModel();
		ArrayList<Resource> urlCategorias=getFatherCategories(ontology);
		System.out.println("Fin categorias");
		
		
		BlogSpaAnalyzer analyzer = new BlogSpaAnalyzer(Version.LUCENE_CURRENT);
		Property type=ontology.createProperty(rdf,"type");
		Directory indexDirectory = FSDirectory.open(new File(".\\resources\\index"));
		IndexWriter iwriter = new IndexWriter(indexDirectory, analyzer, true, new IndexWriter.MaxFieldLength(25000));
		String name = null;	
		String sText = null;
		String sTextSubClasses = null;
		for(Resource category: urlCategorias){
			
			
			////PRIMERA PARTE-->>Recursos de DbPedia
			String pathCategory=category.getURI();
			
			List<Resource> lis=DbPedia.getResourcesOfType(pathCategory);
			System.out.println("lis tiene "+lis.size());
			
			for(Resource resource : lis){
				//if(resource.getLocalName()!=null){
					//name=resource.getLocalName();
				//}else{
			//	System.out.print("asi "+resource.getLocalName());
					String url=resource.getURI();
				//DbPedia.getSpaLabel(rb.getURI());
				 name =Encode.getNameFromUrl(url);
			//	}
				
				System.out.print(name);
				sText=name+" "+sText;
			}
			
			///SEGUNDA PARTE--->Texto de WIkipedia

			String sTextWikipedia= WikipediaText.GetTextFromWikipedia(category.getLocalName());
			Set<String> oSyn = Synonym.lookupSynonyms(category.getLocalName());
			 for(String sWord:oSyn){
				 try{
					 String sTextWikipediaSynonym = WikipediaText.GetTextFromWikipedia(sWord);
					 sTextWikipedia += " " + sTextWikipediaSynonym;
				 }
				 catch(Exception e){
					 
				 
				 }
			 }
			
			 String sTotalText = sText + " " + sTextSubClasses + " " + sTextWikipedia;
			
			///ANALIZADORRRRRR

			System.out.println("Contenido ficheros: "+ name);
		
			//SE RECORREN TODOS LOS FICHEROS DE UNA CATEGORIA
				//SE A�ADEN A CADA DOC=CATEGORIA
			Document categoria = new Document();
			categoria.add( new Field("CategoryName", name, Field.Store.YES,Field.Index.ANALYZED));
			categoria.add( new Field("CategoryText", sTotalText, Field.Store.YES,Field.Index.ANALYZED)); 
			
			iwriter.addDocument(categoria);
		}

		iwriter.close();
		

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