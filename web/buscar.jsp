<%-- 
    Document   : reestablecer
    Created on : 7/03/2015, 07:40:22 PM
    Author     : jeisson
--%>

<%@page import="modelo.TorneoDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="facade.FachadaTorneos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <link rel="shortcut icon" href="imagenes/favicon.ico">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/estilosreestablecer.css">
        <script type="text/javascript" src="js/jquery-1.9.1.js"></script>
        <script type="text/javascript" src="css/bootstrap/js/bootstrap.min.js"></script>
        <script src='https://www.google.com/recaptcha/api.js'></script>
        <style>
body{
    padding-top: 5px;
    margin: 0 10%;
}
nav{
    background: linear-gradient(white, whitesmoke);
}
table th{
    font-family: castellar, verdana, lucida-saens;
}
table td{
    font-family: elephant, verdana, lucida-saens;
}
        </style>
        <title>Pro-level</title>
        <script>
            $(document).ready(function(){
                $("#noresultado").fadeOut(25500);
            });
        </script>
    </head>
    <body>
        <header class="container">
            <nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
        <a class="navbar-brand" href="#">
            <img alt="pro-level" src="imagenes/logo.png" style="width: 35px; height: 40px">
        </a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="reestablecer.jsp">Reestablecer contraseña <span class="sr-only">(current)</span></a></li>
        <li><a href="index.jsp">Ingresar</a></li>
        <li><a href="registro.jsp">Registrarme</a></li>
      </ul>
        <form class="navbar-form navbar-left" role="search" action="Buscador">
        <div class="form-group">
          <input type="text" name="palabra" maxlength="20" class="form-control" placeholder="Buscar...">
        </div>
        <button type="submit" class="btn btn-default">Buscar en Pro-level</button>
      </form>
        <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Nosotros <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">¿Quienes Somos?</a></li>
            <li><a id="acontactanos" href="#">Contáctanos</a></li>
          </ul>
        </li>
    </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
        </header>
        <main class="container">
            
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><a href="index.jsp">Inicio</a></li>
                        <li class="active"><a href="#">Búsqueda</a></li>
                    </ol>
                </div>
            </div>
<%  
    FachadaTorneos facade = new FachadaTorneos();
    ArrayList<TorneoDTO> torneos = new ArrayList();
    torneos=(ArrayList)facade.buscar(request.getParameter("palabra"));
    if (torneos.isEmpty()) {
%>
<div class="alert alert-danger" role="alert" id="noresultado">
  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  <strong>Ningún torneo coincide con tu búsqueda</strong>
</div>
<%
        }else{
%>
<div class="row">
    <div class="col-lg-9 col-md-9 col-sm-12">
        <table class="table table-condensed table-responsive">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Fecha de inicio</th>
                    <th>Fecha fin</th>
                    <th>Genero</th>
                </tr>
            </thead>
            <tbody>
<%
        for(TorneoDTO t : torneos){
 %>
 <tr>
            <td><%=t.getNombre()%></td>
            <td><%=t.getFechaInicio()%></td>
            <td><%=t.getFechaFin()%></td>
            <td><%=t.getGenero()%><td>
 </tr>
 <%
        }
%>
            </tbody>
        </table>
    </div>
</div>
<%  
    } 
%>
        </main>
    </body>
</html>
