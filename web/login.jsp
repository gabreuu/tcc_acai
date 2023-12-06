<%@page language="java" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
        <title>Login - Açaí do Berê</title>

    </head>
    <body>
        <div id="container">
            
                <div id="menu">
                <jsp:include page="template/menu.jsp"></jsp:include>
            </div><!-- Fim da div menu -->
            <main style="height: 100%">
            <div id="conteudo" class="bg-background">
                <%
                HttpSession sessao = request.getSession();
                if(sessao.getAttribute("msg") != null){
                    
                    String msg = (String) sessao.getAttribute("msg");
                    if (msg != null) {
                           
                    %>
                    <div class="alert alert-danger" role="alert">
                        <%= msg %>
                        <button type="button" class="close" data-dismiss="alert">
                            <span>&times;</span>
                        </button>
                    </div>
                    
                    <%
                        
                    }
                    sessao.removeAttribute("msg");
                }
                
                %>
                <div class="container d-flex justify-content-center mt-5">
                    <form id="formLogin" action="gerenciarLogin" method="post" class="form-group border rounded mx-auto">
                        <h1 class="text-center mt-3 ml-3 mr-3">Açaí do Berê</h1>
                        <h3 class="text-center mt-2 ml-3 mr-3">Faça login para prosseguir.</h3>
                        
                        <div class="form-group mt-5">
                            <label class="col-md-2 offset-1">Usuário</label>
                            <div class="col-md-10 offset-1">
                                <input type="text" name="login" value="" placeholder="login do usuário" class="form-control" autocomplete="off" autofocus="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 offset-1">Senha</label>
                            <div class="col-md-10 offset-1">
                                <input type="password" name="senha" value="" placeholder="senha do usuário" class="form-control" autocomplete="off">
                            </div>
                        </div>
                        
                        <div class="form-group mt-4">
                            <div class="col-md-10 offset-1">
                                <button class="btn btn-success btn-md form-control mb-3">Autenticar</button>
                            </div>
                        </div>
                        
                    </form>

                </div>

            </div><!-- Fim da div conteudo -->
            </main>
        </div><!-- Fim da div container -->

    </body>
    <script src="js/jquery-3.6.0.min.js"></script>
    <script src="bootstrap/bootstrap.min.js"></script>

</html>