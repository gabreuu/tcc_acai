package dao;

import com.mysql.cj.xdevapi.Statement;
import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Venda;
import model.Produto;
// import model.Delivery;

public class VendaDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql = "";
    
    public ArrayList<Venda> getLista() throws SQLException{
        ArrayList<Venda> vendas = new ArrayList<>();        
        
        sql = "SELECT c.idCliente, a.idAtendimento, u.idUsuario, v.idVenda, " +
                "v.dataVenda, v.precoTotal, v.status FROM venda v INNER JOIN " +
                "cliente c ON c.idCliente = v.idCliente INNER JOIN " +
                "atendimento a ON a.idAtendimento = v.idAtendimento INNER JOIN " +
                "usuario u ON u.idUsuario = v.idUsuario";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        
        while (rs.next()) {            
            Venda venda = new Venda();
            ClienteDAO cdao = new ClienteDAO();
            AtendimentoDAO adao = new AtendimentoDAO();
            UsuarioDAO udao = new UsuarioDAO();
            //ProdutoDAO pdao = new ProdutoDAO();
            
            venda.setIdVenda(rs.getInt("v.idVenda"));
            venda.setDataVenda(rs.getDate("v.dataVenda"));
            venda.setPrecoTotal(rs.getDouble("v.precoTotal"));
            venda.setStatus(rs.getInt("v.status"));
            
            // associação muitos para um entre venda e cliente respectivamente
            venda.setCliente(cdao.getCarregarPorId(rs.getInt("c.idCliente")));
            // associação muitos para um entre entre venda e atendimento respectivamente
            venda.setAtendimento(adao.getCarregarPorId(rs.getInt("a.idAtendimento")));
            // associação muitos para um entre entre venda e usuario respectivamente
            venda.setUsuario(udao.getCarregarPorId(rs.getInt("u.idUsuario")));
            // associação MUITOS para MUITOS entre venda e produto respectivamente
//            venda.setProdutos(pdao.getCarregarPorIdVenda(venda.getIdVenda()));
            
            vendas.add(venda);
        }
        ConexaoFactory.close(con);
        
        return vendas;
    }
    
    public Venda getCarregarPorId(int idVenda) throws SQLException{                        
        sql = "SELECT c.idCliente, a.idAtendimento, u.idUsuario, v.idVenda, " +
                "v.dataVenda, v.precoTotal, v.status FROM venda v INNER JOIN " +
                "cliente c ON c.idCliente = v.idCliente INNER JOIN " +
                "atendimento a ON a.idAtendimento = v.idAtendimento INNER JOIN " +
                "usuario u ON u.idUsuario = v.idUsuario " +
                "WHERE v.idVenda = ?";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idVenda);
        rs = ps.executeQuery();
        Venda venda = new Venda();
        
        if (rs.next()) {            
            ClienteDAO cdao = new ClienteDAO();
            AtendimentoDAO adao = new AtendimentoDAO();
            UsuarioDAO udao = new UsuarioDAO();
            ProdutoDAO pdao = new ProdutoDAO();
            
            venda.setIdVenda(rs.getInt("v.idVenda"));
            venda.setDataVenda(rs.getDate("v.dataVenda"));
            venda.setPrecoTotal(rs.getDouble("v.precoTotal"));
            venda.setStatus(rs.getInt("v.status"));
            
            // associação muitos para um entre venda e cliente respectivamente
            venda.setCliente(cdao.getCarregarPorId(rs.getInt("c.idCliente")));
            // associação muitos para um entre entre venda e atendimento respectivamente
            venda.setAtendimento(adao.getCarregarPorId(rs.getInt("a.idAtendimento")));
            // associação muitos para um entre entre venda e usuario respectivamente
            venda.setUsuario(udao.getCarregarPorId(rs.getInt("u.idUsuario")));
            venda.setProdutos(pdao.getCarregarPorIdVenda(venda.getIdVenda()));
            
        }
        ConexaoFactory.close(con);
        
        return venda;
    }
    
    public boolean gravar(Venda venda)throws SQLException{ 
        con = ConexaoFactory.conectar();

        int lastInsertedId = 0;

        //verifica se a venda é nova ou ediçõa
        if (venda.getIdVenda() == 0) {
            sql = "INSERT INTO venda (dataVenda, precoTotal, status, idCliente, idAtendimento, idUsuario) "+
                  "VALUES (?, ?, ?, ?, ?, ?)";
            
            ps = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setDate(1, new Date(venda.getDataVenda().getTime()));
            ps.setDouble(2, venda.getPrecoTotal());
            ps.setInt(3, venda.getStatus());
            ps.setInt(4, venda.getCliente().getIdCliente());
            ps.setInt(5, venda.getAtendimento().getIdAtendimento());
            ps.setInt(6, venda.getUsuario().getIdUsuario());
            ps.executeUpdate();
            //busca o ultimo id inserido caso seja novo
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
            {
                lastInsertedId = rs.getInt(1);
            }
        } else {            
            sql = "UPDATE venda SET dataVenda = ?, precoTotal = ?, " +
                  "status = ?, idCliente = ?, idAtendimento = ?, idUsuario = ? " +
                  "WHERE idVenda = ?";
            
            ps = con.prepareStatement(sql);
            
            ps.setDate(1, new Date(venda.getDataVenda().getTime()));
            ps.setDouble(2, venda.getPrecoTotal());
            ps.setInt(3, venda.getStatus());            
            ps.setInt(4, venda.getCliente().getIdCliente());
            ps.setInt(5, venda.getAtendimento().getIdAtendimento());
            ps.setInt(6, venda.getUsuario().getIdUsuario());
            ps.setInt(7, venda.getIdVenda());
            ps.executeUpdate();
            
            sql = "DELETE FROM venda_produto WHERE idVenda = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1,venda.getIdVenda());
                ps.executeUpdate();

        }
        
        for (Produto produto : venda.getProdutos()) {
            
            sql = "INSERT INTO venda_produto (idVenda, idProduto) VALUES (?, ?)";
            ps = con.prepareStatement(sql);
            //verifica se é novo e insere o id recuperado ou se edição insere o id da edição
            if(lastInsertedId != 0 ){
               ps.setInt(1,lastInsertedId); 
            }else{
               ps.setInt(1,venda.getIdVenda()); 
            }
            ps.setInt(2, produto.getIdProduto());
            ps.executeUpdate();
        }

        ConexaoFactory.close(con);
        
        return true;
    }
    
    public boolean ativar(Venda venda) throws SQLException {
        sql = "UPDATE venda SET status = 1 WHERE idVenda = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, venda.getIdVenda());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        
        return true;
    }
    
    public boolean desativar(Venda venda) throws SQLException {
        sql = "UPDATE venda SET status = 0 WHERE idVenda = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, venda.getIdVenda());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        
        return true;
    }
    
    // específicos da associação muitos para muitos.
