package dominio;

//package src;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.core.ResultBinding;


import dao.DAO_Sparql;




public class DbPedia {

private static final int PROF = 3;
private static String ruta;
private static String qsInfoDbpedia2 = "SELECT DISTINCT ?label ?labelOA ?genre ?genreOA ?hometown ?instruments  ?pastMembers ?members ?tipoRDF ?subject ?associatedBand ?associatedMusicalArtist ?associatedBand ?associatedActs  WHERE {{"+ruta+" p:label ?label} UNION{"+ruta+" oa:label ?labelOA {FILTER(?labelOA != ?label)}} " +
"UNION{"+ruta+" p:genre ?genre} UNION{"+ruta+" oa:genre ?genreOA}  UNION{"+ruta+" p:homeTown ?hometown} " +
"UNION{"+ruta+" oa:instrument ?instruments} UNION{"+ruta+" oa:pastMembers ?pastMembers}" +
		" UNION{"+ruta+" p:currentMembers ?members} UNION{"+ruta+" rdf:type ?tipoRDF} UNION{"+ruta+" skos:subject ?subject} " +
				"UNION{"+ruta+" oma:associatedBand ?associatedBand} UNION{"+ruta+" oma:associatedMusicalArtist ?associatedMusicalArtist} " +
						"UNION{"+ruta+" oma:associatedBand ?associatedBand {FILTER (?associatedBand != ?associatedMusicalArtist )}} " +
								"UNION{"+ruta+" owl:associatedActs ?associatedActs}}";

private static String serviceEndpoint="http://dbpedia.org/sparql";




//HACER VARIABLES GLOBALES CON LOS STRINGS DE LAS 2QUERYS DISTINTAS->PROBLEMA CON EL STATIC Y LA VARIBLE RUTA DE LOS STRING


@SuppressWarnings("null")
public static Model getRelatedModel(String pathResource) throws SQLException, ClassNotFoundException {

  
   String qsInfoDbpedia = "CONSTRUCT {"+pathResource+" ?property ?resource} WHERE {"+pathResource+ "?property ?resource}";
   //  UNION {?subject ?property "+pathResource+"}

   Model mo= DAO_Sparql.describeQuery(pathResource,serviceEndpoint,qsInfoDbpedia);	
   
	return mo;
    
}


public static Model getRelatedModelFull(String pathResource) {
	// TODO Auto-generated method stub
	  
	   if (pathResource.startsWith("<")&& pathResource.endsWith(">")){
		   pathResource.replace("<", "");
		   pathResource.replace(">", "");
	   }
	   String qsInfoDbpedia = "SELECT DISTINCT * WHERE {{<"+pathResource+"> ?property ?resource} UNION{?resource ?property <"+pathResource+">}}";
	  
	   ResultSet rs=DAO_Sparql.selectQuery(pathResource,serviceEndpoint,qsInfoDbpedia);	
	  // System.out.print("RS tiene "+rs.getRowNumber());
	   Model mo =null;
	   if(rs!=null){
		   mo= ResultSetFormatter.toModel(rs);
	   }
	 return mo;
	    
}






public static ArrayList<DbPediaResource> getInfoResource(ArrayList<DbPediaResource> urls) {
	// TODO Auto-generated method stub
	ArrayList<DbPediaResource> urls2=new ArrayList<DbPediaResource> ();
	
	int i=0;
	System.out.println("urls tiene "+urls.size());
	while(urls.size()>i){
		
		DbPediaResource dp=urls.get(i);
		
		if(dp!=null){
			String pathResource=dp.getUri();
			String qsInfoDbpedia = "SELECT ?photo ?type WHERE {<"+pathResource+"> a ?type OPTIONAL{<"+pathResource+"> <http://dbpedia.org/ontology/thumbnail> ?photo}}ORDER BY ASC(?type) LIMIT 2";
			List<ResultBinding> lis=DAO_Sparql.selectQueryToList(pathResource,serviceEndpoint,qsInfoDbpedia);	
			ArrayList <String> types=new ArrayList <String>();
			//
		  	if (lis.size()>0)
		  	{
		  		System.out.println("lis tiene "+lis.size());
		  		ResultBinding q = lis.get(0);
			  	
			  	if(q.getResource("?photo")!=null){
					dp.setImage(q.getResource("?photo").getURI());
					System.out.println(q.getResource("?photo").getURI());
			  	}
				if(q.getResource("?type")!=null){	
					types.add(q.getResource("?type").getURI());
					System.out.println(q.getResource("?type").getURI());
				}
				  	
				//urls.remove(dp);
				dp.setTypes(types);
				urls2.add(dp);
			}
		}
		
		i++;
	}
	System.out.print("Fin del array urls, que tiene "+i+ "elementos" );
		
	 
	return urls2;
}

public static DbPediaResource getBasicInfo(String pathResource) {
	// TODO Auto-generated method stub
	DbPediaResource dp=new DbPediaResource(pathResource);
		String qsInfoDbpedia = "SELECT DISTINCT ?name ?photo ?type WHERE {<"+pathResource+"> <http://www.w3.org/2000/01/rdf-schema#label> ?name OPTIONAL{<"+pathResource+"> a ?type} OPTIONAL{<"+pathResource+"> <http://dbpedia.org/ontology/thumbnail> ?photo} OPTIONAL{<"+pathResource+"> <http://xmlns.com/foaf/0.1/name> ?name} }ORDER BY ASC(?type) LIMIT 10";
	
	List<ResultBinding> lis=DAO_Sparql.selectQueryToList(pathResource,serviceEndpoint,qsInfoDbpedia);	
	ArrayList <String> types=new ArrayList <String>();
	if (lis.size()>0)
  	{
		int i =0;
  		//System.out.println("lis tiene "+lis.size());
  		String ant=null;
  		
  		while(i<lis.size()){
  			ResultBinding q = lis.get(i);
  			i++;
  	  	
  	  		if(q.getLiteral("?name")!= null && dp.getName()==null && (q.getLiteral("?name").toString().endsWith("@es") || q.getLiteral("?name").toString().endsWith("@en"))){
			dp.setName(q.getLiteral("?name").toString());
			System.out.println(q.getLiteral("?name").toString());
  	  		}
  	  		//OPTIONAL{<"+pathResource+"> <http://xmlns.com/foaf/0.1/name> ?name}
			if( q.getResource("?photo")!=null && dp.getImage().equals("img/resource.jpg")){
				dp.setImage(q.getResource("?photo").getURI());
				System.out.println(q.getResource("?photo").getURI());
		  	}

			if(q.getResource("?type")!=null && !(q.getResource("?type").getURI().equals(ant))){	
				types.add(q.getResource("?type").getURI());
				ant=q.getResource("?type").getURI();
				System.out.println(q.getResource("?type").getURI());
			}
 		}
  		if(types.size()==0 || types==null){
  			System.out.println("DbPedia: El array types estas vacio");
  		}else{
  			dp.setTypes(types);
  		}
  
  	}

	return dp;
}


public static DbPediaResource getMoreInfo(String pathResource) {
	// TODO Auto-generated method stub
	DbPediaResource dp=new DbPediaResource(pathResource);
	
	
	String serviceEndpoint="http://dbpedia.org/sparql";
	//rdfs:comment foaf:page  foaf:givename 
	String qsInfoDbpedia = "SELECT DISTINCT ?name ?photo ?photoCol ?type ?abstract ?reference WHERE {<"+pathResource+"> <http://dbpedia.org/property/hasPhotoCollection> ?photoCol OPTIONAL{<"+pathResource+"> a ?type} OPTIONAL{<"+pathResource+"> <http://dbpedia.org/ontology/abstract> ?abstract} OPTIONAL{<"+pathResource+"> <http://www.w3.org/2000/01/rdf-schema#label> ?name} OPTIONAL {<"+pathResource+"> <http://dbpedia.org/property/reference> ?reference} }ORDER BY ASC(?type)";
	List<ResultBinding> lis=DAO_Sparql.selectQueryToList(pathResource,serviceEndpoint,qsInfoDbpedia);	
	ArrayList <String> types=new ArrayList <String>();
	ArrayList <String> references=new ArrayList <String>();
	try{
	if (lis.size()>0)
  	{
  		System.out.println("lis tiene "+lis.size());
  		ResultBinding q = lis.get(0);
  		
  		/*if(q.getResource("?photo")!=null){
			dp.setImage(q.getResource("?photo").getURI());
			System.out.println(q.getResource("?photo").getURI());
	  	}*/
  		if(q.getResource("?photoCol").getURI()!=null){
			dp.setPhotoCol(q.getResource("?photoCol").getURI());
			System.out.println(q.getResource("?photoCol").getURI());
	  	}
  		if(q.getResource("?type")!=null){	
  			types.add(q.getResource("?type").getURI());
  			System.out.println(q.getResource("?type").getURI());
		}
  		if(q.getLiteral("?abstract")!=null ){
  			if(q.getLiteral("?abstract").toString().endsWith("@es") || q.getLiteral("?abstract").toString().endsWith("@en")){
			dp.setAbstract(q.getLiteral("?abstract").toString());
			System.out.println(q.getLiteral("?abstract").toString());
  			}
	  	}
  		
  		if(q.getLiteral("?name")!= null && dp.getName()==null && (q.getLiteral("?name").toString().endsWith("@es") || q.getLiteral("?name").toString().endsWith("@en"))){
			dp.setName(q.getLiteral("?name").toString());
			System.out.println(q.getLiteral("?name").toString());
  	  		}
  		
  		if(q.getResource("?reference")!=null){	
  			references.add(q.getResource("?reference").getURI());
			System.out.println(q.getResource("?reference").getURI());
		}

  		
  	}   
		if(types.size()==0 || types==null){
  			System.out.print("DbPedia: El array types estas vacio");
  		}else{
  			dp.setTypes(types);
  		}
	dp.setReferences(references);
  	//dp.setTypes(types);
	}catch(Exception e){
  		System.out.print("Error dbpedia: getting more info"+e.toString());
  		//	System.out.println(q.getResource("?name").toString()+" "+q.getResource("?name").getLocalName()+" "+q.getResource("?name").getURI());
  	}
	
	return dp;
}


public static String getDbpediaUrlResource(String newResource) throws UnsupportedEncodingException {
	// TODO Auto-generated method stub
	
	String path;
	String ruta ="<http://dbpedia.org/resource/";
	System.out.print("Recibe dbpedia get urlresource un "+newResource);
	newResource=parsearDbpedia(newResource);
	System.out.print("Genera dbpedia get urlresource un "+newResource);
	String resource=URLEncoder.encode(newResource,"UTF-8" );
	System.out.print("Genera dbpedia get urlresource un "+newResource);
	
	ruta=ruta+resource+">";
	path=ruta;
	
	
     //BUSQUEDA DE UNA QUERY-->wikilink almacenados en BBD como puntos a favor de un recurso
	
	String qsInfoDbpedia8 = "SELECT DISTINCT * WHERE { {"+ruta+" p:label ?label}}";
	
	
	ResultSet rs0=DAO_Sparql.selectQuery(path,serviceEndpoint,qsInfoDbpedia8);
	if(rs0.getRowNumber()==1)
    {
		//NOT FOUNDED
		return null;
    }
	else{
		return ruta;
	}
	
}

