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
                <div class="container">
                    <form action="gerenciarLogin" method="post" class="form-group">
                        <h3 class="text-center mt-5">Faça login para prosseguir.</h3>
                        
                        <div class="form-group row mt-5 offset-md-3">
                            <label class="col-md-2">Usuário</label>
                            <div class="col-md-5">
                                <input type="text" name="login" value="" class="form-control">
                            </div>
                        </div>
                        <div class="form-group row offset-md-3">
                            <label class="col-md-2">Senha</label>
                            <div class="col-md-5">
                                <input type="password" name="senha" value="" class="form-control">
                            </div>
                        </div>
                        
                        <div class="form-group row offset-md-3">
                            <label class="col-md-2"></label>
                            <div class="col-md-5">
                                <button class="btn btn-outline-success btn-md form-control">Autenticar</button>
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