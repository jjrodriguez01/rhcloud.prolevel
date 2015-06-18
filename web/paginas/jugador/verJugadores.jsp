<%-- 
    Document   : inscribirEquipos
    Created on : 4/04/2015, 12:17:36 AM
    Author     : jeisson
--%>
<%@page import="modelo.PartidoDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.TablaPosicionesDTO"%>
<%@page import="facade.FachadaTorneos"%>
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
                    FachadaTorneos facade = new FachadaTorneos();
                    TablaPosicionesDTO tab = new TablaPosicionesDTO();
                    tab = facade.listarUno(Integer.parseInt(request.getParameter("idTorneo")), Integer.parseInt(request.getParameter("codigoEquipo")));
                    ArrayList<PartidoDTO> partidos = new ArrayList();
                    partidos = (ArrayList) facade.partidosUnEquipo(Integer.parseInt(request.getParameter("idTorneo")), Integer.parseInt(request.getParameter("codigoEquipo")));
                    if(rol == 2){
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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Centro del torneo ${detallestorneo.nombre}</title>
        <link rel="shortcut icon" href="../../imagenes/favicon.ico">
        <link href="../../css/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="../../css/estiloslayout.css" rel="stylesheet" type="text/css">
        <link href="../../css/estilosMisTorneos.css" rel="stylesheet" type="text/css">  
        <link href="../../css/semantic/semantic.min.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../../js/jquery-2.1.1.js"></script>
        <script type="text/javascript" src="../../css/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../../js/jugadoresEquipos.js"></script>

        <style>
            body{
                background-color: white;
            }
            .menu-opciones{
                 clear: both;
                padding-top: 10px;
            }

        </style>
        <script>
            $(document).ready(function(){
                $("a.item").hover(function(){
                    $("a.item").removeClass("active teal");
                    $("div.green").removeClass("green");
                    $(this).addClass("active teal item");
                    $(this).children("div").addClass("green");
                })
            });
        </script>
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
            <li role="presentation" class="active"><a href="verCentro.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a></li>
            <li role="presentation"><a href="verCalendario.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>Calendario</a></li>
            <li role="presentation"><a <c:if test="${detallestorneo.tipo==3}"> href="verresultadoseli.jsp?idTorneo=${param.idTorneo}"</c:if> href="verMarcadores.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-tasks" aria-hidden="true"></span>Resultados</a></li>
            <li role="presentation"><a href="vermisTorneos.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>Tablas</a></li>           
        </ul>
    </div>
    </div>
    <div class="row">
        <div class="col-md-4 col-sm-2 col-xs-12">
            <ol class="breadcrumb">
                <li><a href="../inicio.jsp">Inicio</a></li>
                <li><a href="vermisTorneos.jsp?idTorneo=${param.idTorneo}">Torneos</a></li>
                <li><a href="verCentro.jsp?idTorneo=${param.idTorneo}">Centro</a></li>
                <li class="active">Jugadores</li>
            </ol>
        </div>
    </div>
        
        <div class="row">
            <div class="col-lg-6">
                <div class="page-header">
                    <h1>Jugadores del equipo ${param.nombre}</h1>
                </div>


                <sql:query var="jugadores" dataSource="${sessionScope.cnn}">
                    select concat(usuarios.primernombre,' ',usuarios.primerapellido) jugador, usuarios.idUsuario from usuarios
inner join jugadoresporequipo on
usuarios.idUsuario = jugadoresporequipo.codigoJugador
inner join equipo on
equipo.codigo = jugadoresporequipo.codigoEquipo
where equipo.codigo = ?  <sql:param value="${param.codigoEquipo}"/>
                </sql:query>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Jugador</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="row" items="${jugadores.rows}" varStatus="vs">
                            <tr>
                                <td>${row.jugador}</td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
            </div>
                <div class="col-sm-12 col-md-6 col-lg-6">
                    <c:if test="${detallestorneo.tipo == 1 || detallestorneo.tipo ==2}">
                    <div class="ui vertical menu">
                        <a class="active teal item">
                          Partidos jugados
                          <div class="ui green label"><%=tab.getPartidosJugados()%></div>
                        </a>
                        <a class="item">
                          Partidos ganados
                          <div class="ui label"><%=tab.getPartidosGanados()%></div>
                        </a>
                        <a class="item">
                          Partidos empatados
                          <div class="ui label"><%=tab.getPartidosEmpatados()%></div>
                        </a>
                        <a class="item">
                          Partidos Perdidos
                          <div class="ui label"><%=tab.getPartidosPerdidos()%></div>
                        </a>
                        <a class="item">
                          Goles anotados
                          <div class="ui label"><%=tab.getGolesAnotados()%></div>
                        </a>
                        <a class="item">
                          Goles recibidos
                          <div class="ui label"><%=tab.getGolesRecibidos()%></div>
                        </a>
                        <a class="item">
                          Diferencia de goles
                          <% int diferencia = tab.getGolesAnotados()-tab.getGolesRecibidos();%>
                          <div class="ui label"><%=diferencia%></div>
                        </a>
                    </div>
                    </c:if>
                    <c:if test="${detallestorneo.tipo == 3}">
                        <table class="table table-condensed">
                                <tbody>
                                    <% for(PartidoDTO p : partidos){ %>
                                    <tr>
                                        <td><%=p.getEquipouno().getNombre()%></td>
                                        <td><%=p.getMarcador1()%></td>
                                        <td><pan>-</pan></td>
                                        <td><%=p.getEquipodos().getNombre()%></td>
                                        <td><%=p.getMarcador2()%></td>
                                    </tr>
                                    <%  }  %>
                                </tbody>
                            </table>    
                    </c:if>
                </div>
        </div>
                
                
</main>
    </body>
</html>
<% 
    }
                   
            }//si hay sesion
                    else{
                        response.sendRedirect("../../index.jsp?auth=prohibido");
                    }
%>
