package org.dabravo.java.jdbc;

import org.dabravo.java.jdbc.modelo.Usuario;
import org.dabravo.java.jdbc.repositorio.Repositorio;
import org.dabravo.java.jdbc.repositorio.UsuarioRepositorio;
import org.dabravo.java.jdbc.util.ConexionBaseDatos;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MantenedorUsuariosMain {
    public static void main(String[] args) {
        try (Connection db = ConexionBaseDatos.getConnection()){
            Repositorio<Usuario> repositorio = new UsuarioRepositorio();
            int opcionMenu = 0;

            do {
                Map<String, Integer> operaciones = new HashMap<>();
                operaciones.put("Actualizar", 1);
                operaciones.put("Eliminar", 2);
                operaciones.put("Agregar", 3);
                operaciones.put("Listar", 4);
                operaciones.put("Salir", 5);

                Object[] opArray = operaciones.keySet().toArray();
                Object opcion = JOptionPane.showInputDialog(null,
                        "Seleccione una opcion",
                        "Mantenedor de usuarios",
                        JOptionPane.INFORMATION_MESSAGE, null, opArray, opArray[0]);

                if (opcion == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una operación");
                } else {
                    long id;
                    String username;
                    String password;
                    String email;
                    opcionMenu = operaciones.get(opcion.toString());

                    switch (opcionMenu) {
                        case 1 -> {
                            id = Long.valueOf(JOptionPane.showInputDialog(null,
                                    "Introduce el Id del usuario para actualizar"));

                            Usuario usuario = repositorio.porId(id);
                            if(usuario != null){
                                username = JOptionPane.showInputDialog(null,
                                        "Introduce el nuevo username del usuario: " + usuario.getUsername());
                                password = JOptionPane.showInputDialog(null,
                                        "Introduce la password del usuario: " + usuario.getPassword());
                                email = JOptionPane.showInputDialog(null,
                                        "Introduce el email del usuario: " + usuario.getEmail());

                                usuario.setUsername(username);
                                usuario.setPassword(password);
                                usuario.setEmail(email);

                                repositorio.actualizar(usuario);
                                JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente");
                            }else{
                                JOptionPane.showMessageDialog(null,
                                        "No existe ningun usuario con el id introducido");
                            }
                        }
                        case 2 -> {
                            id = Long.valueOf(JOptionPane.showInputDialog(null,
                                    "Introduce el id del usuario para eliminar"));
                            repositorio.eliminar(id);
                            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
                        }
                        case 3 -> {
                            username = JOptionPane.showInputDialog(null,
                                    "Introduce el nombre de usuario a crear");
                            password = JOptionPane.showInputDialog(null,
                                    "Introduce la passord de usuario a crear");
                            email = JOptionPane.showInputDialog(null,
                                    "Introduce el email de usuario a crear");

                            Usuario usuario = new Usuario();
                            usuario.setUsername(username);
                            usuario.setPassword(password);
                            usuario.setEmail(email);

                            repositorio.crear(usuario);
                            JOptionPane.showMessageDialog(null,
                                    "Usuario creado correctamente");
                        }
                        case 4 -> {
                            repositorio.listar().forEach(System.out::println);
                        }
                    }
                }
            } while (opcionMenu != 5);
            JOptionPane.showMessageDialog(null, "Hasta luego!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
