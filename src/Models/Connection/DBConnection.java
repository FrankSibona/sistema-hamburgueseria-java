package Models.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DBConnection.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties file not found");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Error loading config.properties", e);
        }
    }

    public static Connection getConnection() {

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = "Azemol/0317";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection established successfully");
            return connection;

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            return null;
        }
    }
}