/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import facade.FachadaTorneos;
import facade.FachadaUsuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class Administrador extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        if (request.getParameter("cambiar")!=null) {
        Long idUsuario = Long.parseLong(request.getParameter("idUsuario"));
        int numRol = Integer.parseInt(request.getParameter("numRol"));
        FachadaUsuarios facade = new FachadaUsuarios();
        String cambio = facade.cambiarRol(numRol, idUsuario);
        response.sendRedirect("superadmin/administracion.jsp?cambio="+cambio);
        }else if(request.getParameter("eliminar")!=null){
            if (request.getParameter("confirmo")==null) {
                response.sendRedirect("superadmin/administracion.jsp?confirmacion=Debe confirmar que desea eliminar al usuario");
            }else{
                FachadaTorneos ftorneos = new FachadaTorneos();
                long id = Long.parseLong(request.getParameter("idUsuario"));
                long jugador = ftorneos.existeJugador(id);
                if (jugador!=0) {
                    response.sendRedirect("superadmin/administracion.jsp?existejugador=Este usuario es jugador en un equipo de un torneo en curso");
                }else{
                    FachadaUsuarios fusu = new FachadaUsuarios();
                    String eliminado = fusu.eliminarUsuario(id);
                    response.sendRedirect("superadmin/administracion.jsp?eliminado="+eliminado);
                }
            }
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
            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
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
