/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.*;
import javax.servlet.http.HttpSession;
import servicios.*;

/**
 *
 * @author chemi
 */
public class Login extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session=request.getSession();
            String login=request.getParameter("login");
            String pass=request.getParameter("pass");
            if (!login.equalsIgnoreCase("") && !pass.equalsIgnoreCase("")) {
                Usuario usuario = ServicioUsuarios.getInstancia().validaUsuario(login, pass);
                if (usuario == null) {
                    request.setAttribute("mensajeError", "El usuario introducido no existe o contraseña invalida.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
                else {
                    session.setAttribute("usuario", usuario);
                    request.setAttribute("usuario", usuario);
                    session.setAttribute("login", login);
                    System.out.print("El usuario name ="+login);
                    request.getRequestDispatcher("menu.jsp").forward(request, response);
                }
            }
            else
            {
                request.setAttribute("mensajeError", "Debe introducir su login y contraseña");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("mensajeError", "Error validando el usuario.");
            e.printStackTrace();
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } finally {
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
        processRequest(request, response);
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
        processRequest(request, response);
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
