<%-- 
    Document   : perfil_admin
    Created on : 2/02/2015, 03:40:42 AM
    Author     : jeisson
--%>
<%@page import="utilidades.MiExcepcion"%>
<%@page import="facade.FachadaTorneos"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.UsuariosDTO"%>
<%@page import="modelo.TorneoDTO"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%--query de lista desplegable--%>
<sql:query var="torneo" dataSource="${sessionScope.cnn}">
    SELECT idTorneo, nombre FROM torneo
</sql:query>
<sql:query var="capacidad" dataSource="${sessionScope.cnn}">
        SELECT idTorneo,capacidadEquipos FROM torneo
        WHERE idTorneo =? <sql:param value="${param.torneos}"/>
</sql:query>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
            try{
            if (request.getSession()!=null && request.getSession().getAttribute("usr")!=null) {
                    UsuariosDTO udto = new UsuariosDTO();
                    FachadaTorneos facadeTorneos = new FachadaTorneos();
                    HttpSession miSession=request.getSession(false);
                    udto = (UsuariosDTO)miSession.getAttribute("usr");
                    int rol = (Integer)miSession.getAttribute("rol");
                    if(rol == 1){
                    
        %> 
<!doctype html>
<html lang="es">
<head>
<link rel="shortcut icon" href="../imagenes/favicon.ico">
<link href="../js/dataTables/css/dataTablesBootstrap.css" rel="stylesheet" type="text/css">
<link href="../css/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="../css/perfiladmin.css" rel="stylesheet" type="text/css">
<link href="../css/estiloslayout.css" rel="stylesheet" type="text/css">
<link href="../js/datepicker/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="../js/sweetalert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="../js/jquery.validate.js"></script>
<script type="text/javascript" src="../js/datepicker/jquery-ui.js"></script>
<script type="text/javascript" src="../js/listaTorneo.js"></script>
<script type="text/javascript" src="../css/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/dataTables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="../js/dataTables/js/datatablesbootstrap.js"></script>
<script type="text/javascript" src="../js/sweetalert.min.js"></script>
<script>
$(document).ready(function() {
   $( ".datepicker" ).datepicker( "option", "minDate", 0 );
   $( ".datepicker" ).datepicker( "option", "dateFormat", "yy-mm-dd" );
   $("#ttorneos,#tcanchas").dataTable({
                    language:{
                        url: "../js/dataTables/js/dataespañol.json"
                    } 
                });
 });
</script>
<script>
$(document).ready(function() {
    $( ".datepicker" ).datepicker({
	inline: true
});
$( ".datepicker" ).datepicker( "option", "dateFormat", "yy-mm-dd" );
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
<script>
   $().ready(function(){
       $("#actualizart").validate({
	            rules:{
				  nombreTorneo:{
				    required: true,
					minlength: 3
				  },
				  capacidad:{
            		 required: true,
         			 maxlength: 4,
      			     number:true
        			  },
					  numgrupos:{
						  required:true
						  },
				 idaVueltagrupos:{
				  	required: true
				  },
          			idaVueltaeli:{
            		required: true,
          		  },
				  	finalIdaVuelta:{
					required: true
				  },
				  tercer:{
				  required: true,
        		},
				tipo:{
				  required: true,
					},
				inicio:{
					required: true,
					date: true
					},
				fin:{
					required: true,
					date:true
					}
		       },
		        messages:{
		      nombreTorneo:{
					required:"campo requerido",
				    minlength:"Minimo{0} caracteres"					  
				   },
             capacidad:{
					required:"campo requerido",
					maxlength:"maximo 4 digitos ",
					number:"Solo Numeros Por Favor"
		          },
				  numgrupos:{
					  required:"campo requerido"
					  },
			idaVueltagrupos:{
					required:"campo requerido"
					  },
        	idaVueltaeli:{
            		required:"campo requerido"
            			},
		    finalIdaVuelta:{
					  required:"campo requerido"  
					  },
        	tercer:{
            	    required:"campo requerido"
                	},
			tipo:{
					required:"campo requerido"
				},
			inicio:{
					required:"Indique la fecha de inicio de la liga",
					date:"Por favor ingrese una fecha válida"
			},
			fin:{
					required:"Indique la fecha de fin de la liga",
					date:"Por favor ingrese una fecha válida"
				}			  
		  }
	   
	       });
	   });
</script>
<script>
   $().ready(function(){
       $("#datosp").validate({
	            rules:{
                                cc:{
                                    minlength: 6,
                                    maxlength: 11,
                                    digits: true
				  },
                                nombre:{
				    required: true,
                                    minlength: 3,
                                    maxlength: 15
				  },
                                snombre:{
                                    minlength: 3,
                                    maxlength: 15
        			  },
                                ape:{
                                    required:true,
                                    minlength: 3,
                                    maxlength: 15
						  },
                                sape:{
                                    minlength: 3,
                                    maxlength: 50
				  },
          			nac:{
                                    date:true
                                    },
                                tel:{
                                    digits:true,
                                    minlength: 3,
                                    maxlength: 11
				  },
                                email:{
                                    required: true,
                                    email: true,
                                    minlength: 6,
                                    maxlength: 45,
                                    },
				pass:{
                                    required: true,
                                    minlength: 5,
                                    maxlength: 45,
                                    },
				confpass:{
                                    required: true,
                                    minlength: 5,
                                    maxlength: 45,
                                    equalTo: pass
					},
		       },
		        messages:{
                             cc:{
                                    minlength: "Minimo {0} carácteres",
                                    maxlength: "Maximo {0} carácteres",
                                    digits: true
				  },
                                nombre:{
                                    required:"campo requerido",
				    minlength:"Minimo {0} carácteres",
                                    maxlength:"Maximo {0} carácteres"					  
				   },
                                snombre:{
                                    maxlength:"campo requerido",
                                    minlength:"maximo 4 digitos ",
                                        },
                                ape:{
                                    required:"campo requerido",
				    minlength:"Minimo {0} carácteres",
                                    maxlength:"Maximo {0} carácteres"
					  },
				sape:{
				    minlength:"Minimo {0} carácteres",
                                    maxlength:"Maximo {0} carácteres"
                                    },
                                nac:{
                                    date:"Ingrese una fecha válida" 
                                    },
                                email:{
                                    required:"campo requerido",
                                    email:"Ingrese un correo electrónico válido",
                                    minlength:"Minimo {0} carácteres",
                                    maxlength:"Maximo {0} carácteres"
					  },
                                pass:{
                                    required:"campo requerido",
                                    minlength:"Minimo {0} carácteres",
                                    maxlength:"Maximo {0} carácteres"
                                    },
                                confpass:{
                                    required:"campo requerido",
                                    minlength:"Minimo {0} carácteres",
                                    maxlength:"Maximo {0} carácteres",
                                    equalTo:"Las contraseñas deben coincidir"
                                    },
                                tel:{
                                    digits:"Sólo numeros",
                                    minlength:"Minimo {0} carácteres",
                                    maxlength:"Maximo {0} carácteres"
			 },			  
		    }
	      });
	   });
</script>
<script>
$(document).ready(function() {
    $("#formulario,#torneo,#cancha").hide();
    $("#datos").on("click",function(){
        $("#formulario").fadeIn("slow",function(){
            $("#torneo,#cancha").fadeOut();
        });
    });
    $("#copas").on("click",function(){
    $("#torneo").fadeIn("slow",function(){
        $("#formulario,#cancha").fadeOut("slow");
    });
    });
    $("#campos").on("click",function(){
        $("#cancha").fadeIn("slow",function(){
            $("#formulario,#torneo").fadeOut("slow");
        });
    });
});
</script>	
<script>
   function confirmar()
{
	if(confirm('¿Seguro que desea eliminar la cancha?'))
		return true;
	else
		return false;
}
</script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Pro-level :: Mi Perfil</title>
</head>
<body>   
<header>
<nav>
    <ul id="nav" class="nav nav-pills">
      		<li><a href="inicio.jsp"><img src="../imagenes/inicio.png" width="24" height="24" alt="inicio" /> INICIO</a></li>
      		<li><a href="#"><span><img src="../imagenes/copa.png" width="24" height="24" alt="copa" /> TORNEOS</span></a><
        <div class="subs">
          <div class="col">
            <ul>
              <li><a><img src="../imagenes/micopa.png" width="24" height="24" alt="torneos"/>MIS TORNEOS</a>
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
            	<li><a href="#"><img src="../imagenes/ajustes.png" width="24" height="24" alt="ajustes" />ADMINISTRAR</a></li>
                <li><a href="../Ingreso?logout=cerrar"><img src="../imagenes/out.png" width="24" height="24" alt="cerrar" />CERRAR CESIÓN</a></li>
            </ul>
        </div>
        </li>
    </ul>
</nav>
    <div class="pull-right"><span class="label label-success"> <%=udto.getPrimerNombre()%></span><span class="badge">Administrador</span></div>
</header>
<!-- InstanceBeginEditable name="body" -->
<main>
<div class="" id="contenedor">
<section id="menu" class="container">
        <h3>Administra tu perfil</h3>
            <div class="jumbotron">
                <div class="">
                    <img src="../imagenes/settings.png" id="datos" width="128" height="128"  alt="Modificar datos personales" class="img-circle img-responsive"/> 
                    <h3>DATOS PERSONALES</h3>
                </div>
                <div class="">
                    <img src="../imagenes/copa.png" id="copas" width="128" height="128"  alt="torneos" class="img-circle img-responsive"/>
                        <h3>TORNEOS</h3>
                    </div>
            <div class="">
                <img src="../imagenes/fondoindex.jpg" id="campos" width="128" height="128"  alt="canchas" class="img-circle img-responsive"/> 
                <h3>CANCHAS</h3>
            </div>
            </div>
</section> 
<section id="formulario" class="item">
<div id="usuario" class="container">
<div>
    <ol class="breadcrumb">
        <li class="">Perfil</li>
        <li class="active" id="migadatos">Datos Personales</li>
    </ol>
</div> 
<h2>Datos Personales</h2>
<form id="datosp" name="datospers" method="get" action="../RegistroUsuario" class="form-horizontal center-block">
    <div class="form-group">
        <label for="cc" class="control-label col-md-6">Documento de identificación</label>
            <div class="col-md-6">
                    <input type="text" id="cc" name="cc" value="<%=udto.getIdUsuario()%>" readonly="readonly"/>
            </div>
    </div>
    <div class="form-group">
    <label for="nombre" class="control-label col-md-6">Primer Nombre <span class="obligatorio">*</span></label>
        <div class="col-md-6">
            <input type="text" id="nombre" name="nombre" placeholder="<%=udto.getPrimerNombre()%>"/>
        </div>
    </div>
    <div class="form-group">
    <label for="snombre" class="control-label col-md-6">Segundo Nombre</label>
        <div class="col-md-6">
            <input type="text" id="snombre" name="snombre" placeholder="<%=udto.getSegundoNombre()%>"/>
        </div>
    </div>
    <div class="form-group">
    <label for="ape" class="control-label col-md-6">Primer Apellido <span class="obligatorio">*</span></label>
        <div class="col-md-6">
            <input type="text" id="ape" name="ape" placeholder="<%=udto.getPrimerApellido()  %>"/>
        </div>
    </div>
    <div class="form-group">
        <label for="sape" class="control-label col-md-6">Segundo Apellido</label>
            <div class="col-md-6">
                <input type="text" id="sape" name="sape" placeholder="<%=udto.getSegundoApellido()%>"/>
            </div>
    </div>
    <div class="form-group">
        <label for="nac"  class="control-label col-md-6">Fecha De Nacimiento</label>
            <div class="col-md-6">
                <input type="text" id="nac" name="nac" value="<%=udto.getFecha()%>" readonly="readonly"/>
            </div>
    </div>
    <div class="form-group">
        <label for="tel"  class="control-label col-md-6">Telefono</label>
            <div class="col-md-6">
                <input type="text" id="tel" name="tel" placeholder="<%=udto.getTelefono()%>"/>
            </div>
    </div>
    <div class="form-group">
        <label for="email"  class="control-label col-md-6">Correo Electrónico <span class="obligatorio">*</span></label>
            <div class="col-md-6">
                <input type="email" id="email" name="email" placeholder="<%=udto.getEmail()%>"/>
            </div>
    </div>
    <div class="form-group">
        <label for="pass"  class="control-label col-md-6">Contraseña <span class="obligatorio">*</span></label>
            <div class="col-md-6">
                <input type="password" id="pass" name="pass" placeholder="****"/>
            </div>
    </div>
    <div class="form-group">
        <label for="confpass"  class="control-label col-md-6">Confirmar Contraseña <span class="obligatorio">*</span></label>
            <div class="col-md-6">
                <input type="password" id="confpass" name="confpass" placeholder="****"/>
            </div>
    </div>
    <div class="center-block btn">
        <button name="actudatos" class="btn btn-primary">Guardar Cambios</button>
        <input type="hidden" name="datos" value="datos" />
    </div>
    </form>
<%-- si hubo exito actualizando datos autoclick en icono perfil mostrar mensaje --%>
<% 
    if (request.getParameter("conf")!=null) { 
%>
<script>
    $(document).ready(function(){
         $("#datos").trigger("click");
    });
</script>
<div class="alert alert-warning alert-dismissable">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong><span class="glyphicon glyphicon-ok"></span><%=request.getParameter("conf")%></strong>
</div> 
<%            
       }
%>
</div>
</section>
<section id="torneo" class="item">
    <div id="torneocontainer" class="container">
    <div class="row">
        <div class="col-xs-12 col-md-8">
            <div>
                <ol class="breadcrumb">
                    <li class="">Perfil</li>
                    <li class="active" id="migatorneos">Torneos</li>
                </ol>
            </div>
                <h1>Torneos</h1>      
                <table id="ttorneos" class="table table-responsive">
    <!-- column headers -->
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Fecha De Inicio</th>
                        <th>Fecha Fin</th>
                        <th>Genero</th>
                        <th>Número De Equipos</th>
                        <th>Editar</th>
                        <th>Eliminar</th>
                    </tr>
                </thead>
                <tbody>
    <% 
        ArrayList<TorneoDTO> ttorneos = new ArrayList();
        ttorneos = (ArrayList<TorneoDTO>) facadeTorneos.listarTorneos(); 
        for (TorneoDTO t: ttorneos){
    %>
<tr>
    <td><%=t.getNombre()%></td>
    <td><%=t.getFechaInicio()%></td>
    <td><%=t.getFechaFin()%></td>
    <td><%=t.getGenero()%></td>
    <td><%=t.getCapacidadEquipos()%></td> 
    <td><form> 
        <input type="hidden" name="idTorneo" value="<%=t.getIdTorneo()%>" />
        <input type="hidden" name="nombre" value="<%=t.getNombre()%>" />
        <input type="hidden" name="finicio" value="<%=t.getFechaInicio()%>" />
        <input type="hidden" name="ffin" value="<%=t.getFechaFin()%>" />
        <input type="hidden" name="genero" value="<%=t.getGenero()%>" />
        <input type="hidden" name="capacidad" value="<%=t.getCapacidadEquipos()%>" />
        <button name="actutorneo" class="glyphicon glyphicon-check" value="editar"></button>
        </form>
    </td>
    <td>
        <form> 
        <input type="hidden" name="idTorneo" value="<%=t.getIdTorneo()%>" />
        <input type="hidden" name="nombre" value="<%=t.getNombre()%>" />
        <input type="hidden" name="finicio" value="<%=t.getFechaInicio()%>" />
        <input type="hidden" name="ffin" value="<%=t.getFechaFin()%>" />
        <input type="hidden" name="genero" value="<%=t.getGenero()%>" />
        <input type="hidden" name="capacidad" value="<%=t.getCapacidadEquipos()%>" />
        <button name="elit" class="glyphicon glyphicon-trash" value="eliminar"></button>
        </form>
    </td>
</tr>
<%  }  %>
</tbody>
</table>
    <%--ventana modal de actualizar torneo--%>
<div class="modal fade" id="atorneo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3>Editar Torneo</h3>
            </div>
            <div class="modal-body">
                <form action="../CRUDTorneo">
            <input type="hidden" name="idTorneo" value="<%=request.getParameter("idTorneo")%>" />    
            <div class="form-group">
            <label for="nombre" class="control-label col-md-6">Nombre Del Torneo</label>
                <div class="col-md-6">
                    <input type="text" id="nombre" name="nombre" placeholder="<%=request.getParameter("nombre")%>" maxlength="14" required/>
                </div>
                </div>
                <div class="form-group">
                <label for="finicio" class="control-label col-md-6">Fecha De Inicio</label>
                <div class="col-md-6">
                    <input type="text" class="datepicker" id="finicio" name="finicio" placeholder="<%=request.getParameter("finicio")%>" required/>
                </div>
                </div>
                <div class="form-group">
                <label for="ffin" class="control-label col-md-6">Fecha De Fin</label>
                <div class="col-md-6">
                    <input type="text" class="datepicker" id="ffin" name="ffin" placeholder="<%=request.getParameter("ffin")%>" required/>
                </div>
                </div>
                <div class="form-group">
                <label for="genero" class="control-label col-md-6">Genero</label>
                <div class="col-md-6">
                    <select id="genero" name="genero" required>
                        <option></option>
                        <option value="Femenino">Femenino</option>
                        <option value="Masculino">Masculino</option>
                    </select>
                </div>
                </div>
                <div class="form-group">
                <label for="capacidad"  class="control-label col-md-6">CapacidadEquipos</label>
                <div class="col-md-6">
                    <input type="text" id="capacidad" name="capacidad" value="<%=request.getParameter("capacidad")%>" readonly="readonly"/>
                </div>
                </div>
                <button name="at" class="btn btn-info" value="realizarat">Actualizar</button>
                <input type="hidden" name="confirmactu" value="cat" />
                    </form>
            </div>
        </div>
    </div>
</div>
<%--ventana modal de eliminar torneo--%>
<div class="modal fade" id="etorneo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3>Confirmar Eliminar Torneo</h3>
            </div>
            <div class="modal-body">
            <form action="../CRUDTorneo">
            <input type="hidden" name="idTorneo" value="<%=request.getParameter("idTorneo")%>" />    
            <div class="form-group">
            <label for="nombre" class="control-label col-md-6">Nombre Del Torneo</label>
                <div class="col-md-6">
                    <input type="text" id="nombre" name="nombre" value="<%=request.getParameter("nombre")%>" maxlength="14" readonly="readonly"/>
                </div>
                </div>
                <div class="form-group">
                <label for="finicio" class="control-label col-md-6">Fecha De Inicio</label>
                <div class="col-md-6">
                    <input type="text" id="finicio" name="finicio" placeholder="<%=request.getParameter("finicio")%>" readonly="readonly"/>
                </div>
                </div>
                <div class="form-group">
                <label for="ffin" class="control-label col-md-6">Fecha De Fin</label>
                <div class="col-md-6">
                    <input type="text" id="ffin" name="ffin" placeholder="<%=request.getParameter("ffin")%>" readonly="readonly"/>
                </div>
                </div>
                <div class="form-group">
                <label for="genero" class="control-label col-md-6">Genero</label>
                <div class="col-md-6">
                    <input type="text" id="genero" name="genero" value="<%=request.getParameter("genero")%>" readonly="readonly"/>
                </div>
                </div>
                <div class="form-group">
                <label for="capacidad"  class="control-label col-md-6">CapacidadEquipos</label>
                <div class="col-md-6">
                    <input type="text" id="capacidad" name="capacidad" value="<%=request.getParameter("capacidad")%>" readonly="readonly"/>
                </div>
                </div>
                <button name="et" class="btn btn-info" value="realizaret">Eliminar</button>
                <input type="hidden" name="celiminar" value="cat" />
                    </form>
            </div>
        </div>
    </div>
</div>

<%-- si hubo click en editar autoclick para la ventana modal y el icono copas --%>
<%  if (request.getParameter("actutorneo")!=null) {
%>
<button id="btnat"  data-toggle="modal" data-target="#atorneo"></button>
<script>
    $(document).ready(function(){
        $("#btnat").trigger("click");
         $("#copas").trigger("click");
    });
</script>
<%
    }
%>      
<%-- si hubo exito editando torneo mostrar mensaje --%>
<% 
    if (request.getParameter("updatet")!=null) { 
%>
<script>
    $(document).ready(function(){
         $("#copas").trigger("click");
    });
</script>
<div class="alert alert-warning alert-dismissable">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong><%=request.getParameter("updatet")%></strong>
</div>
<%            
        }
%>
<%-- si hubo click en eliminar autoclick para la ventana modal y el icono copas --%>
<%  if (request.getParameter("elit")!=null) {
%>
<button id="btnet"  data-toggle="modal" data-target="#etorneo"></button>
<script>
    $(document).ready(function(){
        $("#btnet").trigger("click");
         $("#copas").trigger("click");
    });
</script>
<%
    }
%>
<%-- si hubo exito eliminando torneo mostrar mensaje --%>
<% 
    if (request.getParameter("elimt")!=null) { 
%>
<script>
    $(document).ready(function(){
         $("#copas").trigger("click");
    });
</script>
<div class="alert alert-warning alert-dismissable">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong><%=request.getParameter("elimt")%></strong>
</div>
<%            
        }
%>
            </div>
        </div>
    </div>
</section>
<section id="cancha" class="item">
    <div id="canchacontainer" class="container">
<%
    if (request.getParameter("existecancha")!=null) {
%>
<script>
    $(document).ready(function(){
         $("#campos").trigger("click");
    });
</script>
<div class="row">
    <div class="col-xs-12 col-md-8">
        <div class="alert alert-danger alert-dismissable">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <strong>¡Este número de cancha ya está registrado!</strong>
        </div>
    </div>
</div>
<%
        }
%>
<%
    if (request.getParameter("noeliminada")!=null) {
%>
<script>
    $(document).ready(function(){
         $("#campos").trigger("click");
    });
</script>
<div class="row">
    <div class="col-xs-12 col-md-8">
        <div class="alert alert-danger alert-dismissable">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <strong>¡Esta cancha no se puede eliminar!</strong>
            <p>Puede que hallan partidos pendientes en ella</p>
        </div>
    </div>
</div>
<%
        }
%>
        <div class="row">
            <div class="col-xs-12 col-md-8">
                <div>
                    <ol class="breadcrumb">
                        <li class="">Perfil</li>
                        <li class="active" id="migacanchas">Canchas</li>
                    </ol>
                </div>
                <sql:query var="canchas" dataSource="${sessionScope.cnn}">
                    SELECT *  FROM cancha
                </sql:query>
                <h2>Canchas</h2>     
                <table id="tcanchas" class="table table-responsive">
                    <thead>
                        <tr>
                            <th>Cancha</th>
                            <th>Descripcion</th>
                            <th>Editar</th>
                            <th>Eliminar</th>
                        </tr>
                    </thead>
                    <!-- column data -->
                    <tbody>
                    <c:forEach var="row" items="${canchas.rows}">
                        <tr>
                            <td>${row.numeroCancha}</td>
                            <td>${row.descripcion}</td>
                            <td>
                                <form>
                                    <input type="hidden" name="ncancha" value="${row.numeroCancha}" />
                                    <input type="hidden" name="des" value="${row.descripcion}" />
                                    <button name="actucancha" value="editcancha" class="glyphicon glyphicon-check"></button>
                                </form>
                            </td>
                            <td><a onclick="return confirmar();" href="../Canchas?ecancha=eliminar&numero=${row.numeroCancha}" class="glyphicon glyphicon-trash"></a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
<%-- ventana modal edicion de cancha --%>
<div class="modal fade" id="accancha" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3>Editar Cancha</h3>
            </div>
            <div class="modal-body">
            <form action="../Canchas">    
            <div class="form-group">
            <label for="num" class="control-label col-md-6">Numero de la cancha: </label>
                <div class="col-md-6">
                    <input type="number" name="num" value="<%=request.getParameter("ncancha")%>" maxlength="14" />
                </div>
                </div>
                <div class="form-group">
                <label for="descripcion" class="control-label col-md-6">Descripcion </label>
                <div class="col-md-6">
                    <textarea class="form-control" rows="3" name="descripcion" placeholder="<%=request.getParameter("des")%>"></textarea>
                </div>
                </div>
                <button name="ac" class="btn btn-info" value="realizarac">Modificar</button>
                <input type="hidden" name="confirmac" value="cat" />
            </form>
            </div>
        </div>
    </div>
</div>
<%-- si hubo click en editar autoclick para la ventana modal y el icono canchas --%>
<%  if (request.getParameter("actucancha")!=null) {
%>
<button id="btnac"  data-toggle="modal" data-target="#accancha"></button>
<script>
    $(document).ready(function(){
        $("#btnac").trigger("click");
         $("#campos").trigger("click");
    });
</script>
<%
    }
%>
<%--mensaje modificacion de cancha--%>                
<%
    if (request.getParameter("ac")!=null) {
%>
<script>
    $(document).ready(function(){
         $("#campos").trigger("click");
    });
</script>
<div class="alert alert-warning alert-dismissable">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>¡Cancha modificada correctamente!</strong>
</div>
<%
        }
%>
<%--mensaje eliminacion de cancha--%>                
<%
    if (request.getParameter("eliminada")!=null) {
%>
<script>
    $(document).ready(function(){
         $("#campos").trigger("click");
    });
</script>
<div class="alert alert-warning alert-dismissable">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>¡Cancha eliminada correctamente!</strong>
</div>
<%
        }
%>
<%
    if (request.getParameter("inscancha")!=null) {
%>
<script>
    $(document).ready(function(){
         $("#campos").trigger("click");
    });
</script>
<div class="alert alert-warning alert-dismissable">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>¡Cancha añadida correctamente!</strong>
</div>
<%
        }
%>
            </div>
            <div class="col-md-6">
                <div class="container">
                    <div class="row">
                        <article>
                            <h1>Añadir Cancha</h1>
                <div class="col-md-4">
                <form id="nuevacancha" action="../Canchas">
                <label for="numero" class="control-label col-md-6">Número De La Cancha</label>
                    <input type="text" id="numero" name="numero"/><br/><br/>
                    <label for="des"  class="control-label col-md-6">Descripcion</label>
                    <textarea class="form-control" rows="3" name="des"></textarea>
                <button name="icancha" class="btn btn-info" value="insertarc">Añadir Cancha</button>
                <input type="hidden" name="confirmicancha" value="ic" />
                </form>
                </div>
                        </article> 
                    </div>
                </div>
            </div>
        </div>
    </div>
<script>
    $("#nuevacancha").validate({
        rules:{
            numero: {
                required: true,
                digits: true,
                maxlength: 3
            },
            des:{
                required: true,
                maxlength: 45
            }
        },
        messages:{
            numero:{
                required: "Campo requerido",
                digits: "Sólo numeros",
                minlength: "Máximo {0} dígitos"
            },
            des:{
                required: "Añada una descripcion para la cancha",
                minlength: "Máximo {0} caracteres"
            }
        }
    });
</script>
</section>
</div>
</main>
<footer>
<p class="pie">2014 PRO-LEVEL - Todos los derechos reservados | Cambiar idioma 
    <a href="#"><img src="../imagenes/english.png" width="30" height="25" alt="idioma" />
    </a></p> 
</footer>
</body>
</html>
<%
    }//si el rol fue uno
                    else if (rol == 2) {
%>
<!doctype html>
<html lang="es">
<head>
<link rel="shortcut icon" href="../imagenes/favicon.ico">
<link href="../js/dataTables/css/dataTablesBootstrap.css" rel="stylesheet" type="text/css">
<link href="../css/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="../css/perfiladmin.css" rel="stylesheet" type="text/css">
<link href="../css/estiloslayout.css" rel="stylesheet" type="text/css">
<link href="../js/datepicker/jquery-ui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="../js/jquery.validate.js"></script>
<script type="text/javascript" src="../js/datepicker/jquery-ui.js"></script>
<script type="text/javascript" src="../js/listaTorneo.js"></script>
<script type="text/javascript" src="../css/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/dataTables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="../js/dataTables/js/datatablesbootstrap.js"></script>
<script>
$(document).ready(function() {
   $( ".datepicker" ).datepicker( "option", "minDate", 0 );
   $( ".datepicker" ).datepicker( "option", "dateFormat", "yy-mm-dd" );
 });
</script>
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
<script>
   $().ready(function(){
       $("#datosp").validate({
	            rules:{
                                cc:{
                                    minlength: 0,
                                    maxlength: 11,
                                    digits: true
				  },
                                nombre:{
				    required: true,
                                    minlength: 3,
                                    maxlength: 15
				  },
                                snombre:{
                                    minlength: 3,
                                    maxlength: 15
        			  },
                                ape:{
                                    required:true,
                                    minlength: 3,
                                    maxlength: 15
						  },
                                sape:{
                                    minlength: 3,
                                    maxlength: 50
				  },
          			nac:{
                                    date:true
                                    },
                                tel:{
                                    digits:true
				  },
                                email:{
                                    required: true,
                                    email: true,
                                    minlength: 6,
                                    maxlength: 45,
                                    },
				pass:{
                                    required: true,
                                    minlength: 5,
                                    maxlength: 45,
                                    },
				confpass:{
                                    required: true,
                                    minlength: 5,
                                    maxlength: 45,
                                    equalTo: pass
					},
		       },
		        messages:{
                             cc:{
                                    minlength: "Minimo {0} carácteres",
                                    maxlength: "Maximo {0} carácteres",
                                    digits: true
				  },
                                nombre:{
                                    required:"campo requerido",
				    minlength:"Minimo {0} carácteres",
                                    maxlength:"Maximo {0} carácteres"					  
				   },
                                snombre:{
                                    maxlength:"campo requerido",
                                    minlength:"maximo 4 digitos ",
                                        },
                                ape:{
                                    required:"campo requerido",
				    minlength:"Minimo {0} carácteres",
                                    maxlength:"Maximo {0} carácteres"
					  },
				sape:{
				    minlength:"Minimo {0} carácteres",
                                    maxlength:"Maximo {0} carácteres"
                                    },
                                nac:{
                                    date:"Ingrese una fecha válida" 
                                    },
                                email:{
                                    required:"campo requerido",
                                    email:"Ingrese un correo electrónico válido",
                                    minlength:"Minimo {0} carácteres",
                                    maxlength:"Maximo {0} carácteres"
					  },
                                pass:{
                                    required:"campo requerido",
                                    minlength:"Minimo {0} carácteres",
                                    maxlength:"Maximo {0} carácteres"
                                    },
                                confpass:{
                                    required:"campo requerido",
                                    minlength:"Minimo {0} carácteres",
                                    maxlength:"Maximo {0} carácteres",
                                    equalTo:"Las contraseñas deben coincidir"
                                    },
                                tel:{
                                    digits:"Sólo numeros",
			 },			  
		    }
	      });
	   });
</script>
	
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Pro-level :: Mi Perfil</title>
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
    <div class="pull-right"><span class="label label-success"> <%=udto.getPrimerNombre()%></span><span class="badge">Jugador</span></div>
</header>
<!-- InstanceBeginEditable name="body" -->
<main>
<div class="" id="contenedor">

<section id="formulario" class="item">
<div id="usuario" class="container">
<div>
    <ol class="breadcrumb">
        <li><a href="inicio.jsp">Inicio</a></li>
        <li class="active" id="migadatos">Datos Personales</li>
    </ol>
</div> 
<h2>Datos Personales</h2>
<form id="datosp" name="datospers" method="get" action="../RegistroUsuario" class="form-horizontal center-block">
    <div class="form-group">
        <label for="cc" class="control-label col-md-6">Cedula</label>
            <div class="col-md-6">
                    <input type="text" id="cc" name="cc" value="<%=udto.getIdUsuario()%>" readonly="readonly"/>
            </div>
    </div>
    <div class="form-group">
    <label for="nombre" class="control-label col-md-6">Primer Nombre</label>
        <div class="col-md-6">
            <input type="text" id="nombre" name="nombre" placeholder="<%=udto.getPrimerNombre()%>"/>
        </div>
    </div>
    <div class="form-group">
    <label for="snombre" class="control-label col-md-6">Segundo Nombre</label>
        <div class="col-md-6">
            <input type="text" id="snombre" name="snombre" placeholder="<%=udto.getSegundoNombre()%>"/>
        </div>
    </div>
    <div class="form-group">
    <label for="ape" class="control-label col-md-6">Primer Apellido</label>
        <div class="col-md-6">
            <input type="text" id="ape" name="ape" placeholder="<%=udto.getPrimerApellido()  %>"/>
        </div>
    </div>
    <div class="form-group">
        <label for="sape" class="control-label col-md-6">Segundo Apellido</label>
            <div class="col-md-6">
                <input type="text" id="sape" name="sape" placeholder="<%=udto.getSegundoApellido()%>"/>
            </div>
    </div>
    <div class="form-group">
        <label for="nac"  class="control-label col-md-6">Fecha De Nacimiento</label>
            <div class="col-md-6">
                <input type="text" id="nac" name="nac" value="<%=udto.getFecha()%>" readonly="readonly"/>
            </div>
    </div>
    <div class="form-group">
        <label for="tel"  class="control-label col-md-6">Telefono</label>
            <div class="col-md-6">
                <input type="text" id="tel" name="tel" placeholder="<%=udto.getTelefono()%>"/>
            </div>
    </div>
    <div class="form-group">
        <label for="email"  class="control-label col-md-6">Correo Electrónico</label>
            <div class="col-md-6">
                <input type="email" id="email" name="email" placeholder="<%=udto.getEmail()%>"/>
            </div>
    </div>
    <div class="form-group">
        <label for="pass"  class="control-label col-md-6">Contraseña</label>
            <div class="col-md-6">
                <input type="password" id="pass" name="pass" placeholder="****"/>
            </div>
    </div>
    <div class="form-group">
        <label for="confpass"  class="control-label col-md-6">Confirmar Contraseña</label>
            <div class="col-md-6">
                <input type="password" id="confpass" name="confpass" placeholder="****"/>
            </div>
    </div>
    <div class="center-block btn">
        <button name="actudatos" class="btn btn-primary">Guardar Cambios</button>
        <input type="hidden" name="datos" value="datos" />
    </div>
    </form>
<%-- si hubo exito actualizando datos autoclick en icono perfil mostrar mensaje --%>
<% 
    if (request.getParameter("conf")!=null) { 
%>
<script>
    $(document).ready(function(){
         $("#datos").trigger("click");
    });
</script>
<div class="alert alert-warning alert-dismissable">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong><span class="glyphicon glyphicon-ok"></span><%=request.getParameter("conf")%></strong>
</div> 
<%            
       }
%>
</div>
</section>

</div>
</main>
<footer>
<p class="pie">2014 PRO-LEVEL - Todos los derechos reservados | Cambiar idioma 
    <a href="#"><img src="../imagenes/english.png" width="30" height="25" alt="idioma" />
    </a></p> 
</footer>
</body>
</html>
<%
                        }//si el rol fue 2
  
    }else{
                response.sendRedirect("../../../index.jsp");
            }
            
    }catch(MiExcepcion mie){
        response.sendError(500, mie.getMessage());
    }
%>
