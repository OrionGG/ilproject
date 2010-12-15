package dominio;

import java.io.UnsupportedEncodingException;

import com.hp.hpl.jena.query.ResultSet;

import dao.DAO_Sparql;


public class DbTune {

	public static void getJamendoInfo(String nameArtist) {
		// TODO Auto-generated method stub
		
		//Album a= new Album();
		
		//BUSQUEDA COMPLETA
		String path ="<http://dbtune.org/jamendo/artist/";
		String mbid= getArtistId(nameArtist);
		
		path=path+mbid+">";
		String serviceEndpoint="http://dbtune.org/jamendo/sparql";
		//busquedaCompleta(ruta,serviceEndpoint);
	
	     //BUSQUEDA DE UNA QUERY-->wikilink almacenados en BBD como puntos a favor de un recurso
		String qsInfoJamendo = "SELECT * WHERE {"+path+" ?p ?q}";
		
		ResultSet rs0=DAO_Sparql.selectQuery(path,serviceEndpoint,qsInfoJamendo);
	    System.out.println("Numero de filas: "+rs0.getRowNumber());
	}

	
	
	private static String getArtistId(String nameArtist) {
		// TODO Auto-generated method stub
		String webArtist="http://www.jamendo.com/get/artist/name/artist/id/json/";
		webArtist=webArtist+nameArtist;
		
		return null;
	}
	
	public static void getBBCInfo(String nameArtist,String type) {
    	
    	
    	//BUSQUEDA COMPLETA	
    	//NEED TO ENCODE TO CORRECTLY CONCATENATE THE STRINGS TO GENERATE THE URL
		String path ="<http://dbtune.org/bbc/playcount/b006wk6f_1>";
		//http://dbtune.org/bbc/playcount/<id>_<k>

		
		
		nameArtist="U2";
		
	   String serviceEndpoint="http://dbtune.org/bbc/playcount/sparql";
	   String serviceEndpoint2="http://dbtune.org:3062/sparql";
		//busquedaCompleta(ruta,serviceEndpoint);
	   String qsInfoBBC="SELECT ?brand ?title ?count WHERE {?artist rdf:type mo:MusicArtist; foaf:name \""+ nameArtist+"\". ?pc pc:object ?artist; pc:count ?count.	?brand rdf:type po:Brand; pc:playcount ?pc;  dc:title ?title FILTER (?count>10)}";
	   String qsInfoBBC2="SELECT * WHERE {?artist a mo:MusicArtist;}";
	   String qsInfoBBC3="SELECT ?uri ?label  WHERE {?uri po:category  <http://www.bbc.co.uk/programmes/people/bmFtZS9ib25kLCBqYW1lcyAobm8gcXVhbGlmaWVyKQ#person> ; rdfs:label ?label   }";

		ResultSet rs0=DAO_Sparql.selectQuery(path,serviceEndpoint,qsInfoBBC3);
	    
	    System.out.println("Numero de filas: "+rs0.getRowNumber());
	    
	   // int i=0;
	  // ResultSet rs1=null;
	    
	    //EN CASO DE QUE NO SE ENCUENTREN RESPUESTAS, HABARA QUE BUSCAR POSIBLES REDIRECCIONAMIENTOS DE DBPEDIA
	    if(rs0.getRowNumber()==1)
	    {
	    	
	    	//redirects
	        System.out.println("Busqueda en BBC sobre artista vacia \n");
	    
	    
		 }
		    		
    }
	

}
