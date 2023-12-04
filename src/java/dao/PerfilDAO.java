package dao;

import factory.ConexaoFactory;
import model.Perfil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Menu;

public class PerfilDAO {
    // objetos globais da classe, utilizados em todos os métodos
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql = "";
    ArrayList<Perfil> perfis = new ArrayList<>();
    
    // o encapsulamento (public) é seguido pelo tipo de retorno e nome da função.
    // o erro é arremessado pois só será tratado no servlet.
    public ArrayList<Perfil> getLista() throws SQLException {
        
        sql = "SELECT idPerfil, nome, dataCadastro, status FROM perfil";
        
        // chamada do método estático conectar, comum a todos os métodos.
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        
        while (rs.next()) {            
            
            Perfil p = new Perfil();
            p.setIdPerfil(rs.getInt("idPerfil"));
            p.setNome(rs.getString("nome"));
            p.setDataCadastro(rs.getDate("DataCadastro"));
            p.setStatus(rs.getInt("status"));
            
            perfis.add(p);
        }
        
        ConexaoFactory.close(con);
        return perfis;
    }
    
    public Perfil getCarregarPorId(int idPerfil) throws SQLException{
        
        Perfil perfil = new Perfil();
        
        sql = "SELECT idPerfil, nome, dataCadastro, status FROM perfil WHERE idPerfil = ?";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idPerfil);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            perfil.setIdPerfil(rs.getInt("idPerfil"));
            perfil.setNome(rs.getString("nome"));
            perfil.setDataCadastro(rs.getDate("dataCadastro"));
            perfil.setStatus(rs.getInt("status"));
            perfil.setMenus(menusVinculadosPorPerfil(idPerfil));
            perfil.setNaoMenus(menusNaoVinculadosPorPerfil(idPerfil)); 
        }
        
        ConexaoFactory.close(con);
        return perfil;
        
    }
    
    public boolean gravar(Perfil p) throws SQLException {
        
        con = ConexaoFactory.conectar();
        
        if (p.getIdPerfil() == 0) {
            
            sql = "INSERT INTO perfil(nome, dataCadastro, status) VALUES (?, ?, ?)";
            ps = con.prepareStatement(sql);
            
            ps.setString(1, p.getNome());
            ps.setDate(2, new Date(p.getDataCadastro().getTime()));
            ps.setInt(3, p.getStatus());
            
        } else {
            
            sql = "UPDATE perfil SET nome = ?, dataCadastro = ?, status = ? WHERE idPerfil = ?";
            ps = con.prepareStatement(sql);
            
            ps.setString(1, p.getNome());
            ps.setDate(2, new Date(p.getDataCadastro().getTime()));
            ps.setInt(3, p.getStatus());
            ps.setInt(4, p.getIdPerfil());
        }
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }
    
    public boolean ativar(Perfil p) throws SQLException {
        
        sql = "UPDATE perfil SET status = 1 WHERE idPerfil = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, p.getIdPerfil());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
        
    }

    public boolean desativar(Perfil p) throws SQLException {
        
        sql = "UPDATE perfil SET status = 0 WHERE idPerfil = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, p.getIdPerfil());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
        
    }
    
    // específicos da associação muitos para muitos.
    public ArrayList<Menu> menusVinculadosPorPerfil(int idPerfil)
    throws SQLException{
        
        ArrayList<Menu> menus = new ArrayList<>();
        sql = "SELECT m.idMenu, m.nome, m.link, m.exibir, m.status "+
              "FROM perfil_menu as mp, menu as m  " +
              "WHERE mp.idMenu = m.idMenu " +
              "AND mp.idPerfil = ?"; 
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idPerfil);
        rs = ps.executeQuery();
        
        while(rs.next()){
            Menu m = new Menu();
            
            m.setIdMenu(rs.getInt("m.idMenu"));
            m.setNome(rs.getString("m.nome"));
            m.setLink(rs.getString("m.link"));
            m.setExibir(rs.getInt("m.exibir"));
            m.setStatus(rs.getInt("m.status"));
            
            menus.add(m);
        }
        ConexaoFactory.close(con);
        
        return menus;  
    }
    
    public ArrayList<Menu> menusNaoVinculadosPorPerfil(int idPerfil)
    throws SQLException{
        
        ArrayList<Menu> menus = new ArrayList<>();
        sql = "SELECT m.idMenu, m.nome, m.link, m.exibir, m.status " + 
              "FROM menu as m "+
              "WHERE m.idMenu " + 
              "NOT IN(SELECT mp.idMenu FROM perfil_menu as mp WHERE mp.idPerfil = ?)";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idPerfil);
        rs = ps.executeQuery();
        
        while(rs.next()){
            Menu m = new Menu();
            
            m.setIdMenu(rs.getInt("m.idMenu"));
            m.setNome(rs.getString("m.nome"));
            m.setLink(rs.getString("m.link"));
            m.setExibir(rs.getInt("m.exibir"));
            m.setStatus(rs.getInt("m.status"));
            
            menus.add(m);
        }
        ConexaoFactory.close(con);
        return menus;
    }
    
    public boolean vincular(int idMenu, int idPerfil)
    throws SQLException{
        
        sql = "INSERT INTO perfil_menu (idMenu, idPerfil) "+
              "VALUES (?, ?)";  
                
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idMenu);
        ps.setInt(2, idPerfil);
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }
    
    public boolean desvincular(int idMenu ,int idPerfil)
    throws SQLException{
        
        sql = "DELETE FROM perfil_menu " + 
                "WHERE idMenu = ? AND idPerfil = ? ";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idMenu);
        ps.setInt(2, idPerfil);
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }
    
}
