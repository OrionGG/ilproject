package jena;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.musicbrainz.model.Artist;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import dao.DAO_Model;
import dominio.DbPedia;
import dominio.DbPediaResource;


public abstract class Algorithym {

	private static int maximumValue=6;
	private static int valueForResource=2;
	private static int startValue =4; //Start value for the algorithym
	private static int profundity =1; //Numbers of levels to acces to the introduced data 
	

	
	
	public static int fillPropertyValue(Model childModel, Model model,int newValue, List<Statement> statementsEliminar) {
		// TODO Auto-generated method stub
		//Dosnt add statements to the model, just use model in order to know if the statement exists on the model, to delete it
		int i=0;
		int x=0;
		int actualValue;
		int finalValue;
		
		Property valueProp=model.createProperty("http://www.tvblob.com/ratings/#","value");


		//Iterate with the model in order to introduce or increase the property value 
		NodeIterator ni=childModel.listObjects();
		while(ni.hasNext()){
			RDFNode rdf=ni.next();
			
			String path=rdf.toString();
			boolean interesting =true;
			for (String pathNotValid : new String[]{
					"http://sw.opencyc.org/2008/06/10/concept/",
					"http://www4.wiwiss.fu-berlin.de/flickrwrappr/photos/",
					"http://rdf.freebase.com/ns/",
					"http://dbpedia.org/ontology/",
					"http://dbpedia.org/ontology/Band",
					"http://dbpedia.org/ontology/Organisation",
					"http://upload.wikimedia.org/wikipedia",
					"http://www.w3.org",
					"http://dbpedia.org/resource/Template:",
					"http://dbpedia.org/meta/",
					"http://sw.cyc.com",
					"http://www.purl.org",
					
					}) {
						if (path.startsWith(pathNotValid)) {
							interesting=false;
						}
					}
			
			////Whith this if control where the algo is going to go and put values
			if(rdf.isResource() && rdf.isURIResource() && interesting){
			
				Resource childResource=childModel.getResource(rdf.toString());
				
				if(model.contains(childResource, valueProp)){

					Statement st=model.getProperty(childResource, valueProp);
					RDFNode object=st.getObject();
					actualValue=Integer.parseInt(object.toString());
					//System.out.println("Entro en fillProp y modifico "+object.toString()+ "de valor"+valor);
					
					////CALCULO PARA RECURSO YA EXISTENTE
					finalValue=newValue+actualValue;
					statementsEliminar.add(st);
					childModel.add(childResource,valueProp, String.valueOf(finalValue));
					System.out.print(childResource.getLocalName()+ " REPETIDO ->valor="+actualValue+",");
					
				}else{
	
					////CALCULO PARA RECURSO NUEVO
					finalValue=1*newValue;
					System.out.print(childResource.getLocalName()+ " valor="+finalValue+",");
					childModel.add(childResource,valueProp,String.valueOf(finalValue));
				}
				x+=1;
			}
			i+=1;
		}
		//System.out.print(", hay "+i+"objects");
		return x;
	}


