package CategoryGenerator;




import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

import dao.DAO_Model;

import Analizer.BlogSpaAnalyzer;


public class CategoryGerenator {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws CorruptIndexException, IOException, ParseException {
		// TODO Auto-generated method stub
		
		  
		  Model ontology=DAO_Model.generateMainModel();
		  
		
		
		
		   BlogSpaAnalyzer analyzer = new BlogSpaAnalyzer(Version.LUCENE_CURRENT);
		   
		    // Store the index in memory:
		    // Directory directory = new RAMDirectory();
		    // To store an index on disk, use this instead:
		    Directory indexDirectory = FSDirectory.open(new File(".\\resources\\index"));
		 
		   
		    IndexWriter iwriter = new IndexWriter(indexDirectory, analyzer, true, new IndexWriter.MaxFieldLength(25000));
		    
		    //SE ABRE LA CARPETA
		    File directorio=new File("./text_example");
			String[] ListaCategorias=directorio.list();
			  
			//SE RECORRE CADA CATEGORIA=CARPETA
		    for(int i=0;i<ListaCategorias.length;i++){
			   ////1DOC POR CATEGORIA
		      File carpeta=new File("./text_example/"+ListaCategorias[i]);
		      String[] ListaFicheros=carpeta.list();
		      System.out.println("Contenido ficheros: "+ ListaCategorias[i]);
			  Document categoria = new Document();
			  //SE RECORREN TODOS LOS FICHEROS DE UNA CATEGORIA
		       for(int j=0;j<ListaFicheros.length;j++){
		    	   FileReader file=null;
			       System.out.println("Contenido ficheros: "+ ListaFicheros[j]);
			       if(ListaFicheros[j].endsWith("htm")){	
			    	    file= new FileReader("./text_example/"+ListaCategorias[i]+"/"+ListaFicheros[j]);
			    	    BufferedReader reader= new BufferedReader(file);   
			            String line = null;
			            String text = "This is the text to be indexed.";
			            try{
				            while ((line = reader.readLine()) != null) {
				                //Process the data, here we just print it out
				            	  text=text+line;
				            }
			            }catch(Exception e){
			            	System.out.print("error lectura");
			            }
					      
					       //SE A�ADEN A CADA DOC=CATEGORIA
			            
			            categoria.add( new Field("fieldname", text, Field.Store.YES,Field.Index.ANALYZED)); 
			       	}
			       
			       
		        }
		   
		    iwriter.addDocument(categoria);
		    }
		 
		    iwriter.close();
		   		    
		    
	}

}
