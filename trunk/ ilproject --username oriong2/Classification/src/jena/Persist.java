// Package
///////////////
package jena;


// Imports
///////////////
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.xalan.xsltc.trax.OutputSettings;

import com.hp.hpl.jena.sdb.Store;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.RDFReader;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.graph.GraphSDB;
import com.hp.hpl.jena.sdb.sql.JDBC;
import com.hp.hpl.jena.sdb.sql.SDBConnection;
import com.hp.hpl.jena.sdb.store.DatabaseType;
import com.hp.hpl.jena.sdb.store.DatasetStore;
import com.hp.hpl.jena.sdb.store.LayoutType;
import com.hp.hpl.jena.sdb.store.StoreLoader;
import com.hp.hpl.jena.util.FileManager;

import com.hp.hpl.jena.sdb.*;

/**
 * <p>
 * Simple execution wrapper for persistent ontology example.
 * </p>
 * <p>
 * Usage:
 * <pre>
 * java jena.examples.ontology.persistentOntology.Main
 *                  [--dbUser string]     e.g: --dbUser ijd
 *                  [--dbURL string]      e.g: --dbURL jdbc:postgresql://localhost/jenatest
 *                  [--dbPw string]       e.g: --dbPw nosecrets
 *                  [--dbType string]     e.g: --dbType PostgreSQL
 *                  [--dbDriver string]   e.g: --dbDriver org.postgresql.Driver
 *                  [--reload]            if true will reload the source data
 *                  [sourceURL]           optional source URL for the data to persist
 * </pre>
 * If no db parameters or source URL is given, defaults will be used.
 * </p>
 *
 * @author Ian Dickinson, HP Labs
 *         (<a  href="mailto:Ian.Dickinson@hp.com" >email</a>)
 * @version CVS $Id: Main.java.html,v 1.5 2007/01/17 10:44:23 andy_seaborne Exp $
 */
public class Persist {
    // Constants
    //////////////////////////////////

    public static final String ONT1 = "urn:x-hp-jena:test1";
    public static final String ONT2 = "urn:x-hp-jena:test2";

    public static final String M_DB_URL = "jdbc:mysql://localhost/jenatest";
    public static final String M_DB_USER = "root";
    public static final String M_DB_PASSWD = "admin";
    public static final String M_DB = "MySQL";
    public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static final String M_DBDRIVER_CLASS = "com.mysql.jdbc.Driver";

    // Static variables
    //////////////////////////////////

    // database connection parameters, with defaults
    private static String s_dbURL = M_DB_URL;
    private static String s_dbUser = M_DB_USER;
    private static String s_dbPw = M_DB_PASSWD;
    private static String s_dbType = M_DB;
    private static String s_dbDriver = DB_DRIVER;

 
    // if true, reload the data
    private static boolean s_reload = true;

    // source URL to load data from; if null, use default
    private static String s_source;


    // Instance variables
    //////////////////////////////////


    // Constructors
    //////////////////////////////////

    // External signature methods
    //////////////////////////////////
    

