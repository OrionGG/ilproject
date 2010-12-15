package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

import dao.DAO_MySQL;
import dao.DAO_OntModel;
import dominio.DbPedia;
import dominio.DbPediaResource;
import dominio.Usuario;


public class DeleteFavorite extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException 
     */
    @SuppressWarnings("null")
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException {
    	
        //Declaration types
        String login = null;
        String email = null;
        String pass = null;
        String pathResource = null;
        HttpSession session = null;
        Usuario usuario = null;
        RequestDispatcher rd = null; 
    	PrintWriter out = null;
 
    	out = response.getWriter();
        request.setAttribute("mensajeError", "Error acondicionando favoritos del usuario.");
		List <String>listInterestedResources=new ArrayList <String>();
    	  rd=request.getRequestDispatcher("error.jsp");
    	//main
    	response.setContentType("text/html;charset=UTF-8");

        try {
        	

        	
            session=request.getSession();
            login=request.getParameter("name");
            email=request.getParameter("email");
     
        	pathResource=request.getParameter("pathResource");
        	
            ArrayList<DbPediaResource> favoritesDPR=ServicioUsuarios.deleteFavorite(login,pathResource);
            
                
           
        	
        	request.setAttribute("favoritesDPR", favoritesDPR); 
           request.setAttribute("mensajeUsuario", "Tus recomendaciones han cambiado.");
	       rd=request.getRequestDispatcher("favoritos.jsp");
	       
        } catch (Exception e) {
				
		          request.setAttribute("mensajeError", "Error acondicionando favoritos del usuario.");
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

