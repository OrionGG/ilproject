package jena;

 //package src;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.musicbrainz.JMBWSException;
import org.musicbrainz.model.Artist;

//import servlets.DAO;
/*
import DAO_MusicBrainz;
import DbPedia;
import UserMusic;
*/
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.sparql.pfunction.PropertyFunction;

import dao.*;
import dominio.*;
import encoders.*;


public abstract class Jenate implements PropertyFunction {
    /**
     * @param listFinalInterestedResources 
     * @param args
     * @return 
     * @throws IOException 
     * @throws JMBWSException 
     * @throws ClassNotFoundException 
     * @throws SQLException 
     * 
     */
	

	
    public static Model main(List <String>listInterestedResources,List <Artist>listInterestedArtists, List<String> listFinalInterestedResources) throws IOException, JMBWSException, SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
            ///Now you got a model 
       		// Obtenemos un Iterador y recorremos la lista de MBInterested artist.
       	//Now you need a list of words related with the first word
    	
    	int i=0;
    	String pathResource;
       	Model model=ModelFactory.createDefaultModel();
    	
    	System.out.println("COMIENZO: El modelo tiene "+model.size()+" tuplas Hora= "+new Date());
    	System.out.println("Tamaño de list resources es "+ listInterestedResources.size());

    	//Get list RESOURCES
       	while (i<listInterestedResources.size())
	 	{ 
       		System.out.println(listInterestedResources.get(i).toString());
       		if(listInterestedResources.get(i)!=null ){
       			pathResource=listInterestedResources.get(i);
       			if(!(pathResource.startsWith("http://"))){
	       			//pathResource=getDbPediaPathResource(listInterestedResources.get(i));
	       		 	System.out.println("\nBusqueda en DbPedia de la url del recurso "+listInterestedResources.get(i)+"-->");
	       			pathResource=DbPedia.getDbpediaUrlResource(listInterestedResources.get(i));
	       			System.out.println("\nUrl del resource="+pathResource);
	       		}else{
	       			pathResource="<"+pathResource+">";
	       		}
       				if(pathResource !=null){
       					model=MyModel.fillModel(pathResource,model);
	       				listFinalInterestedResources.add(pathResource.replace("<","").replace(">", ""));
	       			}
       		}
	  		i+=1;
	 	}
    	
       	System.out.println("DESPUES DE RESPORCES: El modelo tiene "+model.size()+" tuplas");
       	//get list of ARTIST
       	i=0;
	  	while (i<listInterestedArtists.size())
	 	{ 	
	  		System.out.println(listInterestedArtists.get(i).getName().toString());
	  		pathResource=getDbPediaPathArtist(listInterestedArtists.get(i));
	  		if(pathResource!=null){
	  			listFinalInterestedResources.add(pathResource.replace("<","").replace(">", ""));
	  			model=MyModel.fillModel(pathResource,model);
	  		}
	  		i+=1;
	    }
    	System.out.println("DESPUES DE ARTIST: El modelo tiene "+model.size()+" tuplas ");
    	
   
    	//DAO_Model.printStatements(model);
    	
