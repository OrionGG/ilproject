package dominio;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.musicbrainz.model.Artist;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.query.ResultSet;

import dao.DAO_Sparql;


public class MusicBrainz {
	
	
	
	static void getMusicBrainzInfo(String nameArtist)
    {
    	
    	//BUSQUEDA COMPLETA
		String path ="<http://dbtune.org/musicbrainz/resource/artist/";
		String mbid= getMBid(nameArtist);
		path=path+mbid+">";
		String serviceEndpoint="http://dbtune.org/musicbrainz/sparql";
		//busquedaCompleta(ruta,serviceEndpoint);
	
		
	     //BUSQUEDA DE UNA QUERY-->wikilink almacenados en BBD como puntos a favor de un recurso
		String qsInfoMusicBrainz = "SELECT * WHERE {"+path+" vocab:alias ?alias. OPTIONAL {"+path+" vocab:quality ?quality }. OPTIONAL {"+path+" dc:description ?decription}.OPTIONAL {"+path+"foaf:maker ?maker}.OPTIONAL {"+path+" dc:description ?decription}.}";
		ResultSet rs0=DAO_Sparql.selectQuery(path,serviceEndpoint,qsInfoMusicBrainz);
	    System.out.println("Numero de filas: "+rs0.getRowNumber());
	    
    	
    }
    
    
    
    
    
    
    
	    private static String getMBid(String nameArtist) {
		// TODO Auto-generated method stub
	    	
	    	//retorno mana por ahora
	    	//System.out.println("\n\n\n\n\n Empieza la busqueda en Music brainz: ");
	    	String rutaFicheroXML ="http://musicbrainz.org/ws/1/artist/?type=xml&name=";
			rutaFicheroXML=rutaFicheroXML+nameArtist;

	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = null;
			try {
				db = dbf.newDocumentBuilder();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        Document doc = null;
			try {
				doc = db.parse(rutaFicheroXML);
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        doc.getDocumentElement().normalize();
	      // System.out.println("Root element " + doc.getDocumentElement().getNodeName());
	        NodeList nodeLst = doc.getElementsByTagName("artist");
	        //System.out.println("nodeLst.item(0)"+nodeLst.getLength());
	        org.w3c.dom.Node fstNode =   nodeLst.item(0);
	        String mbidArtista = null;
	        if( nodeLst.getLength()!=0)
	        {
	        	//System.out.println(((Node) fstNode.item(0)).getNodeValue());
	        	
		        Element fstElmnt = (Element) fstNode;
		        mbidArtista=fstElmnt.getAttribute("id");
		        String nameArtista=fstElmnt.getAttribute("name");
		        System.out.println("\nId del artista: "+ mbidArtista + " Nombre: "+nameArtista);
	        }else{
	        	System.out.println("No se encuentra info de ese artista en musicbrainz");
	        }		        

		//return "55441123-89a2-49ab-ae3f-df7a3e86835a";--->MANÃ�
		
		return mbidArtista;
	}







		public static void getMbInfo(Artist a) {
			// TODO Auto-generated method stub
			
			System.out.println("Alias: "+a.getAliases()+" BeginDate: "+a.getBeginDate()+" End date: "+a.getEndDate()+ " Class"+a.getClass()+" RelationList"+a.getRelationList()+" Releases"+a.getReleases()+" UniqueName"+a.getUniqueName());
			
		}

}
