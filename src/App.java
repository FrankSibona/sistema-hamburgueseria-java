
import Models.Connection.DBConnection;
import Views.Checkout;
import Controllers.CheckoutController;
import Models.Entities.Product;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        
        // Probamos que la base de datos conecte primero
        DBConnection.getConnection();

        // Le decimos a Java que inicie la interfaz gráfica
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                // 1. Creamos la vista
                Checkout vistaDePrueba = new Checkout();
                
                // 2. CREAMOS UN CARRITO FALSO PARA PROBAR TU CÓDIGO
                List<Product> carritoFalso = new ArrayList<>();
                
                // Simulamos que el cliente eligió 2 Hamburguesas Clásicas (ID 1 de tu base de datos)
                Product prod1 = new Product(1, "Hamburguesa Clasica", 50, "...", 8500.00, "BURGER");
                prod1.setCantidad(2); // ¡Acá usamos el método que acabás de crear!
                carritoFalso.add(prod1);

                // Simulamos que eligió 1 Gaseosa (ID 4 de tu base de datos)
                Product prod2 = new Product(4, "Gaseosa Cola", 100, "...", 2500.00, "DRINK");
                prod2.setCantidad(1);
                carritoFalso.add(prod2);

                // Total a pagar simulado: (8500 * 2) + 2500 = 19500
                double totalSimulado = 19500.00; 
                
                // 3. Arrancamos el controlador pasándole nuestro carrito falso
                CheckoutController controlador = new CheckoutController(vistaDePrueba, totalSimulado, carritoFalso);
                controlador.iniciar();
            }
        });
    }
}
