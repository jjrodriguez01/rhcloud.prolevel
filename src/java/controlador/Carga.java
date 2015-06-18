/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.CargaInicial;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class Carga extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        DiskFileItemFactory ff = new DiskFileItemFactory();

        ServletFileUpload sfu = new ServletFileUpload(ff);

        List items = null;
        File archivo = null;
        try {
            items = sfu.parseRequest(request);
        } catch (FileUploadException ex) {
            out.print(ex.getMessage());
        }
        for (int i = 0; i < items.size(); i++) {

            FileItem item = (FileItem) items.get(i);

            if (!item.isFormField()) {

//                archivo = new File(this.getServletContext().getRealPath("/datos/") +"/" + item.getName());
                archivo = new File("/var/lib/openshift/55672e834382ec6dbc00010d/jbossews/webapps/" + item.getName());
                try {
                    item.write(archivo);
                } catch (Exception ex) {
                    out.print(ex.getMessage());
                }
            }
            
        }
        out.print("Se subio el archivo"+this.getServletContext().getRealPath("/datos/"));

        CargaInicial cargar;       
        try {
            cargar = new CargaInicial();
            String ubicacion = archivo.toString();        
            cargar.cargar(ubicacion);
        } catch (MiExcepcion ex) {
            out.print("Ha ocurrido un error de conexión a base de datos.");
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
        processRequest(request, response);
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
        processRequest(request, response);
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
