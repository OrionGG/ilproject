package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jena.*;

import org.apache.commons.httpclient.HttpException;
import org.musicbrainz.JMBWSException;
import org.musicbrainz.model.Artist;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.QueryParseException;
import com.hp.hpl.jena.rdf.model.Model;

import servicios.ServicioUsuarios;

import dao.DAO_Interface;
import dao.DAO_MySQL;

import dominio.DbPediaResource;
import dominio.Usuario;


public class SearchNewFields extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
    @SuppressWarnings("null")
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException{
    	
        //Declaration types
        String name = null;
        String email = null;
        String pass = null;
        HttpSession session = null;
        Usuario usuario = null;
        
    	ArrayList <String>listResources=new ArrayList <String>();
		ArrayList <String>listResourcesArtist=new ArrayList <String>();
		List <String>listInterestedResources=new ArrayList <String>();
    	List <Artist>listInterestedArtists=new ArrayList <Artist>();
    		
    	RequestDispatcher rd = null; 
    	PrintWriter out = null;
    	out = response.getWriter();
	    request.setAttribute("mensajeError2", "Error iniciando el servicio.");
	    rd=request.getRequestDispatcher("error.jsp");
    	try {
    	//main
    		session=request.getSession();
	    	response.setContentType("text/html;charset=UTF-8");
	   		//out = response.getWriter();

	   		
	    	usuario=(Usuario) session.getAttribute("usuario");
         
	    if(usuario!=null){
		    	
		    		//usuario = ServicioUsuarios.getInstancia().validaUsuario(name, pass);
	 				
						String grupo1 = null;
						String grupo2 = null;
						String grupo3= null;
						String grupo4= null;
						String recurso1= null;
						String recurso2= null;
						String recurso3= null;
						String recurso4= null;
						String recurso5= null;
						String recurso6= null;
						String recurso7= null;
						String recurso8= null;
						String recurso9= null;
						String recurso10= null;
						String recurso11= null;
						String recurso12= null;
						
						session.setAttribute("usuario", usuario);
				        request.setAttribute("usuario", usuario);
	
				        grupo1=request.getParameter("grupo1");
					     grupo2=request.getParameter("grupo2");
						 grupo3=request.getParameter("grupo3");
						 grupo4=request.getParameter("grupo4");
				  		 recurso1=request.getParameter("recurso1");
					     recurso2=request.getParameter("recurso2");
						 recurso3=request.getParameter("recurso3");
						 recurso4=request.getParameter("recurso4");
				  		 recurso5=request.getParameter("recurso5");
					     recurso6=request.getParameter("recurso6");
						 recurso7=request.getParameter("recurso7");
						 recurso8=request.getParameter("recurso8");
				  		 recurso9=request.getParameter("recurso9");
					     recurso10=request.getParameter("recurso10");
						 recurso11=request.getParameter("recurso11");
						 recurso12=request.getParameter("recurso12");
					
						if(grupo1!=null && !grupo1.equals("")){
							listResourcesArtist.add(grupo1);}
						if(grupo2!=null  && !grupo2.equals("")){
							listResourcesArtist.add(grupo2);}
						if(grupo3!=null  && !grupo3.equals("")){
							listResourcesArtist.add(grupo3);}
						if(grupo4!=null  && !grupo4.equals("")){
							listResourcesArtist.add(grupo4);}
						if(recurso1!=null  && !recurso1.equals("")){
							listResources.add(recurso1);}
						if(recurso2!=null && !recurso2.equals("")){
							listResources.add(recurso2);}
						if(recurso3!=null && !recurso3.equals("")){
							listResources.add(recurso3);}
						if(recurso4!=null && !recurso4.equals("")){
							listResources.add(recurso4);}
						if(recurso5!=null && !recurso5.equals("")){
							listResources.add(recurso5);}
						if(recurso6!=null && !recurso6.equals("")){
							listResources.add(recurso6);}
						if(recurso7!=null && !recurso7.equals("")){
							listResources.add(recurso7);}
						if(recurso8!=null && !recurso8.equals("")){
							listResources.add(recurso8);}
						if(recurso9!=null && !recurso9.equals("")){
							listResources.add(recurso9);}
						if(recurso10!=null && !recurso10.equals("")){
							listResources.add(recurso10);}
						if(recurso11!=null && !recurso11.equals("")){
							listResources.add(recurso11);}
						if(recurso12!=null && !recurso12.equals("")){
							listResources.add(recurso12);}
					    Map<Integer, Collection<DbPediaResource>> mapUrlResources = null;
					    Model modelUrlResources=null;
				        
				        //Send the resources in order to treat them
				        listInterestedResources=DAO_Interface.getUserResources(listResources);
				     	listInterestedArtists=DAO_Interface.getUserArtist(listResourcesArtist);
				     	if(listInterestedArtists.size()==0 && listInterestedResources.size()==0){
				     		
							System.out.print("Recursos introducidos vacios");
							request.setAttribute("mensajeError", "Hay que introducir por lo menos un recurso interesante.");
							rd=request.getRequestDispatcher("error.jsp");
					    }else{
				     	
							
					    	mapUrlResources=Jenate.searchResources(name, listInterestedResources, listInterestedArtists);
					    	
							listInterestedArtists=null;
					        ArrayList<DbPediaResource> urlsResources = new ArrayList<DbPediaResource>();
					        ArrayList<DbPediaResource> urlsPersons= new ArrayList<DbPediaResource>();
					        ArrayList<DbPediaResource> urlsPlaces= new ArrayList<DbPediaResource>();
					        ArrayList<DbPediaResource> urlsArtists = MyMap.convertMapToArrays(mapUrlResources,urlsResources,urlsPersons, urlsPlaces);
					    
					       
					        mapUrlResources=null;

					       
					       if(urlsArtists.size()<2 && urlsPersons.size()<2 && urlsPlaces.size()<2 && urlsResources.size()<2 ){
					    	   ///SE PODRIA HACER UN DESARROLLO O DEVELOP MAS, EN CASO DE QUE NO HAYA SUFICIENTES
					    		request.setAttribute("mensajeError", "No se han encontrado suficientes recomendaciones.");
					    		request.setAttribute("mensajeError2", "Por favor introduzca más información.");
								rd=request.getRequestDispatcher("error.jsp");
					       }
					       
					        request.setAttribute("urlsResources", urlsResources);     
					        request.setAttribute("urlsPersons", urlsPersons);   
					        request.setAttribute("urlsArtists", urlsArtists);     
					        request.setAttribute("urlsPlaces", urlsPlaces);
					        //session.setParameter("urlsResources", urlsResources);
					        
					        session.setAttribute("urlsPlaces",urlsPlaces); 
					        session.setAttribute("urlsResources",urlsResources); 
					        session.setAttribute("urlsArtists",urlsArtists); 
					        session.setAttribute("urlsPersons",urlsPersons); 
					        //Envimos recursos to the DB
					       
					       
					        rd= request.getRequestDispatcher("recomendaciones.jsp");
				        
				     	}
					}else{
			    	    request.setAttribute("mensajeError", "Error generando usuario.");
			 	       rd=request.getRequestDispatcher("error.jsp");
					}
					
			

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
    	    request.setAttribute("mensajeError", "Error generando conexion con base de datos.");
	       rd=request.getRequestDispatcher("error.jsp");
	       e1.printStackTrace();
		
		}
	/*	catch (QueryParseException  e1) {
			// TODO Auto-generated catch block
			request.setAttribute("mensajeError", "Error en la consulta SPARQL a datos,.");

			rd= request.getRequestDispatcher("error.jsp");
	      	  e1.printStackTrace();
		}*/
		
		catch (StringIndexOutOfBoundsException e1) {
			// TODO Auto-generated catch block
			request.setAttribute("mensajeError", "Error generación contenidos, con un recursos introducido,.");
		
			rd= request.getRequestDispatcher("error.jsp");
	      	  e1.printStackTrace();
		}
		catch (HttpException e1) {
			// TODO Auto-generated catch block
			request.setAttribute("mensajeError", "Error de tu conexion a Internet,.");
			request.setAttribute("mensajeError2", "Es indispensable una conexion a Internet estable.");
			rd= request.getRequestDispatcher("error.jsp");
	      	  e1.printStackTrace();
		}catch (JMBWSException e1 ) {
			// TODO Auto-generated catch block
			request.setAttribute("mensajeError", "Error de tu conexion a Internet,.");
			request.setAttribute("mensajeError2", "Es indispensable una conexion a Internet estable.");
			rd= request.getRequestDispatcher("error.jsp");
	      	  e1.printStackTrace();
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			request.setAttribute("mensajeError", "Error generando response.getWriter().");
			rd= request.getRequestDispatcher("error.jsp");
	      	  e1.printStackTrace();
		}
		catch (Error t) {
   	          request.setAttribute("mensajeError2", "Error enviando a recomendaciones.");
	      	  rd=request.getRequestDispatcher("error.jsp");
	      	  t.printStackTrace();
		} 
		finally{
				//rd=request.getRequestDispatcher("error.jsp");
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
		} catch (ClassNotFoundException e) {
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
		} catch (ClassNotFoundException e) {
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

