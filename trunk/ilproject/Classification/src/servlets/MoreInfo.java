package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import servicios.ServicioUsuarios;
import com.hp.hpl.jena.rdf.model.Resource;

import dominio.DbPedia;
import dominio.DbPediaResource;
import dominio.Usuario;


public class MoreInfo extends HttpServlet {
   
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
    	
    	System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    	System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX  MORE INFO  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    	System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        //Declaration types
        String login = null;
        String email = null;
        String pass = null;
        DbPediaResource resource=null;
        HttpSession session = null;
        Usuario usuario = null;
        String pathResource=null;

        /////PARAMETER---->>FOr STRING
        /////ATRIBUTE----->>FOR OBJECTS
        	
    	//main
    	response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
        request.setAttribute("mensajeError", "Error acondicionando contenidos del recurso.");
        System.out.println("MORE INFO : ENTRA");
        try {
            session=request.getSession();
           
            usuario=(Usuario) session.getAttribute("usuario");
           // email=(String) session.getAttribute("email");
		   // pass=(String) session.getAttribute("password");
            System.out.println("sigue");
            pathResource= request.getParameter("pathResource");
            System.out.println("siguiendo");
		    // request.setAttribute("usuario", usuario);
		    //session.setAttribute("usuario", usuario);
	     if(usuario==null){
	    	    request.setAttribute("mensajeError2", "Error recibiendo usuario.");
	    	    System.out.println("MORE INFO : Error recibiendo usuario.");
	     }else{
	    	 //pathResource=resource.getUri();
	    	 //Send the resources in order to treat them
	    	 System.out.println("MORE INFO : COMIENZO PARA "+pathResource);
			 DbPediaResource dp=DbPedia.getMoreInfo(pathResource);
		     System.out.print("Info del resource: "+dp.getName()+" "+ dp.getUri());
		     request.setAttribute("dp", dp);     
    		 rd=request.getRequestDispatcher("ficha_recurso.jsp");
	     }
    	/*} catch (SQLException e1) {
			// TODO Auto-generated catch block
    	    request.setAttribute("mensajeError", "Error generando conexion con base de datos.");
	       rd=request.getRequestDispatcher("error.jsp");
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
    			*/
    			}
	     catch (Exception e) {
				   request.setAttribute("mensajeError", "Error acondicionando contenidos del recurso.");
				   rd=request.getRequestDispatcher("error.jsp");
				   System.out.println("MORE INFO : Error leyendo datos.");
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

