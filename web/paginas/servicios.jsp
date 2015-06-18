<%-- 
    Document   : inicio
    Created on : 28/01/2015, 11:50:40 PM
    Author     : jeisson
--%>
<%@page import="modelo.UsuariosDTO"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<sql:query var="torneo" dataSource="${sessionScope.cnn}">
    SELECT idTorneo, nombre FROM torneo
</sql:query>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
            if (request.getSession() != null && request.getSession().getAttribute("usr")!=null) { 
                    UsuariosDTO udto = new UsuariosDTO();
                    HttpSession miSession=request.getSession(false);
                    udto = (UsuariosDTO)miSession.getAttribute("usr");
                    int rol = (Integer)miSession.getAttribute("rol");
                    if (rol == 1) {
                            
%>
<!doctype html>
<html lang="es">
<head>
<link rel="shortcut icon" href="../imagenes/favicon.ico">
<link href="../css/onepage-scroll.css" rel="stylesheet" type="text/css">
<link href="../css/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="../css/estiloslayout.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../js/jquery.onepage-scroll.js"></script>
<script type="text/javascript" src="../css/bootstrap/js/bootstrap.min.js"></script>
<meta charset="utf-8">
<title>Pro-level::Servicios</title>
<style>
    body{
        margin: 0;
    }
    #head{
        background-color: #34a040;
        height: 180px;
    }
    #head>h1{
        font-family: castellar, arial, verdana;
        text-align: center;
        color: white;
    }
    #head p{
        font-family: verdana, arial, lucida-saens;
        color: white;
        text-align: center;
    }
</style>

<script>
    $(document).ready(function(){
        $(".main").onepage_scroll();
    });
</script>
</head>
<body>
<div class="main">
    <section class="container">
<header>
    <div style="float: right"><span class="label label-success"> <%=udto.getPrimerNombre()%> </span><span class="badge">Administrador</span></div>
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
      <li><a href="servicios.jsp"><img src="../imagenes/servicios.png" width="24" height="24" alt="servicios" />SERVICIOS</a></li>
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
        
    <div id="head" class="container-fluid">
        <h1>Servicios</h1>
        <p>Conozca algunos de nuestros principales servicios</p>
    </div>

    <div class="page-header">
        <h2 class="text-center">Canchas</h2>
    </div>
    
    <div class="row">
        <div class="col-md-4 col-sm-12">
            <div class="thumbnail">
                <img src="../imagenes/fondos/cancha1.jpg" alt="cancha" height="242" width="200" />
                <div class="caption">
                    <h3>Cancha de fútbol 8</h3>
                    <p>En el primer piso se encuentra nuestra cancha de fútbol 8</p>
                </div>
            </div>
        </div>
        
        <div class="col-md-4 col-sm-12">
            <div class="thumbnail">
                <img src="../imagenes/fondos/cancha2.jpg" alt="cancha" height="242" width="200" />
                <div class="caption">
                    <h3>Cancha de fútbol 5</h3>
                    <p>En el primer piso se encuentra una cancha de fútbol 5</p>
                </div>
            </div>
        </div>
        
        <div class="col-md-4 col-sm-12">
            <div class="thumbnail">
                <img src="../imagenes/fondos/cancha3.jpg" alt="cancha" height="242" width="200" />
                <div class="caption">
                    <h3>Cancha de fútbol 5</h3>
                    <p>En el primer piso se encuentra una cancha de fútbol 5</p>
                </div>
            </div>
        </div>
    </div>
</section>
    <section>
        
        <div class="page-header">
        <h2 class="text-center">Servicios adicionales</h2>
    </div>
        <div class="well">
            <p>Servicios adicionales para nuestros clientes</p>
        </div>
    <div class="row">
        <div class="col-md-4 col-sm-12">
            <div class="thumbnail">
                <img src="../imagenes/fondos/ducha.jpg" alt="cancha" height="242" width="200" />
                <div class="caption">
                    <h3>Servicio de baño</h3>
                    <p>Nuestrans instalaciones a demás de baño ofrecen varias duchas en cada uno de los 
                    pisos</p>
                </div>
            </div>
        </div>
        
        <div class="col-md-4 col-sm-12">
            <div class="thumbnail">
                <img src="../imagenes/fondos/parqueadero.jpg" alt="cancha" height="242" width="200" />
                <div class="caption">
                    <h3>Parqueadero</h3>
                    <p>Parqueadero vigilado y gratuito para nuestros clientes</p>
                </div>
            </div>
        </div>
        
        <div class="col-md-4 col-sm-12">
            <div class="thumbnail">
                <img src="../imagenes/fondos/tienda.jpg" alt="cancha" height="242" width="200" />
                <div class="caption">
                    <h3>Tienda</h3>
                    <p>Tienda de comestibles y bebidas en el interior de nuestro
                     establecimiento</p>
                </div>
            </div>
        </div>
    </div>
        
        <footer>
<div id="pie">
<p class="pie">2014 PRO-LEVEL - Todos los derechos reservados | Cambiar idioma <a href="#">
        <img src="../imagenes/english.png" width="40" height="30" /></a></p>  