	//@SuppressWarnings("deprecation")
public static String getDbpediaUrlArtist(String nombreArtista,String type) throws UnsupportedEncodingException{
    	nombreArtista=parsearDbpedia(nombreArtista);
    	String path=null;
    	if(nombreArtista!=null){
    	
    	//BUSQUEDA COMPLETA	
    	//NEED TO ENCODE TO CORRECTLY CONCATENATE THE STRINGS TO GENERATE THE URL
		String ruta ="<http://dbpedia.org/resource/";
		//encode the url
		//nombreArtista=URLEncoder.encode(nombreArtista);
		
		URLEncoder.encode(nombreArtista,"UTF-8" );
		ruta=ruta+nombreArtista+">";

		path=ruta;
		
	
			
		String qsInfoDbpedia8 = "SELECT DISTINCT * WHERE { {"+ruta+" p:label ?label}}";

		ResultSet rs0=DAO_Sparql.selectQuery(path,serviceEndpoint,qsInfoDbpedia8);
	    
 
	    //EN CASO DE QUE NO SE ENCUENTREN RESPUESTAS, HABARA QUE BUSCAR POSIBLES REDIRECCIONAMIENTOS DE DBPEDIA
	    if(rs0.getRowNumber()==1)
	    {
	    	
	    	//redirects
	        ////System.out.println("Busqueda en dbpedia sobre artista vacia \n\tBuscamos un redireccionamiento en \n"+ruta);
	    	String qsRedireccionaminetos = "SELECT * WHERE {{"+ruta+" owl:sameAs ?sameAs} UNION{"+ruta+" p:redirect ?redirect}.}";
	    	ResultSet rs1;
	    	rs1=DAO_Sparql.selectQuery(ruta,serviceEndpoint,qsRedireccionaminetos);
		   		    
		    if((rs1.getRowNumber()==1)){
		    	
		    	//redirects
		        ruta ="<http://dbpedia.org/resource/";
		       
		        if(type.equalsIgnoreCase("http://musicbrainz.org/ns/mmd-1.0#Group")){
		        	ruta=ruta+nombreArtista+"_%28band%29>";
		        	URLEncoder.encode(ruta, "UTF-8");
		        	
		        ////Busqueda en dbpedia sobre artista vacia \n\tAÃ±ado _(Band) a ver si asi lo encuentra
		        		
		        }else{
		        	ruta=ruta+nombreArtista+"_%28musician%29>";
		        	URLEncoder.encode(ruta, "UTF-8");
		        }  

		    	path=ruta;
				ResultSet rs2;
		    	rs2=DAO_Sparql.selectQuery(ruta,serviceEndpoint,qsInfoDbpedia2);
		    
			    int numFilas2=rs2.getRowNumber();		
	    		//dbtune.org
			    if(numFilas2==1)
			    {
		    	//	System.out.println("\nEstamos trabajando para acceder a mÃ¡s informacion en la web 2.0 de otras paginas distintas a dbpedia.\n-->Debido a que en dbpedia no se encuentra nada\n");
		    		//System.out.println("Se mira en music brainz\n\n");
			    }
	    		
	    		
	    	}else
	    		//HEMOS ENCONTRADO REDIRECCIONAMIENTOS
	    	{
	    		redireccionamiento();
	    		//APAÃ‘O (DEBERIA FUNCIONAR AL DEVOLVER UN RESULT SET EN VEZ DE LIST)
	    		List l=DAO_Sparql.selectQueryList(ruta,serviceEndpoint,qsRedireccionaminetos);
	    		
		    	ResultBinding rv=(ResultBinding) l.get(0);
		    	Object varName;
		
				String sameAs="sameAs";
		    	int redireccionamiento=0;
		    	//System.out.println(rv.getResource("sameAs"));
		    	if(rv.getResource(sameAs)!=null)
		    		
		    	{
		    		redireccionamiento=1;
		    		ruta=rv.getResource("sameAs").getURI();
		    		
		    	}else 
		    		if(rv.getResource("redirect")!=null)
		    	{
		    		ruta=rv.getResource("redirect").getURI();
		    		redireccionamiento=1;
		    	}
		    	ResultSet rs2 = null;
		    	if (redireccionamiento==1)
		    	{
		    		ruta="<"+ruta+">";
		    		URLEncoder.encode(ruta, "UTF-8");

			    	rs2=DAO_Sparql.selectQuery(ruta,serviceEndpoint,qsInfoDbpedia2);
			    	path=ruta;
			   // System.out.println("Numero de lineas:" +rs2.getRowNumber());
		    	}
		    	
		    	  int numFilas3=1;
				  numFilas3=rs2.getRowNumber();
		    	  if((numFilas3==1)){
				    	
				    	//redirects
		    		  
				        
				        ruta ="<http://dbpedia.org/resource/";
				       // System.out.print(type + type.equalsIgnoreCase("http://musicbrainz.org/ns/mmd-1.0#Person"));
				        
				        if(type.equalsIgnoreCase("http://musicbrainz.org/ns/mmd-1.0#Group")){
				        	ruta=ruta+nombreArtista+"_%28band%29>";
				        	URLEncoder.encode(ruta, "UTF-8");
				       // 	System.out.println("Busqueda en dbpedia sobre artista vacia \n\tAÃ±ado _(Band) a ver si asi lo encuentra");
				        }else{
				        //	System.out.println("Busqueda en dbpedia sobre artista vacia \n\tAÃ±ado _(muscician) a ver si asi lo encuentra");
				        	ruta=ruta+nombreArtista+"_%28musician%29>";
				        	URLEncoder.encode(ruta, "UTF-8");
				        }  
				    	path=ruta;
						rs2=DAO_Sparql.selectQuery(ruta,serviceEndpoint,qsInfoDbpedia2);
				    
					    int numFilas2=rs2.getRowNumber();		
			    		//dbtune.org
					    if(numFilas2==1)
					    {
				    		System.out.println("\nEstamos trabajando para acceder a mÃ¡s informacion\n-->Debido a que en dbpedia no se encuentra nada\n");
				    		path="";
					    }
		    	  }
		    }
	    }
    	}
    	return path;
    }
    


    


private static void redireccionamiento() {
		// TODO Auto-generated method stub
		
	}


private static String parsearDbpediaArtist(String nombreArtista) {
		// TODO Auto-generated method stub
		if (nombreArtista.contains(" "))
		{
			//System.out.println("Tiene espacios");
			nombreArtista=nombreArtista.replace(" ", "_");
			//System.out.println(nombreArtista);
			
		}
		//nombreArtista.replaceFirst("\p{Lower}", "\p{Upper}");
		return nombreArtista;
}


private static String parsearDbpedia(String nameResource) {
	// TODO Auto-generated method stub

	if(nameResource!=null){
		//System.out.print(nameResource);

		nameResource = nameResource.toLowerCase();
		if(!nameResource.equals("")){
			nameResource=toTittleCase(nameResource,0);
			
			while(nameResource.contains(" ")){
				nameResource=nameResource.replace(" ", "_");
				int index=nameResource.indexOf("_");
				
				index+=1;
				//System.out.print(nameResource.length()+""+ index);
				if(nameResource.length() > index){
					nameResource=toTittleCase(nameResource,index);
					
				}
				
				System.out.print(nameResource);
			}
		}
	}

	return nameResource;
}


private static String toTittleCase(String str, int index) {
	// TODO Auto-generated method stub
	String strUp=str;
	strUp=strUp.toUpperCase();
	//System.out.print("convierte");
   //  str=str.replaceFirst(str.charAt(index), strUp.charAt(index)) ;
    char [] strChar=str.toCharArray();
    strChar[index]= strUp.charAt(index);
    //str=strChar.toString();
    str = String.valueOf(strChar);

	//System.out.println(str);
	return str;
	
}










/*

@SuppressWarnings("null")
public static List<Resource> getRelatedTermsAndSaveDB(String pathResource) throws SQLException, ClassNotFoundException {


   String serviceEndpoint="http://dbpedia.org/sparql";
	//busquedaCompleta(ruta,serviceEndpoint);
   System.out.print("ENCONTRADO EN DBPEDIA.\n\n Palabras relaccionadas en DBPEDIA con la palabra "+ pathResource+": \n");
			
   //GEnerar modelo para ese recurso principal
////	DAO_Model.storeResourceAsModel(pathResource);
	
     //BUSQUEDA DE UNA QUERY-->wikilink almacenados en BBD como puntos a favor de un recurso
	String qsInfoDbpedia = "SELECT * WHERE {"+pathResource+ "?property ?resource }";
	
	List <Resource>arrayRelatedTerms= new ArrayList();
	
	TreeMap <Resource,Integer>mapRelatedTerms = new TreeMap();
	
	List l0=DAO_Sparql.selectQueryToList(pathResource,serviceEndpoint,qsInfoDbpedia);
	
	
	//System.out.println("Hay lineas = "+l0.size());
	if (l0.size()>1)
	{
		int v=0;
			int i=0;
			////Preparo base de datos para introducir datos
			DAO_MySQL.setUp();
	
			while(l0.size()>i)
		    {
		       	QuerySolution q = (QuerySolution)l0.get(i);
		       			Resource r=null;
			       		Resource r2 = null;
			       		String resource=null;
			       		String resourceName=null;
			       		String urlResource=null;
			       		String property=null;
			       		String urlProperty=null;
		    	RDFNode rdf=q.get("?resource");

		    	////Resource r=model.createResource(personURI).addProperty();
		    	int valueResource=1;
		       	if(rdf.isResource() )
		       	{		 
		       		//System.out.println("http://www.w3.org/2002/07/owl#Class".equals(q.getResource("?resource").toString()));
	       			if (!(q.getResource("?resource").toString().startsWith("http")))
	       			{
	       			//	(("http://www.w3.org/2002/07/owl#Class".equals(q.getResource("?resource").toString())) ||
	       			}
	       			else
	       			{	
	       				
	       				    		    
	       				////INNECESARY(OLD METHOD)WITHOUT USING MODELS//////////////////////////////////////////////////////
		       		
	       				r=q.getResource("?resource");
	       				////System.out.print(q.getResource("?resource")+"\n");
	       				//Put inside the array all the resources that are not in the array..even if they are already in the db
	       				resource = r.getLocalName();
	       				//if(mapRelatedTerms.containsKey(r))
	       				if(arrayRelatedTerms.contains(r))
	       				{	       					
	       					//valueResource=mapRelatedTerms.get(r);
	       					valueResource=valueResource+1;
	       					//System.out.print("cha cha");
	       					//mapRelatedTerms.put(r,valueResource);
	       					
	       				}else{
	       					arrayRelatedTerms.add(r);
	       					System.out.print("cha cha");
		       				//mapRelatedTerms.put(r,valueResource);
	       				}
	       				
	       				System.out.println("Recurso= "+q.getResource("?resource")+" Relacion: "+q.getResource("?property"));
	       				//r.getLocalName()+r.getURI()
	       				urlResource=r.getURI();
	       				RDFNode rdfProp=q.get("?property");
				       	if(rdfProp.isResource() )
				       	{
				 			r2=q.getResource("?property");
				       	}
			  			property=r2.getLocalName();;
		       			urlProperty=r2.getURI();
		       			
		       			if(r.getLocalName().length()>200)
		       			{
		       				 resource ="no vale" ;
		       			}else
		       			{
		       				resource = r.getLocalName();
		       				
		       			}
		       			
		       			
		       			//fill the model first
	       				//DAO_Model.storeResourceInModel(pathResource,resource,urlResource,property,urlProperty);
	       				
	       			}
	       			
	       			
	       			int value=1;
	       			String user="cacatua";
	       				       			
	       			
	       			//CHECK IF THE RESOURCE IS GOING TO LOOK FOR IS ALREADY IN THE DB, IF SO IT WILL JUST REALTE THE RESOURCE WITH THE USER
	       			boolean storedResource;
	       			storedResource=readResource(pathResource);
	       			if(storedResource)
	       			{
	       				//asignUser_Resource(String resource, String urlResource,String property, String resource2, String property2,String urlProperty, int value, String user);
	       			}
	       			else
	       			{	
	       				////guarda en base de datos 
	       				DAO_MySQL.storeResource(resource,urlResource,property,pathResource);
	       			    //asignUser_Resource(String resource, String urlResource,String property, String resource2, String property2,String urlProperty, int value, String user);

	       			}
	       	   	}
		       	else{
		       		//System.out.println("Literal= "+q.getLiteral("?resource"));
		       	}
		       	i++;
		    	
			}	
			v++;
	}

    return arrayRelatedTerms;
	
	
}


*/


private static boolean readResource(String pathResource) {
	// TODO Auto-generated method stub
	return false;
}


@SuppressWarnings("null")
public static Model getRelatedTerms(String pathResource) throws SQLException, ClassNotFoundException {


 	//busquedaCompleta(ruta,serviceEndpoint);
   //System.out.print("ENCONTRADO EN DBPEDIA.\n\n Palabras relaccionadas en DBPEDIA con la palabra "+ pathResource+": \n");
			
   //GEnerar modelo para ese recurso principal
////	DAO_Model.storeResourceAsModel(pathResource);
	
     //BUSQUEDA DE UNA QUERY-->wikilink almacenados en BBD como puntos a favor de un recurso
	String qsInfoDbpedia = "SELECT * WHERE {"+pathResource+ "?property ?resource }";
	List <Resource>arrayRelatedTerms= new ArrayList<Resource>();
	
	TreeMap <Resource,Integer>mapRelatedTerms = new TreeMap<Resource, Integer>();
	
	Model mo=DAO_Sparql.describeQuery(pathResource,serviceEndpoint,qsInfoDbpedia);
	
	/*
	//System.out.println("Hay lineas = "+l0.size());
	if (mo.size()>1)
	{
		int v=0;
			int i=0;
			////Preparo base de datos para introducir datos
			DAO_MySQL.setUp();
	
			while(l0.size()>i)
		    {
		       	QuerySolution q = (QuerySolution)l0.get(i);
		       			Resource r=null;
			       		Resource r2 = null;
			       		String resource=null;
			       		String resourceName=null;
			       		String urlResource=null;
			       		String property=null;
			       		String urlProperty=null;
		    	RDFNode rdf=q.get("?resource");

		    	////Resource r=model.createResource(personURI).addProperty();
		    	int valueResource=1;
		       	if(rdf.isResource() )
		       	{		 
		       		//System.out.println("http://www.w3.org/2002/07/owl#Class".equals(q.getResource("?resource").toString()));
	       			if (!(q.getResource("?resource").toString().startsWith("http")))
	       			{
	       			//	(("http://www.w3.org/2002/07/owl#Class".equals(q.getResource("?resource").toString())) ||
	       			}
	       			else
	       			{	
	       				
	       				    		    
	       				////INNECESARY(OLD METHOD)WITHOUT USING MODELS//////////////////////////////////////////////////////
		       		
	       				r=q.getResource("?resource");
	       				////System.out.print(q.getResource("?resource")+"\n");
	       				//Put inside the array all the resources that are not in the array..even if they are already in the db
	       				resource = r.getLocalName();
	       				//if(mapRelatedTerms.containsKey(r))
	       				if(arrayRelatedTerms.contains(r))
	       				{	       					
	       					//valueResource=mapRelatedTerms.get(r);
	       					valueResource=valueResource+1;
	       					//System.out.print("cha cha");
	       					//mapRelatedTerms.put(r,valueResource);
	       					
	       				}else{
	       					arrayRelatedTerms.add(r);
	       					System.out.print("cha cha");
		       				//mapRelatedTerms.put(r,valueResource);
	       				}
	       				
	       				System.out.println("Recurso= "+q.getResource("?resource")+" Relacion: "+q.getResource("?property"));
	       				//r.getLocalName()+r.getURI()
	       				urlResource=r.getURI();
	       				RDFNode rdfProp=q.get("?property");
				       	if(rdfProp.isResource() )
				       	{
				 			r2=q.getResource("?property");
				       	}
			  			property=r2.getLocalName();;
		       			urlProperty=r2.getURI();
		       			
		       			if(r.getLocalName().length()>200)
		       			{
		       				 resource ="no vale" ;
		       			}else
		       			{
		       				resource = r.getLocalName();
		       				
		       			}
		       			
		       			
		       			//fill the model first
	       				//DAO_Model.storeResourceInModel(pathResource,resource,urlResource,property,urlProperty);
	       				
	       			}
	       			
	       			
	       			int value=1;
	       			String user="cacatua";
	       				       			
	       			
	       			//CHECK IF THE RESOURCE IS GOING TO LOOK FOR IS ALREADY IN THE DB, IF SO IT WILL JUST REALTE THE RESOURCE WITH THE USER
	       			boolean storedResource;
	       			storedResource=readResource(pathResource);
	       			if(storedResource)
	       			{
	       				//asignUser_Resource(String resource, String urlResource,String property, String resource2, String property2,String urlProperty, int value, String user);
	       			}
	       			else
	       			{	
	       				//GEnerar modelo para ese recurso principal
	       				//DAO_Model.generateModel(pathResource);
	       				///generate a model first
	       			//	DAO_Model.storeResourceInModel(resource,urlResource,property,pathResource,property,urlProperty,value,user);
	       				////guarda en base de datos 
	       				DAO_MySQL.storeResource1(resource,urlResource,property,pathResource,property,urlProperty,value,user);
	       			    //asignUser_Resource(String resource, String urlResource,String property, String resource2, String property2,String urlProperty, int value, String user);

	       			}
	       			*/
		       		/*
		    		Model m=rs0.getResourceModel();
		    		System.out.print(pathResource);
		    		//Resource r=m.getResource("label");*//*		      
		       	}
		       	else{
		       		//System.out.println("Literal= "+q.getLiteral("?resource"));
		       	}
		       	i++;
		    	
			}	
			v++;
	}
*/
  //  return arrayRelatedTerms;
	  return mo;
	
}
public static ArrayList<Resource> getResourcesFromPaths(
		Collection<Resource> collection) {
	// TODO Auto-generated method stub
	ArrayList<Resource> listFinalResources =new ArrayList<Resource> ();
	Iterator<Resource> it=collection.iterator();
	while(it.hasNext()){
		//Resource resource=new Resource(it.next());
		//listFinalResources.add(resource);
				
	}
	return null;
}
public static Collection<DbPediaResource> conversion2DbPediaResources(
		Collection<Resource> urls) {
	// TODO Auto-generated method stub
		Collection<DbPediaResource> urlsDbPedia = null;
	  	Iterator<Resource> it=urls.iterator();
	  	while(it.hasNext()){
	  		DbPediaResource r=(DbPediaResource) it.next();
	  		urlsDbPedia.add(r);
	  	}
  	return urlsDbPedia;
}


public static List<ResultBinding> getObjectsProperty(String pathResource, Property prop) {
	// TODO Auto-generated method stub
	
	String qsInfoDbpedia = "SELECT ?res WHERE {?res <"+prop.toString()+"> <"+pathResource+">}";
	
	//System.out.print(qsInfoDbpedia);
	List<ResultBinding> lis=DAO_Sparql.selectQueryToList(pathResource,serviceEndpoint,qsInfoDbpedia);	
	//ArrayList <String> types=new ArrayList <String>();
	System.out.println("lis tiene "+lis.size());
///	int i=0;
///	while (lis.size()>i)
 // /	{
  	
  //		ResultBinding q = lis.get(0);
  		
  		//if(q.getResource("?res").getURI()!=null){
			
	//		System.out.println(q.getLiteral("?res").toString());
	//  	}
  		//i++;
  	
	
	
	

	return lis;
}








public static List<Resource> getResourcesOfType(String pathResource) {
	// TODO Auto-generated method stub
	
	String qsInfoDbpedia = "SELECT ?res WHERE {?res a <"+pathResource+">}";
	
	//System.out.print(qsInfoDbpedia);
	List<ResultBinding> lis=DAO_Sparql.selectQueryToList(pathResource,serviceEndpoint,qsInfoDbpedia);	
	ArrayList <Resource> types=new ArrayList <Resource>();
	
	for(ResultBinding rb : lis){
		Resource res=rb.getResource("?res");
		types.add(res);		
	}
	return types;
}


public static String getLabel(String pathResource) {
	
	// TODO Auto-generated method stub
	String qsInfoDbpedia = "SELECT ?res WHERE {<"+pathResource+">  rdfs:label  ?res}";
	//. FILTER regex(?res,"+"\"@es\")
	//System.out.print(qsInfoDbpedia);
	List<ResultBinding> lis=DAO_Sparql.selectQueryToList(pathResource,serviceEndpoint,qsInfoDbpedia);
	boolean spa=false;
	String label="";
	for(ResultBinding rb:lis){
		 Literal labelLit=rb.getLiteral("?res");
		 
		 if(labelLit.toString().endsWith("@es")){
			 label=labelLit.toString();
			  spa=true;
		 }else{
			 if(labelLit.toString().endsWith("@en") && !spa){
				 label=labelLit.toString();
			 }
		 }
	}
	
	return label;
}


public static ArrayList<Resource> getFatherCategories() {
	// TODO Auto-generated method stub
String qsInfoDbpedia = "select distinct ?res where {?res rdfs:subClassOf owl:Thing . FILTER regex(?res,"+"\"^http://dbpedia.org/ontology\")}";
	
	//System.out.print(qsInfoDbpedia);
	List<ResultBinding> lis=DAO_Sparql.selectQueryToList("",serviceEndpoint,qsInfoDbpedia);	
	ArrayList <Resource> types=new ArrayList <Resource>();
	System.out.println("Tiene "+lis.size()+" categorias");
	for(ResultBinding rb : lis){
		Resource res=rb.getResource("?res");
		types.add(res);		
	}
	return types;

}














	

}
