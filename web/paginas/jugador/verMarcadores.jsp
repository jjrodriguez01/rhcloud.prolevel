<%-- 
    Document   : inscribirEquipos
    Created on : 4/04/2015, 12:17:36 AM
    Author     : jeisson
--%>
<%@page import="utilidades.MiExcepcion"%>
<%@page import="modelo.PartidoDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.UsuariosDTO"%>
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
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fechas de ${detallestorneo.nombre}</title>
        <link rel="shortcut icon" href="../../imagenes/favicon.ico">
        <link href="../../css/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="../../css/estiloslayout.css" rel="stylesheet" type="text/css">
        <link href="../../css/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
        <link href="../../js/datepicker/jquery-ui.css" rel="stylesheet" type="text/css">
        <link href="../../js/clock/jquery.timepicker.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../../js/jquery-2.1.1.js"></script>
        <script type="text/javascript" src="../../js/jquery.validate.js"></script>
        <script type="text/javascript" src="../../css/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../../js/datepicker/jquery-ui.min.js"></script>
        <script type="text/javascript" src="../../js/clock/jquery.timepicker.js"></script>
        <style>
            .menu-opciones{
                 clear: both;
                padding-top: 10px;
            }
            table td:hover{
                background-color: #01bd24;
                color: white;
            }
            input,select{
                color: black;
            }
        </style>