//    public ArrayList<Produto> produtosVinculadosPorVenda(int idVenda)
//    throws SQLException{
//        
//        ArrayList<Produto> produtos = new ArrayList<>();
//        sql = "SELECT p.idProduto, p.nome, p.descricao, p.preco, p.status "+
//              "FROM venda_produto as pv, produto as p  " +
//              "WHERE pv.idProduto = p.idProduto " +
//              "AND pv.idVenda = ?"; 
//
//        con = ConexaoFactory.conectar();
//        ps = con.prepareStatement(sql);
//        ps.setInt(1, idVenda);
//        rs = ps.executeQuery();
//        
//        while(rs.next()){
//            Produto p = new Produto();
//            
//            p.setIdProduto(rs.getInt("p.idProduto"));
//            p.setNome(rs.getString("p.nome"));
//            p.setDescricao(rs.getString("p.descricao"));
//            p.setPreco(rs.getDouble("p.preco"));
//            p.setStatus(rs.getInt("p.status"));
//            
//            produtos.add(p);
//        }
//        ConexaoFactory.close(con);
//        
//        return produtos;  
//    }
//    
//    public ArrayList<Produto> produtosNaoVinculadosPorVenda(int idVenda)
//    throws SQLException{
//        
//        ArrayList<Produto> produtos = new ArrayList<>();        
//        sql = "SELECT p.idProduto, p.nome, p.descricao, p.preco, p.status "+
//              "FROM produto as p  " +
//              "WHERE p.idProduto " +
//              "NOT IN(SELECT pv.idProduto FROM venda_produto as pv WHERE pv.idVenda = ?)";
//        
//        con = ConexaoFactory.conectar();
//        ps = con.prepareStatement(sql);
//        ps.setInt(1, idVenda);
//        rs = ps.executeQuery();
//        
//        while(rs.next()){
//            Produto p = new Produto();
//            
//            p.setIdProduto(rs.getInt("p.idProduto"));
//            p.setNome(rs.getString("p.nome"));
//            p.setDescricao(rs.getString("p.descricao"));
//            p.setPreco(rs.getDouble("p.preco"));
//            p.setStatus(rs.getInt("p.status"));
//
//            produtos.add(p);
//        }
//        ConexaoFactory.close(con);
//        
//        return produtos;
//    }
//    
//    public boolean vincular(int idProduto, int idVenda)
//    throws SQLException{         
//        sql = "INSERT INTO venda_produto (idProduto, idVenda) "+
//              "VALUES (?, ?)";  
//                
//        con = ConexaoFactory.conectar();
//        ps = con.prepareStatement(sql);
//        ps.setInt(1, idProduto);
//        ps.setInt(2, idVenda);
//        ps.executeUpdate();
//        ConexaoFactory.close(con);
//        return true;
//    }
//    
//    public boolean desvincular(int idProduto ,int idVenda)
//    throws SQLException{
//        sql = "DELETE FROM venda_produto " + 
//                "WHERE idProduto = ? AND idVenda = ? ";
//
//        con = ConexaoFactory.conectar();
//        ps = con.prepareStatement(sql);
//        ps.setInt(1, idProduto);
//        ps.setInt(2, idVenda);
//        ps.executeUpdate();
//        ConexaoFactory.close(con);
//        return true;
//    }
}
