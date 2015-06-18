/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import facade.FachadaUsuarios;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.UsuariosDTO;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class RegistroUsuario extends HttpServlet {

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
        FachadaUsuarios facadeUsu = null;
        UsuariosDTO udto = null;
        if(request.getParameter("enviar")!= null && request.getParameter("registro")!= null){
            try{
          facadeUsu = new FachadaUsuarios();
          udto = new UsuariosDTO();
          udto.setIdUsuario(Long.parseLong(request.getParameter("cc")));
          udto.setPrimerNombre(request.getParameter("nombre"));
          udto.setSegundoNombre(request.getParameter("snombre"));
          udto.setPrimerApellido(request.getParameter("ape"));
          udto.setSegundoApellido(request.getParameter("sape"));
          udto.setFecha(request.getParameter("nac"));
          udto.setTelefono(request.getParameter("tel"));
          udto.setEmail(request.getParameter("email"));
          udto.setContraseña(request.getParameter("pass"));
          
          String ins = facadeUsu.insertarUsuario(udto);
          response.sendRedirect("index.jsp?registro="+ins);
            }catch(MiExcepcion mie){
                response.sendError(500, mie.getMessage());
            }
          
        }else if (request.getParameter("actudatos")!=null && request.getParameter("datos")!=null) {
            try{
            udto = new UsuariosDTO();
            facadeUsu = new FachadaUsuarios();
            udto.setIdUsuario(Long.parseLong(request.getParameter("cc")));
            udto.setPrimerNombre(request.getParameter("nombre"));
            udto.setSegundoNombre(request.getParameter("snombre"));
            udto.setPrimerApellido(request.getParameter("ape"));
            udto.setSegundoApellido(request.getParameter("sape"));
            udto.setTelefono(request.getParameter("tel"));
            udto.setEmail(request.getParameter("email"));
            udto.setContraseña(request.getParameter("pass"));
            String conf = facadeUsu.actualizarUsuario(udto);
            response.sendRedirect("paginas/admin.jsp?conf="+conf);
            }catch(MiExcepcion mie){
                response.sendError(500, mie.getMessage());
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
