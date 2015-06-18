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
                    if(rol == 1){
%>
<%--  Query con la info del torneo --%>
<sql:query var="torneo" dataSource="${sessionScope.cnn}">
    SELECT idTorneo, nombre FROM torneo
</sql:query>
<%--  Query con los numeros de las canchas --%>
<sql:query var="numcanchas" dataSource="${sessionScope.cnn}">
    SELECT numeroCancha FROM cancha
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
        <link href="../../css/bootstrap/datepicker/css/datepicker.css" rel="stylesheet" type="text/css">
        <link href="../../js/clock/jquery.timepicker.css" rel="stylesheet" type="text/css">
        <link href="../../js/jquery-toastmessage-plugin-jquery-toastmessage-plugin-0.2.0/src/main/resources/css/jquery.toastmessage.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../../js/jquery-2.1.1.js"></script>
        <script type="text/javascript" src="../../js/jquery.validate.js"></script>
        <script type="text/javascript" src="../../css/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../../css/bootstrap/datepicker/js/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="../../js/clock/jquery.timepicker.js"></script>
        <script type="text/javascript" src="../../js/jquery-toastmessage-plugin-jquery-toastmessage-plugin-0.2.0/src/main/javascript/jquery.toastmessage.js"></script>
        <style>
            .menu-opciones{
                 clear: both;
                padding-top: 10px;
            }
            table td:hover{
                background-color: #01bd24;
                color: white;
                transition: all 0.5s;
            }
            td.day:hover{
                background: #1ae832;
                color: black;
                -ms-transform: rotate(7deg); /* IE 9 */
                -webkit-transform: rotate(7deg); /* Chrome, Safari, Opera */
                transform: rotate(7deg);
                transition: all 2s;
            }
            input,select{
                color: black;
            }
            p.fechas{
                color: green;
            }
        </style>
<script>
$(document).ready(function() {
   $( ".datepicker" ).datepicker({
       format: 'yyyy-mm-dd'
   });
   $(".clockpick").timepicker({ 'timeFormat': 'H:i' });
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
            <li role="presentation"><a href="inscribirEquipos.jsp?idTorneo=${param.idTorneo}"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>Inscribir equipos</a></li>
        </ul>
    </div>
    </div>
    <div class="row">
        <div class="col-md-4 col-sm-2 col-xs-12">
            <ol class="breadcrumb">
                <li><a href="../inicio.jsp">Inicio</a></li>
                <li><a href="misTorneos.jsp?idTorneo=${param.idTorneo}">Torneos</a></li>
                <li class="active">Calendario</li>
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
                    <h1 id="hfechasoctavos" data-toggle="popover" 
title="Hecho" data-content="Se han establecido las fechas"
data-placement="top">Modifica Fechas Y Horas <small>octavos</small></h1>
                </div>
<%--confirmacion de fechas octavos--%>
<%
if (request.getParameter("octavos")!=null) {
%>
<script>
    $(document).ready(function(){
        $("#hfechasoctavos").trigger("click");
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
<%--confirmacion de fechas cuartos--%>
<%
if (request.getParameter("cuartos")!=null) {
%>
<script>
    $(document).ready(function(){
        $("#hfechascuartos").trigger("click");
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
                        partidos.cancha,
                        partidos.ronda,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2,
                        partidos.fecha,
                        partidos.hora
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
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/> AND partidos.ronda = 1
                    </sql:query>
                    <div class="panel panel-success">
                    <div class="panel-heading">Octavos De Final</div>
                    <form action="../../GestionEliminatoria" name="calendar" id="calendar" autocomplete="off">
                        <table class="table table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>Equipo</th>
                            <th>vs</th>
                            <th>Equipo</th>
                            <th>Cancha</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                        </tr>
                        </thead>
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${calendario.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><span>-</span></td>
                                <td>${row.eq2}</td>
                                <td>
                                    <select name="cp${vs.index}" id="cp${vs.index}">
                                        <option></option>
                                        <c:forEach var="canchas" items="${numcanchas.rows}">
                                            <option <c:if test="${canchas.numeroCancha == row.cancha}">selected</c:if>>${canchas.numeroCancha}</option>    
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input type="text" name="fecha${vs.index}" id="fecha${vs.index}" class="datepicker"  <c:if test="${row.fecha !=null}"> value="${row.fecha}"</c:if>/></td>
                                <td><input type="text" name="hora${vs.index}" id="hora${vs.index}" class="clockpick" <c:if test="${row.hora !=null}"> value="${row.hora}"</c:if> /></td>
                                <input type="hidden" value="${row.equipo1}" name="${vs.index}equipo1" />
                                <input type="hidden" value="${row.equipo2}" name="${vs.index}equipo2" />
                                <input type="hidden" value="${row.eq1}" name="${vs.index}nequipo1" />
                                <input type="hidden" value="${row.eq2}" name="${vs.index}nequipo2" />
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
<input type="hidden" value="${param.idTorneo}" name="idTorneo" />
<button class="btn btn-success" id="fechasOctavos" name="validarCampos" type="button" onclick="validarIguales()">Añadir Fechas</button>
<input type="hidden" name="asignarfechas" value="fechas" />                    
<input type="hidden" name="foctavos" value="octavos" />
                    </form>
<script>
    function validarIguales(){
    //paso el input a una variable y si es null le asigno algo 
var cancha0 = document.calendar.cp0.value;   
var fecha0 = document.calendar.fecha0.value;
var hora0 = document.calendar.hora0.value;
var partido1 = cancha0+fecha0+hora0 == '' ? 'partido1' : cancha0+fecha0+hora0;

var cancha1 = document.calendar.cp1.value;
var fecha1 = document.calendar.fecha1.value;
var hora1 = document.calendar.hora1.value;
var partido2 = cancha1+fecha1+hora1 == '' ? 'partido2' : cancha1+fecha1+hora1;

var cancha2 = document.calendar.cp2.value;
var fecha2 = document.calendar.fecha2.value;
var hora2 = document.calendar.hora2.value;
var partido3 = cancha2+fecha2+hora2 == '' ? 'partido3' : cancha2+fecha2+hora2;

var cancha3 = document.calendar.cp3.value;
var fecha3 = document.calendar.fecha3.value;
var hora3 = document.calendar.hora3.value;
var partido4 = cancha3+fecha3+hora3 == '' ? 'partido4' : cancha3+fecha3+hora3;

var cancha4 = document.calendar.cp4.value;
var fecha4 = document.calendar.fecha4.value;
var hora4 = document.calendar.hora4.value;
var partido5 = cancha4+fecha4+hora4 == '' ? 'partido5' : cancha4+fecha4+hora4;

var cancha5 = document.calendar.cp5.value;
var fecha5 = document.calendar.fecha5.value;
var hora5 = document.calendar.hora5.value;
var partido6 = cancha5+fecha5+hora5 == '' ? 'partido6' : cancha5+fecha5+hora5;
                
var cancha6 = document.calendar.cp6.value;
var fecha6 = document.calendar.fecha6.value;
var hora6 = document.calendar.hora6.value;
var partido7 = cancha6+fecha6+hora6 == '' ? 'partido7' : cancha6+fecha6+hora6;

var cancha7 = document.calendar.cp7.value;
var fecha7 = document.calendar.fecha7.value;
var hora7 = document.calendar.hora7.value;
var partido8 = cancha7+fecha7+hora7 == '' ? 'partido8' : cancha7+fecha7+hora7;

        if (partido1 === partido2 || partido1 === partido3 || partido1 === partido4 || partido1 === partido5 || partido1 === partido6 || partido1 === partido7 || partido1 === partido8 ) {
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");
        }
    else if(partido2 === partido1 || partido2 === partido3 || partido2 === partido4 || partido2 === partido5 || partido2 === partido6 || partido2 === partido7 || partido2 === partido8){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");   
    }else if(partido3 === partido1 || partido3 === partido2 || partido3 === partido4 || partido3 === partido5 || partido3 === partido6 || partido3 === partido7 || partido3 === partido8){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");
    }else if(partido4 === partido1 || partido4 === partido2 || partido4 === partido3 || partido4 === partido5 || partido4 === partido6 || partido4 === partido7 || partido4 === partido8){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora"); 
    }else if(partido5 === partido1 || partido5 === partido2 || partido5 === partido3 || partido5 === partido4 || partido5 === partido6 || partido5 === partido7 || partido5 === partido8){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");  
    }else if(partido6 === partido1 || partido6 === partido2 || partido6 === partido3 || partido6 === partido4 || partido6 === partido5 || partido6 === partido7 || partido6 === partido8){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");  
    }else if(partido7 === partido1 || partido7 === partido2 || partido7 === partido3 || partido7 === partido4 || partido7 === partido5 || partido7 === partido6 || partido7 === partido8){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");   
    }else if(partido8 === partido1 || partido8 === partido2 || partido8 === partido3 || partido8 === partido4 || partido8 === partido5 || partido8 === partido6 || partido8 === partido7){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");    
    }else{//si nada fue igual envio
        $("#calendar").submit();
    }
}
</script>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12" id="ccuartos">
                <div class="panel panel-success">
                    <div class="panel-heading">Cuartos De Final</div>
                    <%--query para los cuartos--%>
                    <sql:query var="cuartos" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.cancha,
                        partidos.ronda,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2,
                        partidos.fecha,
                        partidos.hora
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
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/> AND partidos.ronda = 2
                    </sql:query>
    
                        <form action="../../GestionEliminatoria" id="calendarcuartos" name="calendarcuartos">
                        <table class="table table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>Equipo</th>
                            <th>vs</th>
                            <th>Equipo</th>
                            <th>Cancha</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                        </tr>
                        </thead>
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${cuartos.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><span>-</span></td>
                                <td>${row.eq2}</td>
                                <td>
                                    <select name="cp${vs.index}" id="cp${vs.index}">
                                        <option></option>
                                        <c:forEach var="canchas" items="${numcanchas.rows}">
                                            <option <c:if test="${canchas.numeroCancha == row.cancha}">selected</c:if>>${canchas.numeroCancha}</option>    
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input type="text" class="datepicker" name="fecha${vs.index}" <c:if test="${row.fecha !=null}"> value="${row.fecha}"</c:if>/></td>
                                <td><input type="text" name="hora${vs.index}" class="clockpick" <c:if test="${row.hora !=null}"> value="${row.hora}"</c:if> /></td>
                                <input type="hidden" value="${row.equipo1}" name="${vs.index}equipo1" />
                                <input type="hidden" value="${row.equipo2}" name="${vs.index}equipo2" />
                                <input type="hidden" value="${row.eq1}" name="${vs.index}nequipo1" />
                                <input type="hidden" value="${row.eq2}" name="${vs.index}nequipo2" />
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
<input type="hidden" value="${param.idTorneo}" name="idTorneo" />
<button class="btn btn-success" name="validarCampos" type="button" onclick="igualesCuartos()">Añadir Fechas</button>
<input type="hidden" name="asignarfechas" value="cuartos" />                   
<input type="hidden" name="fcuartos" value="cuartos" />
<% if(request.getParameter("cuartos")!=null){%> <P style="color:green"><%=request.getParameter("cuartos")%></P> <% }  %>
</form>
<script>
    function igualesCuartos(){
    //paso el input a una variable y si es null le asigno algo 
    
var cancha0 = document.calendarcuartos.cp0.value;   
var fecha0 = document.calendarcuartos.fecha0.value;
var hora0 = document.calendarcuartos.hora0.value;
var partido1 = cancha0+fecha0+hora0 == '' ? 'partido1' : cancha0+fecha0+hora0;

var cancha1 = document.calendarcuartos.cp1.value;
var fecha1 = document.calendarcuartos.fecha1.value;
var hora1 = document.calendarcuartos.hora1.value;
var partido2 = cancha1+fecha1+hora1 == '' ? 'partido2' : cancha1+fecha1+hora1;

var cancha2 = document.calendarcuartos.cp2.value;
var fecha2 = document.calendarcuartos.fecha2.value;
var hora2 = document.calendarcuartos.hora2.value;
var partido3 = cancha2+fecha2+hora2 == '' ? 'partido3' : cancha2+fecha2+hora2 ;

var cancha3 = document.calendarcuartos.cp3.value;
var fecha3 = document.calendarcuartos.fecha3.value;
var hora3 = document.calendarcuartos.hora3.value;
var partido4 = cancha3+fecha3+hora3 == '' ? 'partido4' : cancha3+fecha3+hora3;
    
    if (partido1 == partido2 || partido1 == partido3 || partido1 == partido4) {
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");
        }
    else if(partido2 == partido1 || partido2 == partido3 || partido2 == partido4){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");    
    }else if(partido3 == partido1 || partido3 == partido2 || partido3 == partido4){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora"); 
    }else if(partido4 == partido1 || partido4 == partido2 || partido4 == partido3){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora"); 
    }else{//si nada fue igual envio
        $("#calendarcuartos").submit();
    }
      
    }
</script>
                </div>
                </div>
        </div>
    <div class="row">
        <div class="col-lg-12" id="csemi">
                <div class="panel panel-success">
                    <div class="panel-heading">Semi final</div>
                    <%--query para la semi--%>
                    <sql:query var="semi" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.cancha,
                        partidos.ronda,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2,
                        partidos.fecha,
                        partidos.hora
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
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/> AND partidos.ronda = 3
                    </sql:query>
    
                        <form action="../../GestionEliminatoria" id="calendarsemi" name="calendarsemi">
                        <table class="table table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>Equipo</th>
                            <th>vs</th>
                            <th>Equipo</th>
                            <th>Cancha</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                        </tr>
                        </thead>
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${semi.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><span>-</span></td>
                                <td>${row.eq2}</td>
                                <td>
                                    <select name="cp${vs.index}" id="cp${vs.index}">
                                        <option></option>
                                        <c:forEach var="canchas" items="${numcanchas.rows}">
                                            <option <c:if test="${canchas.numeroCancha == row.cancha}">selected</c:if>>${canchas.numeroCancha}</option>    
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input type="text" class="datepicker" name="fecha${vs.index}" <c:if test="${row.fecha !=null}"> value="${row.fecha}"</c:if> /></td>
                                <td><input type="text" name="hora${vs.index}" class="clockpick" <c:if test="${row.hora !=null}"> value="${row.hora}"</c:if> /></td>
                                <input type="hidden" value="${row.equipo1}" name="${vs.index}equipo1" />
                                <input type="hidden" value="${row.equipo2}" name="${vs.index}equipo2" />
                                <input type="hidden" value="${row.eq1}" name="${vs.index}nequipo1" />
                                <input type="hidden" value="${row.eq2}" name="${vs.index}nequipo2" />
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
<input type="hidden" value="${param.idTorneo}" name="idTorneo" />
<button class="btn btn-success" type="button" onclick="igualesSemi()">Añadir Fechas</button>
<input type="hidden" name="asignarfechas" value="asemi" />                    
<input type="hidden" name="fsemi" value="semi" />
<% if(request.getParameter("semi")!=null){%> <P style="color:green"><%=request.getParameter("semi")%></P> <% }  %>
                    </form>
<script>
    function igualesSemi(){
    //paso el input a una variable y si es null le asigno algo 
var cancha0 = (document.calendarsemi.cp0.value !== null) ? document.calendarsemi.cp0.value : 'cancha0';   
var fecha0 = (document.calendarsemi.fecha0.value !== null) ? document.calendarsemi.fecha0.value: 'fecha0';
var hora0 = (document.calendarsemi.hora0.value !== null) ? document.calendarsemi.hora0.value : 'hora0';
var partido1 = cancha0+fecha0+hora0;

var cancha1 = (document.calendarsemi.cp1.value !== null) ? document.calendarsemi.cp1.value : 'cancha1';
var fecha1 = (document.calendarsemi.fecha1.value !== null) ? document.calendarsemi.fecha1.value: 'fecha1';
var hora1 = (document.calendarsemi.hora1.value !== null) ? document.calendarsemi.hora1.value : 'hora1';
var partido2 = cancha1+fecha1+hora1;
    
        if (partido1 === partido2) {
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");
        }else{//si nada fue igual envio
        $("#calendarsemi").submit();
    }

    }
</script>
                </div>
                </div>
        </div>
<%--query para saber si la eli tiene tercer puesto --%>
<sql:query var="ptercer" dataSource="${sessionScope.cnn}">
    SELECT tercerPuesto FROM eliminatoria 
    WHERE idEliminatoria = ? <sql:param value="${param.idTorneo}"/>
</sql:query>
<c:set var="haytercer" value="${ptercer.rows[0]}" scope="page" />
<c:if test="${haytercer.tercerPuesto == true}">
    <div class="row">
        <div class="col-lg-12" id="ctercerp">
                <div class="panel panel-success">
                    <div class="panel-heading">Tercer Puesto</div>
                    <%--query para los cuartos--%>
                    <sql:query var="fin" dataSource="jdbc/pro-level">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.cancha,
                        partidos.ronda,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2,
                        partidos.fecha,
                        partidos.hora
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
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/> AND partidos.ronda = 5
                    </sql:query>
    
                        <form action="../../GestionEliminatoria">
                        <table class="table table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>Equipo</th>
                            <th>vs</th>
                            <th>Equipo</th>
                            <th>Cancha</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                        </tr>
                        </thead>
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${fin.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><span>-</span></td>
                                <td>${row.eq2}</td>
                                <td>
                                    <select name="cp${vs.index}" id="cp${vs.index}">
                                        <option></option>
                                        <c:forEach var="canchas" items="${numcanchas.rows}">
                                            <option <c:if test="${canchas.numeroCancha == row.cancha}">selected</c:if>>${canchas.numeroCancha}</option>    
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input type="text" name="fecha${vs.index}" class="datepicker" <c:if test="${row.fecha !=null}"> value="${row.fecha}"</c:if> /></td>
                                <td><input type="text" name="hora${vs.index}" class="clockpick" <c:if test="${row.hora !=null}"> value="${row.hora}"</c:if> /></td>
                                <input type="hidden" value="${row.equipo1}" name="${vs.index}equipo1" />
                                <input type="hidden" value="${row.equipo2}" name="${vs.index}equipo2" />
                                <input type="hidden" value="${row.eq1}" name="${vs.index}nequipo1" />
                                <input type="hidden" value="${row.eq2}" name="${vs.index}nequipo2" />
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
<input type="hidden" value="${param.idTorneo}" name="idTorneo" />
                    <button class="btn btn-success" name="asignarfechas">Añadir Fechas</button>
                    <input type="hidden" name="ftercer" value="terceros" />
<% if(request.getParameter("tercer")!=null){%> <P style="color:green"><%=request.getParameter("tercer")%></P> <% }  %>
                    </form>
                </div>
                </div>
        </div>                
</c:if>
                    
    <div class="row">
        <div class="col-lg-12" id="cfinal">
                <div class="panel panel-success">
                    <div class="panel-heading">Final</div>
                    <%--query para los cuartos--%>
                    <sql:query var="fin" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.cancha,
                        partidos.ronda,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2,
                        partidos.fecha,
                        partidos.hora
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
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/> AND partidos.ronda = 4
                    </sql:query>
    
                        <form action="../../GestionEliminatoria">
                        <table class="table table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>Equipo</th>
                            <th>vs</th>
                            <th>Equipo</th>
                            <th>Cancha</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                        </tr>
                        </thead>
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${fin.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><span>-</span></td>
                                <td>${row.eq2}</td>
                                <td>
                                    <select name="cp${vs.index}" id="cp${vs.index}">
                                        <option></option>
                                        <c:forEach var="canchas" items="${numcanchas.rows}">
                                            <option <c:if test="${canchas.numeroCancha == row.cancha}">selected</c:if>>${canchas.numeroCancha}</option>    
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input type="text" name="fecha${vs.index}" class="datepicker" <c:if test="${row.fecha !=null}"> value="${row.fecha}"</c:if> /></td>
                                <td><input type="text" name="hora${vs.index}" class="clockpick" <c:if test="${row.hora !=null}"> value="${row.hora}"</c:if> /></td>
                                <input type="hidden" value="${row.equipo1}" name="${vs.index}equipo1" />
                                <input type="hidden" value="${row.equipo2}" name="${vs.index}equipo2" />
                                <input type="hidden" value="${row.eq1}" name="${vs.index}nequipo1" />
                                <input type="hidden" value="${row.eq2}" name="${vs.index}nequipo2" />
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
<input type="hidden" value="${param.idTorneo}" name="idTorneo" />
                    <button class="btn btn-success" name="asignarfechas">Añadir Fechas</button>
                    <input type="hidden" name="ffinal" value="final" />
<% if(request.getParameter("final")!=null){%> <P style="color:green"><%=request.getParameter("final")%></P> <% }  %>
                    </form>
                </div>
                </div>
        </div>
    </section>
</c:if><%--si es de 16 equipos--%>
</c:if><%--si es eliminatoria--%>











<%--si es liga--%>
<c:if test="${detallestorneo.tipo==2}">
    <div class="row" id="primerar">
        <div class="col-lg-12">
<%--query de la primera ronda liga de seis--%> 
                    <sql:query var="primera" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.cancha,
                        partidos.ronda,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2,
                        partidos.fecha,
                        partidos.hora
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
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/> AND partidos.ronda = 1
                    </sql:query>
                    <div class="panel panel-success">
                    <div class="panel-heading">Primera Ronda</div>
                    <form action="../../GestionLiga" name="pliga" id="pliga" autocomplete="off">
                        <table class="table table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>Equipo</th>
                            <th>vs</th>
                            <th>Equipo</th>
                            <th>Cancha</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                        </tr>
                        </thead>
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${primera.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><span>-</span></td>
                                <td>${row.eq2}</td>
                                <td>
                                    <select name="cp${vs.index}" id="cp${vs.index}">
                                        <option></option>
                                        <c:forEach var="canchas" items="${numcanchas.rows}">
                                            <option <c:if test="${canchas.numeroCancha == row.cancha}">selected</c:if>>${canchas.numeroCancha}</option>    
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input type="text" name="fecha${vs.index}" id="fecha${vs.index}" class="datepicker"  <c:if test="${row.fecha !=null}"> value="${row.fecha}"</c:if>/></td>
                                <td><input type="text" name="hora${vs.index}" id="hora${vs.index}" class="clockpick" <c:if test="${row.hora !=null}"> value="${row.hora}"</c:if> /></td>
                                <input type="hidden" value="${row.equipo1}" name="${vs.index}equipo1" />
                                <input type="hidden" value="${row.equipo2}" name="${vs.index}equipo2" />
                                <input type="hidden" value="${row.eq1}" name="${vs.index}nequipo1" />
                                <input type="hidden" value="${row.eq2}" name="${vs.index}nequipo2" />
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
<input type="hidden" value="${param.idTorneo}" name="idTorneo" />
<button class="btn btn-success" id="fechasOctavos" name="validarCampos" type="button" onclick="fIgualesR1()">Añadir Fechas</button>
<input type="hidden" name="asignarfechas" value="fechas" />                    
<input type="hidden" name="primeraronda" value="primera" />
<% if(request.getParameter("primera")!=null){%><p class="fechas"><%=request.getParameter("primera")%></p> <%} %>
                    </form>
<script>
    function fIgualesR1(){
    //paso el input a una variable y si es null le asigno algo 
var cancha0 = document.pliga.cp0.value;   
var fecha0 = document.pliga.fecha0.value;
var hora0 = document.pliga.hora0.value;
var partido1 = cancha0+fecha0+hora0 == '' ? 'partido1' : cancha0+fecha0+hora0;

var cancha1 = document.pliga.cp1.value;
var fecha1 = document.pliga.fecha1.value;
var hora1 = document.pliga.hora1.value;
var partido2 = cancha1+fecha1+hora1 == '' ? 'partido2' : cancha1+fecha1+hora1;

var cancha2 = document.pliga.cp2.value;
var fecha2 = document.pliga.fecha2.value;
var hora2 = document.pliga.hora2.value;
var partido3 = cancha2+fecha2+hora2 == '' ? 'partido3' : cancha2+fecha2+hora2;

        if (partido1 === partido2 || partido1 === partido3) {
      $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");  
        }
    else if(partido2 === partido1 || partido2 === partido3 ){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");  
    }else if(partido3 === partido1 || partido3 === partido2 ){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");
    }else{//si nada fue igual envio
        $("#pliga").submit();
    }
}
</script>
                </div>
        </div>
    </div>

<div class="row" id="segundar">
    <div class="col-lg-12">
        <%--query de la segunda ronda --%> 
                    <sql:query var="segunda" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.cancha,
                        partidos.ronda,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2,
                        partidos.fecha,
                        partidos.hora
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
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/> AND partidos.ronda = 2
                    </sql:query>
                    <div class="panel panel-success">
                    <div class="panel-heading">Segunda Ronda</div>
                    <form action="../../GestionLiga" name="sliga" id="sliga" autocomplete="off">
                        <table class="table table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>Equipo</th>
                            <th>vs</th>
                            <th>Equipo</th>
                            <th>Cancha</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                        </tr>
                        </thead>
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${segunda.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><span>-</span></td>
                                <td>${row.eq2}</td>
                                <td>
                                    <select name="cp${vs.index}" id="cp${vs.index}">
                                        <option></option>
                                        <c:forEach var="canchas" items="${numcanchas.rows}">
                                            <option <c:if test="${canchas.numeroCancha == row.cancha}">selected</c:if>>${canchas.numeroCancha}</option>    
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input type="text" name="fecha${vs.index}" id="fecha${vs.index}" class="datepicker"  <c:if test="${row.fecha !=null}"> value="${row.fecha}"</c:if>/></td>
                                <td><input type="text" name="hora${vs.index}" id="hora${vs.index}" class="clockpick" <c:if test="${row.hora !=null}"> value="${row.hora}"</c:if> /></td>
                                <input type="hidden" value="${row.equipo1}" name="${vs.index}equipo1" />
                                <input type="hidden" value="${row.equipo2}" name="${vs.index}equipo2" />
                                <input type="hidden" value="${row.eq1}" name="${vs.index}nequipo1" />
                                <input type="hidden" value="${row.eq2}" name="${vs.index}nequipo2" />
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
<input type="hidden" value="${param.idTorneo}" name="idTorneo" />
<button class="btn btn-success" id="fechasOctavos" name="validarCampos" type="button" onclick="fIgualesR2()">Añadir Fechas</button>
<input type="hidden" name="asignarfechas" value="fechas" />                    
<input type="hidden" name="segundaronda" value="octavos" />
<% if(request.getParameter("segunda")!=null){%><p class="fechas"><%=request.getParameter("segunda")%></p> <%} %>
                    </form>
<script>
    function fIgualesR2(){
    //paso el input a una variable y si es null le asigno algo 
var cancha0 = document.sliga.cp0.value;   
var fecha0 = document.sliga.fecha0.value;
var hora0 = document.sliga.hora0.value;
var partido1 = cancha0+fecha0+hora0 == '' ? 'partido1' : cancha0+fecha0+hora0;

var cancha1 = document.sliga.cp1.value;
var fecha1 = document.sliga.fecha1.value;
var hora1 = document.sliga.hora1.value;
var partido2 = cancha1+fecha1+hora1 == '' ? 'partido2' : cancha1+fecha1+hora1;

var cancha2 = document.sliga.cp2.value;
var fecha2 = document.sliga.fecha2.value;
var hora2 = document.sliga.hora2.value;
var partido3 = cancha2+fecha2+hora2 == '' ? 'partido3' : cancha2+fecha2+hora2;

        if (partido1 === partido2 || partido1 === partido3) {
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");
        }
    else if(partido2 === partido1 || partido2 === partido3){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");    
    }else if(partido3 === partido1 || partido3 === partido2){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");
    }else{//si nada fue igual envio
        $("#sliga").submit();
    }
}
</script>
                </div>
    </div>
    </div>



<div class="row" id="tercerar">
    <div class="col-lg-12">
        <%--query de la tercera ronda --%> 
                    <sql:query var="tercera" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.cancha,
                        partidos.ronda,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2,
                        partidos.fecha,
                        partidos.hora
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
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/> AND partidos.ronda = 3
                    </sql:query>
                    <div class="panel panel-success">
                    <div class="panel-heading">Tercera Ronda</div>
                    <form action="../../GestionLiga" name="tliga" id="tliga" autocomplete="off">
                        <table class="table table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>Equipo</th>
                            <th>vs</th>
                            <th>Equipo</th>
                            <th>Cancha</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                        </tr>
                        </thead>
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${tercera.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><span>-</span></td>
                                <td>${row.eq2}</td>
                                <td>
                                    <select name="cp${vs.index}" id="cp${vs.index}">
                                        <option></option>
                                        <c:forEach var="canchas" items="${numcanchas.rows}">
                                            <option <c:if test="${canchas.numeroCancha == row.cancha}">selected</c:if>>${canchas.numeroCancha}</option>    
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input type="text" name="fecha${vs.index}" id="fecha${vs.index}" class="datepicker"  <c:if test="${row.fecha !=null}"> value="${row.fecha}"</c:if>/></td>
                                <td><input type="text" name="hora${vs.index}" id="hora${vs.index}" class="clockpick" <c:if test="${row.hora !=null}"> value="${row.hora}"</c:if> /></td>
                                <input type="hidden" value="${row.equipo1}" name="${vs.index}equipo1" />
                                <input type="hidden" value="${row.equipo2}" name="${vs.index}equipo2" />
                                <input type="hidden" value="${row.eq1}" name="${vs.index}nequipo1" />
                                <input type="hidden" value="${row.eq2}" name="${vs.index}nequipo2" />
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
<input type="hidden" value="${param.idTorneo}" name="idTorneo" />
<button class="btn btn-success" id="fechasOctavos" name="validarCampos" type="button" onclick="fIgualesR3()">Añadir Fechas</button>
<input type="hidden" name="asignarfechas" value="fechas" />                    
<input type="hidden" name="terceraronda" value="tercer" />
<% if(request.getParameter("tercera")!=null){%><p class="fechas"><%=request.getParameter("tercera")%></p> <%} %>
                    </form>
<script>
    function fIgualesR3(){
    //paso el input a una variable y si es null le asigno algo 
var cancha0 = document.tliga.cp0.value;   
var fecha0 = document.tliga.fecha0.value;
var hora0 = document.tliga.hora0.value;
var partido1 = cancha0+fecha0+hora0 == '' ? 'partido1' : cancha0+fecha0+hora0;

var cancha1 = document.tliga.cp1.value;
var fecha1 = document.tliga.fecha1.value;
var hora1 = document.tliga.hora1.value;
var partido2 = cancha1+fecha1+hora1 == '' ? 'partido2' : cancha1+fecha1+hora1;

var cancha2 = document.tliga.cp2.value;
var fecha2 = document.tliga.fecha2.value;
var hora2 = document.tliga.hora2.value;
var partido3 = cancha2+fecha2+hora2 == '' ? 'partido3' : cancha2+fecha2+hora2;

        if (partido1 === partido2 || partido1 === partido3 ) {
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");
        }
    else if(partido2 === partido1 || partido2 === partido3){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");    
    }else if(partido3 === partido1 || partido3 === partido2){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora"); 
    }else{//si nada fue igual envio
        $("#tliga").submit();
    }
}
</script>
                </div>
    </div>
</div>


<div class="row" id="cuartar">
    <div class="col-lg-12">
        <%--query de la cuarta ronda --%> 
                    <sql:query var="cuarta" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.cancha,
                        partidos.ronda,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2,
                        partidos.fecha,
                        partidos.hora
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
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/> AND partidos.ronda = 4
                    </sql:query>
                    <div class="panel panel-success">
                    <div class="panel-heading">Cuarta Ronda</div>
                    <form action="../../GestionLiga" name="cliga" id="cliga" autocomplete="off">
                        <table class="table table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>Equipo</th>
                            <th>vs</th>
                            <th>Equipo</th>
                            <th>Cancha</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                        </tr>
                        </thead>
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${cuarta.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><span>-</span></td>
                                <td>${row.eq2}</td>
                                <td>
                                    <select name="cp${vs.index}" id="cp${vs.index}">
                                        <option></option>
                                        <c:forEach var="canchas" items="${numcanchas.rows}">
                                            <option <c:if test="${canchas.numeroCancha == row.cancha}">selected</c:if>>${canchas.numeroCancha}</option>    
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input type="text" name="fecha${vs.index}" id="fecha${vs.index}" class="datepicker"  <c:if test="${row.fecha !=null}"> value="${row.fecha}"</c:if>/></td>
                                <td><input type="text" name="hora${vs.index}" id="hora${vs.index}" class="clockpick" <c:if test="${row.hora !=null}"> value="${row.hora}"</c:if> /></td>
                                <input type="hidden" value="${row.equipo1}" name="${vs.index}equipo1" />
                                <input type="hidden" value="${row.equipo2}" name="${vs.index}equipo2" />
                                <input type="hidden" value="${row.eq1}" name="${vs.index}nequipo1" />
                                <input type="hidden" value="${row.eq2}" name="${vs.index}nequipo2" />
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
<input type="hidden" value="${param.idTorneo}" name="idTorneo" />
<button class="btn btn-success" id="fechasOctavos" name="validarCampos" type="button" onclick="fIgualesR4()">Añadir Fechas</button>
<input type="hidden" name="asignarfechas" value="fechas" />                    
<input type="hidden" name="cuartaronda" value="octavos" />
<% if(request.getParameter("cuarta")!=null){%><p class="fechas"><%=request.getParameter("cuarta")%></p> <%} %>
                    </form>
<script>
    function fIgualesR4(){
    //paso el input a una variable y si es null le asigno algo 
var cancha0 = document.cliga.cp0.value;   
var fecha0 = document.cliga.fecha0.value;
var hora0 = document.cliga.hora0.value;
var partido1 = cancha0+fecha0+hora0 == '' ? 'partido1' : cancha0+fecha0+hora0;

var cancha1 = document.cliga.cp1.value;
var fecha1 = document.cliga.fecha1.value;
var hora1 = document.cliga.hora1.value;
var partido2 = cancha1+fecha1+hora1 == '' ? 'partido2' : cancha1+fecha1+hora1;

var cancha2 = document.cliga.cp2.value;
var fecha2 = document.cliga.fecha2.value;
var hora2 = document.cliga.hora2.value;
var partido3 = cancha2+fecha2+hora2 == '' ? 'partido3' : cancha2+fecha2+hora2;

        if (partido1 === partido2 || partido1 === partido3) {
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");    
        }
    else if(partido2 === partido1 || partido2 === partido3){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");        
    }else if(partido3 === partido1 || partido3 === partido2){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");     
    }else{//si nada fue igual envio
        $("#cliga").submit();
    }
}
</script>
                </div>
    </div>
</div>


<div class="row" id="quintar">
    <div class="col-lg-12">
        <%--query de la quinta ronda --%> 
                    <sql:query var="quinta" dataSource="${sessionScope.cnn}">
                        SELECT DISTINCT 
                        (select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, 
                        (select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, 
                        torneo.nombre as Torneo, 
                        partidos.cancha,
                        partidos.ronda,
                        partidos.equipo1 as ceq1, 
                        partidos.equipo2 as ceq2,
                        partidos.fecha,
                        partidos.hora
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
                        WHERE torneo.idtorneo = ? <sql:param value="${param.idTorneo}"/> AND partidos.ronda = 5
                    </sql:query>
                    <div class="panel panel-success">
                    <div class="panel-heading">Quinta Ronda</div>
                    <form action="../../GestionLiga" name="qliga" id="qliga" autocomplete="off">
                        <table class="table table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>Equipo</th>
                            <th>vs</th>
                            <th>Equipo</th>
                            <th>Cancha</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                        </tr>
                        </thead>
                        <tbody>
<%-- varstatus me da el estado de la variable el metodo index me da la posicion parece q no toma los alias de el equipo 1--%>
                            <c:forEach var="row" items="${quinta.rows}" varStatus="vs">
                            <tr>
                                <td>${row.eq1}</td>
                                <td><span>-</span></td>
                                <td>${row.eq2}</td>
                                <td>
                                    <select name="cp${vs.index}" id="cp${vs.index}">
                                        <option></option>
                                        <c:forEach var="canchas" items="${numcanchas.rows}">
                                            <option <c:if test="${canchas.numeroCancha == row.cancha}">selected</c:if>>${canchas.numeroCancha}</option>    
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input type="text" name="fecha${vs.index}" id="fecha${vs.index}" class="datepicker"  <c:if test="${row.fecha !=null}"> value="${row.fecha}"</c:if>/></td>
                                <td><input type="text" name="hora${vs.index}" id="hora${vs.index}" class="clockpick" <c:if test="${row.hora !=null}"> value="${row.hora}"</c:if> /></td>
                                <input type="hidden" value="${row.equipo1}" name="${vs.index}equipo1" />
                                <input type="hidden" value="${row.equipo2}" name="${vs.index}equipo2" />
                                <input type="hidden" value="${row.eq1}" name="${vs.index}nequipo1" />
                                <input type="hidden" value="${row.eq2}" name="${vs.index}nequipo2" />
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
<input type="hidden" value="${param.idTorneo}" name="idTorneo" />
<button class="btn btn-success" id="fechasOctavos" name="validarCampos" type="button" onclick="fIgualesR5()">Añadir Fechas</button>
<input type="hidden" name="asignarfechas" value="fechas" />                    
<input type="hidden" name="quintaronda" value="quinta" />
<% if(request.getParameter("quinta")!=null){%><p class="fechas"><%=request.getParameter("quinta")%></p> <%} %>
                    </form>
<script>
    function fIgualesR5(){
    //paso el input a una variable y si es null le asigno algo 
var cancha0 = document.qliga.cp0.value;   
var fecha0 = document.qliga.fecha0.value;
var hora0 = document.qliga.hora0.value;
var partido1 = cancha0+fecha0+hora0 == '' ? 'partido1' : cancha0+fecha0+hora0;

var cancha1 = document.qliga.cp1.value;
var fecha1 = document.qliga.fecha1.value;
var hora1 = document.qliga.hora1.value;
var partido2 = cancha1+fecha1+hora1 == '' ? 'partido2' : cancha1+fecha1+hora1;

var cancha2 = document.qliga.cp2.value;
var fecha2 = document.qliga.fecha2.value;
var hora2 = document.qliga.hora2.value;
var partido3 = cancha2+fecha2+hora2 == '' ? 'partido3' : cancha2+fecha2+hora2;

        if (partido1 === partido2 || partido1 === partido3) {
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");    
        }
    else if(partido2 === partido1 || partido2 === partido3){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");        
    }else if(partido3 === partido1 || partido3 === partido2){
    $().toastmessage('showWarningToast', "!Atención¡ Está intentando asignar calendarios iguales, puede ser un partido en la misma cancha el mismo día a la misma hora");     
    }else{//si nada fue igual envio
        $("#qliga").submit();
    }
}
</script>
                </div>
    </div>
</div>
</c:if>
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
