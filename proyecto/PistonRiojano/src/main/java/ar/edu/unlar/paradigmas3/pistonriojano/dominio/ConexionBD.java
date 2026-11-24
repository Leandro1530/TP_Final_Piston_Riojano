/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.dominio;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Leandro
 */
public class ConexionBD {

    private static final String ARCHIVO = "/db.properties";
    private static final String CLAVE_URL = "db.url";
    private static final String CLAVE_USUARIO = "db.user";
    private static final String CLAVE_PASSWORD = "db.password";

    private ConexionBD() {
    }

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try (InputStream in = ConexionBD.class.getResourceAsStream(ARCHIVO)) {
            if (in == null) {
                throw new SQLException("No se encontro " + ARCHIVO + " en el classpath.");
            }
            props.load(in);
        } catch (IOException e) {
            throw new SQLException("No se pudo leer " + ARCHIVO + ".", e);
        }
        String url = leer(props, CLAVE_URL);
        String usuario = leer(props, CLAVE_USUARIO);
        String password = leer(props, CLAVE_PASSWORD);
        return DriverManager.getConnection(url, usuario, password);
    }

    public static void cerrar(Connection cn) {
        if (cn != null) {
            try {
                cn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexion: " + e.getMessage());
            }
        }
    }

    private static String leer(Properties props, String clave) throws SQLException {
        String valor = props.getProperty(clave);
        if (valor == null || valor.trim().isEmpty()) {
            throw new SQLException("Falta la propiedad '" + clave + "' en " + ARCHIVO + ".");
        }
        return valor.trim();
    }
}
