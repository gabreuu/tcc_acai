package controller;

import dao.ProdutoDAO;
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
import model.Produto;
import util.Util;

@WebServlet(name = "GerenciarProduto", urlPatterns = {"/gerenciarProduto"})
public class GerenciarProduto extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idProduto = request.getParameter("idProduto");
        String mensagem = "";
        
        Produto p = new Produto();
        ProdutoDAO pdao = new ProdutoDAO();

        try {
            if (acao.equals("adicionarAVenda")) {
                ArrayList<Produto> produtos;
                if(request.getAttribute("produtos")!=null){
                   produtos = (ArrayList<Produto>) request.getAttribute("produtos");
                }else{
                  produtos = new ArrayList<>();
                }
                if(request.getParameter("produtoSelecionado").length()>0){
                    int id = Integer.parseInt(request.getParameter("produtoSelecionado"));
                    Produto temp = new Produto();
                    temp = pdao.getCarregarPorId(id);
                   produtos.add(temp);

                }
                RequestDispatcher dispatcher = 
                        getServletContext().getRequestDispatcher("/cadastrarVenda.jsp");
                
                request.setAttribute("produtos", produtos);
                dispatcher.forward(request, response);

            }else if (acao.equals("listar")) {
                ArrayList<Produto> produtos = new ArrayList<>();
                produtos = pdao.getLista();
                RequestDispatcher dispatcher = 
                        getServletContext().getRequestDispatcher("/listarProdutos.jsp");
                
                request.setAttribute("produtos", produtos);
                dispatcher.forward(request, response);

            } else if (acao.equals("alterar")) {
                
                p = pdao.getCarregarPorId(Integer.parseInt(idProduto));
                if (p.getIdProduto()> 0) {
                    RequestDispatcher dispatcher = 
                        getServletContext().getRequestDispatcher("/cadastrarProduto.jsp");
                    request.setAttribute("produto", p);
                    dispatcher.forward(request, response);
                }else{
                    mensagem = "Produto não encontrado na base de dados!";
                }
                
            } else if (acao.equals("ativar")) {
                p.setIdProduto(Integer.parseInt(idProduto));
                if (pdao.ativar(p)) {
                    mensagem = "Produto ativado com sucesso!";
                }else{
                    mensagem = "Falha ao ativar o produto!";
                }
            } else if (acao.equals("desativar")) {
                p.setIdProduto(Integer.parseInt(idProduto));
                if (pdao.desativar(p)) {
                    mensagem = "Produto desativado com sucesso!";
                }else{
                    mensagem = "Falha ao desativar o produto!";
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
                + "location.href='gerenciarProduto?acao=listar';"
                + "</script>"
        );    
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String idProduto = request.getParameter("idProduto");
        // Ajuste de caracteres especiais
        String nome= Util.decode(request.getParameter("nome"));
        String descricao = Util.decode(request.getParameter("descricao"));
        String preco = request.getParameter("preco");
        String status = request.getParameter("status");
        String mensagem = "";
        HttpSession sessao = request.getSession();
        
        Produto p = new Produto();
        ProdutoDAO pdao = new ProdutoDAO();

        if(!idProduto.isEmpty()) {
            try {
                p.setIdProduto(Integer.parseInt(idProduto));
            } catch (NumberFormatException e) {
                mensagem = "Error" + e.getMessage();
            }
        }

        if (nome.isEmpty() || nome.equals("")) {
            sessao.setAttribute("msg", "Por favor, informe o nome do Produto.");
            exibirMensagem(request, response);
        } else {
            p.setNome(nome);
        }

        // atributo não obrigatório.
        p.setDescricao(descricao);
        
        if (preco.isEmpty() || preco.equals("")) {
            sessao.setAttribute("msg", "É necessário informar o preço do produto.");
            exibirMensagem(request, response);
        } else {
             p.setPreco(Double.parseDouble(preco));
        }

        if (status.isEmpty() || status.equals("")) {
            sessao.setAttribute("msg", "Por favor, informe o status do produto.");
            exibirMensagem(request, response);
        } else {
            try {
                p.setStatus(Integer.parseInt(status));
            } catch (NumberFormatException e) {
                mensagem = "Error" + e.getMessage();
            }
        }

        try {
            if (pdao.gravar(p)) {
                mensagem = "Produto salvo na base de dados!";
            } else {
                mensagem = "Falha ao salvar o produto na base de dados!";
            }
        } catch (SQLException e) {
            mensagem = "Error: " + e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarProduto?acao=listar';"
                + "</script>"
        );
    }

    private void exibirMensagem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = getServletContext().
                getRequestDispatcher("/cadastrarProduto.jsp");
        dispatcher.forward(request, response);
    }
           
}