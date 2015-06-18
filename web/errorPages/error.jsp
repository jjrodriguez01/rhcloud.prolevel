<%-- 
    Document   : error
    Created on : 11/03/2015, 01:01:36 AM
    Author     : jeisson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/prolevel/css/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="/prolevel/js/jquery1.9.1.js"></script>
<script src="/prolevel/css/bootstrap/js/bootstrap.min.js"></script>
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


</style>
</head>
<body>

<main>
    <div class="row">
        <div class="col-md-6 centrar">
            <img src="/prolevel/imagenes/buscando.png" alt="pagina error" class="img-responsive img-thumbnail" >
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 centrar">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="alert alert-danger" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        <span class="sr-only">Error:</span>
                        <h2>Lo sentimos, ha ocurrido un error interno</h2>
                        
                       
                        <p>Inténtelo de nuevo más tarde</p>
                    <a href="javascript:window.history.back();">&laquo; Volver atrás</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
</html>
