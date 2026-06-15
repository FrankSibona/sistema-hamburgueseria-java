package Models.Dao;

import Models.Connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO {

    // Crear una orden (checkout principal)
    public int createOrder(String customerName,
                        String customerLastname,
                        String paymentMethod,
                        String deliveryMethod,
                        String address,
                        double total) {

        String sql = """
                INSERT INTO orders
                (customer_name, customer_lastname, payment_method, delivery_method, address, total)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {

            stmt.setString(1, customerName);
            stmt.setString(2, customerLastname);
            stmt.setString(3, paymentMethod);
            stmt.setString(4, deliveryMethod);
            stmt.setString(5, address);
            stmt.setDouble(6, total);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error creating order: " + e.getMessage());
        }

        return -1;
    }

    // Agregar item al detalle de la orden
    public boolean addOrderDetail(int orderId,
                                int productId,
                                int quantity,
                                double price) {

        String sql = """
                INSERT INTO order_details
                (order_id, product_id, quantity, price)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding order detail: " + e.getMessage());
            return false;
        }
    }
}