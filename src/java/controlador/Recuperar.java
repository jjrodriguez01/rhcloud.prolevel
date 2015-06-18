/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.correo.Correo;
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
public class Recuperar extends HttpServlet {

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
        if (request.getParameter("email")!=null  && !request.getParameter("g-recaptcha-response").equals("")) {
            
            String email = request.getParameter("email").trim();
            String asunto = "Recordatorio contraseña Pro-level";
            FachadaUsuarios fusu = new FachadaUsuarios();
            String recuperada = fusu.recuperarPass(email);
            String cuerpo = "<p>Estimado usuario</p>"
                    + "<p>Su contraseña de acceso al sistema es "
                    + "<span style='color:red'>"+recuperada+"</span></p>"
                    + "<p>Le recordamos que puede cambiarla en cualquier momento accediendo a la seccion</p>"
                    + "<p>Perfil -> Administrar</p>"
                    + "<a>Cordialmente Pro-level</a>";
            if (Correo.sendMail(asunto, cuerpo, email)) {
                response.sendRedirect("index.jsp?recuperacion=ok");
            }else{
                response.sendRedirect("reestablecer.jsp?recuperar=no"+email);
            }    
        }else if(request.getParameter("g-recaptcha-response").equals("")){
            response.sendRedirect("reestablecer.jsp?captcha=invalido");
        }
        else{
            response.sendRedirect("reestablecer.jsp?capcha=invalido");
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
            Logger.getLogger(Recuperar.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Recuperar.class.getName()).log(Level.SEVERE, null, ex);
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