	@SuppressWarnings("unused")
	public static Model developModel(Model model, List<String> listInterestedResources, List<Artist> listInterestedArtists) throws SQLException, ClassNotFoundException {
		////	
		///Receives : Model of the user and the list of interested resources
		///Changes: Model
		///Returns : --
		///Description : In this method the model is going to change in order to get more interesting info and taking out info that is not relevant 
		///Called by : Jenate.main()
		////
		
		////Initialize variables
		List<String> grupos = new ArrayList<String>();
		
		//MODEL COPY IN ORDER TO ALTER THE OTHER
		Model modelForReading=model; 
		Property valueProp=modelForReading.createProperty("http://www.tvblob.com/ratings/#","value");
		
		////Take out the resources with the same name of the concpets received
		for (Artist artist : listInterestedArtists) {
			grupos.add(artist.getName());
		}	
		//int i = 0;
		List<Statement> statementsEliminar = new ArrayList<Statement>();
		//List<Model> modelosNuevos = new ArrayList<Model>();
		
		
				//Show all the resources whith object value 
		//StmtIterator sti=modelForReading.listSubjectsWithProperty(arg0, arg1);
		

		ResIterator ri=	modelForReading.listResourcesWithProperty(valueProp);
		int developValue;
		//GROW MODEL
		int listSize=listInterestedArtists.size()+listInterestedResources.size();
		if(listSize>10){
			developValue=4;
			System.out.println("listInterestedResources size= "+listSize+ "developValue = "+developValue);
		}else{
			developValue=2;
			System.out.println("listInterestedResources size= "+listSize+ "developValue = "+developValue);
		}

		System.out.println("Resources with property value: ");
		while(ri.hasNext()){
				
			Resource res=ri.next();
			Statement st =modelForReading.getProperty(res, valueProp);
			if(st != null){
				RDFNode obj=st.getObject();
						
				if(!(res.getLocalName().toString().equals(""))){
					Integer fatherValue=(Integer)Integer.parseInt(obj.toString());
					String name=res.getLocalName();
					String path =res.getURI();
					String namespace=res.getNameSpace();
					
					////FILTERS: Take out the resources with the same name as the ones introduced
					/*for (String grupo : grupos) {
						if (name.equalsIgnoreCase(grupo)) {
							if (!statementsEliminar.contains(st)) {
								statementsEliminar.add(st);
							}
						}
					}
	
					for (String resource : listInterestedResources) {
						if (name.equalsIgnoreCase(resource)) {
							if (!statementsEliminar.contains(st)) {
								statementsEliminar.add(st);
							}
						}
					}*/
					////FILTERS: Remove the statements with not interested resources
					for (String pathNotValid : new String[]{
							"http://dbpedia.org/ontology/Band",
							"http://www.w3.org",
							"http://dbpedia.org/meta/",
							"http://www.w3.org/2006/03/wn/wn20/instances/synset-musician-noun-1.rdf",
							"http://dbpedia.org/ontology/Organisation",
							"http://dbpedia.org/class/yago/LivingPeople",
							"http://dbpedia.org/resource/Category:Living_people",
							
							//"http://dbpedia.org/resource/Category:",
							//"http://dbpedia.org/class/yago/",
							
							}) 
					{
						if (path.startsWith(pathNotValid)) {
							if (!statementsEliminar.contains(st)) {
								statementsEliminar.add(st);
							}
						}
					}
	
					for (String nameNotValid : new String[]{
							"Settlemen",
							"Thing",
							"ResultSet",
							"subject",
							"Ogg",
							"editlink",
							"pageid",
							"type",
							"dimension",
							"subClassOf",
							"modified",
							"Group100031264",
							"modified",
							"LivingPeople",
							"Living_people",
							
							}) {
						if (name.equals(nameNotValid)) {
							if (!statementsEliminar.contains(st)) {
								statementsEliminar.add(st);
							}
						}
					}
					for (String namespaceNotValid : new String[]{
							"http://dbpedia.org/meta/",
							"http://dbpedia.org/resource/Template:",
							"http://purl.org/dc/terms/",
							"http://purl.org/NET/scovo#",
							"http://www.w3.org/2000/01/rdf-schema#",
							"http://www.w3.org/2004/02/skos/core#",
							"http://www.w3.org/2001/sw/DataAccess/tests/result-set#",
							"http://www.w3.org/1999/02/22-rdf-syntax-ns#",
							"http://www.w3.org/2004/02/skos/core#",
							"http://sw.cyc.com/CycAnnotations_v1#",
							"http://www.w3.org/2000/01/rdf-schema#",
							}) {
						if (name.equals(namespaceNotValid)) {
							if (!statementsEliminar.contains(st)) {
								statementsEliminar.add(st);
							}
						}
					}
					
					if(path.endsWith(".jpg")){
						statementsEliminar.add(st);
					}

					if (!statementsEliminar.contains(st) && path.startsWith("http://dbpedia.org/") && fatherValue>developValue) {
						if(namespace.equals("http://dbpedia.org/resource/Category:") || namespace.equals("http://dbpedia.org/class/yago/") || namespace.startsWith("http://dbpedia.org/class/yago") ||namespace.startsWith("http://dbpedia.org/resource/Category:")){
								System.out.println(path+" "+fatherValue);
								Model childModel=DbPedia.getRelatedModelFull(path);
								if(childModel !=null){
									List<Statement> newStatementsEliminar = new ArrayList<Statement>();
								
									
									if(fatherValue>1){
										valueForResource=fatherValue/2;
									}
									//Ad new model of profundity algorithym to the general model
								
									int info=fillPropertyValue(childModel,model,valueForResource, statementsEliminar);
		
									int growmodelSize;
									if (childModel != null) {
										 model.remove(newStatementsEliminar);
										 newStatementsEliminar=null;
										int modelSize=(int) model.size();
										model=model.union(childModel);
										
										 growmodelSize=(int) model.size();
										int added=growmodelSize-modelSize;
									
										System.out.println("\n---AGORITHYM DEVELOP: Ha devuelto resources modificados de "+res.getLocalName()+", añade al model "+childModel.size()+"="+added+", TOTAL MODEL: "+growmodelSize);
										childModel=null;
										if(growmodelSize>202657){
											break;
										}
										//modelosNuevos.add(modeloNuevo);
									}else{
										System.out.print("Modelo vacio");
									}
								}
						//childModel=null;
							}
						}
	
					
				}
			
			}	
			
			model.remove(statementsEliminar);
			
		}
	

		statementsEliminar=null;
		
		modelForReading=null;
		
		ri=null;

		return model;
	}


/*	
	private static void fusionModels( Model model, Model childModel, int fatherValue, Resource res, List<Statement> statementsEliminar){
		////Get more info from  the interesting resources

		//// more options ---->  || namespace.equals("http://rdf.freebase.com/ns/") || namespace.equals("http://sw.opencyc.org/2008/06/10/concept/")
		
		
		String path=res.getURI();
		String namespace=res.getNameSpace();
		String name=res.getLocalName();
		
		System.out.println(path+" "+fatherValue);
		//if(value >= maximumValue/2 ){
			if(namespace.equals("http://dbpedia.org/resource/Category:") || namespace.equals("http://dbpedia.org/class/yago/") || namespace.startsWith("http://dbpedia.org/class/yago") ||namespace.startsWith("http://dbpedia.org/resource/Category:")){
				
				//if( path.startsWith("http://dbpedia.org/resource/Category:19") || path.startsWith("http://dbpedia.org/resource/Category:20") || path.startsWith("http://dbpedia.org/class/yago/20") || path.startsWith("http://dbpedia.org/class/yago/19")){
					//No hace nada
				//}else{
					//System.out.print("Recurso a profundizar="+path);
													
					//Get more profundity data
					
						
						
						
						
					}					
			//}
		//}
		
	}
	
	*/
	





	public static int getStartValue() {
		// TODO Auto-generated method stub
		return startValue;
	}


	public static int getProfundity() {
		// TODO Auto-generated method stub
		return profundity;
	}

	
	
	
	

}