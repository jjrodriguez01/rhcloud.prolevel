<%-- 
    Document   : superadmin
    Created on : 4/04/2015, 12:17:36 AM
    Author     : jeisson
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="facade.FachadaUsuarios"%>
<%@page import="persistencia.UsuariosDAO"%>
<%@page import="modelo.UsuariosDTO"%>
<% 
            response.setHeader("Cache - Control", "no - cache");

            response.setHeader("Cache - Control", "no - store");

            response.setDateHeader("Expires",0);
            if (request.getSession()!=null) {
                    
                    HttpSession miSession=request.getSession(false);
                    int rol = (Integer)miSession.getAttribute("rol");
                    if(rol == 3){
                    FachadaUsuarios facade = new FachadaUsuarios();
                    ArrayList<UsuariosDTO> listarUsuarios = new ArrayList();
                    listarUsuarios = (ArrayList) facade.listarUsuariosConRoles();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración</title>
        <link rel="shortcut icon" href="../imagenes/favicon.ico">
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
        <link href="../css/estiloslayout.css" rel="stylesheet" type="text/css">
        <link href="../css/semantic/semantic.min.css" rel="stylesheet" type="text/css">
        <link href="../js/dataTables/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../js/jquery-2.1.1.js"></script>
        <script type="text/javascript" src="../css/semantic/semantic.min.js"></script>
        <script type="text/javascript" src="../js/jugadoresEquipos.js"></script>
        <script type="text/javascript" src="../js/dataTables/js/jquery.dataTables.js"></script>
        <style>
            .menu-opciones{
                 clear: both;
                padding-top: 10px;
            }
            body{
                margin-left: 5%;
                margin-right: 5%;
                margin-top: 5px;
            }
        </style>
        <script>
            $(document).ready(function(){
        $("#tusuarios").dataTable({
                    language:{
                        url: "../js/dataTables/js/dataespañol.json"
                    } 
                });
        $('select.dropdown').dropdown();
        $('.message').fadeOut(25000);
        $('.ui.checkbox').checkbox();
    });
        </script>
    </head>
    <body>
        <header>
            <nav>
        <div class="ui one column centered grid">
            <div class="column">
                <div class="ui inverted menu">
                    <a class="active item">
                        <i class="fa fa-user"></i> Cambiar Roles
                    </a>
                    <a class="item" href="../Ingreso?logout=cerrar">
                      <i class="fa fa-sign-out"></i> Salir
                    </a>
                  </div>
            </div>
              </div>
            </nav>
        </header>
<main>
       
    <article>
      <form name="cargar" action="Carga" method="POST" enctype="multipart/form-data">
          <input type="file" name="archivo" class="ui teal button"/>
          <button name="subir" value="subir" class="ui green button">Cargar</button>
      </form>
  </article>
        </div>
    </div>
    
</main>
    </body>
</html>
<% 
    }//si el rol fue 3
                    
}//si hay sesion
                    else{
                        response.sendRedirect("../../index.jsp?auth=prohibido");
                    }
%>
