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
        <link rel="stylesheet" href="datatables/dataTables.bootstrap4.min.css" type="text/css"/>
        <link rel="stylesheet" href="datatables/jquery.dataTables.min.css" type="text/css"/>
        <link rel="shortcut icon" href="./imagens/logo-pequena.png">
        <title>Listagem de Clientes - Açaí do Berê</title>
    </head>
    <body>
        <div id="container">
            <div id="menu">
                <jsp:include page="template/menu.jsp"></jsp:include>
                </div><!-- Fim da div menu -->
                <main>
                    <div id="conteudo" class="bg-background border rounded mx-auto">
                        <div id="formLogin" class="container border rounded mx-auto mt-5">
                            <h3 class="text-center mt-2">Listagem de Clientes</h3>
                            <div>
                                <a href="cadastrarCliente.jsp" class="btn-sm btn-primary mb-3" 
                               role="button" style="text-decoration: none;display:inline-block;">Cadastrar Cliente</a>
                               
                               <a href="index.jsp" class="btn-sm btn-outline-danger"
                                   role="button" style="text-decoration: none;display:inline-block;">Voltar</a>
                            </div>
                            <table class="table table-hover table-striped table-bordered mt-3" id="listarClientes">
                                <thead>
                                    <tr class="thead-dark">
                                        <th scope="col">Código</th>
                                        <th scope="col">Nome</th>
                                        <th scope="col">CPF</th>
                                        <th scope="col">Endereço</th>
                                        <th scope="col">Telefone</th>
                                        <th scope="col">Status</th>
                                        <th scope="col">Ação</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${clientes}" var="c">
                                    <tr>
                                        <td>${c.idCliente}</td>
                                        <td>${c.nome}</td>
                                        <td>${c.cpf}</td>
                                        <td>${c.endereco}</td>
                                        <td>${c.telefone}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${c.status == 1}">
                                                    Ativado
                                                </c:when>
                                                <c:otherwise>
                                                    Desativado
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <script type="text/javascript">
                                                function confirmDesativar(id, nome) {
                                                    if (confirm("Deseja desativar o cliente " +
                                                            nome + "?")) {
                                                        location.href = "gerenciarCliente?acao=desativar&idCliente=" + id;
                                                    }
                                                }
                                                function confirmAtivar(id, nome) {
                                                    if (confirm("Deseja ativar o cliente " +
                                                            nome + "?")) {
                                                        location.href = "gerenciarCliente?acao=ativar&idCliente=" + id;
                                                    }
                                                }
                                            </script>
                                            <a href="gerenciarCliente?acao=alterar&idCliente=${c.idCliente}" 
                                               class="btn btn-primary btn-sm" role="button">Alterar</a>
                                            <c:choose>
                                                <c:when test="${c.status == 1}">
                                                    <a class="btn btn-outline-danger btn-sm" role="button"
                                                       onclick="confirmDesativar('${c.idCliente}', '${c.nome}')">Desativar</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a class="btn btn-outline-success btn-sm" role="button"
                                                       onclick="confirmAtivar('${c.idCliente}', '${c.nome}')">Ativar &nbsp &nbsp &nbsp</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div><!-- Fim da div conteudo -->
            </main>
        </div><!-- Fim da div container -->
        <!-- JQuery -->
        <script src="js/jquery-3.6.0.min.js"></script>
        <!-- JQuery.Datatables -->
        <script src="datatables/jquery.dataTables.min.js"></script>
        <!-- Bootstrap.min -->
        <script src="bootstrap/bootstrap.min.js"></script>
        <!-- Datables.Bootstrap.min -->
        <script src="datatables/dataTables.bootstrap4.min.js"></script>
        <!-- Configuracao da tabela com JQuery -->
        <script>
                                                           $(document).ready(function () {
                                                               $('#listarClientes').dataTable({
                                                                   "bJQueryUI": true,
                                                                   "lengthMenu": [[5, 10, 20, 25, -1], [5, 10, 20, 25, "Todos"]],
                                                                   "oLanguage": {
                                                                       "sProcessing": "Processando",
                                                                       "sLengthMenu": "Mostrar _MENU_ registros",
                                                                       "sZeroRecords": "Não foram encontrados resultados",
                                                                       "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                                                                       "sInfoEmpty": "Monstrado de 0 até 0 de 0 registros",
                                                                       "sInfoFiltered": "",
                                                                       "sInfoPostFix": "",
                                                                       "sSearch": "Pesquisar",
                                                                       "sUrl": "",
                                                                       "oPaginate": {
                                                                           "sFirst": "Primeiro",
                                                                           "sPrevious": "Anterior",
                                                                           "sNext": "Próximo",
                                                                           "sLast": "Último"
                                                                       }
                                                                   }
                                                               });
                                                           });
        </script>
    </body>
</html>