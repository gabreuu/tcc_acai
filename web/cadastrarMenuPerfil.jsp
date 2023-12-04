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
        <title>Vinculação de Menus - Açaí do Berê</title>

    </head>
    <body>
        <div id="container">

            <div id="menu">
                <jsp:include page="template/menu.jsp"></jsp:include>
                </div><!-- Fim da div menu -->
                <main>
                    <div id="conteudo" class="bg-background">
                        <div class="container">
                            <form action="gerenciarMenuPerfil?acao=cadastrar" method="post" class="form-group">
                                <h3 class="text-center">Vinculação de Menus</h3>
                                <input type="hidden" name="idPerfil" value="${perfilv.idPerfil}">

                            <div class="form-group row mt-5 offset-md-2">
                                <label class="col-md-3">Nome</label>
                                <div class="col-md-5">
                                    <input type="text" name="nome" value="${perfilv.nome}" class="form-control"
                                           readonly>
                                </div>
                            </div>
                            <div class="form-group row offset-md-2">
                                <label class="col-md-3">Menu</label>
                                <div class="col-md-5">
                                    <select class="form-control"name="idMenu">
                                        <option value="">Escolha um menu</option>
                                        <c:forEach var="m" items="${perfilv.naoMenus}">
                                            <option value="${m.idMenu}">${m.nome}</option>                 
                                        </c:forEach>
                                    </select>

                                </div>
                            </div>
                            <div class="d-md-flex justify-content-md-end mt-5 mr-5">
                                <button class="btn btn-primary btn-sm mr-2">Vincular</button>
                                <a href="gerenciarPerfil?acao=listar" 
                                   class="btn  btn-danger btn-sm" role="button">Listar Perfis
                                </a>
                            </div>

                        </form>
                        <h3 class="text-center">Listagem de Menu Perfil</h3>
                        <table class="table table-hover table-striped table-bordered mt-3" id="listarPerfis">
                            <thead>
                                <tr class="thead-dark">
                                    <th scope="col">Código</th>
                                    <th scope="col">Nome</th>
                                    <th scope="col">Link</th>
                                    <th scope="col">Exibir</th>
                                    <th scope="col">Status</th>
                                    <th scope="col">Acao</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${perfilv.menus}" var="m">
                                    <tr>
                                        <td>${m.idMenu}</td>
                                        <td>${m.nome}</td>
                                        <td>${m.link}</td>
                                        <td>
                                            <c:if test="${m.exibir == 1}">
                                                Sim
                                            </c:if>
                                            <c:if test="${m.exibir == 0}">
                                                Não
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${m.status == 1}">
                                                    Ativado
                                                </c:when>
                                                <c:otherwise>
                                                    Desativado
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <script type="text/javascript">
                                                function confirmDesvincular(idMenu, nome, idPerfil) {
                                                    if (confirm("Deseja desvincular o menu " + nome + "?")) {
                                                        location.href = "gerenciarMenuPerfil?acao=desvincular&idMenu=" + idMenu +
                                                                "&idPerfil=" + idPerfil;
                                                    }

                                                }
                                            </script>

                                            <button class="btn btn-danger btn-sm"
                                                    onclick="confirmDesvincular('${m.idMenu}', '${m.nome}', '${perfilv.idPerfil}')">Desvincular</button>
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
                                                            $('#listarPerfis').dataTable({
                                                                "bJQueryUI": true,
                                                                "lengthMenu": [[5, 10, 20, 25, -1], [5, 10, 20, 25, "Todos"]],
                                                                "oLanguage": {
                                                                    "sProcessing": "Processando",
                                                                    "sLenghtMenu": "Montrar _MENU_ registros",
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