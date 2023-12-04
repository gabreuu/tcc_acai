<%@page import="model.Venda"%>
<%@page import="java.util.List"%>
<%@page import="model.Produto"%>
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
        <title>Cadastro de Venda - Açaí do Berê</title>

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
                        sessao.getAttribute("produtos");
                        List<Produto> produtos = (List<Produto>) request.getSession().getAttribute("produtos");
                        Venda venda = (Venda) request.getSession().getAttribute("venda");

                        request.getSession().setAttribute("produtos", produtos);
                        if (sessao.getAttribute("msg") != null) {

                            String msg = (String) sessao.getAttribute("msg");
                            if (msg != null) {

                    %>
                    <div class="alert alert-danger" role="alert">
                        <%= msg%>
                        <button type="button" class="close" data-dismiss="alert"
                                arial-label="Close">
                            <span>&times;</span>
                        </button>
                    </div>

                    <%

                            }

                        }

                    %>
                    <div class="container">
                        <form action="gerenciarVenda" method="post" id="frmVenda" class="form-group">
                            <h3 class="text-center mt-5">Cadastro de Venda</h3>
                            <input type="hidden" name="idVenda" value="${venda.idVenda}">
                            <div class="form-group row offset-md-2">
                                <label class="col-md-3">Data da Venda</label>
                                <div class="col-md-5">
                                    <input type="date" name="dataVenda" 
                                           class="form-control" value="${venda.dataVendaString}">
                                </div>
                            </div>
                                <c:set var="total" value="0" ></c:set>
                                <div class="col-md-5">
                                    <input type="hidden" name="precoTotal" value="${total}" class="form-control">
                                </div>
                            <div class="form-group row offset-md-2">
                                <label class="col-md-3">Status</label>
                                <div class="col-md-5">
                                    <select class="form-control" name="status">
                                        <option value="">Escolha uma opção</option>
                                        <option value="1"
                                                <c:if test="${venda.status == 1}">
                                                    selected
                                                </c:if>>Ativado</option>
                                        <option value="0"
                                                <c:if test="${venda.status == 0}">
                                                    selected
                                                </c:if>>Desativado</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row offset-md-2">
                                <label class="col-md-3">Atendente</label>
                                <div class="col-md-5">
                                    <select class="form-control" name="idUsuario">
                                        <option value="">Escolha um usuário</option>
                                        <jsp:useBean class="dao.UsuarioDAO" id="udao"/>
                                        <c:forEach items="${udao.lista}" var="u">
                                            <option value="${u.idUsuario}"
                                                    <c:if test="${venda.usuario.idUsuario == u.idUsuario}">
                                                    selected
                                                </c:if>>
                                                    ${u.nome}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row offset-md-2">
                                <label class="col-md-3">Cliente</label>
                                <div class="col-md-5">
                                    <select class="form-control" name="idCliente">
                                        <option value="">Cliente não cadastrado</option>
                                        <jsp:useBean class="dao.ClienteDAO" id="cdao"/>
                                        <c:forEach items="${cdao.lista}" var="c">
                                            <option value="${c.idCliente}"
                                                    <c:if test="${venda.cliente.idCliente == c.idCliente}">
                                                    selected
                                                </c:if>>
                                                    ${c.nome}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row offset-md-2">
                                <label class="col-md-3">Atendimento</label>
                                <div class="col-md-5">
                                    <select class="form-control" name="idAtendimento">
                                        <option value="">Escolha tipo de Atendimento</option>
                                        <jsp:useBean class="dao.AtendimentoDAO" id="adao"/>
                                        <c:forEach items="${adao.lista}" var="a">
                                            <option value="${a.idAtendimento}"
                                                    <c:if test="${venda.atendimento.idAtendimento == a.idAtendimento}">
                                                    selected
                                                </c:if>>
                                                    ${a.tipoAtendimento} no ${a.tipoPagto}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        
                                                <div class="form-group row offset-md-2">
                                                <label class="col-md-3">Produtos</label>
                                                <div class="col-md-5">
                                                    <select class="form-control" name="produtoSelecionado">
                                                        <option value="">Escolha um produto</option>
                                                        <jsp:useBean class="dao.ProdutoDAO" id="pdao"/>
                                                        <c:forEach items="${pdao.lista}" var="p">
                                                            <option value="${p.idProduto}">
                                                                    ${p.nome}</option>
                                                        </c:forEach>
                                                    </select>
                                                                                                                <button class="btn btn-primary mr-3 mt-3" type="submit" id="adicionarProduto" onclick="document.getElementById('frmVenda').action='gerenciarAdicaoProdutoVenda';document.getElementById('frmVenda').submit()">Adicionar Produto</button>

                                                </div>
                                            </div>
                                        </form>
                                                        <table class="table table-hover table-striped table-bordered mt-5" id="listarVendas">
                                <thead>
                                    <tr class="thead-dark">
                                        <th scope="col">Código</th>
                                        <th scope="col">Nome</th>
                                        <th scope="col">Descrição</th>
                                        <th scope="col">preço</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${produtos}" var="v">
                                    <tr>
                                        <td>${v.idProduto}</td>
                                        <td>${v.nome}</td>
                                        <td>${v.descricao}</td>
                                        <td>${v.preco}</td>
                                    </tr>
                                </c:forEach>
                                    <tr>
                                        <td colspan="3" style="text-align: right">Total:</td>
                                        <c:forEach items="${produtos}" var="v">
                                            <c:set var="total" value="${total+v.preco}"/>
                                        </c:forEach>
                                        <td>${total}</td>
                                    </tr>
                            </tbody>
                        </table>
                            <div class="d-md-flex justify-content-md-around mt-5 mr-5">
                                <button class="btn btn-primary mr-3" id="gravarVenda" type="submit" onclick="document.getElementById('frmVenda').submit();">Gravar</button>
                                <a href="gerenciarVenda?acao=listar" 
                                   class="btn  btn-warning" role="button">Voltar
                                </a>
                            </div>

                    </div>

                </div><!-- Fim da div conteudo -->
            </main>
        </div><!-- Fim da div container -->

    </body>
    <script>
        $("#gravarVenda").click(function(){  
            $("#frmVenda").submit();
        }
        ,("#adicionarProduto").click(function(){  
            $("#frmProduto").submit();
        }
    </script>
    <script src="js/jquery-3.6.0.min.js"></script>
    <script src="bootstrap/bootstrap.min.js"></script>
</html>