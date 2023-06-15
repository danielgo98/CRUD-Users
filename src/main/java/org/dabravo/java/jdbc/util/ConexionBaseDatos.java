package org.dabravo.java.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private static String url = "jdbc:mysql://localhost:3306/proyecto_usuarios";
    private static String username = "root";
    private static String password = "root";
    private static Connection connection;

    public static Connection getConnection(){
        if(connection == null){
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