    public static Model read(String login) throws IOException, SQLException {

        
    	 //create a database connection
    	IDBConnection conn = new DBConnection(M_DB_URL, M_DB_USER, M_DB_PASSWD, M_DB);
    	System.out.println("Creado conexion para la bd");
    	//create a model maker with the given connection parameters
   	 	ModelMaker maker = ModelFactory.createModelRDBMaker(conn);
     	System.out.println("Creado maker para la bd");
   	  
	    // or open a previously created named model
	    Model prvModel = maker.openModel(login);
	    System.out.println("Creado prvModel para la bd con tamaño ="+prvModel.size());
	    
	    //conn.close();
	   // System.out.println("Creado prvModel para la bd con tamaño ="+prvModel.size());
	    return prvModel;	   
	   
	    
    }
     public static void persists(Model modelUrlResources, String login  ) throws IOException, ClassNotFoundException, SQLException {

    // processArgs( args );

     // check for default sources
     if (s_source == null) {
         s_source = getDefaultSource();
     }

     // create the helper class we use to handle the persistent ontologies
     PersistentOntology po = new PersistentOntology();

     // ensure the JDBC driver class is loaded
     try {
         Class.forName( s_dbDriver );
     }
     catch (Exception e) {
         System.err.println( "Failed to load the driver for the database: " + e.getMessage() );
         System.err.println( "Have you got the CLASSPATH set correctly?" );
     }
     //po.saveModelToDb(s_dbURL, s_dbUser, s_dbPw, s_dbType, true );

     // are we re-loading the data this time?
     //READING
     if (s_reload) {

         // we pass cleanDB=true to clear out existing models
         // NOTE: this will remove ALL Jena models from the named persistent store, so
         // use with care if you have existing data stored
    	 
    	 
    	// load the the driver class
    	 Class.forName(M_DBDRIVER_CLASS);


    	 // create a database connection
    	 IDBConnection conn = new DBConnection(M_DB_URL, M_DB_USER, M_DB_PASSWD, M_DB);

    	 // create a model maker with the given connection parameters
    	 ModelMaker maker = ModelFactory.createModelRDBMaker(conn);


         System.out.println("Creado maker para la bd");
         // or create a named model
         
         //Start a database transaction. Without one, each statement will be auto-committed
         //as it is added, which slows down the model import significantly. 
         // nmModel=model;
       //  String inputFileName1="C://Users//JORGE//workspace//gestionate//WebContent//resources//dbpedia-ontology.owl";
         //InputStream in = FileManager.get().open(inputFileName);
         //nmModel.read(in,null);
         //System.out.print("Lee el fichero temporal");
         
         //<v>
         //RDFNode rdf;
        // modelUrlResources.containsResource(rdf)
         
         //modelUrlResources.listSubjectsWithProperty("<v>");
         
     	String inputFileName="c://Users//JORGE//workspace//gestionate//WebContent//resources//temp_xml.owl";
 		File file = new File(inputFileName);
 		//Hay que capturar las Excepciones
 		if (!file.exists()){
 		     file.createNewFile();
 		}
 		//"RDF/XML-ABBREV", xmlbase
 		modelUrlResources.write(new PrintWriter(file));
 		//System.out.print(modelUrlResources.toString());
 		//modelUrlResources.write(System.out);
 		
 		System.out.println("Escribe el fichero temporal");
 		
 		//use the FileManager to find the input file

 		InputStream in = FileManager.get().open( inputFileName );
 		
 		Model nmModel = maker.createModel(login);
         nmModel.begin();
         
         nmModel.read(in,null);
        // nmModel.read((Reader)modelUrlResources, "");
         System.out.println("Leyendo el fichero temporal");
         nmModel.commit();
      //   nmModel.write(System.out);
         conn.close();

      
     }
 }

    // Internal implementation methods
    //////////////////////////////////

    /**
     * Process any command line arguments
     */
    private static void processArgs( String[] args ) {
        int i = 0;
        while (i < args.length) {
            String arg = args[i++];

            if      (arg.equals( "--dbUser" ))   {s_dbURL = args[i++];}
            else if (arg.equals( "--dbURL" ))    {s_dbURL = args[i++];}
            else if (arg.equals( "--dbPasswd" )) {s_dbPw = args[i++];}
            else if (arg.equals( "--dbType" ))   {s_dbType = args[i++];}
            else if (arg.equals( "--reload" ))   {s_reload = true;}
            else if (arg.equals( "--dbDriver" )) {s_dbDriver = args[i++];}
            else {
                // assume this is a URL to load data from
                s_source = arg;
            }
        }
    }

    /**
     * Answer the default source document, and set up the document manager
     * so that we can find it on the file system
     *
     * @return The URI of the default source document
     */
    private static String getDefaultSource() {
        // use the ont doc mgr to map from a generic URN to a local source file
        OntDocumentManager.getInstance().addAltEntry( ONT1, "file:src-examples/data/test1.owl" );
        OntDocumentManager.getInstance().addAltEntry( ONT2, "file:src-examples/data/test2.owl" );

        return ONT1;
    }


    //==============================================================================
    // Inner class definitions
    //==============================================================================

}


/*
    (c) Copyright 2002, 2003, 2004, 2005, 2006, 2007 Hewlett-Packard Development Company, LP
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:

    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.

    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.

    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

