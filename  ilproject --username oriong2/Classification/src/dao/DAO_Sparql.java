package dao;

import java.util.List;

import jena.*;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.algebra.Algebra;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.algebra.Table;
import com.hp.hpl.jena.sparql.algebra.TableFactory;
import com.hp.hpl.jena.sparql.algebra.op.OpBGP;
import com.hp.hpl.jena.sparql.algebra.op.OpFilter;
import com.hp.hpl.jena.sparql.algebra.op.OpJoin;
import com.hp.hpl.jena.sparql.algebra.op.OpTable;
import com.hp.hpl.jena.sparql.core.BasicPattern;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.engine.ExecutionContext;
import com.hp.hpl.jena.sparql.engine.QueryIterator;
import com.hp.hpl.jena.sparql.engine.main.QC;
import com.hp.hpl.jena.sparql.expr.E_Regex;
import com.hp.hpl.jena.sparql.expr.Expr;
import com.hp.hpl.jena.sparql.expr.ExprVar;
import com.hp.hpl.jena.sparql.pfunction.PropFuncArg;
import com.hp.hpl.jena.sparql.pfunction.PropertyFunctionRegistry;
import com.hp.hpl.jena.sparql.util.NodeUtils;
import com.hp.hpl.jena.vocabulary.RDFS;


public class DAO_Sparql {

	public static String getImage() {
		// TODO Auto-generated method stub
		return "img/mulholland_drive.jpg";
	}
	
	 public static ResultSet selectQuery(String ruta,String serviceEndpoint,String qs)
	    {
	    	
	    	//Generate the comun prologue
	        PropertyFunctionRegistry.get().put("http://dbtune.org:3062/sparql", Jenate.class) ;

	        String prologue = "PREFIX po: <http://purl.org/ontology/po/>\n PREFIX pc:<http://dbtune.org/bbc/playcount/>\nPREFIX mo: <http://purl.org/ontology/mo/>\n PREFIX oa: <http://dbpedia.org/ontology/Artist/>\nPREFIX oma: <http://dbpedia.org/ontology/MusicalArtist/>\n PREFIX p: <http://dbpedia.org/property/>\n PREFIX pa: <http://dbpedia.org/property/Artist/>\n PREFIX db-ont:<http://dbpedia.org/ontology/>\n PREFIX owl: <http://www.w3.org/2002/07/owl#>\n PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns/#> \n PREFIX skos: <http://www.w3.org/TR/skos-reference/skos.html#> \nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
	        prologue=prologue+" PREFIX vocab: <http://dbtune.org/musicbrainz/resource/vocab/> \n PREFIX db: <http://dbtune.org/musicbrainz/resource/>PREFIX dc: <http://purl.org/dc/elements/1.1/>\n PREFIX foaf: <http://xmlns.com/foaf/0.1/> ";

	        qs = prologue+qs;
	        
	     	Query query = QueryFactory.create(qs) ;

	        Model model = make();
	     
	       //Funciona con service end point accede a dbpedia
	       QueryExecution qExec = QueryExecutionFactory.sparqlService(serviceEndpoint, query);
	       ResultSet rs=null;
	     
	       try {
	       	//System.out.print("Resultados:\n");
	          rs = qExec.execSelect() ;

	       } catch(Exception e){
	    	   System.out.print(e.toString());
	       }
	       finally { qExec.close() ; }
	       
		return rs;	
	    }
	    
