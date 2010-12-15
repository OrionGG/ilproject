package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
import java.util.Map;
//import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jena.*;

import org.musicbrainz.model.Artist;

import servicios.ServicioUsuarios;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import dominio.DbPediaResource;
import dominio.Usuario;


public class SeeRecomendations extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException 
     */

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException {
    	
        //Declaration types
        String login = null;
        String email = null;
        String pass = null;
        HttpSession session = null;
        Usuario usuario = null;
 
        RequestDispatcher rd = null; 
        request.setAttribute("mensajeError", "Error iniciando con contenidos del nuevo usuario.");
    	  rd=request.getRequestDispatcher("error.jsp");
    	//main
    	response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ArrayList<DbPediaResource> urlsResources=null;
        ArrayList<DbPediaResource> urlsPersons=null;
        ArrayList<DbPediaResource> urlsPlaces =null;
        ArrayList<DbPediaResource> urlsArtists1=null; 
       
        try {
        	
        	System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        	System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX  SEE Recomendations  SXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        	System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            session=request.getSession();
            //login=request.getParameter("name");
            //email=request.getParameter("email");
       
            
            usuario=(Usuario) session.getAttribute("usuario");

            if(usuario ==null){
            	System.out.println("User null");
            	
            }else{
            	
            	//intentando recibirlos de la session
            	         	
            	if(urlsPlaces !=null || urlsPersons!=null || urlsResources!=null || urlsArtists1!=null){
            		System.out.println("Info recogida de la session");
            		if(urlsArtists1!=null){
            			System.out.println(urlsArtists1.get(0).getLocalName());
            		}
            		rd=request.getRequestDispatcher("recomendaciones.jsp");
            	}
            	else{
            		System.out.print("Lectura de bd");
            		login=usuario.getLogin();
            		System.out.println("Login ="+login);
		        	//Map<Integer, Collection<DbPediaResource>> mapUrlResources =MyMap.readMapFromDb(login);
            		Model modelDB=Persist.read(login);            		
		        	urlsResources = new ArrayList<DbPediaResource>();
		        	 urlsPersons = new ArrayList<DbPediaResource>();
		          	urlsPlaces = new ArrayList<DbPediaResource>();
		          	if(modelDB==null){
		          		System.out.print("Model null");
		          	}else{
				        Map<Integer, Collection<DbPediaResource>> mapUrlResources = null;
				        System.out.println("Creado modelDB en seeRecomendations para la bd con tamaño ="+modelDB.size());
		          		mapUrlResources=MyModel.filterModelToMap(modelDB,usuario.getLogin());
		          	     System.out.println("El map tiene "+ mapUrlResources.size());
					    
		          		System.out.print("Map relleno, bigger value "+MyMap.getBiggerValueMap(mapUrlResources));
		          		ArrayList<DbPediaResource> urlsArtists = MyMap.convertMapToArrays(mapUrlResources,urlsResources, urlsPersons, urlsPlaces);
		          	    mapUrlResources=null;
			           System.out.print("El url places tiene: "+urlsPlaces.size());
				       System.out.print("El url resources tiene: "+urlsResources.size());
 				       System.out.println("El url artist tiene: "+urlsArtists.size());
				       System.out.println("El url persons tiene: "+urlsPersons.size());
				       
				       	request.setAttribute("urlsResources", urlsResources); 
				        request.setAttribute("urlsPersons", urlsPersons);  
				        request.setAttribute("urlsArtists", urlsArtists);     
				        request.setAttribute("urlsPlaces", urlsPlaces);   
		          	}
		          	
		          	
		          
            
            	}
            	rd=request.getRequestDispatcher("recomendaciones.jsp");
            }
        	} catch (Exception e) {
				
		          request.setAttribute("mensajeError", "Error acondicionando contenidos del nuevo usuario.");
		      	  rd=request.getRequestDispatcher("error.jsp");
		    } 
			finally {
					rd.forward(request,response);
				    out.close();
			}
   }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

