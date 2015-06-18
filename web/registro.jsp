<%-- 
    Document   : registro
    Created on : 27/01/2015, 03:31:30 PM
    Author     : jeisson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<link rel="shortcut icon" href="imagenes/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pro-level - Registro</title>
<link href="css/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="js/datepicker/jquery-ui.css" rel="stylesheet" type="text/css">
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery.validate.js"></script>
<script src="css/bootstrap/js/bootstrap.min.js"></script>
<script src="js/datepicker/jquery-ui.js"></script>
<script>
$(document).ready(function() {
    $(function() {
   $(".datepicker").datepicker(
   {
        changeMonth: true,
        changeYear: true
   });
   $( ".datepicker" ).datepicker( "option", "dateFormat", "yy-mm-dd" );
  });
});
</script>
<script>

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
<style>
body{
    padding-top: 5px;
    margin: 0 10%;
}
nav{
    background: linear-gradient(white, whitesmoke);
}
h1{
    font-family: 'times new roman', verdana, thaoma;
    text-align: center;
}
label.error{
    color:red;
    font-weight: bold;
    font-style: italic;
}
input.error{
    border: 1px solid red;
}
input.valid{
    border: 1px solid green;
}
label.required::after{
    content: "*" "(" attr(style='color:red') ")";
}
span.required{
    color: red;
}
select.ui-datepicker-year, select.ui-datepicker-month{
    color: black;
}
</style>
<script>
    $(document).ready(function(){
        $("#acontactanos").on("click", function(){
                    $("#contactanos").modal("show");
                });
    });
</script>
</head>
<body>
<header>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#menunav">
        <span class="sr-only">Menu</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
        <a class="navbar-brand" href="#">
            <img alt="pro-level" src="imagenes/logo.png" style="width: 35px; height: 40px">
        </a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="menunav">
      <ul class="nav navbar-nav">
        <li class="active"><a href="index.jsp">Ingresar</a></li>  
        <li><a href="reestablecer.jsp">Reestablecer contraseña <span class="sr-only">(current)</span></a></li>
        <li><a href="#">Registrarme</a></li>
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
                    <li class="active"><a href="#">Registro</a></li>
                </ol>
            </div>
        </div>
<article>
    <h1>Formulario De Registro <small>Usuario Nuevo</small></h1>
 <form id="datosp" name="datospers" method="post" action="RegistroUsuario" class="form-horizontal center-block">
     <div class="form-group">
        <label for="cc" class="control-label col-md-6">Documento de identificación<span class="required"> *</span></label>
            <div class="col-md-6">
                <input type="text" id="cc" name="cc" required/>
            </div>
    </div>
    <div class="form-group">
        <label for="nombre" class="control-label col-md-6">Primer Nombre<span class="required"> *</span></label>
            <div class="col-md-6">
                <input type="text" id="nombre" name="nombre" required/>
            </div>
    </div>
    <div class="form-group">
        <label for="snombre" class="control-label col-md-6">Segundo Nombre</label>
            <div class="col-md-6">
                <input type="text" id="snombre" name="snombre"/>
            </div>
        </div>
    <div class="form-group">
        <label for="ape" class="control-label col-md-6">Primer Apellido<span class="required"> *</span></label>
            <div class="col-md-6">
                <input type="text" id="ape" name="ape" required/>
            </div>
    </div>
    <div class="form-group">
        <label for="sape" class="control-label col-md-6">Segundo Apellido</label>
            <div class="col-md-6">
                <input type="text" id="sape" name="sape"/>
            </div>
    </div>
    <div class="form-group">
        <label for="nac"  class="control-label col-md-6">Fecha De Nacimiento<span class="required"> *</span></label>
            <div class="col-md-6">
                    <input type="text" id="nac" name="nac" class="datepicker" required/>
            </div>
    </div>
    <div class="form-group">
        <label for="tel"  class="control-label col-md-6">Telefono<span class="required"> *</span></label>
            <div class="col-md-6">
                <input type="text" id="tel" name="tel" required/>
            </div>
    </div>
                <div class="form-group">
                <label for="email"  class="control-label col-md-6">Correo Electrónico<span class="required"> *</span></label>
                <div class="col-md-6">
                <input type="text" id="email" name="email" required/>
                </div>
                </div>
                <div class="form-group">
                <label for="pass"  class="control-label col-md-6">Contraseña<span class="required"> *</span></label>
                <div class="col-md-6">
                    <input type="password" id="pass" name="pass" required/>
                </div>
                </div>
                <div class="form-group">
                <label for="confpass"  class="control-label col-md-6">Confirmar Contraseña<span class="required"> *</span></label>
                <div class="col-md-6">
                    <input type="password" id="confpass" name="confpass" required/>
                </div>
                </div>
                <div class="col-lg-10 center-block btn">
                    <label><input type="checkbox" name="terminos" required>Acepto los terminos y condiciones</label>
                        <button type="button" class="btn btn-default" aria-label="Left Align" data-toggle="modal" data-target="#terminos">
                        <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
                        </button>
                </div>
                <div class="col-lg-10 center-block btn">
                    <input type="submit" value="Registrarme" name="enviar" class="btn btn-success"/>
                <input type="hidden" value="datos" name="registro"/> 
                </div>
            </form>
 <% 
     if (request.getParameter("ins")!=null) {
 %>
 <div class="alert alert-success alert-dismissible" role="alert">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  <strong><%=request.getParameter("ins")%></strong>
</div>
 <%
         }
 %>
    </article>
    <div class="modal fade" id="terminos" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Terminos Y Condiciones De Uso</h4>
      </div>
      <div class="modal-body">
        ...
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
      </div>
    </div>
  </div>
</div>
    
 <%--modal de contactanos--%>
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
<script>
   $().ready(function(){
       $("#datosp").validate({
	            rules:{
                                cc:{
				    required: true,
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
                                    maxlength: 15
				  },
          			nac:{
                                    required: true,
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
                                terminos:{
                                    required: true
                                },
		       },
		        messages:{
                                cc:{
                                    required:"campo requerido",
				    minlength:"Minimo {0} carácteres",
                                    maxlength:"Maximo {0} carácteres",
                                    digits: "Sólo numeros"
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
                                    required:"campo requerido",
                                    date:"Ingrese una fecha válida",
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
                                    required:"campo requerido",
			 },
                                 terminos:{
                                    required:"Lea los términos y condiciones",
			 },
		    }
	      });
	   });
</script>
<footer class="col-lg-12">
<p class="pie">2014 PRO-LEVEL - Todos los derechos reservados | Cambiar idioma 
<a href="#"><img src="imagenes/english.png" width="40" height="30" /></a></p> 
</footer>
</body>
</html>