    		public static List selectQueryToList(String ruta,String serviceEndpoint,String qs) 
		{
			// TODO Auto-generated method stub
			
		   	//Generate the comun prologue
	        PropertyFunctionRegistry.get().put("http://dbtune.org:3062/sparql", Jenate.class) ;
	        String prologue = "PREFIX po: <http://purl.org/ontology/po/>\n PREFIX pc:<http://dbtune.org/bbc/playcount/>\nPREFIX mo: <http://purl.org/ontology/mo/>\n PREFIX oa: <http://dbpedia.org/ontology/Artist/>\nPREFIX oma: <http://dbpedia.org/ontology/MusicalArtist/>\n PREFIX p: <http://dbpedia.org/property/>\n PREFIX pa: <http://dbpedia.org/property/Artist/>\n PREFIX db-ont:<http://dbpedia.org/ontology/>\n PREFIX owl: <http://www.w3.org/2002/07/owl#>\n PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns/#> \n PREFIX skos: <http://www.w3.org/TR/skos-reference/skos.html#> \nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
	        prologue=prologue+" PREFIX vocab: <http://dbtune.org/musicbrainz/resource/vocab/> \n PREFIX db: <http://dbtune.org/musicbrainz/resource/>PREFIX dc: <http://purl.org/dc/elements/1.1/>\n PREFIX foaf: <http://xmlns.com/foaf/0.1/> ";

	        //prologue="PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns/#> PREFIX mo:<http://purl.org/ontology/mo/>";
	        qs = prologue+qs;
	        
	     	Query query = QueryFactory.create(qs) ;
	        ////System.out.print("Comienza\n");
	        Model model = make();
	     
	       //Funciona con service end point accede a dbpedia
	       QueryExecution qExec = QueryExecutionFactory.sparqlService(serviceEndpoint, query);
	       ResultSet rs;
	     
	       List l;
	       
	      //	System.out.print("Resultados en tripletas:\n");
	       
     		rs = qExec.execSelect() ;
	       	// System.out.print(rs.toString());
	        //ResultSetFormatter.out(rs) ;	
	         l=ResultSetFormatter.toList(rs) ;	
	             //  List l=ResultSetFormatter.toList(rs);
	          // System.out.print("Fin recogida resultados\n");
	      //  finally { qExec.close() ; }
	       
	       
	       
		return l;
		}
	
    
    public static Model describeQuery(String ruta,String serviceEndpoint,String qs)
    {
    	
    	//Generate the comun prologue
        PropertyFunctionRegistry.get().put("http://dbtune.org:3062/sparql", Jenate.class) ;
        String prologue = "PREFIX po: <http://purl.org/ontology/po/>\n PREFIX pc:<http://dbtune.org/bbc/playcount/>\nPREFIX mo: <http://purl.org/ontology/mo/>\n PREFIX oa: <http://dbpedia.org/ontology/Artist/>\nPREFIX oma: <http://dbpedia.org/ontology/MusicalArtist/>\n PREFIX p: <http://dbpedia.org/property/>\n PREFIX pa: <http://dbpedia.org/property/Artist/>\n PREFIX db-ont:<http://dbpedia.org/ontology/>\n PREFIX owl: <http://www.w3.org/2002/07/owl#>\n PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns/#> \n PREFIX skos: <http://www.w3.org/TR/skos-reference/skos.html#> \nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
        prologue=prologue+" PREFIX vocab: <http://dbtune.org/musicbrainz/resource/vocab/> \n PREFIX db: <http://dbtune.org/musicbrainz/resource/>PREFIX dc: <http://purl.org/dc/elements/1.1/>\n PREFIX foaf: <http://xmlns.com/foaf/0.1/> ";

        //prologue="PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns/#> PREFIX mo:<http://purl.org/ontology/mo/>";
        qs = prologue+qs;
        
     //	Query query =  ;
       // System.out.print("Comienza\n");
        Model model = make();
     
       //Funciona con service end point accede a dbpedia
       QueryExecution qExec = QueryExecutionFactory.sparqlService(serviceEndpoint, QueryFactory.create(qs));
       
       Model modelito;

       try {
    	   System.out.println("error generando modelo");
       	modelito = qExec.execDescribe();

       } finally { qExec.close() ; }
       
       
       
	return modelito;
    	
    }
    
    
    public static List selectQueryList(String ruta,String serviceEndpoint,String qs)
    {
    	
		//Recojo toda la info de ese artista
    	PropertyFunctionRegistry.get().put("http://dbtune.org:3062/sparql", Jenate.class) ;
        String prologue = "PREFIX oa: <http://dbpedia.org/ontology/Artist/>\n PREFIX oma: <http://dbpedia.org/ontology/MusicalArtist/>\n PREFIX p: <http://dbpedia.org/property/> \n PREFIX pa: <http://dbpedia.org/property/Artist/>\n PREFIX db-ont: <http://dbpedia.org/ontology/>\n PREFIX owl: <http://www.w3.org/2002/07/owl#>\n PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n PREFIX skos: <http://www.w3.org/2004/02/skos/core#>";
        
        
        qs = prologue+qs;
     	Query query = QueryFactory.create(qs) ;
        System.out.print("Comienza\n");
        Model model = make();
     
       //Funciona con service end point accede a dbpedia
       QueryExecution qExec = QueryExecutionFactory.sparqlService(serviceEndpoint, query);
       ResultSet rs;
   	List l;
       try {
       	System.out.print("Resultados:\n");
          rs = qExec.execSelect() ;
           l=ResultSetFormatter.toList(rs);

           System.out.print("Fin resultados\n");
       } finally { qExec.close() ; }
       
       
       

	return l;
    	
    }
    
 
    private static Model make()
    {
        String BASE = "http://dbpedia.org/resource/" ;
        Model model = ModelFactory.createDefaultModel();
        /*model.setNsPrefix("", BASE) ;
        Resource r1 = model.createResource(BASE+"r1") ;
        Resource r2 = model.createResource(BASE+"r2") ;

        r1.addProperty(RDFS.label, "abc") ;
        r2.addProperty(RDFS.label, "def") ;
*/
        return model  ;
    }
    
