package jena;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import servicios.ServicioUsuarios;

import com.hp.hpl.jena.*;
import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import com.mysql.jdbc.Connection;

import dao.DAO_Model;
import dominio.DbPedia;
import dominio.DbPediaResource;

  public class MyModel {

	
	
	/*public Model model;
	
	public MyModel(Model _model){
		model =_model;
	}
	MyModel.getModel();
*/

	public static Model fillModel(String pathResource,Model model) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		Model modelRelatedTerms=DbPedia.getRelatedModel(pathResource);

		///Fullll and fill
		System.out.print("Comprobando");
		int x=0;
		if(model !=null && modelRelatedTerms!=null){
				if(modelRelatedTerms.size() > 2){
			
				//Asignamos un valor distinto de base al algortimo desde jenate, al ser las primeras entradas
				List<Statement> statemenstEliminar = new ArrayList<Statement>();
				//x=Algorithym.fillPropertyValue(modelRelatedTerms,model,Algorithym.getStartValue(), statemenstEliminar);

				model.remove(statemenstEliminar);
				
				model=DAO_Model.modelUnion(model,modelRelatedTerms);
				}
		}
		System.out.println("\n\nEl modelo tiene "+model.size()+" tuplas, "+x+"de propiedad value");
					

		return model;
		
	}

	
	@SuppressWarnings("null")
	public static Map<Integer, Collection<DbPediaResource>> filterModelToMap(Model model, String login) {

			//System.out.println("El modelo tiene "+model.size()+" tuplas ");
			Property prop=model.getProperty("http://www.tvblob.com/ratings/#","value");

			Map <Integer,Collection<DbPediaResource>> mapUrlResources= new TreeMap <Integer,Collection<DbPediaResource>>();

			//Show all the resources whith object value 
			//StmtIterator sti=model.listStatements();
			ResIterator ri=model.listSubjectsWithProperty(prop);
			
			System.out.println("\n\nGet favorites, in order not to show them: ");
			int i = 0;
			boolean interesting=true;
			ArrayList <String> listOfResources=new ArrayList();
			int j=0;
		//	ArrayList<DbPediaResource> favoritesDPR=ServicioUsuarios.getFavorites(login);
			System.out.println("\n\nResources of property value and his object value: ");
			while(ri.hasNext()){
				interesting=true;
				Resource res=ri.next();
				String path=res.toString();
				String name=res.getLocalName();
				String namespace=res.getNameSpace();
		
			
				for (j=0;listOfResources.size() > j;j++){
				
					String pathNotValid=listOfResources.get(j);
					j++;
					if (path.equals(pathNotValid)) {
						interesting=false;
					}
				}
				//Controlling for not getting the same urls
				if(login!=null){
					for(DbPediaResource dp: favoritesDPR){
						if (path.equals(dp.getUri())) {
							interesting=false;
						}
					}
				}
				
				for (String pathNotValid : new String[]{
						"http://dbpedia.org/ontology/Band",
						"http://dbpedia.org/resource/Category:",
						"http://www.w3.org/2000/01/rdf-schema#",
						"http://dbpedia.org/class/yago/2000",
						"http://www.w3.org",
						"http://dbpedia.org/ontology/Organisation",	
						"http://dbpedia.org/resource/Template:",
						"http://purl.org/NET/scovo#",
						"_:bad_iid_",
						"http://www.opengis.net",
						"http://dbpedia.org/about",
						}) {
							if (path.startsWith(pathNotValid)) {
								interesting=false;
							}
						}
				if( interesting && !(name.equals("Ogg")||name.equals("link to")||name.equals("links to") || name.equals("Thing") || name.equals("Group100031264") || name.equals("ResultSet") ||name.equals("subClassOf") || path.endsWith(".jpg") || 
						name.equals("subject") ||  name.equals("dimension") ||  name.equals("type") ||  name.equals("Label") || name.equals("Concept") ||  name.equals("Concept") || name.equals("Ogg"))){
				
					Statement st=model.getRequiredProperty(res, prop);
					//Statement st=sti.next();
					
					//Resource res=st.getSubject();
					Property pre=st.getPredicate();
					RDFNode obj=st.getObject();
					
					System.out.print(pre.getLocalName());
					if(pre.getLocalName().equals("value") && !(res.getLocalName().toString().equals("")) && !(res.getLocalName().equals(null)) ){
						listOfResources.add(path);	
						Integer value=(Integer)Integer.parseInt(obj.toString());
						DbPediaResource dbRes = new DbPediaResource(name,namespace,path);
					
						//Mapping of multiple values for the same key, this is for not deleting the initial values of the map
						Collection<DbPediaResource> list = mapUrlResources.get(value);
		                if (list == null){
		                	list=new ArrayList<DbPediaResource> ();
		                }
		                list.add(dbRes);
						Collection x=mapUrlResources.put(value,list);
						if(x==null){
						//	System.out.println("filterModelToMap: error introuciendo al map con valor = "+value+".");
						}
						i=i+1;
					}
				}
			}
			System.out.println("filterModelToMap: Hay "+mapUrlResources.values().size()+ " claves distintas en algorithym y hay " +i+" resources");
			return mapUrlResources;
	}

	
	}