    	//TREATMENT WITH THE MODEL, GET MORE INFO IN A NEW MODEL
    	//Where the model is desarrollated and oriented by me
    	int prof =0;
    	while(prof<Algorithym.getProfundity()){
    		prof++;
    		model=Algorithym.developModel(model,listInterestedResources,listInterestedArtists);
    		System.out.println("DESPUES DE DEVELOP DE "+prof+" NIVEL: El modelo tiene "+model.size()+" tuplas");
    	}
    	listInterestedResources=null;
	  	return model;
}
    
    
    

	private static String getDbPediaPathArtist(Artist newArtist) throws UnsupportedEncodingException, JMBWSException {
		// TODO Auto-generated method stub	

	 		String pathResource;
 			//Get the artist from the arraylist
 			String nameArtist=newArtist.getName();
 			System.out.println("\nBusqueda en MusicBrainz para devolver el tipo de artista "+nameArtist+"... \n");
 			Artist a= getMBInfo(nameArtist);
 			if(a==null || a.getName().equals("null")){
 				System.out.println("\nBusqueda en DbPedia de la url del recurso(No es un artista MB)-->");
 				pathResource=DbPedia.getDbpediaUrlResource(nameArtist);
 			}
 			else{
 				System.out.println("\nBusqueda en DbPedia de la url del artista-->");
 				
				pathResource=DbPedia.getDbpediaUrlArtist(a.getName(),a.getType());
				System.out.println("\nUrl del artista="+pathResource);
				//listResourcesRelatedTerms=DbPedia.getRelatedTermsAndSaveDB(pathResource);
 			}
 			// 	System.out.print("\n\nInfo del artista ="+a.getName()+"\n\n \n"); 
		  
			return pathResource;
}
	
			

	public static Artist getMBInfo(String nameArtist) throws JMBWSException{
    		DAO_MusicBrainz mb =new DAO_MusicBrainz();
    		mb.setUp();
    		Artist a=null;
    		a=mb.findArtist(nameArtist);
    		if(a.getName()!= null){
    				nameArtist=a.getName();
    		}else{
    			a.setName("null");
    		}
    		return a;
    		
		}
	
	public static Map<Integer, Collection<DbPediaResource>> addResources(String login, List<String> listInterestedResources,List <Artist>listInterestedArtists) throws IOException, JMBWSException, SQLException, ClassNotFoundException{
		
		Model model = Jenate.main(listInterestedResources,listInterestedArtists,null);
		Map<Integer, Collection<DbPediaResource>> newResourcesMap=MyModel.filterModelToMap(model,login);
		Map<Integer, Collection<DbPediaResource>> oldResourcesMap =MyMap.readMapFromDb(login);
		Map<Integer, Collection<DbPediaResource>> nextResourcesMap=null;
		nextResourcesMap=MyMap.addMaps(newResourcesMap,oldResourcesMap);
		
		return nextResourcesMap;
	}
	public static Map<Integer, Collection<DbPediaResource>> searchResources(String login, List<String> listInterestedResources,List <Artist>listInterestedArtists) throws IOException, JMBWSException, SQLException, ClassNotFoundException{
		
		Model model = Jenate.main(listInterestedResources,listInterestedArtists,null);
		Map<Integer, Collection<DbPediaResource>> newResourcesMap=MyModel.filterModelToMap(model,login);
		
		return newResourcesMap;
	}
	/*
	public static void addResourcesString(String login, ArrayList <String> addingNames,List <Artist>listInterestedArtists)) throws IOException, JMBWSException, SQLException, ClassNotFoundException{
		
		Model newResourcesModel = Jenate.main(addingNames,listInterestedArtists);
		Model<Integer, Collection<DbPediaResource>> oldUResourcesMap =MyMap.readMapFromDb(login);
		
		//fillPropertyValue(Model childModel, Model model,int newValue, List<Statement> statementsEliminar);
*/

}




































//Freebase	 
//String example="http://api.freebase.com/api/service/mqlread?query={%22query%22:{%22type%22:%22/music/artist%22,%22name%22:%22The%20Police%22,%22album%22:[]}}";
/*

System.out.println("\nBusqueda en BBC: \n");
DbTune.getBBCInfo(a.getName(),a.getType());
System.out.println("\n\n\nBusqueda en Jameedo: \n");
DbTune.getJamendoInfo(nameArtist);
*/
	
	
////System.out.println("\nBusqueda en Music brainz: \n");
////MusicBrainz.getMbInfo(a);





//System.out.println(r.toString());
//Artist a= new Artist();
//a=a.getArtist(nameArtist);
//String mbid=a.getId();


//System.out.println("MBID de "+nameArtist +" es: "+mbid);

//System.out.println("\n\n\nBusqueda en MusicBrainz: \n");
//MusicBrainz.getMusicBrainzInfo(nameArtist);




	/*
	
	
	   nameArtist=listInterestedArtists[i];
	   System.out.println(nameArtist + "\t");
	   i++;*/	





     /*   
  
		*/

	
