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
        <title>Cadastro de Cliente - Açaí do Berê</title>

    </head>
    <body>
        <div id="container">

            <div id="menu">
                <jsp:include page="template/menu.jsp"></jsp:include>
                </div><!-- Fim da div menu -->
                <main>
                    <div id="conteudo" class="bg-background">
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
                                sessao.removeAttribute("msg");
                            }

                        }

                    %>
                    <div class="container">
                        <form action="gerenciarCliente?acao=cadastrar" method="post" class="form-group">
                            <h3 class="text-center mt-5">Cadastro de Cliente</h3>
                            <input type="hidden" name="idCliente" value="${cliente.idCliente}">

                            <div class="form-group row mt-5 offset-md-2">
                                <label class="col-md-3">Nome do Cliente</label>
                                <div class="col-md-5">
                                    <input type="text" name="nome" value="${cliente.nome}" class="form-control">
                                </div>
                            </div>      
                            <div class="form-group row offset-md-2">
                                <label class="col-md-3">CPF</label>
                                <div class="col-md-5">
                                    <input type="text" name="cpf" value="${cliente.cpf}" class="form-control"  maxlength="14" onkeyup="mascaraCPF(this)">
                                </div>
                            </div> 
                            <div class="form-group row offset-md-2">
                                <label class="col-md-3">Endereço</label>
                                <div class="col-md-5">
                                    <input type="text" name="endereco" value="${cliente.endereco}" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row offset-md-2">
                                <label class="col-md-3">Telefone</label>
                                <div class="col-md-5">
                                    <input type="text" name="telefone" value="${cliente.telefone}" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row offset-md-2">
                                <label class="col-md-3">Status</label>
                                <div class="col-md-5">
                                    <select class="form-control" name="status">
                                        <option value="">Escolha uma opção</option>
                                        <option value="1"
                                                <c:if test="${cliente.status == 1}">
                                                    selected
                                                </c:if>>Ativado</option>
                                        <option value="0"
                                                <c:if test="${cliente.status == 0}">
                                                    selected
                                                </c:if>>Desativado</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="d-md-flex justify-content-md-end mt-5 mr-5">
                                <button class="btn btn-primary mr-3">Gravar</button>
                                <a href="gerenciarCliente?acao=listar" 
                                   class="btn  btn-warning" role="button">Voltar
                                </a>
                            </div>
                        </form>
                    </div>
                </div><!-- Fim da div conteudo -->
            </main>
        </div><!-- Fim da div container -->

    </body>
    <script src="js/jquery-3.6.0.min.js"></script>
    <script src="js/script.js"></script>
    <script src="bootstrap/bootstrap.min.js"></script>

</html>
