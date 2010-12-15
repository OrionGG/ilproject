package dao;

import java.io.IOException;
import java.io.InputStream;

import jena.MyModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;



public class DAO_Model { 

	public static void storeResourceAsModel(String pathResource ){
		// TODO Auto-generated method stub
				
			// URI declarations
		//	String resourceUri = urlResource;
			//String relationshipUri = urlProperty;
			
			// Create an empty Model
			Model model = ModelFactory.createDefaultModel();
			model=model.read(pathResource);
			
			// Create a Resource for each resource member, identified by their URI
			//String x;
			//x=resource;
			//Resource r = model.createResource(urlResource);
			
			/// Create properties for the different types of relationship to represent
			////Property p = model.createProperty();
			
			// Add properties to adam describing relationships to other family members
			
			
			// Can also create statements directly . . .
			////Statement statement = model.createStatement(pathResource,urlProperty,urlResource);
			
			// but remember to add the created statement to the model
			
			////model.add(statement);
		
		
	}

	public static void storeResourceInModel(String resource,
			String urlResource, String property, String pathResource,
			String property2, String urlProperty, int value, String user) {
		// TODO Auto-generated method stub
		
	}

	public static Model generateMainModel() throws IOException {
		// TODO Auto-generated method stub
		//String inputFileName="resources//dbpedia_3.5.1.owl";
		String inputFileName=".\\resources\\dbpedia_3.5.1.owl";
		
		Model model =  ModelFactory.createDefaultModel();

 // use the FileManager to find the input file
		InputStream in = FileManager.get().open( inputFileName );
		if (in == null) {
				throw new IllegalArgumentException(
                                 "File: " + inputFileName + " not found");
		}

		// read the RDF/XML file
		model.read(in, null);
		
		// write it to standard out
		////model.write(System.out);
		in.close();
		return model;
			

	}

	
	
	public static void generateModel2(String pathResource) {
	
       // Store store = SDBFactory.connectStore("C:\\users\\jgp\\Desktop\\Proyecto\\store.ttl") ;
      //  if(store != null){
        //    System.out.println("Conexi�n al store SDB ... Ok");
        //}

       
    }

	public static boolean getDbpediaUrlArtist(String name, String type,
			Model modelDbpedia) {
		// TODO Auto-generated method stub
	
		Property prop=modelDbpedia.createProperty(type);
		Resource res=modelDbpedia.createResource(name);
		boolean b=modelDbpedia.contains(res, prop);
		if(b){
			System.out.println("Encontrado recurso");
		}else{System.out.println("No encontrado recurso");}
	////	modelDbpedia.containsResource(RDFNode);
	
		return b;
	}

	public static Model modelUnion(Model modelDbpedia, Model modelRelatedTerms) {
		// TODO Auto-generated method stub
		Model model = modelDbpedia;
		if (modelRelatedTerms != null){
			model=modelDbpedia.union(modelRelatedTerms);
		}
		return model;
		
	}

	public static Model generateOntModel() {
		// TODO Auto-generated method stub
		
		String inputFileName="C://Users//jgp//Desktop//Proyecto//dbpedia_3.5.1.owl";
		Model model = ModelFactory.createOntologyModel();
	
 // use the FileManager to find the input file
		InputStream in = FileManager.get().open( inputFileName );
		if (in == null) {
				throw new IllegalArgumentException(
                                 "File: " + inputFileName + " not found");
		}

		// read the RDF/XML file
		model.read(in, null);
		
		// write it to standard out
		////model.write(System.out);
		
		return model;

	}

	public static void printStatements(Model model) {
		// TODO Auto-generated method stub
	 	System.out.println("lista de tuplas del modelo "+model.size()+":\n");
	 	StmtIterator si=model.listStatements();
	 	int ix=0;
	 	int zx=0;
		while(si.hasNext()){
			
			 Statement st=si.next();
			 if(st.getSubject().getLocalName()!=null){
			
				 System.out.println("El Sujeto es "+st.getSubject().getLocalName().toString());
				 System.out.println("El predicado o propiedad es "+st.getPredicate().getLocalName().toString());
				 System.out.println("El object es " +st.getObject().toString()+"\n");
				 ix +=1;
			 }else{
				// System.out.println("El Sujeto es "+st.getSubject().toString());
				 //System.out.println("El predicado o propiedad es "+st.getPredicate().toString());
				 //System.out.println("El object es " +st.getObject().toString()+"\n");
				 zx +=1;
			 }		 
		}
	//	System.out.print("\nhabia"+ix+"statements de "+zx);
		
	}

	public static void printSubjects(Model model) {
		// TODO Auto-generated method stub
	 	System.out.println("lista de tuplas del modelo "+model.size()+":\n");
	 	ResIterator si=model.listSubjects();
	 	int ix=0;
	 	int zx=0;
		while(si.hasNext()){
			
			 Resource st=si.next();
			 if(st.getLocalName()!=null){
				 System.out.println("El Sujeto es "+st.getLocalName().toString());
				// System.out.println("El predicado o propiedad es "+st.getPredicate().getLocalName().toString());
				 //System.out.println("El object es " +st.getObject().toString()+"\n");
				 ix +=1;
			 }else{
				 System.out.println("El Sujeto es "+st.toString());
				// System.out.println("El predicado o propiedad es "+st.getPredicate().toString());
				 //System.out.println("El object es " +st.getObject().toString()+"\n");
				 zx +=1;
			 }		 
		}
		System.out.print("\nhabia"+ix+"statements de "+zx);
		
	}

	

}
