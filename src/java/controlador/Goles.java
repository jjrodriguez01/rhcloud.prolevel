/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import facade.FachadaTorneos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.GoleadoresDTO;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class Goles extends HttpServlet {

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
        if (request.getParameter("asignargoles")!=null && request.getParameter("goles")!=null) {
            FachadaTorneos facade = new FachadaTorneos();
            GoleadoresDTO gol = new GoleadoresDTO();
            
            long idJugador = Long.parseLong(request.getParameter("jugadores"));
            int nrogoles = Integer.parseInt(request.getParameter("nrogoles"));
            int idTorneo = Integer.parseInt(request.getParameter("idTorneo"));
            int idEquipo = Integer.parseInt(request.getParameter("equipo"));
            
            gol.setIdJugador(idJugador);
            gol.setNumeroGoles(nrogoles);
            gol.setIdTorneo(idTorneo);
            gol.setIdEquipo(idEquipo);
            
            //creo el arraylist q trae si ya esta este jugador en la tabla de goleadores
            ArrayList<GoleadoresDTO> existe = new ArrayList();
            existe = (ArrayList) facade.existeGoleador(idTorneo, idJugador, idEquipo);
            //si el array esta vacio es porq no esta en la tabla asi q lo inserto si procedimiento
            if (existe.isEmpty()) {
                String goles = facade.insertarPrimerGol(gol);
                response.sendRedirect("paginas/torneos/misTorneos.jsp?idTorneo="+idTorneo+"&goles="+goles);
            }else{//si no esta vacio tengo q aumentar el marcador con el procedimiento
            
            String goles = facade.insertarGoles(gol);
            
            response.sendRedirect("paginas/torneos/misTorneos.jsp?idTorneo="+idTorneo+"&goles="+goles);
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
