package org.dabravo.java.jdbc.repositorio;

import org.dabravo.java.jdbc.modelo.Usuario;
import org.dabravo.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio implements Repositorio<Usuario> {

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();

        try {
            Statement stmt = ConexionBaseDatos.getConnection().createStatement();
            ResultSet query = stmt.executeQuery("SELECT * FROM usuarios");

            while(query.next()){
                Usuario u = crearUsuario(query);
                usuarios.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    @Override
    public Usuario porId(Long id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        Usuario usuario = null;

        try{
            PreparedStatement stmt = ConexionBaseDatos.getConnection().prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                usuario = crearUsuario(rs);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return usuario;
    }

    @Override
    public void crear(Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, password, email) values(?,?,?)";
        try{
            PreparedStatement stmt = ConexionBaseDatos.getConnection().prepareStatement(sql);
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getEmail());

            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql;
        try{
            sql = "UPDATE usuarios SET username = ?, password = ?, email = ? where id = ?";
            PreparedStatement stmt = ConexionBaseDatos.getConnection().prepareStatement(sql);
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getEmail());
            stmt.setLong(4, usuario.getId());

            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void eliminar(long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try{
            PreparedStatement stmt = ConexionBaseDatos.getConnection().prepareStatement(sql);
            stmt.setLong(1, id);

            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private Usuario crearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setEmail(rs.getString("email"));

        return u;
    }
}
