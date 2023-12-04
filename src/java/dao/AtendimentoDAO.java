package dao;

import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Atendimento;

public class AtendimentoDAO {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql = "";
    ArrayList<Atendimento> atendimentos = new ArrayList<>();
    public ArrayList<Atendimento> getLista() throws SQLException {
        sql = "SELECT idAtendimento, tipoAtendimento, tipoPagto, status " +
              "FROM atendimento";

        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Atendimento atendimento = new Atendimento();

            atendimento.setIdAtendimento(rs.getInt("idAtendimento"));
            atendimento.setTipoAtendimento(rs.getString("tipoAtendimento"));
            atendimento.setTipoPagto(rs.getString("tipoPagto"));
            atendimento.setStatus(rs.getInt("status"));

            atendimentos.add(atendimento);
        }

        ConexaoFactory.close(con);
        return atendimentos;
    }

    public boolean gravar(Atendimento atendimento) throws SQLException {
        con = ConexaoFactory.conectar();

        if (atendimento.getIdAtendimento()== 0) {
            sql = "INSERT INTO atendimento(tipoAtendimento, tipoPagto, status) " +
                  "VALUES(?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, atendimento.getTipoAtendimento());
            ps.setString(2, atendimento.getTipoPagto());
            ps.setInt(3, atendimento.getStatus());
        } else {
            sql = "UPDATE atendimento SET tipoAtendimento = ?, tipoPagto = ?, status = ? " +
                    "WHERE idAtendimento = ?";
            ps = con.prepareStatement(sql);
            ps = con.prepareStatement(sql);
            ps.setString(1, atendimento.getTipoAtendimento());
            ps.setString(2, atendimento.getTipoPagto());
            ps.setInt(3, atendimento.getStatus());
            ps.setInt(4, atendimento.getIdAtendimento());
        }
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }

    public Atendimento getCarregarPorId(int idAtendimento) throws SQLException {
        Atendimento atendimento = new Atendimento();      

        sql = "SELECT idAtendimento, tipoAtendimento, tipoPagto, status " +
              "FROM atendimento WHERE idAtendimento = ?";

        con = ConexaoFactory.conectar();

        ps = con.prepareStatement(sql);
        ps.setInt(1, idAtendimento);
        rs = ps.executeQuery();

        if (rs.next()) {
            atendimento.setIdAtendimento(rs.getInt("idAtendimento"));
            atendimento.setTipoAtendimento(rs.getString("tipoAtendimento"));
            atendimento.setTipoPagto(rs.getString("tipoPagto"));
            atendimento.setStatus(rs.getInt("status"));
        }

        ConexaoFactory.close(con);
        return atendimento;
    }

    public boolean ativar(Atendimento atendimento) throws SQLException {
        sql = "UPDATE atendimento SET status = 1 WHERE idAtendimento = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, atendimento.getIdAtendimento());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }

    public boolean desativar(Atendimento atendimento) throws SQLException {
        sql = "UPDATE atendimento SET status = 0 WHERE idAtendimento = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, atendimento.getIdAtendimento());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }
    
//    public ArrayList<Produto> getCarregarPorIdVenda(int idVenda) throws SQLException {
//        Produto produto = new Produto();      
//
//        sql = "SELECT p.idProduto, p.nome, p.descricao, p.preco, p.status " +
//              "FROM venda_produto vp inner join produto p on p.idProduto = vp.idProduto WHERE idVenda = ?";
//
//        con = ConexaoFactory.conectar();
//
//        ps = con.prepareStatement(sql);
//        ps.setInt(1, idVenda);
//        rs = ps.executeQuery();
//
//        ArrayList<Produto> produtos = new ArrayList<>();
//        if (rs.next()) {
//            produto.setIdProduto(rs.getInt("idProduto"));
//            produto.setNome(rs.getString("nome"));
//            produto.setDescricao(rs.getString("descricao"));
//            produto.setPreco(rs.getDouble("preco"));
//            produto.setStatus(rs.getInt("status"));
//            produtos.add(produto);
//        }
//
//        ConexaoFactory.close(con);
//        return produtos;
//    }
}
