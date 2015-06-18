/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import facade.FachadaTorneos;
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
public class Jugadores extends HttpServlet {

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
        if(request.getParameter("elijugador")!=null  && request.getParameter("celi")!=null){
            FachadaTorneos facade = new FachadaTorneos();
            long idUsuario = Long.parseLong(request.getParameter("idUsuario"));
            int codigoEquipo = Integer.parseInt(request.getParameter("codigoEquipo"));
            int idTorneo  = Integer.parseInt(request.getParameter("idTorneo"));
            String nombreEquipo = request.getParameter("nombreEquipo");
            String usuarioeli = facade.eliminarJugador(idUsuario,codigoEquipo);
            response.sendRedirect("paginas/torneos/editarjugadores.jsp?codigoEquipo="+codigoEquipo+"&nombre="+nombreEquipo+"&idTorneo="+idTorneo+"&usuarioeli="+usuarioeli);
        }else if(request.getParameter("injugador")!=null){
            FachadaTorneos facade = new FachadaTorneos();
            long idUsuario = Long.parseLong(request.getParameter("idUsuario"));
            int codigoEquipo = Integer.parseInt(request.getParameter("codigoEquipo"));
            int idTorneo  = Integer.parseInt(request.getParameter("idTorneo"));
            String nombreEquipo = request.getParameter("nombreEquipo");
            String newjugador = facade.inscribirJugadorAEquipo(codigoEquipo, idUsuario);
            response.sendRedirect("paginas/torneos/editarjugadores.jsp?codigoEquipo="+codigoEquipo+"&nombre="+nombreEquipo+"&idTorneo="+idTorneo+"&newjugador="+newjugador);
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
            response.sendError(500, ex.getMessage());
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
            response.sendError(500, ex.getMessage());
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