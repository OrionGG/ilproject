package dao;

import java.sql.DriverManager;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.mysql.jdbc.Connection;

public class DAO_OntModel {
	

	public static void createModelToDb(String dbname,String dbuser,String dbpass){
		
		
		
        session=request.getSession();
        //login=request.getParameter("name");
        email=request.getParameter("email");
    	String loginDb="persistance";
    	String passDb="persistaance";
    	String login2="prueba";
    	//DAO_OntModel.createModelToDb(login2,loginDb,passDb);
    	DAO_OntMOdel.save();
    	
    	
		boolean created =false;
		/*if(existsDatabase(dbname,dbuser,dbpass)==false){
			createDatabase(dbname,dbuser,dbpass);
		}*/
		IDBConnection con=null;
		con=new DBConnection("jdbc:mysql://localhost/"+dbname,dbuser,dbpass,dengine);
		try{
			con.cleanDB();
			ModelRDB RDBModel=null;
			RDBModel =ModelRDB.createModel(con,dbmodelname);
			System.out.print("base ready");
			RDBModel.close();
			con.close();
			created=true;
			
		}catch(Exception e){
			
		}



	}
public static read(){

	//NAme db pruebaOntology
	
	String strModel = "jdbc:mysql://myserver/PruebaOntology";
	String strModelUser = "protege";
	String strModelPwd = "";
	Model model=null;

	//ModelRDB model = null;
	OntModel modelOnt = null;
	String ns = "http://www.example.com/PruebasOntology.owl";

	// Creo la conexión con el modelo persistente
	IDBConnection conModel = new DBConnection(strModel, strModelUser, strModelPwd, "MySQL");

	// abro el modelo desde el almacenamiento
	ModelMaker maker = ModelFactory.createModelRDBMaker(conModel);
	model = (ModelRDB)maker.openModel("PruebaOntology");

	// creo el modelo utilizando como razonador OWL_MEM_RULE_INF, sobre el modelo abierto
	modelOnt = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF, model);

	
	

	
}




public static save(){
	

	try{
	boolean saved =false;
	IDBConnection con=null;
	con=new DBConnection("jdbc:mysql://localhost/"+dbname,dbuser,dbpass,dengine);
	ModelMaker maker=ModelFactory.createModelRDBMaker(con);
	ModelRDB RDBModel=maker.openModel(dbmodelname);
	OntModel ontmodel2=null;
	ontmodel2=ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,RDBModel);
	ontmodel2.add(ontmodel);

		
	}catch(Exception e){
		
	}


}


public class existsModelToDb(dbname,dbuser,dbpass){
	
	
	

	Connection con=null;
	boolean exists =false;
	coon=DriverManager.getConnection("");
	if(con.isClosed())
	try{
		con.cleanDB();
		ModelRDB RDBModel=null;
		RDBModel =ModelRDB.createModel(con,dbmodelname);
		System.out.print("base ready");
		RDBModel.close();
		con.close();
		created=true;
		
	}
}
public class createModelToDb(dbname,dbuser,dbpass){
		
		
		Connection con=null;
		boolean created =false;
		int ret=0;
		coon=DriverManager.getConnection("");
		ret=con.createStatement().executeUpdate("CREATE DATABASE '"+dbname"'+"';");
		if(ret>0){
			created=true;
		}
		con.close();
		
	
		try{
			
		con=
		
			
		}
		coon=DriverManager.getConnection("");
		if(con.isClosed())
		try{
			con.cleanDB();
			ModelRDB RDBModel=null;
			RDBModel =ModelRDB.createModel(con,dbmodelname);
			System.out.print("base ready");
			RDBModel.close();
			con.close();
			created=true;
			
		}




/*RECORRER
 * 
 * 
	
	//
	// Como ejemplo de utilización obtengo todas los canciones del autor "Elvis Presley"
	//

	// obtengo la instacia del autor "Elvis Presley"
	Individual autor = model.getIndividual( ns + "#Elvis_Presley" );

	// obtengo la propiedad que relaciona las canciones con su autor
	Property isAutorCancion = modelOnt.getProperty(ns+"#isAutorCancion");

	// obtengo las canciones y las recorro
	NodeIterator iteratorCanciones = autor.listPropertyValues(isAutorCancion);
	while ( iteratorCanciones.hasNext() ) {
	    OntResource cancion = (OntResource)iteratorCanciones.next();
	    System.out.println(cancion);
	}
 * */

}
