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


import Analizer.BlogSpaAnalyzer;
import GetBlogText.WikipediaText;


public class CategoryGerenator {

	/**
	 * @param args
	 * @throws Exception 
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub


		Model ontology=DAO_Model.generateMainModel();
		ArrayList<Resource> urlCategorias=getCategories(ontology);
		System.out.println("Fin categorias");
		
		
		BlogSpaAnalyzer analyzer = new BlogSpaAnalyzer(Version.LUCENE_CURRENT);
		Directory indexDirectory = FSDirectory.open(new File(".\\resources\\index"));
		IndexWriter iwriter = new IndexWriter(indexDirectory, analyzer, true, new IndexWriter.MaxFieldLength(25000));
		for(Resource resource: urlCategorias){
			String sText=getResourcesCategorias(resource.getURI());
			String sTextSubClasses=getSubClassesCategorias(resource.getURI());

			String sTextWikipedia= WikipediaText.GetTextFromWikipedia(resource.getLocalName());
			Set<String> oSyn = Synonym.lookupSynonyms(resource.getLocalName());
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

			System.out.println("Contenido ficheros: "+ resource.getLocalName());
		
			//SE RECORREN TODOS LOS FICHEROS DE UNA CATEGORIA
				//SE A�ADEN A CADA DOC=CATEGORIA
			Document categoria = new Document();
			categoria.add( new Field("CategoryName", resource.getLocalName(), Field.Store.YES,Field.Index.ANALYZED));
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

	private static ArrayList<Resource> getCategories(Model model) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
		String rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns/#";
		Property subClassOf = model.createProperty(rdfs,"subClassOf");
		Property label = model.createProperty(rdfs,"label");
		Property type=model.createProperty(rdf,"type");
		//model.write(System.out);

		// List everyone in the model who has a child:
		ResIterator parents = model.listSubjectsWithProperty(subClassOf);
		model.listObjects();
		// Because subjects of statements are Resources, the method returned a ResIterator
		System.out.println("Entra subjects categorias");
		/*while (parents.hasNext()) {

		  // ResIterator has a typed nextResource() method
		  Resource object = parents.nextResource();

		  // Print the URI of the resource
		  System.out.println(object.getLocalName());
		}*/

		// Can also find all the parents by getting the objects of all "childOf" statements
		// Objects of statements could be Resources or literals, so the Iterator returned
		// contains RDFNodes
		NodeIterator moreParents = model.listObjectsOfProperty(subClassOf);

		// To find all the siblings of a specific person, the model itself can be queried 
		//NodeIterator siblings = model.listObjectsOfProperty(edward, siblingOf);
		System.out.println("\n\n\nEntra objects categorias");

		//  po: http://purl.org/ontology/po/
		//	http://www.w3.org/2002/07/owl# rdf: http://www.w3.org/1999/02/22-rdf-syntax-ns/#
		// skos http://www.w3.org/TR/skos-reference/skos.html# rdfs http://www.w3.org/2000/01/rdf-schema#
		ArrayList<Resource> urlCategorias=new ArrayList<Resource>();
		while (moreParents.hasNext()) {

			// ResIterator has a typed nextResource() method
			Resource object = (Resource) moreParents.nextNode();
			System.out.println("\n\n"+object.getURI()+":\n\n");
			urlCategorias.add(object);
			if(!(object.getURI().equals("http://dbpedia.org/ontology/Eukaryote"))){
				Model resourceModel=DbPedia.getRelatedModelFull(object.getURI());

				//System.out.print(resourceModel.getRequiredProperty(object, label).getObject());
				//System.out.println(object.getRequiredProperty(label).toString());
				Resource res=null;

				NodeIterator ni=resourceModel.listObjectsOfProperty(type);
				//System.out.print(object.getURI());
				while(ni.hasNext()){
					RDFNode node=ni.next();
					if(node.isResource()){
						Resource r=(Resource)node;

						System.out.print("/ Type = "+r.getLocalName());
					}




				}
				NodeIterator ni1=resourceModel.listObjectsOfProperty(subClassOf);
				//System.out.print(object.getURI());
				while(ni1.hasNext()){
					RDFNode node=ni1.next();

					System.out.print("/ LABEL = "+node.toString());


				}

			}

			// But it's more elegant to ask the Resource directly
			// This method yields an iterator over Statements
			//StmtIterator moreSiblings = edward.listProperties(siblingOf);



		}
		return urlCategorias;
	}
}