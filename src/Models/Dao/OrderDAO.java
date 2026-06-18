package Models.Dao;

import Models.Connection.DBConnection;
import Models.Entities.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // Crear una nueva orden (checkout confirmado)
    public boolean create(Order order) {

        String sql = """
                INSERT INTO orders
                (customer_name, customer_lastname, phone,
                payment_method, delivery_method,
                address, total)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, order.getCustomerName());
            stmt.setString(2, order.getCustomerLastname());
            stmt.setString(3, order.getPhone());
            stmt.setString(4, order.getPaymentMethod());
            stmt.setString(5, order.getDeliveryMethod());
            stmt.setString(6, order.getAddress());
            stmt.setDouble(7, order.getTotal());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error creating order: " + e.getMessage());
            return false;
        }
    }

    // Obtener todas las órdenes (para informes)
    public List<Order> findAll() {

        List<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM orders ORDER BY created_at DESC";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_lastname"),
                        rs.getString("phone"),
                        rs.getString("payment_method"),
                        rs.getString("delivery_method"),
                        rs.getString("address"),
                        rs.getString("house"),
                        rs.getString("description"),
                        rs.getString("products"),
                        rs.getDouble("total"),
                        rs.getTimestamp("created_at")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving orders: " + e.getMessage());
        }

        return orders;
    }

    // Obtener una orden por ID (para detalles)
    public Order findById(int id) {

        String sql = "SELECT * FROM orders WHERE id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Order(
                            rs.getInt("id"),
                            rs.getString("customer_name"),
                            rs.getString("customer_lastname"),
                            rs.getString("phone"),
                            rs.getString("payment_method"),
                            rs.getString("delivery_method"),
                            rs.getString("address"),
                            rs.getString("house"),
                            rs.getString("description"),
                            rs.getString("products"),
                            rs.getDouble("total"),
                            rs.getTimestamp("created_at")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving order by ID: " + e.getMessage());
        }

        return null; 
    }
}