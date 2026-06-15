package Models.Dao;

import Models.Connection.DBConnection;
import Models.Entities.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAO {

    // Insertar items del pedido
    public boolean create(int orderId, List<OrderDetail> items) {

        String sql = """
                INSERT INTO order_details 
                (order_id, product_id, quantity, price)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                for (OrderDetail item : items) {
                    stmt.setInt(1, orderId);
                    stmt.setInt(2, item.getProductId());
                    stmt.setInt(3, item.getQuantity());
                    stmt.setDouble(4, item.getPrice());
                    stmt.addBatch();
                }

                stmt.executeBatch();
                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error inserting order details: " + e.getMessage());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
            return false;
        }
    }
}