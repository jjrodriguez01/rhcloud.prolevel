<%-- 
    Document   : inscribirEquipos
    Created on : 4/04/2015, 12:17:36 AM
    Author     : jeisson
--%>
<%@page import="persistencia.UsuariosDAO"%>
<%@page import="modelo.UsuariosDTO"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
            if (request.getSession()!=null && request.getSession().getAttribute("usr")!=null) {
                    UsuariosDTO udto = new UsuariosDTO();
                    UsuariosDAO udao = new UsuariosDAO();
                    HttpSession miSession=request.getSession(false);
                    udto = (UsuariosDTO)miSession.getAttribute("usr");
                    int rol = (Integer)miSession.getAttribute("rol");
                    if(rol == 1){
%>
<%--  Query con la info del torneo --%>
<sql:query var="torneo" dataSource="${sessionScope.cnn}">
    SELECT idTorneo, nombre FROM torneo
</sql:query>
<%--  Query para que el contexto sea el torneo --%>
<sql:query var="infotorneo" dataSource="${sessionScope.cnn}">
    SELECT *  FROM torneo
    WHERE torneo.idTorneo = ? <sql:param value="${param.idTorneo}"/>
</sql:query>
    <%--  pasamos los resultados a una variable --%>
<c:set var="detallestorneo" value="${infotorneo.rows[0]}" scope="page" />
<%--  Query para saber cuantos equipos hay inscritos --%>
<sql:query var="disponibilidad" dataSource="${sessionScope.cnn}">
select count(torneoidtorneo) as capacidad  from equiposdeltorneo where torneoidtorneo=? <sql:param value="${param.idTorneo}"/>
</sql:query>
<%--  pasamos el resultado del query disponibilidad a una variable --%>
<c:set var="inscritos" value="${disponibilidad.rows[0]}" scope="page" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inscripciones a ${detallestorneo.nombre}</title>
        <link rel="shortcut icon" href="../../imagenes/favicon.ico">
        <link href="../../css/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="../../css/estiloslayout.css" rel="stylesheet" type="text/css">
        <link href="../../css/estilosMisTorneos.css" rel="stylesheet" type="text/css">
        <link href="../../js/dataTables/css/dataTablesBootstrap.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../../js/jquery-2.1.1.js"></script>
        <script type="text/javascript" src="../../js/jquery.validate.js"></script>
        <script type="text/javascript" src="../../css/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../../js/jugadoresEquipos.js"></script>
        <script type="text/javascript" src="../../js/dataTables/js/jquery.dataTables.js"></script>
        <script type="text/javascript" src="../../js/dataTables/js/datatablesbootstrap.js"></script>
        <script type="text/javascript" src="../../js/validaDocumento.js"></script>
        <style>
            .menu-opciones{
                 clear: both;
                padding-top: 10px;
            }
        </style>
    </head>
    <body>
        <header>
            <nav>
                <ul id="nav" class="nav">
                    <li><a href="../inicio.jsp"><img src="../../imagenes/inicio.png" width="24" height="24" alt="inicio" /> INICIO</a></li>
                    <li><a href="#"><span><img src="../../imagenes/copa.png" width="24" height="24" alt="copa" /> TORNEOS</span></a><
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
<main class="container">
    <div class="row">
    <div class="col-lg-12 menu-opciones">
        <ul class="nav nav-tabs nav-justified">
            <li role="presentation"><a href="centro.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a></li>
            <li role="presentation"><a href="calendario.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>Calendario</a></li>
            <li role="presentation"><a <c:if test="${detallestorneo.tipo==3}"> href="resultadoseli.jsp?idTorneo=${param.idTorneo}"</c:if> href="marcadores.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-tasks" aria-hidden="true"></span>Resultados</a></li>
            <li role="presentation"><a href="misTorneos.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>Tablas</a></li>
            <li role="presentation" class="active"><a href="inscribirEquipos.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>Inscribir equipos</a></li>
        </ul>
    </div>
    </div>
    <div class="row">
        <div class="col-md-4 col-sm-2 col-xs-12">
            <ol class="breadcrumb">
                <li><a href="../inicio.jsp">Inicio</a></li>
                <li><a href="#">Torneos</a></li>
                <li class="active">Inscribir equipos</li>
            </ol>
        </div>
    </div>
    <section>
        <div class="row">
            <div class="col-md-4 col-sm-2 col-xs-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                    Inscripcion De Equipos
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-9 col-sm-6 col-xs-12">
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only">Error:</span>
                Antes de crear un equipo e inscribir jugadores
                Asegusere de que los jugadores que va a inscribir esten registrados en el sistema
            </div>
            </div>
        </div>
<%
if (request.getParameter("registro")!=null) {
%>
<div class="alert alert-info alert-dismissible" role="alert">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  <span class="glyphicon glyphicon-ok" aria-hidden="true"></span><strong> <%=request.getParameter("registro")%></strong>
</div>
<%
    }
%>
        <div class="row">
<c:forEach var="formularios" begin="1" end="${detallestorneo.capacidadEquipos-inscritos.capacidad}">
            <div class="col-lg-6">
                <div class="panel panel-default">
                <div class="panel-body">
                    <form action="../../RegistroEquipos" class="form-horizontal" role="form">
  <div class="form-group">
    <label class="control-label col-sm-2" for="nombre">Nombre del equipo:</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="nombre" name="nombre"
             placeholder="Ingrese un nombre" maxlength="45" required>
    </div>
  </div>
  <div class="form-group">
      <label class="control-label col-sm-2" for="jugador">Jugador</label>
    <div class="col-sm-10">
        <input type="number" class="form-control" id="juno" name="juno" 
        placeholder="Ingrese documento" onchange="validarDocumento(this,${param.idTorneo})" maxlength="11" required>
        <input type="number" class="form-control" id="jdos" name="jdos" 
        placeholder="Ingrese documento" onchange="validarDocumento(this,${param.idTorneo})" maxlength="11" required>
        <input type="number" class="form-control" id="jtres" name="jtres" 
        placeholder="Ingrese documento" onchange="validarDocumento(this,${param.idTorneo})" maxlength="11" required>
        <input type="number" class="form-control" id="jcuatro" name="jcuatro" 
        placeholder="Ingrese documento" onchange="validarDocumento(this,${param.idTorneo})" maxlength="11" required>
        <input type="number" class="form-control" id="jcinco" name="jcinco" 
        placeholder="Ingrese documento" onchange="validarDocumento(this,${param.idTorneo})" maxlength="11" required>
        <input type="number" class="form-control" id="jseis" name="jseis" 
        placeholder="Ingrese documento" onchange="validarDocumento(this,${param.idTorneo})" maxlength="11" required>
        <input type="number" class="form-control" id="jsiete" name="jsiete" 
        placeholder="Ingrese documento" onchange="validarDocumento(this,${param.idTorneo})" maxlength="11" required>
        <input type="number" class="form-control" id="jocho" name="jocho" 
        placeholder="Ingrese documento" onchange="validarDocumento(this,${param.idTorneo})" maxlength="11" required>
        <div id="resultadouno" style="color: red"></div>
        <div id="resultadodos" style="color: green"></div>
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
        <input type="hidden" name="idTorneo" value="<%=request.getParameter("idTorneo")%>" />
        <button type="submit" class="btn btn-primary" name="crearEquipo" id="crearEquipo">Crear</button>
    </div>
  </div>
</form>
                </div>
</div>
            </div>
            </c:forEach>
        </div>
<%--con este query se si ya inicio el campeonato y la suma de estados si es 8 se q ya se jugaron todos--%>  
<sql:query var="haypartidos" dataSource="${sessionScope.cnn}">
    select count(partidos.ronda) as rondas, sum(partidos.estado) as pjugados from partidos where idTorneo = ? <sql:param value="${param.idTorneo}"/>
</sql:query>
<%-- pasamos la consulta a una variable con <c:set/> ojo pasa como array --%>
<c:set var="rondas" value="${haypartidos.rows[0]}" scope="page" />
        <c:if test="${inscritos.capacidad == detallestorneo.capacidadEquipos}">
            
            
            
            
            
            <%--si es una eliminatoria--%>
            <c:if test="${detallestorneo.tipo ==3}">
                <form method="get" action="../../GestionEliminatoria" autocomplete="off">
                <input type="hidden" name="idTorneo" value="${detallestorneo.idTorneo}" />
                <input type="hidden" name="nombre" value="${detallestorneo.nombre}" />
                <input type="hidden" name="fechaInicio" value="${detallestorneo.fechaInicio}" />
                <input type="hidden" name="fechaFin" value="${detallestorneo.fechaFin}" />
                <input type="hidden" name="genero" value="${detallestorneo.genero}" />
                <input type="hidden" name="capacidadEquipos" value="${detallestorneo.capacidadEquipos}" />
                <input type="hidden" name="tipo" value="${detallestorneo.tipo}" />
                <button name="iniciar" value="${detallestorneo.idTorneo}" class="btn btn-lg btn-danger"<c:if test="${rondas.rondas > 0}"> disabled="disabled" </c:if>>
                Iniciar Torneo
            </button>
            </form>
            
            <div class="row">
                <form method="get" action="../../GestionEliminatoria" autocomplete="off">
                <input type="hidden" name="idTorneo" value="${detallestorneo.idTorneo}" />
                <input type="hidden" name="nombre" value="${detallestorneo.nombre}" />
                <input type="hidden" name="fechaInicio" value="${detallestorneo.fechaInicio}" />
                <input type="hidden" name="fechaFin" value="${detallestorneo.fechaFin}" />
                <input type="hidden" name="genero" value="${detallestorneo.genero}" />
                <input type="hidden" name="capacidadEquipos" value="${detallestorneo.capacidadEquipos}" />
                <input type="hidden" name="tipo" value="${detallestorneo.tipo}" />
                <%--si ya se jugaron 8 habilito el boton, si ya suman 12 rondas lo deshabilito--%>
                <c:if test="${rondas.pjugados == 8}">
                <button name="iniciarcuartos" value="cuartos" class="btn btn-lg btn-danger"<c:if test="${rondas.rondas == 12}"> disabled="disabled" </c:if>>
                    Iniciar Cuartos De Final
                </button>
                </c:if>
                <c:if test="${rondas.pjugados == 12}">
                <button name="iniciarsemi" value="semi" class="btn btn-lg btn-danger"<c:if test="${rondas.rondas == 14}"> disabled="disabled" </c:if>>
                    Iniciar Semi final
                </button>
                </c:if>
                <c:if test="${rondas.pjugados == 14}">
                    <button name="iniciarfinal" value="fin" class="btn btn-lg btn-danger"<c:if test="${rondas.rondas >= 15}"> disabled="disabled" </c:if>>
                    Iniciar Final
                </button>
                </c:if>
            </form>
            </div>
            </c:if>
            
            
            
            
            
            
            
            
            
            
            <%--si esto es una liga--%>
            <c:if test="${detallestorneo.tipo == 2}">
                <div class="row">
            <form method="get" action="../../GestionLiga" autocomplete="off">
                <input type="hidden" name="idTorneo" value="${detallestorneo.idTorneo}" />
                <input type="hidden" name="nombre" value="${detallestorneo.nombre}" />
                <input type="hidden" name="fechaInicio" value="${detallestorneo.fechaInicio}" />
                <input type="hidden" name="fechaFin" value="${detallestorneo.fechaFin}" />
                <input type="hidden" name="genero" value="${detallestorneo.genero}" />
                <input type="hidden" name="capacidadEquipos" value="${detallestorneo.capacidadEquipos}" />
                <input type="hidden" name="tipo" value="${detallestorneo.tipo}" />
                <button name="iniciar" value="${detallestorneo.idTorneo}" class="btn btn-lg btn-success"<c:if test="${rondas.rondas > 0}"> disabled="disabled" </c:if>>
                    Iniciar Torneo
                </button>
                    <input type="hidden" name="liga"/>
            </form>
                </div>
                <%--si ya se jugo todo hay q declara al campeon--%>
                <c:if test="${rondas.rondas >= 15}">
                    <div class="row">
                        <hr/>
            <form method="get" action="../../GestionLiga" autocomplete="off">
                <input type="hidden" name="idTorneo" value="${detallestorneo.idTorneo}" />
                <input type="hidden" name="nombre" value="${detallestorneo.nombre}" />
                <input type="hidden" name="fechaInicio" value="${detallestorneo.fechaInicio}" />
                <input type="hidden" name="fechaFin" value="${detallestorneo.fechaFin}" />
                <input type="hidden" name="genero" value="${detallestorneo.genero}" />
                <input type="hidden" name="capacidadEquipos" value="${detallestorneo.capacidadEquipos}" />
                <input type="hidden" name="tipo" value="${detallestorneo.tipo}" />
                <button name="campeonliga" value="campeon" class="btn btn-lg btn-success">
                    Declarar al campeón
                </button>
                    <input type="hidden" name="declarecampeon"/>
            </form>
                </div>
                </c:if>
            </c:if>
            
        </c:if>
    </section>
</main>
    </body>
</html>
<% 
    }//si el rol fue uno
                    else if(rol==2){
                        
                    }//si el rol fue 2
            }//si hay sesion
                    else{
                        response.sendRedirect("../../index.jsp?auth=prohibido");
                    }
%>