</div>
</footer>
    </section>
</div>



</body>
</html>
<% }//si el rol fue uno  se muestra la anterior pagina
                    else if(rol == 2){
%>
<!doctype html>
<html lang="es">
<head>
<link rel="shortcut icon" href="../imagenes/favicon.ico">
<link href="../css/onepage-scroll.css" rel="stylesheet" type="text/css">
<link href="../css/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="../css/estiloslayout.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../js/jquery.onepage-scroll.js"></script>
<script type="text/javascript" src="../css/bootstrap/js/bootstrap.min.js"></script>
<meta charset="utf-8">
<title>Pro-level::Servicios</title>
<style>
    body{
        margin: 0;
    }
    #head{
        background-color: #34a040;
        height: 180px;
    }
    #head>h1{
        font-family: castellar, arial, verdana;
        text-align: center;
        color: white;
    }
    #head p{
        font-family: verdana, arial, lucida-saens;
        color: white;
        text-align: center;
    }
</style>

<script>
    $(document).ready(function(){
        $(".main").onepage_scroll();
    });
</script>
</head>
<body>
<div class="main">
    <section class="container">
<header>
    <div style="float: right"><span class="label label-success"> <%=udto.getPrimerNombre()%> </span><span class="badge">Administrador</span></div>
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
                                            <li><a href="jugador/vermisTorneos.jsp?idTorneo=${row.idTorneo}">${row.nombre}</a></li>
                                        </c:forEach>
                                    </ul>
                        </div>
                    </div>
                  </li>
            </ul>
          </div>
        </div>
      </li>
      <li><a href="servicios.jsp"><img src="../imagenes/servicios.png" width="24" height="24" alt="servicios" />SERVICIOS</a></li>
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
        
    <div id="head" class="container-fluid">
        <h1>Servicios</h1>
        <p>Conozca algunos de nuestros principales servicios</p>
    </div>

    <div class="page-header">
        <h2 class="text-center">Canchas</h2>
    </div>
    
    <div class="row">
        <div class="col-md-4 col-sm-12">
            <div class="thumbnail">
                <img src="../imagenes/fondos/cancha1.jpg" alt="cancha" height="242" width="200" />
                <div class="caption">
                    <h3>Cancha de fútbol 8</h3>
                    <p>En el primer piso se encuentra nuestra cancha de fútbol 8</p>
                </div>
            </div>
        </div>
        
        <div class="col-md-4 col-sm-12">
            <div class="thumbnail">
                <img src="../imagenes/fondos/cancha2.jpg" alt="cancha" height="242" width="200" />
                <div class="caption">
                    <h3>Cancha de fútbol 5</h3>
                    <p>En el primer piso se encuentra una cancha de fútbol 5</p>
                </div>
            </div>
        </div>
        
        <div class="col-md-4 col-sm-12">
            <div class="thumbnail">
                <img src="../imagenes/fondos/cancha3.jpg" alt="cancha" height="242" width="200" />
                <div class="caption">
                    <h3>Cancha de fútbol 5</h3>
                    <p>En el primer piso se encuentra una cancha de fútbol 5</p>
                </div>
            </div>
        </div>
    </div>
</section>
    <section>
        
        <div class="page-header">
        <h2 class="text-center">Servicios adicionales</h2>
    </div>
        <div class="well">
            <p>Servicios adicionales para nuestros clientes</p>
        </div>
    <div class="row">
        <div class="col-md-4 col-sm-12">
            <div class="thumbnail">
                <img src="../imagenes/fondos/ducha.jpg" alt="cancha" height="242" width="200" />
                <div class="caption">
                    <h3>Servicio de baño</h3>
                    <p>Nuestrans instalaciones a demás de baño ofrecen varias duchas en cada uno de los 
                    pisos</p>
                </div>
            </div>
        </div>
        
        <div class="col-md-4 col-sm-12">
            <div class="thumbnail">
                <img src="../imagenes/fondos/parqueadero.jpg" alt="cancha" height="242" width="200" />
                <div class="caption">
                    <h3>Parqueadero</h3>
                    <p>Parqueadero vigilado y gratuito para nuestros clientes</p>
                </div>
            </div>
        </div>
        
        <div class="col-md-4 col-sm-12">
            <div class="thumbnail">
                <img src="../imagenes/fondos/tienda.jpg" alt="cancha" height="242" width="200" />
                <div class="caption">
                    <h3>Tienda</h3>
                    <p>Tienda de comestibles y bebidas en el interior de nuestro
                     establecimiento</p>
                </div>
            </div>
        </div>
    </div>
        
        <footer>
<div id="pie">
<p class="pie">2014 PRO-LEVEL - Todos los derechos reservados | Cambiar idioma <a href="#">
        <img src="../imagenes/english.png" width="40" height="30" /></a></p>  
</div>
</footer>
    </section>
</div>



</body>
</html>
<%
                    }
    }//si no hay sesion
            else{
                response.sendRedirect("index.jsp");
            }
        
%>
