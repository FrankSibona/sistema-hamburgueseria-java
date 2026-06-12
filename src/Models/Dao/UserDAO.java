package Models.Dao;

import Models.Connection.DBConnection;
import Models.Entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Insertar un nuevo usuario
    public boolean create(User user) {

        String sql = """
            INSERT INTO users (name, password_hash, email, role)
            VALUES (?, ?, ?, ?)
            """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
            return false;
        }
    }

    // Buscar por ID a un usuario
    public User findById(int id) {

        String sql = "SELECT * FROM users WHERE id = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password_hash"),
                    rs.getString("email"),
                    rs.getString("role")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }

        return null;
    }
    
    // Buscar por Email a un usuario
    public User findByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password_hash"),
                    rs.getString("email"),
                    rs.getString("role")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error finding user by email: " + e.getMessage());
        }

        return null;
    }
    
    // consultar todos los usuarios
    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                users.add(new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password_hash"),
                    rs.getString("email"),
                    rs.getString("role")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }

        return users;
    }
    
    // modificar datos de algun usuario
    public boolean update(User user) {

        String sql = """
                UPDATE users
                SET name = ?,
                    password_hash = ?,
                    email = ?,
                    role = ?
                WHERE id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());
            stmt.setInt(5, user.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    // eliminar a un usuario
    public boolean delete(int id) {

        String sql = "DELETE FROM users WHERE id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }


}