<%@page language="java" contentType="text/html; utf-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="model.Usuario, controller.GerenciarLogin"%>
<link rel="shortcut icon" href="./imagens/logo-pequena.png">

<%
    Usuario ulogado = GerenciarLogin.verificarAcesso(request, response);
    request.setAttribute("ulogado", ulogado);
%>

<c:choose>
    <c:when test="${ulogado != null}">
        <div class="d-md-flex justify-content-sm-between">
            <div class="mt-2">
                <h6 class="ml-4">Bem vindo(a), ${ulogado.nome}!</h6>

            </div>

            
            
            <div class="mt-1 row"> 
                <div class="mt-1 mr-5">
                <h6 class="ml-4">Perfil de Usuário: ${ulogado.perfil.nome}</h6>
                </div>
                <a href="gerenciarLogin?" class="btn btn-outline-danger btn-sm mr-4" role="button">
                    Sair
                </a>
            </div>
        </div>
    </c:when>
</c:choose>


<header>
    <nav class="navbar mt-2 navbar-expand-lg navbar-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse mb-2" id="navbarSupportedContent">
            <ul class="navbar-nav ml-1 mt-1">
                <c:if test="${ulogado != null && ulogado.perfil != null}">
                    <c:forEach var="menu" items="${ulogado.perfil.menus}">
                        <c:if test="${menu.exibir == 1}">
                            <li class="nav-item">
                                <a class="nav-link" href="${menu.link}">
                                    ${menu.nome}
                                </a>
                            </li>                            
                        </c:if>
                    </c:forEach>
                </c:if>
            </ul>
        </div>
    </nav>

</header>
