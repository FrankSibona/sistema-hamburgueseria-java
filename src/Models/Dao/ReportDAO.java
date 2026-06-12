package Models.Dao;

import Models.Connection.DBConnection;
import Models.Entities.Report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    // Insertar un nuevo informe
    public boolean create(Report report) {

        String sql = """
                INSERT INTO reports (user_id, report_type, description)
                VALUES (?, ?, ?)
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, report.getUser_id());
            stmt.setString(2, report.getReport_type());
            stmt.setString(3, report.getDescription());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error creating report: " + e.getMessage());
            return false;
        }
    }

    // Buscar un informe por ID
    public Report findById(int id) {

        String sql = "SELECT * FROM reports WHERE id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Report(
                        rs.getInt("id"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("user_id"),
                        rs.getString("report_type"),
                        rs.getString("description")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error finding report: " + e.getMessage());
        }

        return null;
    }

    // Ver todos los informes
    public List<Report> findAll() {

        List<Report> reports = new ArrayList<>();

        String sql = "SELECT * FROM reports";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                reports.add(new Report(
                        rs.getInt("id"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("user_id"),
                        rs.getString("report_type"),
                        rs.getString("description")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving reports: " + e.getMessage());
        }

        return reports;
    }

    // Eliminar un informe
    public boolean delete(int id) {

        String sql = "DELETE FROM reports WHERE id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting report: " + e.getMessage());
            return false;
        }
    }
}