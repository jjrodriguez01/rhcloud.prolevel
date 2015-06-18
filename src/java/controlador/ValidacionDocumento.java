/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

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
public class ValidacionDocumento extends HttpServlet {

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
        PrintWriter out = response.getWriter();
            if (request.getParameter("jugador")!=null && request.getParameter("idTorneo")!= null) {
            StringBuilder respuesta = new StringBuilder("");
            FachadaUsuarios facadeUsu = new FachadaUsuarios();
            long cc = Long.parseLong(request.getParameter("jugador").trim());
            int idTorneo = Integer.parseInt(request.getParameter("idTorneo"));
            respuesta.append(facadeUsu.validarDocumento(cc,idTorneo));
            this.writeResponse(response, respuesta.toString());
        }else{
               try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet validacionDocumento</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet validacionDocumento at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }finally{
                   out.close();
               } 
            }  
    }
    public void writeResponse(HttpServletResponse response, String output)throws IOException{
        response.setContentType("text/plain");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("content","text/html; charset=utf-8");
        response.getWriter().write(output);
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
            Logger.getLogger(ValidacionDocumento.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ValidacionDocumento.class.getName()).log(Level.SEVERE, null, ex);
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