/*
		public QueryIterator exec(QueryIterator arg0, PropFuncArg arg1,
				com.hp.hpl.jena.graph.Node arg2, PropFuncArg arg3,
				ExecutionContext arg4) {
			// TODO Auto-generated method stub
			return null;
		}
*/
		/*
		//BUSQUEDA DE UNA QUERY-->wikilink almacenados en BBD como puntos a favor de un recurso
	    String qs = "SELECT  ?o WHERE {"+ ruta+"?q <http://dbpedia.org/resource/Band>}";
		busquedaQuery(ruta,serviceEndpoint,qs);
		//BUSQUEDA DE UNA QUERY-->wikilink almacenados en BBD como puntos a favor de un recurso
	    String qs2 = "SELECT  ?o WHERE {"+ ruta+"?q <http://dbpedia.org/resource/Singer>}";
		busquedaQuery(ruta,serviceEndpoint,qs2);
		
		
		//BUSQUEDA DE UNA QUERY-->wikilink almacenados en BBD como puntos a favor de un recurso
		String qs6 = "SELECT  ?o WHERE {"+ ruta+"<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://dbpedia.org/ontology/Band>}";
		busquedaQuery(ruta,serviceEndpoint,qs6);
	*/		

        /*
      
      ResultSet rs2copia=rs2;
      //ResultSetFormatter.out(rs2copia);
      List lista;
      while(rs2.hasNext()){
        	QuerySolution q = rs2.next();
        	//System.out.println(q.toString());
        	System.out.println(q.getResource("?o"));
        	/*lista=new ArrayList();
        	lista.add(q.getResource("?o"));*/
        	//System.out.println(q.getLiteral("?o"));
       
       
        	//System.out.print("\n\n"+ lista.toString());
	 	    // if (((Iterator) lista).hasNext()) {
	      	        //while (((Iterator) lista).hasNext()) {
	    /*  	          ruta="<"+q.getResource("?o")+">";
	      	          //Preparo la segunda query con solo alguna inf
	      	          PropertyFunctionRegistry.get().put("<http://dbpedia.org/resource/kakadela>", jenate.class) ;
	      	          String prologue3 = "PREFIX db-ont:"+ ruta +"\n PREFIX owl: <http://www.w3.org/2002/07/owl#>\n PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
	      	          String qs3 = prologue3+"SELECT ?q ?o WHERE {"+ ruta +" ?q  ?o}";
	      	        
	      	       	 Query query3 = QueryFactory.create(qs3);
	      	         System.out.print("Comienza\n");
	      	         //Model model2 = make();
	      	         //Funciona con service end point accede a dbpedia
	      	         QueryExecution qExec3 = QueryExecutionFactory.sparqlService(serviceEndpoint, query3);
	      	         ResultSet rs3;
	      	         try {
	      	   
	      	         System.out.print("Resultados:\n");
	      	      	//model=qExec.execConstruct();
	      	          rs3 = qExec3.execSelect();
	      	          ResultSetFormatter.out(rs3);
	      	          } finally { qExec3.close() ; }
	      	        //rs2.next();
 	    
	 	  */   

        //COMENTARIOS CON POSIBLES SOLUCIONES O VARIANTES:

    	/*
   	 //LEER DE UN FICHERO LOCAL
   	 String filename = "C:/Users/JORGE_GIL/Desktop/musicGroup.rdf";
       InputStream in = new FileInputStream(filename);
     	java.io.InputStream in = FileManager.get().open( "../Ficheros RDF/eswc-2006-09-21.rdf" );
     	if (in == null) {
     	    throw new IllegalArgumentException("Archivo no encontrado");
     	} 
     	m2.read(in,"");
     	*/
      
        // SELECCIONAR DEL MODELO EL RECURSO CON:John Smith vcard resource from the model
       // Resource vcard = m2.getResource("Bob Dylan");
       // System.out.println(vcard.getNameSpace());
       
        // retrieve the given name property
        //String fullName = vcard.getProperty(VCARD.FN).getString();
       

    	  /* 
    	   // SELECCIONAR DEL MODELO LOS RECURSOS CON:VCARD.FN property
    	    ResIterator iter = model.listResourcesWithProperty(VCARD.FN);
        if (iter.hasNext()) {
            System.out.println("The database contains vcards for:");
            while (iter.hasNext()) {
                System.out.println("  " + iter.nextResource()
                                              .getRequiredProperty(VCARD.FN)
                                              .getString() );
            }
        } else {
            System.out.println("No vcards were found in the database");
        }            */
    	
        //UNION DE MODELOS
        // read the RDF/XML files
        //model1.read(new InputStreamReader(in1), "");
        //model2.read(new InputStreamReader(in2), "");

        // merge the Models
        //Model model = model1.union(model2);

    	
        /*
         // create an empty model
         Model model = ModelFactory.createDefaultModel();
         // use the FileManager to find the input file
         InputStream in = FileManager.get().open( inputFileName );
        if (in == null) {
            throw new IllegalArgumentException(
                                         "File: " + inputFileName + " not found");
            }
        // read the RDF/XML file
        model.read(in, null);
        // write it to standard out
        model.write(System.out);
       */  
    	     	        //   and add the properties cascading style
    	        /*Resource johnSmith  = model.createResource(personURI)
    	             .addProperty(VCARD.FN, fullName)
    	             .addProperty(VCARD.N, 
    	                      model.createResource()
    	                           .addProperty(VCARD.Given, givenName)
    	                           .addProperty(VCARD.Family, familyName));*/
    	

 
    

