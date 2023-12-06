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
                if (venda.getIdVenda() > 0) {
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
            mensagem = "Error: " + e.getMessage();
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

        try {
            Produto p = new Produto();
            ProdutoDAO pdao = new ProdutoDAO();
            AtendimentoDAO adao = new AtendimentoDAO();
            UsuarioDAO udao = new UsuarioDAO();
            ClienteDAO cdao = new ClienteDAO();
            PrintWriter out = response.getWriter();
            Venda venda = new Venda();
            
            venda.setIdVenda(Integer.parseInt(request.getParameter("idVenda")));
            // Ajuste de caracteres especiais
            venda.setDataVenda(Util.stringToDate(request.getParameter("dataVenda")));
            venda.setStatus(Integer.parseInt(request.getParameter("status")));
            venda.setUsuario(udao.getCarregarPorId(Integer.parseInt(request.getParameter("idUsuario"))));
            venda.setCliente(cdao.getCarregarPorId(Integer.parseInt(request.getParameter("idCliente"))));
            venda.setAtendimento(adao.getCarregarPorId(Integer.parseInt(request.getParameter("idAtendimento"))));
            
            response.setContentType("text/html");
            
            ArrayList<Produto> produtos;
            //verifica se há produtos vindo da pagina (alteração) se não tiver busca os produtos originalmente salvos
            if (request.getSession().getAttribute("produtos") != null) {
                produtos = (ArrayList<Produto>) request.getSession().getAttribute("produtos");
                
            } else {
                produtos = pdao.getCarregarPorIdVenda(venda.getIdVenda());
                
            }
            //calcula o valor total da venda
            for (Produto produto : produtos) {
                    venda.setPrecoTotal(venda.getPrecoTotal() + produto.getPreco());
                }
            venda.setProdutos(produtos);
            
            
            String mensagem = "";
            HttpSession sessao = request.getSession();
            
            if (venda.getProdutos() == null || venda.getProdutos().size() == 0) {
                out.println(
                        "<script type='text/javascript'>"
                                + "alert('É necessário selecionar pelo menos um produto.');"
                                + "location.href='cadastrarVenda.jsp';"
                                + "</script>"
                );
                return;
            } else {
                try {
                    if (vdao.gravar(venda)) {
                        mensagem = "Venda salva na base de dados!";
                    } else {
                        mensagem = "Falha ao salvar a venda na base de dados.";
                    }
                } catch (SQLException e) {
                    mensagem = "Error: " + e.getMessage();
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            out.println(
                    "<script type='text/javascript'>"
                            + "alert('" + mensagem + "');"
                                    + "location.href='gerenciarVenda?acao=listar';"
                                    + "</script>"
            );
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exibirMensagem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatcher = getServletContext().
                getRequestDispatcher("/cadastrarVenda.jsp");
        dispatcher.forward(request, response);
    }

}
