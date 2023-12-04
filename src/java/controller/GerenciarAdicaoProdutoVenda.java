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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cliente;
import model.Atendimento;
import model.Produto;
import model.Venda;
import util.Util;

@WebServlet(name = "gerenciarAdicaoProdutoVenda", urlPatterns = {"/gerenciarAdicaoProdutoVenda"})
public class GerenciarAdicaoProdutoVenda extends HttpServlet {
    VendaDAO vdao = null;
    Cliente cliente = null;
    Atendimento atendimento = null;
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
        
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher("/cadastrarVenda.jsp");

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Produto p = new Produto();
            ProdutoDAO pdao = new ProdutoDAO();
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");

            ArrayList<Produto> produtos;
            if (request.getSession().getAttribute("produtos") != null) {
                produtos = (ArrayList<Produto>) request.getSession().getAttribute("produtos");
            } else {
                produtos = new ArrayList<>();
            }
            // seleção múltipla de produtos
            if (request.getParameter("produtoSelecionado").length() > 0) {
                try {
                    int id = Integer.parseInt(request.getParameter("produtoSelecionado"));
                    Produto temp = new Produto();
                    temp = pdao.getCarregarPorId(id);
                    produtos.add(temp);
                } catch (SQLException ex) {
                    Logger.getLogger(GerenciarAdicaoProdutoVenda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Venda venda = new Venda();
            if (request.getParameter("idVenda") != null && !request.getParameter("idVenda").equalsIgnoreCase("")) {
                venda.setIdVenda(Integer.parseInt(request.getParameter("idVenda")));
            }
            venda.setDataVenda(Util.stringToDate(request.getParameter("dataVenda")));
            venda.setPrecoTotal(Double.parseDouble(request.getParameter("precoTotal")));
            venda.setStatus(Integer.parseInt(request.getParameter("status")));
            ClienteDAO cdao = new ClienteDAO();
            UsuarioDAO udao = new UsuarioDAO();
            AtendimentoDAO adao = new AtendimentoDAO();
            // associação MUITOS para UM entre venda e cliente respectivamente
            venda.setCliente(cdao.getCarregarPorId(Integer.parseInt(request.getParameter("idCliente"))));
            // associação MUITOS para UM entre venda e atendimento respectivamente
            venda.setAtendimento(adao.getCarregarPorId(Integer.parseInt(request.getParameter("idAtendimento"))));
            // associação MUITOS para UM entre venda e usuario respectivamente
            venda.setUsuario(udao.getCarregarPorId(Integer.parseInt(request.getParameter("idUsuario"))));
            // associação MUITOS para MUITOS entre venda e produto respectivamente (muitas vendas tem muitos produtos);
            venda.setProdutos(produtos);
            
            RequestDispatcher dispatcher
                    = getServletContext().getRequestDispatcher("/cadastrarVenda.jsp");

            request.getSession().setAttribute("produtos", produtos);
            request.getSession().setAttribute("venda", venda);
            request.setAttribute("venda", venda);
            dispatcher.forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarAdicaoProdutoVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
