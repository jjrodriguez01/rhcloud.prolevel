<%@page import="persistencia.UsuariosDAO"%>
<%@page import="modelo.UsuariosDTO"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<sql:query var="torneo" dataSource="${sessionScope.cnn}">
    SELECT idTorneo, nombre FROM torneo
</sql:query>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    
%>    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<meta charset="utf-8">
<title>Pro-level</title>
<style>
    html{
        font-size: 10pt;
    }
    .centrar{
        float: none;
        margin-left: auto;
        margin-right: auto;
    }
    h2{
        font-size: 3rem;
        font-family: 'Copperplate Gothic Bold',centaur, georgia;
    }
    .panel{
        padding-top: 5p;
        box-shadow: 1px 1px 8px #000;
    }

/* centrado de la pagina*/
body{
	margin:0 5%;
	max-width: 1800px;
}

/* Menu*/
nav {
	margin:0 auto;
	position: relative;
        width:720px;
}/*centrado del menu*/
ul#nav {
    height: 50px;
    display:inline-block;
    float:left;
    font-family:Trebuchet MS,sans-serif;
    font-size:0;
    padding:5px 5px 5px 0;
    list-style:none;
    background: -moz-linear-gradient(#f5f5f5, #c4c4c4); /* FF 3.6+ */  
    background: -ms-linear-gradient(#f5f5f5, #c4c4c4); /* IE10 */  
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #f5f5f5), color-stop(100%, #c4c4c4)); /* Safari 4+, Chrome 2+ */  
    background: -webkit-linear-gradient(#f5f5f5, #c4c4c4); /* Safari 5.1+, Chrome 10+ */  
    background: -o-linear-gradient(#f5f5f5, #c4c4c4); /* Opera 11.10 */  
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#f5f5f5', endColorstr='#c4c4c4'); /* IE6 & IE7 */  
    -ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr='#f5f5f5', endColorstr='#c4c4c4')"; /* IE8+ */  
    background: linear-gradient(#f5f5f5, #c4c4c4 ); /* the standard */ 
}


ul#nav,ul#nav .subs {
    background-color:#444;
    border:1px solid #454545;
    border-radius:9px;
    -moz-border-radius:9px;
    -webkit-border-radius:9px;
}
ul#nav .subs {
    background-color:#fff;
    border:2px solid #222;
    display:none;
    float:left;
    left:0;
    padding:0 6px 6px;
    position:absolute;
    top:100%;
    width:310px;
    border-radius:5px;
    -moz-border-radius:5px;
    -webkit-border-radius:5px;
    z-index: 9;
}
ul#nav li:hover>* {
    display:block;
}
ul#nav li:hover {
    position:relative;
}
ul#nav ul .subs {
    left:100%;
    position:absolute;
    top:0;
    z-index: 9;
}
ul#nav ul {
    padding:0 5px 5px;
}
ul#nav .col {
    float:left;
    width:50%;
}
ul#nav li {
    display:block;
    float:left;
    font-size:0;
    white-space:nowrap;
}
ul#nav>li,ul#nav li {
    margin:0 0 0 5px;
}
ul#nav ul>li {
    margin:5px 0 0;
}
ul#nav a:active,ul#nav a:focus {
    outline-style:none;
}
ul#nav a {
    border-style:none;
    border-width:0;
    color:#181818;
    cursor:pointer;
    display:block;
    font-size:13px;
    font-weight:bold;
    padding:8px 18px;
    text-align:left;
    text-decoration:none;
    text-shadow:#fff 0 1px 1px;
    vertical-align:middle;
	text-align:center;
}
ul#nav ul li {
    float:none;
    margin:6px 0 0;
}
ul#nav ul a {
    background-color:#fff;
    border-color:#efefef;
    border-style:solid;
    border-width:1px 0 1px;
    color:#000;
    font-size:11px;
    padding:4px;
    text-align:left;
    text-decoration:none;
    text-shadow:#fff 0 0 0;
    border-radius:0;
    -moz-border-radius:0;
    -webkit-border-radius:0;
}
ul#nav li:hover>a {
    border-style:none;
    color:#fff;
    font-size:13px;
    font-weight:bold;
    text-decoration:none;
    text-shadow:lime 0 1px 1px;
}
ul#nav img {
    border:none;
    margin-right:8px;
    vertical-align:middle;
}
ul#nav span {
    background-position:right center;
    background-repeat:no-repeat;
    display:block;
    overflow:visible;
    padding-right:0;
}
ul#nav ul span {
    background-image:url("../imagenes/balonsmall.png");
    padding-right:20px;
	
}
ul#nav ul li:hover>a {
    border-color:#444;
    border-style:solid;
    color:#444;
    font-size:11px;
    text-decoration:none;
    text-shadow:#fff 0 0 0;
}
ul#nav > li >a {
    background-color:transpa;
    height:25px;
    line-height:25px;

    border-radius:11px;
    -moz-border-radius:11px;
    -webkit-border-radius:11px;
}
ul#nav > li:hover > a {
    background-color:#313638;
    line-height:25px;
    height: 85%;
}
/*pie de pagina*/
footer{
	margin-bottom:1px;
	height:40px;
}
footer p{
	text-align:center;
	margin-bottom:1px;
	top:100px;
	color: #000;
	clear:both;
}
</style>
</head>
<body>
<header>
    <nav class="navbar">
    <ul id="nav" class="nav">
      		<li><a href="inicio.jsp"><img src="../imagenes/inicio.png" width="24" height="24" alt="inicio" /> INICIO</a></li>
      		<li><a href="#"><span><img src="../imagenes/copa.png" width="24" height="24" alt="copa" /> TORNEOS</span></a><
        <div class="subs">
          <div class="col">
            <ul>
              <li><a><img src="../imagenes/micopa.png" width="24" height="24" alt="micopa"/>MIS TORNEOS</a>
              		<div class="subs">
                    	<div class="col">
                                    <ul>
                                        <c:forEach var="row" items="${torneo.rows}">
                                            <li><a href="torneos/misTorneos.jsp?idTorneo=${row.idTorneo}">${row.nombre}</a></li>
                                        </c:forEach>
                                    </ul>
                        </div>
                    </div>
                  </li>
              <li><a href="torneos/crear_torneo.jsp"><img src="../imagenes/crear.png" width="24" height="24" alt="crear" />CREAR TORNEOS</a></li>
            </ul>
          </div>
        </div>
      </li>
      <li><a href="#"><span><img src="../imagenes/telefono.png" width="24" height="24" alt="reservar" />RESERVAS</span></a>
        <div class="subs">
          <ul>
            <li><a href="#"><img src="imagenes/cancha.png" width="24" height="24" alt="reservas" />RESERVAR</a></li>
            <li><a href="#"><img src="imagenes/instructivo.png" width="24" height="24" alt="ins" />INSTRUCTIVO</a></li>
            <li><a href="#"><img src="imagenes/informe.png" width="24" height="24" alt="info" />INFORME DE RESERVAS</a></li>
          </ul>
        </div>
        </li>
      <li><a href="#"><img src="../imagenes/servicios.png" width="24" height="24" alt="servicios" />SERVICIOS</a></li>
      <li><a href="#"><span><img src="../imagenes/perfil.png" width="24" height="24" alt="perfil" />PERFIL</span></a>
      	<div class="subs">
        	<ul>
            	<li><a href="admin.jsp"><img src="../imagenes/ajustes.png" width="24" height="24" alt="ajustes" />ADMINISTRAR</a></li>
                <li><a href="../Ingreso?logout=cerrar"><img src="../imagenes/out.png" width="24" height="24" alt="cerrar" />CERRAR CESIÓN</a></li>
            </ul>
        </div>
        </li>
    </ul>
</nav>
</header>
<!-- InstanceBeginEditable name="body" -->
<main>
    <div class="row">
        <div class="col-md-6 centrar">
            <img src="../imagenes/buscando.png" alt="pagina no encontrada" class="img-responsive img-thumbnail" >
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 centrar">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="alert alert-danger" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        <span class="sr-only">Error:</span>
                        <h2>Lo sentimos, la pagina no ha sido encontrada</h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
</html>