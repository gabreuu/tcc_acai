package dao;

import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Produto;

public class ProdutoDAO {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql = "";
    ArrayList<Produto> produtos = new ArrayList<>();
    public ArrayList<Produto> getLista() throws SQLException {
        sql = "SELECT idProduto, nome, descricao, preco, status " +
              "FROM produto";

        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Produto produto = new Produto();

            produto.setIdProduto(rs.getInt("idProduto"));
            produto.setNome(rs.getString("nome"));
            produto.setDescricao(rs.getString("descricao"));
            produto.setPreco(rs.getDouble("preco"));
            produto.setStatus(rs.getInt("status"));

            produtos.add(produto);
        }

        ConexaoFactory.close(con);
        return produtos;
    }

    public boolean gravar(Produto produto) throws SQLException {
        con = ConexaoFactory.conectar();

        if (produto.getIdProduto()== 0) {
            sql = "INSERT INTO produto(nome, descricao, preco, status) " +
                  "VALUES(?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getPreco());
            ps.setInt(4, produto.getStatus());

        } else {
            sql = "UPDATE produto SET nome = ?, descricao = ?, preco = ?, status = ? " +
                    "WHERE idProduto = ?";
            ps = con.prepareStatement(sql);
            ps = con.prepareStatement(sql);
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getPreco());
            ps.setInt(4, produto.getStatus());
            ps.setInt(5, produto.getIdProduto());
        }
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }

    public Produto getCarregarPorId(int idProduto) throws SQLException {
        Produto produto = new Produto();      

        sql = "SELECT idProduto, nome, descricao, preco, status " +
              "FROM produto WHERE idProduto = ?";

        con = ConexaoFactory.conectar();

        ps = con.prepareStatement(sql);
        ps.setInt(1, idProduto);
        rs = ps.executeQuery();

        if (rs.next()) {
            produto.setIdProduto(rs.getInt("idProduto"));
            produto.setNome(rs.getString("nome"));
            produto.setDescricao(rs.getString("descricao"));
            produto.setPreco(rs.getDouble("preco"));
            produto.setStatus(rs.getInt("status"));
        }

        ConexaoFactory.close(con);
        return produto;
    }

    public boolean ativar(Produto produto) throws SQLException {
        sql = "UPDATE produto SET status = 1 WHERE idProduto = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, produto.getIdProduto());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }

    public boolean desativar(Produto produto) throws SQLException {
        sql = "UPDATE produto SET status = 0 WHERE idProduto = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, produto.getIdProduto());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }
    
    public ArrayList<Produto> getCarregarPorIdVenda(int idVenda) throws SQLException {
        Produto produto = new Produto();      

        sql = "SELECT p.idProduto, p.nome, p.descricao, p.preco, p.status " +
              "FROM venda_produto vp inner join produto p on p.idProduto = vp.idProduto WHERE idVenda = ?";

        con = ConexaoFactory.conectar();

        ps = con.prepareStatement(sql);
        ps.setInt(1, idVenda);
        rs = ps.executeQuery();

        ArrayList<Produto> produtos = new ArrayList<>();
        if (rs.next()) {
            produto.setIdProduto(rs.getInt("idProduto"));
            produto.setNome(rs.getString("nome"));
            produto.setDescricao(rs.getString("descricao"));
            produto.setPreco(rs.getDouble("preco"));
            produto.setStatus(rs.getInt("status"));
            produtos.add(produto);
        }

        ConexaoFactory.close(con);
        return produtos;
    }
}
