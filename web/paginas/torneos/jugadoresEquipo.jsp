<%-- 
    Document   : jugadoresEquipo
    Created on : 9/02/2015, 01:22:21 AM
    Author     : jeisson
--%>
<%@page import="utilidades.MiExcepcion"%>
<%@page import="facade.FachadaTorneos"%>
<%@page import="java.util.LinkedList"%>
<%@page import="modelo.JugadoresporequipoDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%
        try{
        FachadaTorneos facadeTorneos = new FachadaTorneos();
        LinkedList<JugadoresporequipoDTO> jugador = new LinkedList<JugadoresporequipoDTO>();
        if (request.getParameter("codigo")!=null) {
            
        jugador = (LinkedList) facadeTorneos.jugadoresDeEquipo(Integer.parseInt(request.getParameter("codigo")));
        if(jugador.size()>0){      
        for(JugadoresporequipoDTO jeq : jugador){
         out.write("<option value = " + jeq.getCodigoJugador() + ">" + jeq.getNombreJugador()+ "</option>");            
                }
            }
        }else{
        out.write("<h1></h1>");
        }
        
        }catch(MiExcepcion mie){
            response.sendError(500, mie.getMessage());
        }
        %>
    </body>
</html>
