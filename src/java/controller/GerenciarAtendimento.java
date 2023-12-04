package controller;

import dao.AtendimentoDAO;
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
import model.Atendimento;
import util.Util;

@WebServlet(name = "GerenciarAtendimento", urlPatterns = {"/gerenciarAtendimento"})
public class GerenciarAtendimento extends HttpServlet {    
    AtendimentoDAO adao = null;
    Atendimento atendimento = null;
    RequestDispatcher dispatcher = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        String acao = request.getParameter("acao");
        
        String idAtendimento = request.getParameter("idAtendimento");
        String mensagem = "";
        atendimento = new Atendimento();
        adao = new AtendimentoDAO();

        try {
            if (acao.equals("listar")) {
                ArrayList<Atendimento> atendimentos = new ArrayList<>();
                atendimentos = adao.getLista();
                dispatcher = getServletContext()
                        .getRequestDispatcher("/listarAtendimentos.jsp");

                request.setAttribute("atendimentos", atendimentos);
                dispatcher.forward(request, response);
            } else if (acao.equals("alterar")) {
                
                atendimento = adao.getCarregarPorId(Integer.parseInt(idAtendimento));
                if (atendimento.getIdAtendimento()> 0) {
                    dispatcher = getServletContext()
                            .getRequestDispatcher("/cadastrarAtendimento.jsp");
                    request.setAttribute("atendimento", atendimento);
                    dispatcher.forward(request, response);
                } else {
                    mensagem = "Atendimento não encontrado na base de dados!";
                }
            } else if (acao.equals("ativar")) {
                atendimento.setIdAtendimento(Integer.parseInt(idAtendimento));
                if (adao.ativar(atendimento)) {
                    mensagem = "Atendimento ativado com sucesso!";
                } else {
                    mensagem = "Falha ao ativar o atendimento.";
                }
            } else if (acao.equals("desativar")) {
                atendimento.setIdAtendimento(Integer.parseInt(idAtendimento));
                if (adao.desativar(atendimento)) {
                    mensagem = "Atendimento desativado com sucesso.";
                } else {
                    mensagem = "Falha ao desativar o atendimento.";
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
                + "location.href='gerenciarAtendimento?acao=listar';"
                + "</script>"
        );
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        String idAtendimento = request.getParameter("idAtendimento");
        String tipoAtendimento = Util.decode(request.getParameter("tipoAtendimento"));
        String tipoPagto = Util.decode(request.getParameter("tipoPagto"));
        String status = request.getParameter("status");
        String mensagem = "";
        HttpSession sessao = request.getSession();

        if (!idAtendimento.isEmpty()) {
            try {
                atendimento.setIdAtendimento(Integer.parseInt(idAtendimento));
            } catch (NumberFormatException e) {
                mensagem = "Error" + e.getMessage();
            }
        }
        
        if (tipoAtendimento.isEmpty() || tipoAtendimento.equals("")) {
            sessao.setAttribute("msg", "Por favor, informe o tipo de atendimento.");
            exibirMensagem(request, response);
        } else {
            atendimento.setTipoAtendimento(tipoAtendimento);
        }

        if (tipoPagto.isEmpty() || tipoPagto.equals("")) {
            sessao.setAttribute("msg", "Por favor, informe a forma de pagamento.");
            exibirMensagem(request, response);
        } else {
            atendimento.setTipoPagto(tipoPagto);
        }

        if (status.isEmpty() || status.equals("")) {
            sessao.setAttribute("msg", "Por favor, informe o status do delivery.");
            exibirMensagem(request, response);
        } else {
            atendimento.setStatus(Integer.parseInt(status));
        }
        
//        if (idUsuario.isEmpty() || idUsuario.equals("")) {
//            sessao.setAttribute("msg", "Por favor, informe o usuário associado.");
//            exibirMensagem(request, response);
//        } else {
//            Usuario usuario = new Usuario();
//            try {
//                usuario.setIdUsuario(Integer.parseInt(idUsuario));
//                delivery.setUsuario(usuario);
//            } catch (NumberFormatException e) {
//                mensagem = "Error" + e.getMessage();
//            }   
//        }

        try {
            if (adao.gravar(atendimento)) {
                mensagem = "Atendimento salvo na base de dados!";
            } else {
                mensagem = "Falha ao salvar o atendimento na base de dados!";
            }
        } catch (SQLException e) {
            mensagem = "Error: " + e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarAtendimento?acao=listar';"
                + "</script>"
        );
    }

    private void exibirMensagem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        dispatcher = getServletContext().
        getRequestDispatcher("/cadastrarAtendimento.jsp");
        dispatcher.forward(request, response);
    }
        

}