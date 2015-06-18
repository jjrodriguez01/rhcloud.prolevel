/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import facade.FachadaUsuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.UsuariosDTO;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class Ingreso extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MiExcepcion {
            if (request.getParameter("ingresar") != null) {
                String email = request.getParameter("email").trim();
                String contraseña = request.getParameter("pass").trim();
                FachadaUsuarios facadeUsu = new FachadaUsuarios();
                UsuariosDTO datosUsuario = new UsuariosDTO();
                if (email.equals("superadmin@prolevel.com") && contraseña.equals("prolevel")) {
                    HttpSession miSesion = request.getSession(true);
                    int rolSuperadmin = 3;
                    String usuario = "";
                    miSesion.setAttribute("rol", rolSuperadmin);
                    miSesion.setAttribute("usr", usuario);
                    response.sendRedirect("superadmin/administracion.jsp");
                }else{
                long cc = facadeUsu.validarUsuario(email, contraseña);
                if (cc != 0) {
                    datosUsuario = facadeUsu.getUsuario(cc);
                    int numerorol = facadeUsu.getRol(datosUsuario);
                    if(datosUsuario!=null && numerorol!=0){   
                    HttpSession miSesion = request.getSession(true);
                    miSesion.setAttribute("usr", datosUsuario);
                    miSesion.setAttribute("rol", numerorol);
                    response.sendRedirect("paginas/inicio.jsp"); 
                    }              
                }else {
                response.sendRedirect("index.jsp?auth=noauth");
                }
            }
            }else if(request.getParameter("logout")!=null){
                    HttpSession miSesion = request.getSession(false);
                    miSesion.removeAttribute("usr");
                    miSesion.removeAttribute("rol");
                    miSesion.invalidate();
                    response.sendRedirect("index.jsp?sesion=cerrada");
            }
            else{
                response.sendRedirect("index.jsp?action=noaction");
            }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
        } catch (MiExcepcion ex) {
            Logger.getLogger(Ingreso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
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
        } catch (MiExcepcion ex) {
            Logger.getLogger(Ingreso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

