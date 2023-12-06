package controller;

import dao.MenuDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Menu;
import util.Util;

@WebServlet(name = "GerenciarMenu", urlPatterns = {"/gerenciarMenu"})
public class GerenciarMenu extends HttpServlet {
    
    RequestDispatcher dispatcher = null;
    Menu menu = null;
    MenuDAO mdao = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idMenu = request.getParameter("idMenu");
        String mensagem = "";

        menu = new Menu();
        mdao = new MenuDAO(); 

        try {
            if (acao.equals("listar")) {
                ArrayList<Menu> menus = new ArrayList<>();
                menus = mdao.getLista();
                dispatcher = getServletContext().getRequestDispatcher("/listarMenus.jsp");
                
                request.setAttribute("menus", menus);
                dispatcher.forward(request, response);
            } else if (acao.equals("alterar")) {
                
                menu = mdao.getCarregarPorId(Integer.parseInt(idMenu));
                if (menu.getIdMenu() > 0) {
                    RequestDispatcher dispatcher = 
                        getServletContext().getRequestDispatcher("/cadastrarMenu.jsp");
                    request.setAttribute("menu", menu);
                    dispatcher.forward(request, response);
                }else{
                    mensagem = "Menu não encontrado na base de dados!";
                }
                
            } else if (acao.equals("ativar")) {
                menu.setIdMenu(Integer.parseInt(idMenu));
                if (mdao.ativar(menu)) {
                    mensagem = "Menu ativado com sucesso!";
                }else{
                    mensagem = "Falha ao ativar o menu!";
                }
            } else if (acao.equals("desativar")) {
                menu.setIdMenu(Integer.parseInt(idMenu));
                if (mdao.desativar(menu)) {
                    mensagem = "Menu desativado com sucesso!";
                }else{
                    mensagem = "Falha ao desativar o menu!";
                }
            } else {
                response.sendRedirect("index.jsp");
            }

        } catch (SQLException e) {
            mensagem = "Erro: "+ e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarMenu?acao=listar';"
                + "</script>"
        );    
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String idMenu = request.getParameter("idMenu");
        // Ajuste de caracteres especiais
        String nome = Util.decode(request.getParameter("nome"));
        String link = request.getParameter("link");
        String exibir = request.getParameter("exibir");
        String status = request.getParameter("status");
        String mensagem = "";
        HttpSession sessao = request.getSession();

        Menu m = new Menu();
        MenuDAO mdao = new MenuDAO();

        // validações do lado servidor e atribuição de valores.
        if(!idMenu.isEmpty()) {
            try {
                m.setIdMenu(Integer.parseInt(idMenu));
            } catch (NumberFormatException e) {
                mensagem = "Error" + e.getMessage();
            }
        }

        if (nome.isEmpty() || nome.equals("")) {
            //sessao.setAttribute("msg", "Informe o nome do Menu!");
            out.println(
                "<script type='text/javascript'>"
                + "alert('É necessário informar o nome do Menu.');"
                + "location.href='cadastrarMenu.jsp';"
                + "</script>"
            );
            return;
            //exibirMensagem(request, response);
        } else {
            m.setNome(nome);
        }

        if (link.isEmpty() || link.equals("")) {
            //sessao.setAttribute("msg", "Informe o link do menu!");
            //mensagem = "Informe o link do Menu!";
            out.println(
                "<script type='text/javascript'>"
                + "alert('Por favor, informe o link do Menu.');"
                + "location.href='cadastrarMenu.jsp';"
                + "</script>"
            );
            return;
            //exibirMensagem(request, response);
        } else {
             m.setLink(link);
        }
        
        if (exibir.isEmpty() || exibir.equals("")) {
            //sessao.setAttribute("msg", "Informe o tipo de exibição do Menu!");
            //mensagem = "Informe o tipo de exibição do Menu!";
            out.println(
                "<script type='text/javascript'>"
                + "alert('Informe o tipo de exibição do Menu!');"
                + "location.href='cadastrarMenu.jsp';"
                + "</script>"
            );
            return;
            //exibirMensagem(request, response);
        } else {
            try {
                m.setExibir(Integer.parseInt(exibir));
            } catch (NumberFormatException e) {
                mensagem = "Error" + e.getMessage();
            }
        }

        if (status.isEmpty() || status.equals("")) {
            //sessao.setAttribute("msg", "Informe o status do Menu!");
            //mensagem = "Informe o status do Menu!";
            out.println(
                "<script type='text/javascript'>"
                + "alert('O status do Menu deve ser selecionado.');"
                + "location.href='cadastrarMenu.jsp';"
                + "</script>"
            );
            return;
            //exibirMensagem(request, response);
        } else {
            try {
                m.setStatus(Integer.parseInt(status));
            } catch (NumberFormatException e) {
                mensagem = "Error" + e.getMessage();
            }
        }

        try {
            if (mdao.gravar(m)) {
                mensagem = "O Menu foi salvo na base de dados!";
            } else {
                mensagem = "Falha ao salvar o Menu na base de dados.";
            }
        } catch (SQLException e) {
            mensagem = "Error: " + e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarMenu?acao=listar';"
                + "</script>"
        );
    }

    private void exibirMensagem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = getServletContext().
                getRequestDispatcher("/cadastrarMenu.jsp");
        dispatcher.forward(request, response);
    }          
}