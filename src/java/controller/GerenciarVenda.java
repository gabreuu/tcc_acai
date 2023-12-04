package controller;

import dao.ClienteDAO;
import dao.AtendimentoDAO;
import dao.ProdutoDAO;
import dao.UsuarioDAO;
import dao.VendaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Venda;
import model.Cliente;
import model.Atendimento;
import model.Usuario;
import model.Produto;
import util.Util;

@WebServlet(name = "GerenciarVenda", urlPatterns = {"/gerenciarVenda"})
public class GerenciarVenda extends HttpServlet {    
    VendaDAO vdao = null;
    Cliente cliente = null;
    Atendimento atendimento = null;
    Usuario usuario = null;
    Venda venda = null;
    
    RequestDispatcher dispatcher = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        String acao = request.getParameter("acao");
        
        String idVenda = request.getParameter("idVenda");
        request.getSession().removeAttribute("produtos");
                request.getSession().removeAttribute("venda");
        String mensagem = "";
        
        venda = new Venda();
        vdao = new VendaDAO();

        try {
            if (acao.equals("listar")) {
                ArrayList<Venda> vendas = new ArrayList<>();
                vendas = vdao.getLista();
                dispatcher = getServletContext()
                        .getRequestDispatcher("/listarVendas.jsp");

                request.setAttribute("vendas", vendas);
                dispatcher.forward(request, response);
            } else if (acao.equals("alterar")) {
                
                venda = vdao.getCarregarPorId(Integer.parseInt(idVenda));
                if (venda.getIdVenda()> 0) {
                    dispatcher = getServletContext()
                            .getRequestDispatcher("/cadastrarVenda.jsp");
                    request.setAttribute("venda", venda);
                    dispatcher.forward(request, response);
                } else {
                    mensagem = "Venda não encontrada na base de dados.";
                }
            } else if (acao.equals("ativar")) {
                venda.setIdVenda(Integer.parseInt(idVenda));
                if (vdao.ativar(venda)) {
                    mensagem = "Venda ativada com sucesso.";
                } else {
                    mensagem = "Falha ao ativar a venda.";
                }
            } else if (acao.equals("desativar")) {
                venda.setIdVenda(Integer.parseInt(idVenda));
                if (vdao.desativar(venda)) {
                    mensagem = "Venda desativada com sucesso.";
                } else {
                    mensagem = "Falha ao desativar a venda.";
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
                + "location.href='gerenciarVenda?acao=listar';"
                + "</script>"
        );
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Venda venda;
        PrintWriter out = response.getWriter();
        if(request.getSession().getAttribute("venda")!=null){
            venda = (Venda) request.getSession().getAttribute("venda");
        }else{
            venda = new Venda();
        }
        ArrayList<Produto> produtos ;
        if(request.getSession().getAttribute("produtos")!=null){
            produtos = (ArrayList<Produto>) request.getSession().getAttribute("produtos");
            for (Produto produto : produtos) {
                venda.setPrecoTotal(venda.getPrecoTotal()+produto.getPreco());
            }
        }else{
            produtos = new ArrayList<>();
        }
        venda.setProdutos(produtos);
        String mensagem = "";
        HttpSession sessao = request.getSession();
        try {
            if (vdao.gravar(venda)) {
                mensagem = "Venda salva na base de dados!";
            } else {
                mensagem = "Falha ao salvar a venda na base de dados.";
            }
        } catch (SQLException e) {
            mensagem = "Error: " + e.getMessage();
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        out.println(
                "<script type='text/javascript'>"
                        + "alert('" + mensagem + "');"
                                + "location.href='gerenciarVenda?acao=listar';"
                                + "</script>"
        );
    }

    private void exibirMensagem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatcher = getServletContext().
        getRequestDispatcher("/cadastrarVenda.jsp");
        dispatcher.forward(request, response);
    }
        

}