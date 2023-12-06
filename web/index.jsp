<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="model.Usuario, controller.GerenciarLogin"%>
<link rel="shortcut icon" href="./imagens/logo-pequena.png">

<%
    Usuario ulogado = GerenciarLogin.verificarAcesso(request, response);
    request.setAttribute("ulogado", ulogado);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <link rel="stylesheet" href="bootstrap/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/menu.css" type="text/css">
        <link rel="stylesheet" href="css/styles.css" type="text/css"/>
        <link rel="shortcut icon" href="./imagens/logo-pequena.png">
        <title>Açaí do Berê - Home Page</title>
    </head>
    <body>
        <div id="container">
            
            <div id="menu">
                <jsp:include page="template/menu.jsp"></jsp:include>
                </div><!-- Fim da div menu -->

                <!-- ??? -->
                <main>
                    <div id="conteudo" class="bg-background border rounded mx-auto">
                        <div id="formLogin" class="container border rounded mx-auto mt-5">
                        </div>   
                        <h3 class="text-center mt-2">Sistema de Gerenciamento</h3>
                        <div>
                            <img src="<%= request.getContextPath()%>/imagens/fundo.svg" width="100%" height="100%" alt="imagem de Fundo"/>
                        </div>
                </div><!-- Fim div conteudo -->
                
            </main>
                
        </div><!-- Fim div container -->
        
    </body>
    <script src="js/jquery-3.6.0.min.js"></script>
    <script src="bootstrap/bootstrap.min.js"></script>

</html>

