/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author franco
 */
public class ConexionDB {
    // Parámetros de la conexión
    private static final String URL = "jdbc:mysql://localhost:3306/hamburgueseria";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // La contraseña que configuramos en tu MySQL
    
    /**
     * Método estático para obtener la conexión a la base de datos
     * @return Connection objeto de conexión listo para usar
     */
    public static Connection getConexion() {
        Connection conexion = null;
        try {
            // 1. Registrar el driver (opcional en versiones nuevas, pero buena práctica)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Establecer el canal de conexión
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el Driver de MySQL. " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error: No se pudo conectar a la base de datos. " + e.getMessage());
        }
        return conexion;
    }
}
