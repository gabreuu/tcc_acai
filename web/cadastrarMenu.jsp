<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>Cadastro de Menu - Açaí do Berê</title>

    </head>
    <body>
        <div id="container">

            <div id="menu">
                <jsp:include page="template/menu.jsp"></jsp:include>
                </div><!-- Fim da div menu -->
                <main>
                    <div id="conteudo" class="bg-background border rounded mx-auto">
                    <%
                        HttpSession sessao = request.getSession();
                        if (sessao.getAttribute("msg") != null) {

                            String msg = (String) sessao.getAttribute("msg");
                            if (msg != null) {
                    %>
                    <div class="alert alert-danger" role="alert">
                        <%= msg%>
                        <button type="button" class="close" data-dismiss="alert">
                            <span>&times;</span>
                        </button>
                    </div>

                    <%
                            }
                            sessao.removeAttribute("msg");
                        }
                    %>
                    <div id="conteudo">
                        <form id="formLogin" action="gerenciarMenu?acao=cadastrar" method="post" class="form-group border rounded mx-auto mt-5">
                            <input type="hidden" name="idMenu" value="${menu.idMenu}">
                            <c:choose>
                                <c:when test="${menu.idMenu == NULL}">
                                    <h3 class="text-center  mt-2">Cadastro de Menu</h3>
                                </c:when>
                                <c:otherwise>
                                    <h3 class="text-center  mt-2">Alteração do Menu ${menu.nome}</h3>
                                </c:otherwise>
                            </c:choose>

                            <div class="form-group row mt-5 offset-md-2">
                                <label class="col-md-2 offset-1">Nome<sup class="text-danger">*</sup></label>
                                <div class="col-md-5">
                                    <input type="text" name="nome" value="${menu.nome}" class="form-control" autofocus="true">
                                </div>
                            </div>
                            <div class="form-group row offset-md-2">
                                <label class="col-md-2 offset-1">Link<sup class="text-danger">*</sup></label>
                                <div class="col-md-5">
                                    <input type="text" name="link" value="${menu.link}" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row offset-md-2">
                                <label class="col-md-2 offset-1">Exibir<sup class="text-danger">*</sup></label>
                                <div class="col-md-5">
                                    <select class="form-control" name="exibir">
                                        <option value="">Escolha uma opção</option>
                                        <option value="1" selected
                                                <c:if test="${menu.exibir == 1}">
                                                    selected
                                                </c:if>>Sim</option>
                                        <option value="0"
                                                <c:if test="${menu.exibir == 0}">
                                                    selected
                                                </c:if>>Não</option>
                                    </select>

                                </div>
                            </div>
                            <div class="form-group row offset-md-2">
                                <label class="col-md-2 offset-1">Status<sup class="text-danger">*</sup></label>
                                <div class="col-md-5">
                                    <select class="form-control" name="status">
                                        <option value="">Escolha uma opção</option>
                                        <option value="1" selected
                                                <c:if test="${menu.status == 1}">
                                                    selected
                                                </c:if>>Ativado</option>
                                        <option value="0"
                                                <c:if test="${menu.status == 0}">
                                                    selected
                                                </c:if>>Desativado</option>
                                    </select>
                                </div>
                            </div>
                                
                            <div class="form-group row offset-md-2">
                                
                                <a href="gerenciarMenu?acao=listar" 
                                       class="btn  btn-outline-danger form-control col-md-2 offset-1" role="button">Voltar
                                    </a>
                                <div class="col-md-5">
                                    <button class="btn btn-primary form-control col-md-12">Gravar</button>
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