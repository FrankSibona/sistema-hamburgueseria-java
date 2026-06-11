package Models.Dao;

import Models.Connection.DBConnection;
import Models.Entities.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    // insertar en la bd un nuevo producto
    public boolean create(Product product) {

        String sql = """
                INSERT INTO products
                (name, stock, description, price, category)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getStock());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setString(5, product.getCategory());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error creating product: " + e.getMessage());
            return false;
        }
    }
    
    //buscar un producto por su id
    public Product findById(int id) {

        String sql = "SELECT * FROM products WHERE id = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("stock"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getString("category")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error finding product: " + e.getMessage());
        }

        return null;
    }

    //buscar todos los productos
    public List<Product> findAll() {

        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                products.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("stock"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getString("category")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }

        return products;
    }

//actualizar un producto existente en la bd
    public boolean update(Product product) {

        String sql = """
                UPDATE products
                SET name = ?,
                    stock = ?,
                    description = ?,
                    price = ?,
                    category = ?
                WHERE id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getStock());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setString(5, product.getCategory());
            stmt.setInt(6, product.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
            return false;
        }
    }

    //eliminar un producto de la bd por su id
    public boolean delete(int id) {

        String sql = "DELETE FROM products WHERE id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }
}