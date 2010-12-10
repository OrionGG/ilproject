
package Analizer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


import DBLayer.DAOUrl;
import GetBlogText.ExtractText;
import Spider.GetSubUrls;

public class Categorizator {


	public static void main(String[] args) throws Exception {
		if (args.length > 1){
			String sOption = args[0].toString();
			String sUrl = args[1].toString();
			if(sOption.equals("-u")){
				String sText = ExtractText.GetBlogText(sUrl);
				DAOUrl oDAOUrl = new DAOUrl();
				oDAOUrl.insertOrUpdateUrl(sUrl, sText);
				similarityFunction(sText);
			}
			else if(sOption.equals("-l")){
				List<String> oList = GetSubUrls.DefaultSpiderUrl(sUrl);
				for(String sSubUrl : oList){
					String sText = ExtractText.GetBlogText(sSubUrl);
					DAOUrl oDAOUrl = new DAOUrl();
					oDAOUrl.insertOrUpdateUrl(sSubUrl, sText);
					similarityFunction(sText);
				
				}
			}
		}
	
	}
	
	public static void similarityFunction(String parseo) throws CorruptIndexException, IOException, ParseException{
		 Directory indexDirectory = FSDirectory.open(new File(".\\resources\\index"));
		// Now search the index:
	    IndexSearcher isearcher = new IndexSearcher(indexDirectory, true); // read-only=true
	    // Parse a simple query that searches for "text":
	    BlogSpaAnalyzer analyzer = new BlogSpaAnalyzer(Version.LUCENE_CURRENT);
	    QueryParser parser = new QueryParser(Version.LUCENE_CURRENT,"fieldname", analyzer);
	   // Term term=new Term("baile");
	   
	    Query query = parser.parse(parseo);
	  
	
	    ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs; 
	    
	    
	    //////FUNCIONALIDADES POSIBLES PARA LEER EL INDICE
	    //System.out.println("El termino -"+term+"- aparece "+isearcher.docFreq(term)+" veces");
	    System.out.println("Tras ejecutar la query " + hits.length+ " encontrados para");
	    // Iterate through the results:
	    for (int i = 0; i < hits.length; i++) {
	      /////TE DICE EN QUE FICHERO SE ENCUENTRA EL TERM
	      Document hitDoc = isearcher.doc(hits[i].doc);
	      System.out.println(parseo+" ->Esta en Documento "+hitDoc.hashCode());
	     // System.out.println(hitDoc.toString());
	      System.out.println("En el texto "+hitDoc.get("fieldname"));
	      
	      //assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
	    }
	    isearcher.close();
	    indexDirectory.close();
	}

}
