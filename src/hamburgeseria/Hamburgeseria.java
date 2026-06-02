/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hamburgeseria;

import java.sql.Connection;
import config.ConexionDB;

/**
 *
 * @author franco
 */
public class Hamburgeseria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Intentando conectar con la hamburguesería...");
        
        // Llamamos al método de nuestra clase
        Connection cn = ConexionDB.getConexion();
        
        if (cn != null) {
            System.out.println("¡CONEXIÓN EXITOSA! Java ya puede hablar con MySQL.");
            
            try {
                // Siempre es buena práctica cerrar la conexión de prueba
                cn.close(); 
            } catch (Exception e) {
                System.out.println("Error al cerrar: " + e.getMessage());
            }
        } else {
            System.out.println("La conexión falló. Revisa los errores en la consola.");
        }
    }
    
}
