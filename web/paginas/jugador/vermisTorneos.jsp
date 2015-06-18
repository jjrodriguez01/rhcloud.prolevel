<%-- 
    Document   : misTorneos
    Created on : 10/02/2015, 07:20:21 PM
    Author     : jeisson
--%>
<%@page import="utilidades.MiExcepcion"%>
<%@page import="facade.FachadaTorneos"%>
<%@page import="modelo.UsuariosDTO"%>
<%@page import="java.util.LinkedList"%>
<%@page import="modelo.EquipoDTO"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
            if (request.getSession()!=null && request.getSession().getAttribute("usr")!=null) {
                    UsuariosDTO udto = new UsuariosDTO();
                    HttpSession miSession=request.getSession(false);
                    udto = (UsuariosDTO)miSession.getAttribute("usr");
                    int rol = (Integer)miSession.getAttribute("rol");
                    if(rol == 2){
        %>

<%--  Query para con la info de torneos --%>
<sql:query var="torneo" dataSource="${sessionScope.cnn}">
    SELECT idTorneo, nombre FROM torneo
</sql:query>
<%--  Query para que el contexto sea el torneo --%>
<sql:query var="infotorneo" dataSource="${sessionScope.cnn}">
    SELECT *  FROM torneo
    WHERE torneo.idTorneo = ? <sql:param value="${param.idTorneo}"/>
</sql:query>
<sql:query var="tposregistros" dataSource="${sessionScope.cnn}">
    SELECT count(*) from tablaposiciones where idTorneo = ? <sql:param value="${param.idTorneo}"/>
</sql:query>    
<c:set var="detallestorneo" value="${infotorneo.rows[0]}"/>
<%--  Query para la tabla de posiciones --%>
<sql:query var="tablaposiciones" dataSource="${sessionScope.cnn}">
    SELECT equipo.nombre, 
    tablaposiciones.partidosJugados as PJ,
    tablaposiciones.partidosGanados as PG, 
    tablaposiciones.partidosEmpatados as PE,
    tablaposiciones.partidosPerdidos as PP, 
    tablaposiciones.golesAnotados as Goles,
    tablaposiciones.golesRecibidos as GC,
    tablaposiciones.golesAnotados-tablaposiciones.golesRecibidos AS GD,
    tablaposiciones.puntos as pts
    FROM equipo
    inner join equiposdeltorneo
    on equipo.codigo = equiposdeltorneo.equipoCodigo
    inner join tablaposiciones
    on equiposdeltorneo.equipoCodigo = tablaposiciones.idEquipo
    WHERE equiposdeltorneo.torneoidtorneo=? <sql:param value="${param.idTorneo}"/> 
    and
    tablaposiciones.idTorneo = ? <sql:param value="${param.idTorneo}"/>
    ORDER BY pts DESC, Goles DESC, GC ASC
</sql:query>
<%--  Query para la tabla de goleadores --%>            
<sql:query var="tablagoleadores" dataSource="${sessionScope.cnn}">
    SELECT DISTINCT usuarios.primerNombre, 
    usuarios.primerApellido, tablagoleadores.numeroGoles, 
    equipo.nombre
    FROM tablagoleadores
    INNER JOIN jugadoresporequipo
    ON tablagoleadores.idJugador = jugadoresporequipo.codigoJugador
    INNER JOIN usuarios
    ON jugadoresporequipo.codigoJugador = usuarios.idUsuario
    INNER JOIN equiposdeltorneo
    ON tablagoleadores.idEquipo = equiposdeltorneo.equipoCodigo
    INNER JOIN equipo
    ON equiposdeltorneo.equipoCodigo = equipo.codigo
    INNER JOIN torneo
    ON tablagoleadores.idTorneo = torneo.idTorneo
    WHERE tablagoleadores.idTorneo = ? <sql:param value="${param.idTorneo}"/>
    AND equiposdeltorneo.torneoIdTorneo=? <sql:param value="${param.idTorneo}"/>
    ORDER BY numeroGoles DESC
</sql:query>
<%--  Query para la tabla de tarjetas --%>  
<sql:query var="tarjetas" dataSource="${sessionScope.cnn}">
    SELECT DISTINCT concat(usuarios.primerNombre,' ', usuarios.primerApellido), 
    tarjetas.tarjetaAzul, tarjetas.tarjetaRoja, equipo.nombre
    FROM tarjetas
    INNER JOIN  jugadoresporequipo
    ON tarjetas.idJugador = jugadoresporequipo.codigoJugador
    INNER JOIN  usuarios
    ON jugadoresporequipo.codigoJugador = usuarios.idUsuario
    INNER JOIN equipo 
    ON jugadoresporequipo.codigoequipo = equipo.codigo
	INNER JOIN equiposdeltorneo
	On jugadoresporequipo.codigoEquipo = equiposdeltorneo.equipoCodigo
    WHERE tarjetas.idTorneo =? <sql:param value="${param.idTorneo}"/>
	and equiposdeltorneo.torneoIdTorneo =? <sql:param value="${param.idTorneo}"/>
</sql:query>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <title>${detallestorneo.nombre}</title>
        <link rel="shortcut icon" href="../../imagenes/favicon.ico">
        <link href="../../css/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="../../css/estiloslayout.css" rel="stylesheet" type="text/css">
        <link href="../../css/estilosMisTorneos.css" rel="stylesheet" type="text/css">
        <link href="../../js/dataTables/css/dataTablesBootstrap.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
        <script type="text/javascript" src="../../js/jquery-2.1.1.js"></script>
        <script type="text/javascript" src="../../js/jquery.validate.js"></script>
        <script type="text/javascript" src="../../css/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../../js/jugadoresEquipos.js"></script>
        <script type="text/javascript" src="../../js/dataTables/js/jquery.dataTables.js"></script>
        <script type="text/javascript" src="../../js/dataTables/js/datatablesbootstrap.js"></script>
        <script>
            $(document).ready(function(){
                $("#tposiciones, #tgoleadores, #ttarjetas").dataTable({
                    language:{
                        url: "../../js/dataTables/js/dataespañol.json"
                    }
                });
            });
        </script>
        <script>//script del tooltip de los th
            $(document).ready(function(){
               $(".mitooltip").tooltip();
            });
        </script> 
    </head>
    <body>
        <%--facebook plugin like & share--%>
        <div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/es_LA/sdk.js#xfbml=1&version=v2.3";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/es_LA/sdk.js#xfbml=1&version=v2.3&appId=548986785241428";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

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
                                                        <li><a href="vermisTorneos.jsp?idTorneo=${row.idTorneo}">${row.nombre}</a></li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </li>
                    <li><a href="#"><img src="../../imagenes/servicios.png" width="24" height="24" alt="servicios" />SERVICIOS</a></li>
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
            <div class="pull-right"><span class="label label-success"> <%=udto.getPrimerNombre()%></span><span class="badge">Jugador</span></div>
        </header>
<main class="container">
    <div class="row">
    <div class="col-lg-12 menu-opciones">
        <ul class="nav nav-tabs nav-justified">
            <li role="presentation"><a href="verCentro.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a></li>
            <li role="presentation"><a href="verCalendario.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>Calendario</a></li>
            <li role="presentation"><a <c:if test="${detallestorneo.tipo==3}"> href="verresultadoseli.jsp?idTorneo=${param.idTorneo}"</c:if> href="verMarcadores.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-tasks" aria-hidden="true"></span>Resultados</a></li>
            <li role="presentation" class="active"><a href="vermisTorneos.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>Tablas</a></li>           
        </ul>
    </div>
    </div>
    <div class="row">
        <div class="col-md-4 col-sm-2 col-xs-12">
            <ol class="breadcrumb">
                <li><a href="../inicio.jsp">Inicio</a></li>
                <li><a href="vermisTorneos.jsp?idTorneo=${param.idTorneo}">Torneos</a></li>
                <li class="active">Tablas</li>
            </ol>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-1">
            <section>
                <div class="fb-like" data-layout="button" data-action="like" data-show-faces="false" data-share="true"></div>
            </section>
        </div>
    </div> 
            <hgroup>
                <h1 id="titulo">${detallestorneo.nombre}</h1>
                <c:if test="${detallestorneo.tipo == 1 || detallestorneo.tipo == 2}">
                <h3 class="tablatit">Tabla De Posiciones</h3>
            </hgroup>
            <div class="row">
                <div class="col-md-9 col-sm-9">
                    <table id="tposiciones" class="table table-responsive">
                        <!-- column headers -->
                        <thead>
                            <tr>
                                <th class="mitooltip" title="Posicion">POS</th>
                                <th class="mitooltip" title="Equipo">EQUIPO</th>
                                <th class="mitooltip" title="Partidos Jugados">PJ</th>
                                <th class="mitooltip" title="Partidos Ganados">PG</th>
                                <th class="mitooltip" title="Parrtidos Empadados">PE</th>
                                <th class="mitooltip" title="Partidos Perdidos">PP</th>
                                <th class="mitooltip" title="Goles Marcados">GOLES</th>
                                <th class="mitooltip" title="Goles En Contra">GC</th>
                                <th class="mitooltip" title="Diferencia De Goles">GD</th>
                                <th class="mitooltip" title="Puntos">PTS</th>
                            </tr>
                        </thead>
                        <!-- column data -->
                        <tbody>
                            <%!  int pos = 0;  %>
                            <c:forEach var="row" items="${tablaposiciones.rowsByIndex}" varStatus="vs">
                                <tr>
                                    <% 
                                       pos += 1;
                                    %>
                                    <td>${vs.index+1}</td>
                                    <c:forEach var="column" items="${row}">
                                        <td><c:out value="${column}"/></td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                        <a href="../../Reportes?tabla=posiciones&idTorneo=${param.idTorneo}"><i class="fa fa-file-pdf-o"></i> Exportar esta tabla a PDF</a>
                        </tfoot>
                    </table>
                </div>
            </div>
            </c:if>
            <div class="row">
                <div class="col-md-6 col-sm-4">
                    <h3 class="tablatit">Tabla Goleadores</h3>
                    <table id="tgoleadores" class="table">
                        <!-- column headers -->
                        <thead>
                            <tr>
                                <th>Posicion</th>
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Goles</th>
                                <th>Equipo</th>
                            </tr>
                        </thead>
                        <!-- column data -->
                        <tbody>
                        <%!  int pgol = 0;  %>
                        <c:forEach var="row" items="${tablagoleadores.rowsByIndex}" varStatus="vs">
                                <% pgol += 1; %>
                                <tr>
                                    <td>${vs.index+1}</td>
                                    <c:forEach var="column" items="${row}">
                                        <td><c:out value="${column}"/></td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                        <a href="../../Reportes?tabla=goleadores&idTorneo=${param.idTorneo}"><i class="fa fa-file-pdf-o"></i> Exportar esta tabla a PDF</a>
                        </tfoot>
                    </table>
                </div>
                <div id="tablatarjetas" class="col-md-6 col-sm-4">
                    <h3 class="tablatit">Tabla De Tarjetas</h3>
                    <table id="ttarjetas" class="table">
                        <!-- column headers -->
                        <thead>
                            <tr>
                                <th>Jugador</th>
                                <th>AZULES</th>
                                <th>ROJAS</th>
                                <th>EQUIPO</th>
                            </tr>
                        </thead>
                        <!-- column data -->
                        <tbody>
                            <c:forEach var="row" items="${tarjetas.rowsByIndex}">
                                <tr>
                                    <c:forEach var="column" items="${row}">
                                        <td><c:out value="${column}"/></td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <aside>
                                <div class="fb-comments" data-numposts="10" data-colorscheme="light" data-version="v2.3"></div>
                                </aside>
                            </div>
                        </div>     
</main>
        <footer>
            <p class="pie">2014 PRO-LEVEL - Todos los derechos reservados | Cambiar idioma 
            <a href="index_english.html"><img src="../../imagenes/english.png" width="40" height="30" alt="ingles" /></a>
            </p> 
        </footer>
    </body>
</html>
<%
    }
        }
            else{
                response.sendRedirect("../../index.jsp?auth=prohibido");
            }                 
%>
