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
                        <form id="formVenda" action="gerenciarVenda" method="post" class="form-group border rounded mx-auto mt-5">
                            <input type="hidden" name="idVenda" value="${venda.idVenda}">
                            <c:choose>
                                <c:when test="${venda.idVenda == NULL}">
                                    <h3 class="text-center mt-2">Cadastro de Venda</h3>
                                </c:when>
                                <c:otherwise>
                                    <h3 class="text-center  mt-2">Alteração de Venda</h3>
                                </c:otherwise>
                            </c:choose>
                                    
                            <div class="form-group row offset-md-2 mt-5">
                                <label class="col-md-2 offset-1">Data da Venda<sup class="text-danger">*</sup></label>
                                <div class="col-md-5">
                                    <input type="date" name="dataVenda" 
                                           class="form-control" value="${venda.dataVendaString}" autofocus="true">
                                </div>
                            </div>
                            <c:set var="total" value="0" ></c:set>
                                <div class="col-md-5">
                                    <input type="hidden" name="precoTotal" value="${total}" class="form-control">
                            </div>
                            <div class="form-group row offset-md-2">
                                <label class="col-md-2 offset-1">Status<sup class="text-danger">*</sup></label>
                                <div class="col-md-5">
                                    <select class="form-control" name="status">
                                        <option value="">Escolha uma opção</option>
                                        <option value="1" selected
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
                                <label class="col-md-2 offset-1">Atendente<sup class="text-danger">*</sup></label>
                                <div class="col-md-5">
                                    <select class="form-control" name="idUsuario">
                                        <option value="">Selecione um Usuário Cadastrado</option>
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
                                <label class="col-md-2 offset-1">Cliente<sup class="text-danger">*</sup></label>
                                <div class="col-md-5">
                                    <select class="form-control" name="idCliente">
                                        <option value="">Selecione um Cliente Cadastrado</option>
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
                                <label class="col-md-2 offset-1">Atendimento<sup class="text-danger">*</sup></label>
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
                                <label class="col-md-2 offset-1">Produtos<sup class="text-danger">*</sup></label>
                                <div class="col-md-5">
                                    <select class="form-control" name="produtoSelecionado">
                                        <option value="">Escolha um Produto da Lista</option>
                                        <jsp:useBean class="dao.ProdutoDAO" id="pdao"/>
                                        <c:forEach items="${pdao.lista}" var="p">
                                            <option value="${p.idProduto}">
                                                ${p.nome}</option>
                                            </c:forEach>
                                    </select>                                                                                                          
                                </div>
                            </div>

                            <div class="form-group row offset-md-2">
                                <label class="col-md-2 offset-1"></label>

                                <div class="col-md-5">
                                    <button class="btn btn-dark mr-3 col-md-12" 
                                            type="submit" id="adicionarProduto" 
                                            onclick="document.getElementById('formVenda').action = 'gerenciarAdicaoProdutoVenda';
                                                    document.getElementById('formVenda').submit()">Adicionar Produto</button>
                                </div>
                            </div>
                            <div class="form-group row offset-md-2">
                                <a href="gerenciarVenda?acao=listar" 
                                   class="btn  btn-outline-danger form-control col-md-2 offset-1" role="button">Voltar
                                </a>

                                <div class="col-md-5">
                                    <button class="btn btn-primary mr-3 col-md-12" id="gravarVenda" type="submit" onclick="document.getElementById('formVenda').submit();">Gravar</button>
                                </div>
                            </div>


                            <table class="table table-hover table-striped table-bordered mt-1" id="listarVendas">
                                <thead>
                                    <tr class="thead-dark">
                                        <th scope="col">Código</th>
                                        <th scope="col">Nome</th>
                                        <th scope="col">Descrição</th>
                                        <th scope="col">Preço</th>
                                    </tr>
                                </thead>
                                <tbody>
                                   <!-- verifica se é venda nova e exibo os produtos selecionados ou verifica se é edição e carrega os produtos da venda -->

                                    <c:choose>
                                        <c:when test="${venda.idVenda == NULL}">
                                            <c:forEach items="${produtos}" var="v">
                                                <tr>
                                                    <td>${v.idProduto}</td>
                                                    <td>${v.nome}</td>
                                                    <td>${v.descricao}</td>
                                                    <td>${v.precoFormatado}</td>
                                                </tr>
                                            </c:forEach>                                        
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach items="${venda.produtos}" var="v">
                                                <tr>
                                                    <td>${v.idProduto}</td>
                                                    <td>${v.nome}</td>
                                                    <td>${v.descricao}</td>
                                                    <td>${v.precoFormatado}</td>
                                                </tr>
                                            </c:forEach> 
                                        </c:otherwise>
                                    </c:choose>
                                    
                                    <tr>
                                        <td colspan="3" style="text-align: right">Total:</td>
                                        <!-- Inicia um loop sobre os produtos para calcular o total -->
                                        <c:choose>
                                        <c:when test="${venda.idVenda == NULL}">
                                            <c:forEach items="${produtos}" var="v">
                                                <!-- Define a variável 'total' somando o preço de cada produto -->
                                                <c:set var="total" value="${total+v.preco}"/>
                                            </c:forEach>                                        
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach items="${venda.produtos}" var="v">
                                                <!-- Define a variável 'total' somando o preço de cada produto -->
                                                <c:set var="total" value="${total+v.preco}"/>
                                            </c:forEach> 
                                        </c:otherwise>
                                    </c:choose>
                                        
                                        <!-- Exibe o total calculado no formato de moeda -->
                                        <td>R$ ${total}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div><!-- Fim da div conteudo -->
            </main>
        </div><!-- Fim da div container -->

    </body>
    <script>
        $("#gravarVenda").click(function(){
        $("#formVenda").submit();
        }
        , ("#adicionarProduto").click(function(){
        $("#frmProduto").submit();
        }
    </script>
    <script src="js/jquery-3.6.0.min.js"></script>
    <script src="bootstrap/bootstrap.min.js"></script>
</html>