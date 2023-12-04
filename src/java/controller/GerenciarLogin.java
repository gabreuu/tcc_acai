package controller;

import dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import model.Menu;
import util.Util;

@WebServlet(name = "GerenciarLogin",
        urlPatterns = {"/gerenciarLogin"})
public class GerenciarLogin extends HttpServlet {

    private static HttpServletResponse response;
    RequestDispatcher dispatcher = null;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        if (sessao.getAttribute("ulogado") != null) {
            sessao.removeAttribute("ulogado");
            sessao.invalidate();
            response.sendRedirect("index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        GerenciarLogin.response = response;
        String login = Util.decode(request.getParameter("login"));
        String senha = Util.decode(request.getParameter("senha"));
        String mensagem = "";
        Usuario u = new Usuario();
        UsuarioDAO udao = new UsuarioDAO();

        if (login.equals("") || login.isEmpty()) {
            //request.setAttribute("msg", "Informe nome login do Usuário!");
            out.println(
                "<script type='text/javascript'>"
                + "alert('Informe nome de login do Usuário!');"
                + "location.href='login.jsp';"
                + "</script>"
            );
            return;
            //exibirMensagem(request, response);
        } else {
            u.setLogin(login);

        }

        if (senha.equals("") || senha.isEmpty()) {
            //request.setAttribute("msg","Informe a senha do usuário!");
            out.println(
                "<script type='text/javascript'>"
                + "alert('Informe a senha do Usuário!');"
                + "location.href='login.jsp';"
                + "</script>"
            );
            return;
            //exibirMensagem(request, response);
        } else {
            u.setLogin(login);
        }

        try {
            u = udao.getRecuperarUsuario(login);

            if ((u.getIdUsuario() > 0)
                    && (u.getSenha().equals(senha))) {
                HttpSession sessao = request.getSession();
                sessao.setAttribute("ulogado", u);
                response.sendRedirect("index.jsp");
            } else {
                mensagem = "Login ou senha inválidos!";
            }
        } catch (SQLException e) {
            mensagem = "Erro: " + e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='login.jsp';"
                + "</script>");

    }

    private void exibirMensagem(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        dispatcher
                = getServletContext().
                        getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);

    }

    public static Usuario verificarAcesso(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        GerenciarLogin.response = response;
        Usuario usuario = null;

        try {
            HttpSession sessao = request.getSession();
            if (sessao.getAttribute("ulogado") == null) {
                request.setAttribute("msg",
                        "Usuário não autenticado no sistema!");
                response.sendRedirect("login.jsp");

            } else {
                String uri = request.getRequestURI();
                String queryString = request.getQueryString();

                if (queryString != null) {
                    uri += "?" + queryString;

                }

                usuario = (Usuario) request.getSession().
                        getAttribute("ulogado");

                if (usuario == null) {
                    request.setAttribute("msg",
                            "Usuário não autenticado no sistema!");
                    response.sendRedirect("login.jsp");
                } else {
                    boolean possuiAcesso = false;
                    for (Menu m : usuario.getPerfil().getMenus()) {
                        if (uri.contains(m.getLink())) {
                            possuiAcesso = true;
                            break;
                        }
                    }

                    if (!possuiAcesso) {
                        request.setAttribute("msg",
                                "Usuário não Autorizado");
                        response.sendRedirect("login.jsp");
                    }
                }

            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }
    
    //verificar permissão.

}
