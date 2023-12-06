package controller;

import dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Perfil;
import model.Usuario;
import util.Util;

@WebServlet(name = "GerenciarUsuario", urlPatterns = {"/gerenciarUsuario"})
public class GerenciarUsuario extends HttpServlet {

    UsuarioDAO udao = null;
    Usuario usuario = null;
    RequestDispatcher dispatcher = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        String acao = request.getParameter("acao");
        String idUsuario = request.getParameter("idUsuario");
        //int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        String mensagem = "";
        usuario = new Usuario();
        udao = new UsuarioDAO();

        try {
            if (acao.equals("listar")) {
                ArrayList<Usuario> usuarios = new ArrayList<>();
                usuarios = udao.getLista();
                dispatcher = getServletContext()
                        .getRequestDispatcher("/listarUsuarios.jsp");

                request.setAttribute("usuarios", usuarios);
                dispatcher.forward(request, response);
            } else if (acao.equals("alterar")) {
                
                usuario = udao.getCarregarPorId(Integer.parseInt(idUsuario));
                if (usuario.getIdUsuario() > 0) {
                    dispatcher = getServletContext()
                            .getRequestDispatcher("/cadastrarUsuario.jsp");
                    request.setAttribute("usuario", usuario);
                    dispatcher.forward(request, response);
                } else {
                    mensagem = "Usuario não encontrado na base de dados!";
                }
            } else if (acao.equals("ativar")) {
                usuario.setIdUsuario(Integer.parseInt(idUsuario));
                if (udao.ativar(usuario)) {
                    mensagem = "Usuário ativado com sucesso!";
                } else {
                    mensagem = "Falha ao ativar o usuário!";
                }
            } else if (acao.equals("desativar")) {
                usuario.setIdUsuario(Integer.parseInt(idUsuario));
                if (udao.desativar(usuario)) {
                    mensagem = "Usuário ativado com sucesso!";
                } else {
                    mensagem = "Falha ao ativar o usuário!";
                }
            } else {
                response.sendRedirect("index.jsp");
            }
        } catch (SQLException e) {
            mensagem = "Error: "+e.getMessage();
            e.printStackTrace();
        }
        
        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarUsuario?acao=listar';"
                + "</script>"
        );
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        String idUsuario = request.getParameter("idUsuario");
        // Ajuste de caracteres especiais
        String nome = Util.decode(request.getParameter("nome"));
        String login = Util.decode(request.getParameter("login"));
        String senha = Util.decode(request.getParameter("senha"));
        String idPerfil = request.getParameter("idPerfil");
        String status = request.getParameter("status");
        String mensagem = "";
        HttpSession sessao = request.getSession();

        // validações do lado do servidor e atribuição de valores.
        if (!idUsuario.isEmpty()) {
            try {
                usuario.setIdUsuario(Integer.parseInt(idUsuario));
            } catch (NumberFormatException e) {
                mensagem = "Error" + e.getMessage();
            }
        }

        if (nome.isEmpty() || nome.equals("")) {
            out.println(
                "<script type='text/javascript'>"
                + "alert('É necessário informar o nome do Usuário.');"
                + "location.href='cadastrarUsuario.jsp';"
                + "</script>"
            );
            return;
        } else {
            usuario.setNome(nome);
        }

        if (login.isEmpty() || login.equals("")) {
            out.println(
                "<script type='text/javascript'>"
                + "alert('Informe o login do usuário para prosseguir.');"
                + "location.href='cadastrarUsuario.jsp';"
                + "</script>"
            );
            return;
        } else {
            usuario.setLogin(login);
        }
        
        if (senha.isEmpty() || senha.equals("")) {
            out.println(
                "<script type='text/javascript'>"
                + "alert('A senha do usuário deve ser informada.');"
                + "location.href='cadastrarUsuario.jsp';"
                + "</script>"
            );
            return;
        } else {
            try {
                usuario.setSenha(senha);
            } catch (NumberFormatException e) {
                mensagem = "Error" + e.getMessage();
            }
        }
        
        if (status.isEmpty() || status.equals("")) {
            out.println(
                "<script type='text/javascript'>"
                + "alert('O status do Usuário deve ser selecionado.');"
                + "location.href='cadastrarUsuario.jsp';"
                + "</script>"
            );
            return;
        } else {
            usuario.setStatus(Integer.parseInt(status));
        }
        
        if (idPerfil.isEmpty() || idPerfil.equals("")) {
            out.println(
                "<script type='text/javascript'>"
                + "alert('Um Perfil deve ser associado ao Usuário. Por favor, selecione um Perfil.');"
                + "location.href='cadastrarUsuario.jsp';"
                + "</script>"
            );
            return;
        } else {
            Perfil perfil = new Perfil();
            try {
                perfil.setIdPerfil(Integer.parseInt(idPerfil));
                usuario.setPerfil(perfil);
            } catch (NumberFormatException e) {
                mensagem = "Error" + e.getMessage();
            }
            
            
        }

        try {
            if (udao.gravar(usuario)) {
                mensagem = "O Usuário foi salvo na base de dados!";
            } else {
                mensagem = "Falha ao salvar o Usuário na base de dados.";
            }
        } catch (SQLException e) {
            mensagem = "Error: " + e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarUsuario?acao=listar';"
                + "</script>"
        );
    }

    private void exibirMensagem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        dispatcher = getServletContext().
        getRequestDispatcher("/cadastrarUsuario.jsp");
        dispatcher.forward(request, response);
    }
        

}