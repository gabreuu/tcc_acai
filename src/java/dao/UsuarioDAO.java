package dao;

import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Usuario;
import model.Perfil;

public class UsuarioDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql = "";
    
    public ArrayList<Usuario> getLista() throws SQLException{

        ArrayList<Usuario> usuarios = new ArrayList<>();
        sql = "SELECT p.idPerfil, u.idUsuario, u.nome, u.login, "+
                "u.senha, u.status, u.idPerfil "+
                "FROM perfil p INNER JOIN usuario u "+
                " ON p.idPerfil = u.idPerfil";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        
        while (rs.next()) {            
            Usuario usuario = new Usuario();
            PerfilDAO pdao = new PerfilDAO();
            
            usuario.setIdUsuario(rs.getInt("u.idUsuario"));
            usuario.setLogin(rs.getString("u.login"));
            usuario.setNome(rs.getString("u.nome"));
            usuario.setSenha(rs.getString("u.senha"));
            usuario.setStatus(rs.getInt("u.status"));
            
            // associação entre usuário e perfil
            usuario.setPerfil(pdao.getCarregarPorId(rs.getInt("p.idPerfil")));
            
            usuarios.add(usuario);
        }
        
        ConexaoFactory.close(con);
        return usuarios;
    }
    
    public Usuario getCarregarPorId(int idUsuario) throws SQLException{
        sql = "SELECT p.idPerfil, u.idUsuario, u.nome, u.login, "+
                "u.senha, u.status, u.idPerfil "+
                "FROM perfil p INNER JOIN usuario u "+
                "ON p.idPerfil = u.idPerfil WHERE u.idUsuario = ?";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idUsuario);
        rs = ps.executeQuery();
        Usuario usuario = new Usuario();
        
        if (rs.next()) {            
            
            PerfilDAO pdao = new PerfilDAO();
            
            usuario.setIdUsuario(rs.getInt("u.idUsuario"));
            usuario.setLogin(rs.getString("u.login"));
            usuario.setNome(rs.getString("u.nome"));
            usuario.setSenha(rs.getString("u.senha"));
            usuario.setStatus(rs.getInt("u.status"));
            
            // associação entre os objetos da classe Usuário e Perfil
            usuario.setPerfil(pdao.getCarregarPorId(rs.getInt("p.idPerfil")));
        }
        ConexaoFactory.close(con);
        return usuario;
    }
    
    public boolean gravar(Usuario u)throws SQLException{
        
        con = ConexaoFactory.conectar();
        
        if (u.getIdUsuario() == 0) {
            sql = "INSERT INTO usuario (nome,login,senha,status,idPerfil) "+
                  "VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNome());
            ps.setString(2, u.getLogin());
            ps.setString(3, u.getSenha());
            ps.setInt(4, u.getStatus());
            ps.setInt(5, u.getPerfil().getIdPerfil());
            
        } else {
            sql = "UPDATE usuario SET nome = ?, login = ?, senha = ?, " +
                  "status = ?,idPerfil = ? WHERE idUsuario = ? ";
            
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNome());
            ps.setString(2, u.getLogin());
            ps.setString(3, u.getSenha());
            ps.setInt(4, u.getStatus());
            ps.setInt(5, u.getPerfil().getIdPerfil());
            ps.setInt(6, u.getIdUsuario());
            
        }
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }
    
    public boolean desativar(Usuario u) throws SQLException {
        sql = "UPDATE usuario SET status = 0 WHERE idUsuario = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, u.getIdUsuario());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }
    
    public boolean ativar(Usuario u) throws SQLException {
        sql = "UPDATE usuario SET status = 1 WHERE idUsuario = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, u.getIdUsuario());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }
    
    public Usuario getRecuperarUsuario(String login)
    throws SQLException{
        Usuario u = new Usuario();
        sql = "SELECT u.idUsuario, u.nome, u.login, u.senha, u.status, u.idPerfil " + 
              "FROM usuario u WHERE u.login = ?";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setString(1, login);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            PerfilDAO pdao = new PerfilDAO();
                    
            u.setIdUsuario(rs.getInt("u.idUsuario"));
            u.setLogin(rs.getString("u.login"));
            u.setNome(rs.getString("u.nome"));
            u.setPerfil(pdao.getCarregarPorId(rs.getInt("u.idPerfil")));
            u.setSenha(rs.getString("u.senha"));
            u.setStatus(rs.getInt("u.status"));
        }
        ConexaoFactory.close(con);
        
        return u; 
    }   
}
