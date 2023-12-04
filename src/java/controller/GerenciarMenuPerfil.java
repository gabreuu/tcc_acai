package controller;

import dao.PerfilDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Perfil;

@WebServlet(name = "GerenciarMenuPerfil", urlPatterns = {"/gerenciarMenuPerfil"})
public class GerenciarMenuPerfil extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        String acao = request.getParameter("acao");
        String idPerfil = request.getParameter("idPerfil");
        String mensagem = "";
//        System.out.println("Acao : " + acao);
//        System.out.println("idPerfil : " + idPerfil);

        PerfilDAO pdao = new PerfilDAO();
        Perfil perfil = new Perfil();

        try {
            if (acao.equals("vincular")) {
                perfil = pdao.getCarregarPorId(Integer.parseInt(idPerfil));
                if (perfil.getIdPerfil() > 0) {
                    RequestDispatcher dispatcher = getServletContext().
                            getRequestDispatcher("/cadastrarMenuPerfil.jsp");
                    request.setAttribute("perfilv", perfil);
                    dispatcher.forward(request, response);
                } else {
                    mensagem = "Perfil não encontrado na base de dados!";
                }
            } else if (acao.equals("desvincular")) {
                String idMenu = request.getParameter("idMenu");
                if (idMenu.equals("") || idMenu.isEmpty()) {
                    mensagem = "O codigo do menu deve ser selecionado!";
                }else{
                    if (pdao.desvincular(Integer.parseInt(idMenu) , 
                            Integer.parseInt(idPerfil))) {
                        mensagem = "Menu desvinculado com sucesso!";
                    }else{
                        mensagem = "Falha ao desvincular o menu!";
                    }
                }
                
            }else{
                response.sendRedirect("index.jsp");
            }

        } catch (SQLException e) {
            mensagem = "Error: " + e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarMenuPerfil?acao=vincular&idPerfil=" + idPerfil + "';"
                + "</script>"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String idMenu = request.getParameter("idMenu");
        String idPerfil = request.getParameter("idPerfil");
        String mensagem = "";

        if (idMenu.equals("") || idPerfil.equals("")) {
            mensagem = "Os campos obrigatorios devem ser preenchidos!";
        } else {
            try {
                PerfilDAO pdao = new PerfilDAO();
                if (pdao.vincular(Integer.parseInt(idMenu),
                        Integer.parseInt(idPerfil))) {

                    mensagem = "Menu Vinculado com sucesso!";

                } else {
                    mensagem = "Falha ao vincular o menu!";
                }

            } catch (SQLException e) {
                mensagem = "Error : " + e.getMessage();
                e.printStackTrace();
            }
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarMenuPerfil?acao=vincular&idPerfil=" + idPerfil + "';"
                + "</script>"
        );
    }
}
