// IMPORTACIONES NUEVAS PARA EL SERVIDOR WEB
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
// IMPORTACIONES PARA LA BASE DE DATOS
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class App {
    public static void main(String[] args) throws IOException {
        
        // 🌟 ACÁ ESTÁ LA LÍNEA: NetBeans se adueña del puerto 8080 para escuchar al VS Code
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        System.out.println("=================================================");
        System.out.println("🚀 SERVIDOR API INICIADO - PUERTO 8080");
        System.out.println("=================================================");

        // Creamos la ruta de la API
        server.createContext("/api/carrito", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // Permisos CORS para que el frontend de VS Code pueda comunicarse sin bloqueos
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
                exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");

                if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                    exchange.sendResponseHeaders(204, -1);
                    return;
                }

                // Cuando desde VS Code manden las hamburguesas (Petición POST)
                if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                    System.out.println("\n📥 ¡Llegó una petición desde el Carrito de VS Code!");

                    // Leemos el JSON que viene desde la página
                    InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                    String body = new BufferedReader(isr).lines().collect(Collectors.joining("\n"));
                    
                    System.out.println("Datos del carrito recibidos: " + body);

                    // 🛠️ CREDENCIALES ACORDADAS: Conexión interna a XAMPP por el puerto 3306
                    String url = "jdbc:mysql://localhost:3306/burger_system";
                    String user = "root";
                    String password = "root";

                    try (Connection con = DriverManager.getConnection(url, user, password)) {
                        String sql = "INSERT INTO order_items (product_name, quantity, price) VALUES (?, ?, ?)";
                        try (PreparedStatement ps = con.prepareStatement(sql)) {
                            // Guardamos un registro testigo para avisar que el frente se conectó
                            ps.setString(1, "Pedido desde Frente Web");
                            ps.setInt(2, 1);
                            ps.setDouble(3, 5000.00);
                            ps.executeUpdate();
                            System.out.println("✅ Pedido impactado con éxito en la tabla order_items.");
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Error al guardar en base de datos: " + e.getMessage());
                    }

                    // Le respondemos al VS Code que llegó todo joya
                    String response = "{\"status\":\"success\",\"message\":\"Pedido recibido en NetBeans y guardado\"}";
                    byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, responseBytes.length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(responseBytes);
                    }
                }
            }
        });

        // Arrancamos el servidor
        server.setExecutor(null);
        server.start();
        
        System.out.println("🔄 Escuchando en http://localhost:8080/api/carrito");
        System.out.println("⚠️ Mantené esta consola abierta en NetBeans.");
        System.out.println("=================================================");
    }
}