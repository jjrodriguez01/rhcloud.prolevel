<%-- 
    Document   : crear_torneo
    Created on : 24/02/2015, 04:18:46 AM
    Author     : jeisson
--%>
<%@page import="persistencia.UsuariosDAO"%>
<%@page import="modelo.UsuariosDTO"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<sql:query var="torneo" dataSource="${sessionScope.cnn}">
    SELECT idTorneo, nombre FROM torneo
</sql:query>
<%
    if (request.getSession() != null && request.getSession().getAttribute("usr")!=null) { 
                UsuariosDTO udto = new UsuariosDTO();
                    UsuariosDAO udao = new UsuariosDAO();
                    HttpSession miSession=request.getSession(false);
                    udto = (UsuariosDTO)miSession.getAttribute("usr");
                    int rol = (Integer)miSession.getAttribute("rol");
                    if (rol == 1) {
%>    
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Crear Torneos</title>
        <link href="../../js/datepicker/jquery-ui.min.css" rel="stylesheet" type="text/css">
        <link href="../../css/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
        <link href="../../css/estiloslayout.css" rel="stylesheet" type="text/css">
        <link href="../../css/estiloscrear_torneo.css" rel="stylesheet" type="text/css">
        <link href="../../js/select/jquery-ui.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../../js/listaTorneo.js"></script>
        <script type="text/javascript" src="../../js/jquery-1.9.1.js"></script>
        <script type="text/javascript" src="../../js/jquery.validate.js"></script>
        <script type="text/javascript" src="../../css/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../../js/datepicker/jquery-ui.min.js"></script>
        <script type="text/javascript" src="../../js/validacionTorneos.js"></script>
        <script type="text/javascript" src="../../js/select/jquery-ui.min.js"></script>
<script>
$(document).ready(function() {
    $(function() {
   $( ".datepicker" ).datepicker( "option", "minDate", 0 );
   $( ".datepicker" ).datepicker( "option", "dateFormat", "yy-mm-dd" );
  });
});
</script>
</head>
<body>
<header>
<nav>
    <ul id="nav" class="nav">
        <li><a href="../inicio.jsp"><img src="../../imagenes/inicio.png" width="24" height="24" alt="inicio" /> INICIO</a></li>
        <li><a href="#"><span><img src="../../imagenes/copa.png" width="24" height="24" alt="copa" /> TORNEOS</span></a>
            <div class="subs">
                <div class="col">
                    <ul>
                        <li><a><img src="../../imagenes/micopa.png" width="24" height="24" alt="micopa"/>MIS TORNEOS</a>
                            <div class="subs">
                                <div class="col">
                                    <ul>
                                        <c:forEach var="row" items="${torneo.rows}">
                                            <li><a href="misTorneos.jsp?idTorneo=${row.idTorneo}">${row.nombre}</a></li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </li>
                        <li><a href="crear_torneo.jsp"><img src="../../imagenes/crear.png" width="24" height="24" alt="crear" />CREAR TORNEOS</a></li>
                    </ul>
                </div>
            </div>
                    </li>
                    <li><a href="../servicios.jsp"><img src="../../imagenes/servicios.png" width="24" height="24" alt="servicios" />SERVICIOS</a></li>
                    <li><a href="#"><span><img src="../../imagenes/perfil.png" width="24" height="24" alt="perfil" />PERFIL</span></a>
                        <div class="subs">
                            <ul>
                                <li><a href="../admin.jsp"><img src="../../imagenes/ajustes.png" width="24" height="24" alt="ajustes" />ADMINISTRAR</a></li>
                                <li><a href="../../Ingreso?logout=cerrar"><img src="../../imagenes/out.png" width="24" height="24" alt="cerrar" />CERRAR CESIÓN</a></li>
                            </ul>
                        </div>
                    </li>
    </ul>
</nav>
    <div class="pull-right"><span class="label label-success"> <%=udto.getPrimerNombre()%></span><span class="badge">Administrador</span></div>
</header>  
<main>
    <div id="encabezado" class="row">
        <div class="col-lg-12">
            <hgroup>
            <h1 id="titulo">CREAR UN NUEVO TORNEO</h1>
            <h2>Bienvenido al centro de creacion de torneos</h2>
            </hgroup>
            <div class="container"><hr/></div>
        </div>
        <div class="row">
            <div class="col-lg-12">
            <section class="container" id="informacion">
                <h6>Estos son los torneos que puede crear:</h6>
<div class="row">
                <p class="info"
data-toggle="popover" 
title="Copa" data-content="Los equipos se dividen en grupos de 4,
pasan los 2 primeros de cada grupo a cuadrangulares de eliminacion directa" >
<img src="../../imagenes/flecha.png" width="24" height="24" />¿Que es una copa?
                </p>
</div>
<div class="row">
                <p class="info" 
data-toggle="popover" 
title="Liga" data-content="Los equipos se enfrentan todos contra todos, quien al final de todos los 
partidos tenga mayor cantidad de puntos gana, si hay empate entre dos o mas equipos en puntos 
se decidira al campeon por cantidad de goles anotados">
<img src="../../imagenes/flecha.png" width="24" height="24" />¿Que es una liga?
                </p>
</div>
<div class="row">
                <p class="info" data-toggle="popover" 
title="Eliminatoria" data-content="Torneo formato knock-out, los equipos se enfrentan en partido simple quien pierda sera eliminado">
<img src="../../imagenes/flecha.png" width="24" height="24" />¿Que es una eliminatoria?
                </p>
</div>
<script>                           
$('[data-toggle="popover"]').popover(
                {
                    trigger: 'hover',
                    html: true,
                    delay: 300,
                }
            );
</script>
            </section>
            </div>
        </div>
        </div>
    
    <div class="container">        
    <div id="ftorneos" class="row">
        <div class="hide">
            <div class="row">
                <header>
                    <img src="../../imagenes/balon.png" width="40" height="40" alt="copa">
                    <h3>COPA</h3>
                </header>
            </div>
            <hr/>
            <div class="row">
<form id="copa" method="get" action="../../GestionCopa" role="form"> 
    <div class="form-group">
        <label for="nombreTorneo">Nombre del torneo</label>
      	<input id="nombreTorneo" name="nombreTorneo" type="text" maxlength="25" required>
    </div>
    <div class="form-group">
        <label for="capacidad">Capacidad de equipos</label>
        <select id="capacidad" name="capacidad" required>
            <option></option>
            <option>16</option>
            <option>32</option>
        </select>
    </div>
    <div class="form-group">
        <label for="grupos">Numero de equipos en caga grupo</label>
        <select id="grupos" name="numgrupos" required>
            <option></option>
            <option>4</option>
        </select>
    </div>
    <div class="form-group">
        <label>En la fase de grupos</label>
        <label for="idaVueltagrupos">¿Se juegan partidos de ida y vuelta?</label>
        <select id="idaVueltagrupos" name="idaVueltagrupos" title="Debe seleccionar algo" required>
          <option></option>
          <option>Si</option>
          <option>No, partido único</option>
        </select>
    </div>
    <div class="form-group">
        <label>En la ronda eliminatoria</label>
        <label for="idaVueltaeli">¿Se juegan partidos de ida y vuelta?</label>
        <select id="idaVueltaeli" name="idaVueltaeli" required>
           <option></option>
           <option>Si</option>
           <option>No, partido único</option>
         </select>
    </div>
    <div class="form-group">
        <label for="finalIdaVuelta">¿El partido de la final se juega de ida y vuelta?</label>
        <select id="finalIdaVuelta" name="finalIdaVuelta" required>
            <option></option>
            <option>Si</option>
            <option>No, partido único</option>
        </select>
    </div>
    <div class="form-group">
        <label for="tercer">¿Se juega partido pot tercer puesto?</label>
        <select id="tercer" name="tercer" required>
          <option></option>
          <option>Si</option>
          <option>No</option>
        </select>
    </div>
    <div class="form-group">
        <label>Torneo:</label>
        <select id="tipo" name="tipo">
            <option></option>
            <option>Masculino</option>
            <option>Femenino</option>
        </select>
    </div>
    <div class="form-group">
        <label for="inicio">Fecha de inicio:</label>
        <input type="text" class="datepicker" name="inicio" required>
    </div>
    <div class="form-group">
        <label for="fin">Fecha de finalizacion:</label>
        <input type="text" class="datepicker" name="fin" required>
    </div>                                                           
    <div class="form-group">                   
        <button class="btn btn-primary" type="submit" name="copa">CREAR</button>
        <input type="hidden" name="enviarcopa" />
    </div>
  </form>
            </div>
            <%//si hay parametro de q se creo una copa
              if (request.getParameter("copa")!=null) {
%>
<div class="alert alert-success alert-dismissible" role="alert">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  <strong><span class="glyphicon glyphicon-ok" aria-hidden="true"></span><%=request.getParameter("copa")%></strong>
</div>
<% 
                  }
          
            %>
            <div class="row">
                
            </div>
        </div><%--fin col de copa--%>
        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
           <div class="row">
                <header>
                    <img src="../../imagenes/balon.png" width="40" height="40" alt="copa">
                    <h3>Eliminatoria</h3>
                </header>
            </div>
            <hr/>
            <div class="row">
                <form id="eliminatoria" action="../../GestionEliminatoria">
    <div class="form-group">
        <label for="nombreTorneo">Nombre del torneo</label>
        <input id="nombreTorneo" name="nombreTorneo" type="text" maxlength="25" required>
    </div>
    <div class="form-group">
        <label for="capacidad">Capacidad de equipos</label>
        <select id="capacidad" name="capacidad">
            <option></option>
            <option>16</option>
            <option>20</option>
        </select>
    </div>
    <div class="form-group">
        <label for="finalIdaVuelta">¿El partido de la final se juega de ida y vuelta?</label>
        <select id="finalIdaVuelta" required>
            <option></option>
            <option id="finaIidaVueltaSi">Si</option>
            <option id="finalIdaVueltaNo">No, partido único</option>
        </select>
    </div>
    <div class="form-group">    
        <label for="tipo">Torneo:</label>
        <select name="tipo">
            <option></option>
            <option>Masculino</option>
            <option>Femenino</option>
        </select>
    </div>
    <div class="form-group">
        <label for="inicio">Fecha de inicio:</label>
        <input type="text" class="datepicker" name="inicio" required>
    </div>
    <div class="form-group">
        <label for="fin">Fecha de finalizacion:</label>
        <input type="text" class="datepicker" name="fin" required>
    </div>
        
    <button class="btn btn-primary" type="submit" name="eliminatoria">CREAR</button>
    <input type="hidden" name="enviareli" />
</form>
        </div>
            <%
            if (request.getParameter("eliminatoria")!=null) {
                    %>
<div class="row">
<div class="alert alert-success alert-dismissible" role="alert">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  <strong><span class="glyphicon glyphicon-ok" aria-hidden="true"></span><%=request.getParameter("eliminatoria")%></strong>
</div>
</div>                    
                    <%
                }
  
            %>
        </div><%--col de eli--%>
        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
            <div class="row">
                <header>
                    <img src="../../imagenes/balon.png" width="40" height="40" alt="copa">
                    <h3>Liga</h3>
                </header>
            </div>
            <hr/>
            <div class="row">
<form id="liga" method="get" action="../../GestionLiga">
    <div class="form-group">
        <label for="nombretorneo">Nombre del torneo</label>
        <input id="nombreTorneo" name="nombreTorneo" type="text" maxlength="25">
    </div>
    <div class="form-group">
        <label for="capacidad">Capacidad de equipos</label>
            <select id="capacidad" name="capacidad">
                <option></option>
                <option>6</option>
                <option>20</option>
            </select>
    </div>
    <div class="form-group">
        <label for="inicio">Fecha de inicio:</label>
        <input type="text" id="inicio" name="inicio" class="datepicker" required>
    </div>
    <div class="form-group">
        <label for="fin">Fecha de finalizacion:</label>
        <input type="text" id="fin" name="fin" class="datepicker" required>
    </div>
    <div class="form-group">
        <label for="tipo">Torneo:</label>
        <select id="tipo" name="tipo" required>
                <option></option>
                <option>Masculino</option>
                <option>Femenino</option>
        </select>
    </div>
                <button class="btn btn-primary" type="submit" name="liga">CREAR</button>
                <input type="hidden" name="enviarliga"/>
</form>
            </div>
            <%
            if (request.getParameter("liga")!=null) {
            %>
<div class="row">
<div class="alert alert-success alert-dismissible" role="alert">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  <strong><span class="glyphicon glyphicon-ok" aria-hidden="true"></span><%=request.getParameter("liga")%></strong>
</div>
</div>
            <%
                }
  
            %>
        </div><%--col de liga--%>
        
    </div><%--row de formularios--%>
</main>    
<footer>
<p class="pie">2014 PRO-LEVEL - Todos los derechos reservados | Cambiar idioma 
    <a href="#"><img src="../../imagenes/español.png" width="40" height="30" /></a></p>  
</footer>
    </div><%--fin container--%>
 <script>
$(document).ready(function() {
    $( ".datepicker" ).datepicker({
	inline: true
});

// Hover states on the static widgets
$( "#dialog-link, #icons li" ).hover(
	function() {
		$( this ).addClass( "ui-state-hover" );
	},
	function() {
		$( this ).removeClass( "ui-state-hover" );
	}
);
});
</script>
</body>
</html>

<%
    }//si el rol fue uno se muestra la anterior pagina
    else if (rol == 2) {
                            
    }//si el rol fue 2
    }//si no hay sesion
            else{
                response.sendRedirect("index.jsp");
            }
%>