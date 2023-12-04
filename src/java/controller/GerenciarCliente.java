package controller;

import dao.ClienteDAO;
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
import model.Cliente;
import util.Util;

@WebServlet(name = "GerenciarCliente", urlPatterns = {"/gerenciarCliente"})
public class GerenciarCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idCliente = request.getParameter("idCliente");
        String mensagem = "";
        
        Cliente c = new Cliente();
        ClienteDAO cdao = new ClienteDAO();

        try {
            if (acao.equals("listar")) {
                ArrayList<Cliente> clientes = new ArrayList<>();
                clientes = cdao.getLista();
                RequestDispatcher dispatcher = 
                        getServletContext().getRequestDispatcher("/listarClientes.jsp");
                
                request.setAttribute("clientes", clientes);
                dispatcher.forward(request, response);

            } else if (acao.equals("alterar")) {
                
                c = cdao.getCarregarPorId(Integer.parseInt(idCliente));
                if (c.getIdCliente()> 0) {
                    RequestDispatcher dispatcher = 
                        getServletContext().getRequestDispatcher("/cadastrarCliente.jsp");
                    request.setAttribute("cliente", c);
                    dispatcher.forward(request, response);
                }else{
                    mensagem = "Cliente não encontrado na base de dados!";
                }
                
            } else if (acao.equals("ativar")) {
                c.setIdCliente(Integer.parseInt(idCliente));
                if (cdao.ativar(c)) {
                    mensagem = "Cliente ativado com sucesso!";
                }else{
                    mensagem = "Falha ao ativar o cliente.";
                }
            } else if (acao.equals("desativar")) {
                c.setIdCliente(Integer.parseInt(idCliente));
                if (cdao.desativar(c)) {
                    mensagem = "Cliente desativado com sucesso!";
                }else{
                    mensagem = "Falha ao desativar o cliente.";
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
                + "location.href='gerenciarCliente?acao=listar';"
                + "</script>"
        );    
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String idCliente = request.getParameter("idCliente");
        String nome = Util.decode(request.getParameter("nome"));
        String cpf = request.getParameter("cpf");
        String endereco = Util.decode(request.getParameter("endereco"));
        String telefone = request.getParameter("telefone");
        String status = request.getParameter("status");
        String mensagem = "";
        HttpSession sessao = request.getSession();
        
        Cliente c = new Cliente();
        ClienteDAO cdao = new ClienteDAO();

        if(!idCliente.isEmpty()) {
            try {
                c.setIdCliente(Integer.parseInt(idCliente));
            } catch (NumberFormatException e) {
                mensagem = "Error" + e.getMessage();
            }
        }

        if (nome.isEmpty() || nome.equals("")) {
            sessao.setAttribute("msg", "É necessário inserir o nome do cliente.");
            exibirMensagem(request, response);
        } else {
            c.setNome(nome);
        }

        // atributo não obrigatório.
        c.setCpf(cpf);
        c.setEndereco(endereco);
        c.setTelefone(telefone);

        if (status.isEmpty() || status.equals("")) {
            sessao.setAttribute("msg", "Por favor, informe o status do cliente.");
            exibirMensagem(request, response);
        } else {
            try {
                c.setStatus(Integer.parseInt(status));
            } catch (NumberFormatException e) {
                mensagem = "Error" + e.getMessage();
            }
        }

        try {
            if (cdao.gravar(c)) {
                mensagem = "Cliente salvo na base de dados!";
            } else {
                mensagem = "Falha ao salvar o cliente na base de dados.";
            }
        } catch (SQLException e) {
            mensagem = "Error: " + e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarCliente?acao=listar';"
                + "</script>"
        );
    }

    private void exibirMensagem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = getServletContext().
                getRequestDispatcher("/cadastrarCliente.jsp");
        dispatcher.forward(request, response);
    }        
}