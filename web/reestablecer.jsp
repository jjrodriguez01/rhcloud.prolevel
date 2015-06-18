<%-- 
    Document   : reestablecer
    Created on : 7/03/2015, 07:40:22 PM
    Author     : jeisson
--%>

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
        </style>
        <title>Reestabler contraseña</title>
        <script>
            $(document).ready(function(){
                $("#norecuperar").fadeOut(25500);
                $("#acontactanos").on("click", function(){
                    $("#contactanos").modal("show");
                });
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
        <li class="active"><a href="#">Reestablecer contraseña <span class="sr-only">(current)</span></a></li>
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
                    <li class="active"><a href="#">Reestablecer Contraseña</a></li>
                </ol>
            </div>
        </div>
            <div id="j" class="row" >
                <div class="">
                <h1>Reestablecer Contraseña</h1>
                <P>Si has olvidado tu contraseña, diligencia el formulario abajo y
                te enviaremos un correo electrónico</P>
                </div>
                <div class="row">
                <div id="formu" class="col-xs-12 col-sm-12 col-md-12 col-lg-6 center-block">
                    <form role="form" action="Recuperar">
  <div class="form-group">
      <label for="email">Email<span class="requerido"> *</span></label>
      <input type="email" name="email" id="email" class="form-control" id="ejemplo_email_1"
           placeholder="Introduce tu email" required>
  </div>
    <div class="form-group">
    <div class="g-recaptcha" data-sitekey="6Lc-KAMTAAAAACflZ_XeCXakQGNwb3FO0clkK8Ph"></div>
    </div>
                        <button type="submit" name="recuperar" value="enviar" class="btn btn-default">Enviar</button>
</form>
                    <%  
                    if (request.getParameter("captcha")!=null && request.getParameter("captcha").equals("invalido") ) {
                    %>
<div class="alert alert-danger" role="alert">
  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  <span class="sr-only">Error:</span>
  Complete el captcha
</div>
                    <%
                        }else if(request.getParameter("recuperar")!=null && request.getParameter("recuperar").equals("no")){
                    %>
<div id="norecuperar" class="alert alert-danger" role="alert">
<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  <span class="sr-only">Error:</span>
  Ha ocurrido algo y no hemos podido reestablecer tu contraseña,
   inténtalo de nuevo o ponte en contacto con el administrador.
</div>                        
                    <%
                        }
    
                    %>
                </div>
                </div>
            </div>
                
<div class="modal fade" id="contactanos">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title">Contáctanos</h3>
        <h6>Diligencia el formulario y envíanos tu opinion.</h6>
      </div>
      <div class="modal-body">
          <form method="POST" action="Contactanos">
          <div class="form-group">
            <label for="emailusu" class="control-label">Tu correo:</label>
            <input type="email" maxlength="50" class="form-control" id="emailusu" name="emailusu">
          </div>
          <div class="form-group">
            <label for="mensaje" class="control-label">Tu mensaje para nosotros:</label>
            <textarea class="form-control" id="mensaje" name="mensaje" maxlength="200"></textarea>
          </div>
              <button type="submit" class="btn btn-primary" name="btncontactanos">Enviar</button>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal --> 
        </main>
    </body>
</html>
