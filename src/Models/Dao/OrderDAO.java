package Models.Dao;

import Models.Entities.Product;
import Models.Connection.DBConnection; 
import java.sql.*;
import java.util.List;

public class OrderDAO {

    public boolean registrarVenta(String nombre, String apellido, String telefono, 
                                  String pago, String entrega, String direccion, 
                                  double total, List<Product> carrito) {
                                      
        Connection con = null;
        PreparedStatement psPedido = null;
        PreparedStatement psDetalle = null;
        PreparedStatement psStock = null;
        ResultSet rs = null;
        boolean exito = false;

        try {
            con = DBConnection.getConnection(); 
            con.setAutoCommit(false); 
           
            String sqlPedido = "INSERT INTO orders (customer_name, customer_lastname, phone, payment_method, delivery_method, address, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
            psPedido = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            psPedido.setString(1, nombre);
            psPedido.setString(2, apellido);
            psPedido.setString(3, telefono);
            psPedido.setString(4, pago);
            psPedido.setString(5, entrega);
            psPedido.setString(6, direccion);
            psPedido.setDouble(7, total);
            psPedido.executeUpdate();


            rs = psPedido.getGeneratedKeys();
            int idPedidoGenerado = 0;
            if (rs.next()) {
                idPedidoGenerado = rs.getInt(1);
            }

           
            String sqlDetalle = "INSERT INTO order_details (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
            String sqlStock = "UPDATE products SET stock = stock - ? WHERE id = ?";
            
            psDetalle = con.prepareStatement(sqlDetalle);
            psStock = con.prepareStatement(sqlStock);

            for (Product item : carrito) {

                psDetalle.setInt(1, idPedidoGenerado);
                psDetalle.setInt(2, item.getId()); 
                
                psDetalle.setInt(3, item.getCantidad()); 
                psDetalle.setDouble(4, item.getPrice()); 
                psDetalle.executeUpdate();


                psStock.setInt(1, item.getCantidad());
                psStock.setInt(2, item.getId());
                psStock.executeUpdate();
            }

            con.commit(); 
            exito = true;

        } catch (SQLException e) {
            System.out.println("Error en la transacción: " + e.getMessage());
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                System.out.println("Error haciendo rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (con != null) con.setAutoCommit(true);
                if (rs != null) rs.close();
                if (psPedido != null) psPedido.close();
                if (psDetalle != null) psDetalle.close();
                if (psStock != null) psStock.close();
            } catch (SQLException e) {
                System.out.println("Error cerrando conexiones.");
            }
        }
        return exito;
    }
}