    static int hiddenVariableCount = 0 ; 

    // Create a new, hidden, variable.
    private static Var createNewVar()
    {
        hiddenVariableCount ++ ;
        String varName = "-search-"+hiddenVariableCount ;
        return Var.alloc(varName) ;
    }
      	
        /* This be called once, with unevaluated arguments.
         * To do a rewrite of part of a query, we must use the fundamental PropertyFunction
         * interface to be called once with the input iterator.
         * Must not return null nor throw an exception.  Instead, return a QueryIterNullIterator
         * indicating no matches.  
         */

        public QueryIterator exec(QueryIterator input, PropFuncArg argSubject, Node predicate, PropFuncArg argObject, ExecutionContext execCxt)
        {
            // No real need to check the pattern arguments because
            // the replacement triple pattern and regex will cope
            // but we illustrate testing here.

            com.hp.hpl.jena.graph.Node nodeVar = argSubject.getArg() ;
            String pattern = NodeUtils.stringLiteral(argObject.getArg()) ;
         /*   if ( pattern == null )
            {
                ALog.warn(this, "Pattern must be a plain literal or xsd:string: "+argObject.getArg()) ;
                return new QueryIterNullIterator(execCxt) ;
            }*/

          /*  if ( false )
                // Old (ARQ 1) way - not recommended.
                return buildSyntax(input, nodeVar, pattern, execCxt) ;
            */
            // Better 
            // Build a SPARQL algebra expression
            Var var2 = createNewVar() ;                     // Hidden variable
            
            BasicPattern bp = new BasicPattern() ;
            Triple t = new Triple(nodeVar, RDFS.label.asNode(), var2) ;
            bp.add(t) ;
            OpBGP op = new OpBGP(bp) ;
            
            Expr regex = new E_Regex(new ExprVar(var2.getName()), pattern, "i") ;
            Op filter = OpFilter.filter(regex, op) ;

            // ---- Evaluation
            if ( true )
            {
                // Use the reference query engine
                // Create a table for the input stream (so it uses working memory at this point, 
                // which is why this is not the preferred way).  
                // Then join to expression for this stage.
                Table table = TableFactory.create(input) ;
                Op op2 = OpJoin.create(OpTable.create(table), filter) ;
                return Algebra.exec(op2, execCxt.getDataset()) ;
            }        
            
            // Use the default, optimizing query engine.
            return QC.execute(filter, input, execCxt) ;
        }


		public void build(PropFuncArg arg0, com.hp.hpl.jena.graph.Node arg1,PropFuncArg arg2, ExecutionContext arg3) {
			// TODO Auto-generated method stub
			
		}





	
}
