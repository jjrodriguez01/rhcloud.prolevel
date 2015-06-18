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
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.TarjetasDTO;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class Tarjetas extends HttpServlet {

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
       
            if (request.getParameter("asigtarjetas")!=null && request.getParameter("tarjetas")!=null) {
             
                TarjetasDTO tar = new TarjetasDTO();
                tar.setIdJugador(Integer.parseInt(request.getParameter("jugadores")));
                tar.setIdtorneo(Integer.parseInt(request.getParameter("idTorneo")));
                tar.setTarjetaAzul(!request.getParameter("azules").equals("")? Integer.parseInt(request.getParameter("azules")) : 0);
                tar.setTarjetaRoja(!request.getParameter("rojas").equals("")? Integer.parseInt(request.getParameter("rojas")): 0);
                FachadaTorneos facadeTorneo = new FachadaTorneos();
                LinkedList<TarjetasDTO> listar = new LinkedList();
                listar = (LinkedList) facadeTorneo.listarTarjetasJugador(tar.getIdtorneo(), tar.getIdJugador());
                //si no existe en la tabla de goleadores ese jugador en ese torneo quiere decir q es la
                //primera vez q se va a insertar su registro de tarjetas por lo que
                //lo hacemos sin el procedimiento, de lo contrario insertamos con el oprocedimienrto
                if (listar.isEmpty()) {
                    String itarjetas = facadeTorneo.insertarPrimera(tar);
                    response.sendRedirect("paginas/torneos/misTorneos.jsp?tarjetas="+itarjetas+"&idTorneo="+tar.getIdtorneo()+"#tablatarjetas");
                }else{
                    String itarjetas = facadeTorneo.insertarTarjetas(tar);
                    response.sendRedirect("paginas/torneos/misTorneos.jsp?tarjetas="+itarjetas+"&idTorneo="+tar.getIdtorneo()+"#tablatarjetas");
                }
            }else if(request.getParameter("disminuir") != null && request.getParameter("retirar") != null){
                String nombreEquipo = request.getParameter("nombreEquipo");//necesito devolver codigo y nombre del equipo
                int codigoEquipo = Integer.parseInt(request.getParameter("codigoEquipo"));
                int numero = Integer.parseInt(request.getParameter("numero"));
                int idJugador = Integer.parseInt(request.getParameter("idJugador"));
                int idTorneo = Integer.parseInt(request.getParameter("idTorneo"));
                
                FachadaTorneos facadeTorneo = new FachadaTorneos();
                //el metodo disminuir me devuelve 0 si el numero q se mando fue mayor a las tarjetas q habian
                //entonces no se disminuye nada y 1 si se logro disminuir correctamente o 3 si hubo algun error
                int respuesta = facadeTorneo.disminuir(numero, idJugador, idTorneo);
                if (respuesta == 1) {
                    response.sendRedirect("paginas/torneos/editarjugadores.jsp?codigoEquipo="+codigoEquipo+"&nombre="+nombreEquipo+"&idTorneo="+idTorneo+"&disminuir=ok");
                }else{
                    response.sendRedirect("paginas/torneos/editarjugadores.jsp?codigoEquipo="+codigoEquipo+"&nombre="+nombreEquipo+"&idTorneo="+idTorneo+"&disminuir=no");
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
            response.sendError(500, ex.toString());
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
            response.sendError(500, ex.toString());
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
