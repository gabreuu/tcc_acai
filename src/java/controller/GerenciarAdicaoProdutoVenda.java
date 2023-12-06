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
            String idVenda = request.getParameter("idVenda");
            // Ajuste de caracteres especiais
            String dataVenda = request.getParameter("dataVenda");
            String status = request.getParameter("status");
            String idUsuario = request.getParameter("idUsuario");
            String idCliente = request.getParameter("idCliente");
            String idAtendendimento = request.getParameter("idAtendimento");
            
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
//            if (request.getParameter("idVenda") != null && !request.getParameter("idVenda").equalsIgnoreCase("")) {
//                venda.setIdVenda(Integer.parseInt(request.getParameter("idVenda")));
//            }
            
            String mensagem = "";
            if (!idVenda.isEmpty()) {
                try {
                    venda.setIdVenda(Integer.parseInt(idVenda));
                } catch (NumberFormatException e) {
                    mensagem = "Error" + e.getMessage();
                }
            }

            if (dataVenda.isEmpty() || dataVenda.equals("")) {
                out.println(
                        "<script type='text/javascript'>"
                        + "alert('Por favor, informe uma data válida.');"
                        + "location.href='cadastrarVenda.jsp';"
                        + "</script>"
                );
                return;
            } else {
                venda.setDataVenda(Util.stringToDate(dataVenda));
            }

            // o preço total é calculado e enviado automaticamente
            venda.setPrecoTotal(Double.parseDouble(request.getParameter("precoTotal")));

            if (status.isEmpty() || status.equals("")) {
                out.println(
                        "<script type='text/javascript'>"
                        + "alert('É necessário definir o status da venda primeiro.');"
                        + "location.href='cadastrarVenda.jsp';"
                        + "</script>"
                );
                return;
            } else {
                venda.setStatus(Integer.parseInt(status));
            }

            UsuarioDAO udao = new UsuarioDAO();
            // associação MUITOS para UM entre venda e usuario (com função de atendente) respectivamente
            if (idUsuario.isEmpty() || idUsuario.equals("")) {
                out.println(
                        "<script type='text/javascript'>"
                        + "alert('Informe o responsável pelo atendimento.');"
                        + "location.href='cadastrarVenda.jsp';"
                        + "</script>"
                );
                return;
            } else {
                venda.setUsuario(udao.getCarregarPorId(Integer.parseInt(idUsuario)));
            }

            ClienteDAO cdao = new ClienteDAO();
            // associação MUITOS para UM entre venda e cliente respectivamente
            if (idCliente.isEmpty() || idCliente.equals("")) {
                out.println(
                        "<script type='text/javascript'>"
                        + "alert('Por favor, informe o cliente.');"
                        + "location.href='cadastrarVenda.jsp';"
                        + "</script>"
                );
                return;
            } else {
                venda.setCliente(cdao.getCarregarPorId(Integer.parseInt(idCliente)));
            }

            AtendimentoDAO adao = new AtendimentoDAO();
            // associação MUITOS para UM entre venda e atendimento respectivamente
            if (idAtendendimento.isEmpty() || idAtendendimento.equals("")) {
                out.println(
                        "<script type='text/javascript'>"
                        + "alert('Por favor, selecione uma modalidade de atendimento.');"
                        + "location.href='cadastrarVenda.jsp';"
                        + "</script>"
                );
                return;
            } else {
                venda.setAtendimento(adao.getCarregarPorId(Integer.parseInt(idAtendendimento)));
            }

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