<script>
$(document).ready(function() {
    $(function() {
   $( ".datepicker" ).datepicker( "option", "minDate", 0 );
   $( ".datepicker" ).datepicker( "option", "dateFormat", "yy-mm-dd" );
   $(".clockpick").timepicker({ 'timeFormat': 'H:i' });
  });
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
            <ul class="nav nav-tabs nav-justified">
            <li role="presentation"><a href="verCentro.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a></li>
            <li role="presentation"><a href="verCalendario.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>Calendario</a></li>
            <li role="presentation"><a <c:if test="${detallestorneo.tipo==3}"> href="verresultadoseli.jsp?idTorneo=${param.idTorneo}"</c:if> href="verMarcadores.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-tasks" aria-hidden="true"></span>Resultados</a></li>
            <li role="presentation"><a href="vermisTorneos.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>Tablas</a></li>           
            <li role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-globe" aria-hidden="true"></span>Resultados</a></li>
        </ul>
    </div>
    </div>
    <div class="row">
        <div class="col-md-4 col-sm-2 col-xs-12">
            <ol class="breadcrumb">
                <li><a href="../inicio.jsp">Inicio</a></li>
                <li><a href="misTorneos.jsp?idTorneo=${param.idTorneo}">Torneos</a></li>
                <li class="active">Resultados</li>
            </ol>
        </div>
    </div>
            <%--si el torneo es tipo 3 es eliminatoria--%>
<c:if test="${detallestorneo.tipo==3}">
<%--si la capacidad de este torneo es de 16--%>
<c:if test="${detallestorneo.capacidadEquipos==16}">
    <section>
        <div class="row">
            <div class="col-lg-12">
                <div class="page-header">
                    <h1 id="hmarcadoresoctavos" data-toggle="popover" 
title="Hecho" data-content="Se han establecido las fechas"
 data-placement="top">Modifica Fechas Y Horas</h1>
<%--confirmacion de fechas octavos--%>
<%
if (request.getParameter("octavos")!=null) {
%>
<script>
    $(document).ready(function(){
        $("#hmarcadoresoctavos").trigger("click");
    });
</script>
<script>
$('[data-toggle="popover"]').popover(
                {
                    trigger: 'click',
                    html: true,
                    delay: 500,
                }
            );
</script>
<%
    }
%>
                    <%--query de la primera ronda octavos en eli de 16 equipos--%> 
                    <sql:query var="calendario" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.marcador1,
                        partidos.marcador2,
                        partidos.ronda,
                        partidos.numero,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2
                        FROM 
                        partidos 
                        INNER JOIN equiposdeltorneo 
                        ON partidos.equipo1 = equiposdeltorneo.equipoCodigo 
                        INNER JOIN equipo
                        ON equiposdeltorneo.equipoCodigo = equipo.codigo 
                        INNER JOIN torneo 
                        ON partidos.idTorneo = torneo.idTorneo 
                        INNER JOIN cancha 
                        ON partidos.cancha = cancha.numeroCancha 
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/>  AND partidos.ronda = 1
                         
                    </sql:query>
                    <div class="panel panel-success">
                    <div class="panel-heading">Octavos De Final</div>
                    <form name="marcadores" action="../../GestionEliminatoria" autocomplete="off">
                        <table class="table table-hover table-responsive">
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${calendario.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td style="text-align: right"><c:if test="${row.marcador1 ==null}"><input type="number" id="${vs.index}muno" name="${vs.index}muno" <c:if test="${row.marcador1 !=null}"> value="${row.marcador1}"</c:if> onchange="validarEmpate${vs.index}()"/></c:if><span>${row.marcador1}</span></td>
                                <td style="text-align: center"><span>vs</span></td>
                                <td><c:if test="${row.marcador1 ==null}"><input type="number" id="${vs.index}mdos" name="${vs.index}mdos" <c:if test="${row.marcador2 !=null}"> value="${row.marcador2}"</c:if> onchange="validarEmpate${vs.index}()" /></c:if><span>${row.marcador2}</span></td>
                                <td>${row.eq2}</td>                     
                                
                            </tr>

                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-success">
                    <div class="panel-heading">Cuartos De Final</div>
                    <%--query para los cuartos--%>
                    <sql:query var="cuartos" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.marcador1,
                        partidos.marcador2,
                        partidos.ronda,
                        partidos.numero,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2
                        FROM 
                        partidos 
                        INNER JOIN equiposdeltorneo 
                        ON partidos.equipo1 = equiposdeltorneo.equipoCodigo 
                        INNER JOIN equipo
                        ON equiposdeltorneo.equipoCodigo = equipo.codigo 
                        INNER JOIN torneo 
                        ON partidos.idTorneo = torneo.idTorneo 
                        INNER JOIN cancha 
                        ON partidos.cancha = cancha.numeroCancha 
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/>  AND partidos.ronda = 2
                    </sql:query>
    
                        <table class="table table-hover table-responsive">
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
<%--munoc = marcador 1 en cuartos --%>                      
<c:forEach var="row" items="${cuartos.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><c:if test="${row.marcador1 ==null}"> <input type="number" id="${vs.index}munoc" name="${vs.index}munoc" <c:if test="${row.marcador1 !=null}"> value="${row.marcador1}"</c:if> onchange="noEmpateCuartos${vs.index}()"/></c:if><span>${row.marcador1}</span></td>
                                <td><span>vs</span></td>
                                <td><c:if test="${row.marcador2 ==null}"> <input type="number" id="${vs.index}mdosc" name="${vs.index}mdosc" <c:if test="${row.marcador2 !=null}"> value="${row.marcador2}"</c:if> onchange="noEmpateCuartos${vs.index}()" /></c:if><span><c:out value="${row.marcador2}"/></span></td>
                                <td>${row.eq2}</td>                     
                                
                            </tr>

                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                </div>
        </div>
    <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-success" id="psemi">
                    <div class="panel-heading">Semi final</div>
                    <%--query para la semi--%>
                    <sql:query var="semi" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.marcador1,
                        partidos.marcador2,
                        partidos.ronda,
                        partidos.numero,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2
                        FROM 
                        partidos 
                        INNER JOIN equiposdeltorneo 
                        ON partidos.equipo1 = equiposdeltorneo.equipoCodigo 
                        INNER JOIN equipo
                        ON equiposdeltorneo.equipoCodigo = equipo.codigo 
                        INNER JOIN torneo 
                        ON partidos.idTorneo = torneo.idTorneo 
                        INNER JOIN cancha 
                        ON partidos.cancha = cancha.numeroCancha 
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/>  AND partidos.ronda = 3
                    </sql:query>
    
                        <table class="table table-hover table-responsive">
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${semi.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><c:if test="${row.marcador1 ==null}"><input type="number" id="${vs.index}munos" name="${vs.index}munos" <c:if test="${row.marcador1 !=null}"> value="${row.marcador1}"</c:if> onchange="noEmpateSemi${vs.index}()"/></c:if><span><c:out value="${row.marcador1}" /></span></td>
                                <td><span>vs</span></td>
                                <td><c:if test="${row.marcador2 ==null}"><input type="number" id="${vs.index}mdoss" name="${vs.index}mdoss" <c:if test="${row.marcador2 !=null}"> value="${row.marcador2}"</c:if> onchange="noEmpateSemi${vs.index}()" /></c:if><span><c:out value="${row.marcador2}" /></span></td>
                                <td>${row.eq2}</td>                     
                              
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                </div>
        </div>
<%--query para saber si la eli tiene tercer puesto --%>
<sql:query var="ptercer" dataSource="${sessionScope.cnn}">
    SELECT tercerPuesto FROM eliminatoria 
    WHERE idEliminatoria = ? <sql:param value="${param.idTorneo}"/>
</sql:query>
<c:set var="haytercer" value="${ptercer.rows[0]}" scope="page" />   
<div class="row">
            <div class="col-lg-12">
                <div class="panel panel-success">
                    <div class="panel-heading">Tercer Puesto</div>
                    <%--query para los cuartos--%>
                    <sql:query var="tercer" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.marcador1,
                        partidos.marcador2,
                        partidos.ronda,
                        partidos.numero,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2
                        FROM 
                        partidos 
                        INNER JOIN equiposdeltorneo 
                        ON partidos.equipo1 = equiposdeltorneo.equipoCodigo 
                        INNER JOIN equipo
                        ON equiposdeltorneo.equipoCodigo = equipo.codigo 
                        INNER JOIN torneo 
                        ON partidos.idTorneo = torneo.idTorneo 
                        INNER JOIN cancha 
                        ON partidos.cancha = cancha.numeroCancha 
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/>  AND partidos.ronda = 5
                    </sql:query>
    
                        <table class="table table-hover table-responsive">
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${tercer.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><c:if test="${row.marcador1 ==null}"><input type="number" name="${vs.index}muno" <c:if test="${row.marcador1 !=null}"> value="${row.marcador1}"</c:if>/></c:if> <span><c:out value="${row.marcador1}" /></span></td>
                                <td><span>vs</span></td>
                                <td><c:if test="${row.marcador2 ==null}"><input type="number" name="${vs.index}mdos" <c:if test="${row.marcador2 !=null}"> value="${row.marcador2}"</c:if> /></c:if> <span><c:out value="${row.marcador2}" /></span></td>
                                <td>${row.eq2}</td>                     
                                
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                </div>
        </div>


    <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-success">
                    <div class="panel-heading">Final</div>
                    <%--query para los cuartos--%>
                    <sql:query var="paridofinal" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.marcador1,
                        partidos.marcador2,
                        partidos.ronda,
                        partidos.numero,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2
                        FROM 
                        partidos 
                        INNER JOIN equiposdeltorneo 
                        ON partidos.equipo1 = equiposdeltorneo.equipoCodigo 
                        INNER JOIN equipo
                        ON equiposdeltorneo.equipoCodigo = equipo.codigo 
                        INNER JOIN torneo 
                        ON partidos.idTorneo = torneo.idTorneo 
                        INNER JOIN cancha 
                        ON partidos.cancha = cancha.numeroCancha 
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/>  AND partidos.ronda = 4
                    </sql:query>
    
                        <table class="table table-hover table-responsive">
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${partidofinal.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><c:if test="${row.marcador1 ==null}"> <input type="number" name="${vs.index}muno" <c:if test="${row.marcador1 !=null}"> value="${row.marcador1}"</c:if>/></c:if><span><c:out value="${row.marcador1}" /></span></td>
                                <td><span>vs</span></td>
                                <td><c:if test="${row.marcador2 ==null}"> <input type="number" name="${vs.index}mdos" <c:if test="${row.marcador2 !=null}"> value="${row.marcador2}"</c:if> /></c:if><span><c:out value="${row.marcador2}" /></span></td>
                                <td>${row.eq2}</td>                     
                             
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                </div>
        </div>
    </section>
</c:if><%--si es de 16 equipos--%>
</c:if><%--si es eliminatoria--%>







<c:if test="${detallestorneo.tipo == 2}">
    <div class="row">
            <div class="col-lg-12">
                <div class="page-header">
                    <h1>Marcadores</h1>
                </div>
                    <%--query de la primera ronda importantisimo ordenar todo por numero de partido o me cambia el numero cuando llegue al controlador--%> 
                    <sql:query var="lpronda" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.marcador1,
                        partidos.marcador2,
                        partidos.ronda,
                        partidos.numero,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2
                        FROM 
                        partidos 
                        INNER JOIN equiposdeltorneo 
                        ON partidos.equipo1 = equiposdeltorneo.equipoCodigo 
                        INNER JOIN equipo
                        ON equiposdeltorneo.equipoCodigo = equipo.codigo 
                        INNER JOIN torneo 
                        ON partidos.idTorneo = torneo.idTorneo 
                        INNER JOIN cancha 
                        ON partidos.cancha = cancha.numeroCancha 
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/>  AND partidos.ronda = 1
                        order by numero 
                    </sql:query>
                    <div class="panel panel-success">
                    <div class="panel-heading">Primera Ronda</div>
                   
                        <table class="table table-hover table-responsive">
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${lpronda.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><span>${row.marcador1}</span></td>
                                <td><span>vs</span></td>
                                <td><span>${row.marcador2}</span></td>
                                <td>${row.eq2}</td>                     
                                
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

<%--segunda ronda--%>
<div class="row">
            <div class="col-lg-12">
                    <%--query de la segunda ronda --%> 
                    <sql:query var="lsronda" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.marcador1,
                        partidos.marcador2,
                        partidos.ronda,
                        partidos.numero,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2
                        FROM 
                        partidos 
                        INNER JOIN equiposdeltorneo 
                        ON partidos.equipo1 = equiposdeltorneo.equipoCodigo 
                        INNER JOIN equipo
                        ON equiposdeltorneo.equipoCodigo = equipo.codigo 
                        INNER JOIN torneo 
                        ON partidos.idTorneo = torneo.idTorneo 
                        INNER JOIN cancha 
                        ON partidos.cancha = cancha.numeroCancha 
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/>  AND partidos.ronda = 2
                         order by numero
                    </sql:query>
                    <div class="panel panel-success">
                    <div class="panel-heading">Segunda Ronda</div>
                
                        <table class="table table-hover table-responsive">
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${lsronda.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><span>${row.marcador1}</span></td>
                                <td><span>vs</span></td>
                                <td><c:if test="${row.marcador1 ==null}"><input type="number" id="${vs.index}mdos" name="${vs.index}mdos" <c:if test="${row.marcador2 !=null}"> value="${row.marcador2}"</c:if> /></c:if><span>${row.marcador2}</span></td>
                                <td>${row.eq2}</td>                     
                              
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>

<%--tercera ronda--%>
<div class="row">
            <div class="col-lg-12">
                    <%--query de la primera ronda octavos en eli de 16 equipos--%> 
                    <sql:query var="ltronda" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.marcador1,
                        partidos.marcador2,
                        partidos.ronda,
                        partidos.numero,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2
                        FROM 
                        partidos 
                        INNER JOIN equiposdeltorneo 
                        ON partidos.equipo1 = equiposdeltorneo.equipoCodigo 
                        INNER JOIN equipo
                        ON equiposdeltorneo.equipoCodigo = equipo.codigo 
                        INNER JOIN torneo 
                        ON partidos.idTorneo = torneo.idTorneo 
                        INNER JOIN cancha 
                        ON partidos.cancha = cancha.numeroCancha 
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/>  AND partidos.ronda = 3
                         order by numero
                    </sql:query>
                    <div class="panel panel-success">
                    <div class="panel-heading">Tercera Ronda</div>
                  
                        <table class="table table-hover table-responsive">
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${ltronda.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><c:if test="${row.marcador1 ==null}"><input type="number" id="${vs.index}muno" name="${vs.index}muno" <c:if test="${row.marcador1 !=null}"> value="${row.marcador1}"</c:if> /></c:if><span>${row.marcador1}</span></td>
                                <td><span>vs</span></td>
                                <td><c:if test="${row.marcador1 ==null}"><input type="number" id="${vs.index}mdos" name="${vs.index}mdos" <c:if test="${row.marcador2 !=null}"> value="${row.marcador2}"</c:if> /></c:if><span>${row.marcador2}</span></td>
                                <td>${row.eq2}</td>                     
                               
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>

<%--cuarta ronda--%>
<div class="row">
            <div class="col-lg-12">
                    <%--query de la 4--%> 
                    <sql:query var="lcronda" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.marcador1,
                        partidos.marcador2,
                        partidos.ronda,
                        partidos.numero,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2
                        FROM 
                        partidos 
                        INNER JOIN equiposdeltorneo 
                        ON partidos.equipo1 = equiposdeltorneo.equipoCodigo 
                        INNER JOIN equipo
                        ON equiposdeltorneo.equipoCodigo = equipo.codigo 
                        INNER JOIN torneo 
                        ON partidos.idTorneo = torneo.idTorneo 
                        INNER JOIN cancha 
                        ON partidos.cancha = cancha.numeroCancha 
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/>  AND partidos.ronda = 4
                         order by numero
                    </sql:query>
                    <div class="panel panel-success">
                    <div class="panel-heading">Cuarta Ronda</div>
                  
                        <table class="table table-hover table-responsive">
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${lcronda.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><c:if test="${row.marcador1 ==null}"><input type="number" id="${vs.index}muno" name="${vs.index}muno" <c:if test="${row.marcador1 !=null}"> value="${row.marcador1}"</c:if> /></c:if><span>${row.marcador1}</span></td>
                                <td><span>vs</span></td>
                                <td><c:if test="${row.marcador1 ==null}"><input type="number" id="${vs.index}mdos" name="${vs.index}mdos" <c:if test="${row.marcador2 !=null}"> value="${row.marcador2}"</c:if> /></c:if><span>${row.marcador2}</span></td>
                                <td>${row.eq2}</td>                     
                               
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


<%--quinta ronda--%>
<div class="row">
            <div class="col-lg-12">
                    <%--query de la quinta ronda--%> 
                    <sql:query var="lqronda" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.marcador1,
                        partidos.marcador2,
                        partidos.ronda,
                        partidos.numero,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2
                        FROM 
                        partidos 
                        INNER JOIN equiposdeltorneo 
                        ON partidos.equipo1 = equiposdeltorneo.equipoCodigo 
                        INNER JOIN equipo
                        ON equiposdeltorneo.equipoCodigo = equipo.codigo 
                        INNER JOIN torneo 
                        ON partidos.idTorneo = torneo.idTorneo 
                        INNER JOIN cancha 
                        ON partidos.cancha = cancha.numeroCancha 
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/>  AND partidos.ronda = 5
                         order by numero
                    </sql:query>
                    <div class="panel panel-success">
                    <div class="panel-heading">Cuarta Ronda</div>
                   
                        <table class="table table-hover table-responsive">
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${lqronda.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><c:if test="${row.marcador1 ==null}"><input type="number" id="${vs.index}muno" name="${vs.index}muno" <c:if test="${row.marcador1 !=null}"> value="${row.marcador1}"</c:if> /></c:if><span>${row.marcador1}</span></td>
                                <td><span>vs</span></td>
                                <td><c:if test="${row.marcador1 ==null}"><input type="number" id="${vs.index}mdos" name="${vs.index}mdos" <c:if test="${row.marcador2 !=null}"> value="${row.marcador2}"</c:if> /></c:if><span>${row.marcador2}</span></td>
                                <td>${row.eq2}</td>                     
                            
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>
</c:if>
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
</main>
    </body>
</html>
<% 
    }//si el rol fue 2
            }//si hay sesion
                    else{
                        response.sendRedirect("../../index.jsp?auth=prohibido");
                    }
%>
