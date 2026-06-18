//Backend de la pagina Principal
package Controllers;

import Models.Dao.ProductDAO;
import Models.Entities.Product;
import Views.Home;
import java.util.List;

public class HomeController {
    
    private Home vistaHome;
    private ProductDAO productDAO;

    public HomeController(Home vistaHome) {
        this.vistaHome = vistaHome;
        this.productDAO = new ProductDAO();
        
        // ¡ESTO ES CLAVE! Apenas se abre el Home, mandamos a cargar los productos
        cargarProductos();
    }

    public void cargarProductos() {
        // 1. El controlador va a la base de datos (DAO) y se trae todas las hamburguesas
        List<Product> listaProductos = productDAO.findAll();

        // 2. Le pasamos esa lista llena de datos al método nuevo que creaste en la Vista
        // para que dibuje los recuadros en la pantalla
        vistaHome.mostrarProductosEnPantalla(listaProductos);
    }
    // --- MÉTODO TEMPORAL PARA PROBAR LA PANTALLA ---
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // 1. Levantamos la conexión a la base de datos primero para que no falle el DAO
                Models.Connection.DBConnection.getConnection();
                
                // 2. Creamos tu vista del Home (que internamente ya llama a este controlador)
                Views.Home vistaDePrueba = new Views.Home();
                
                // 3. La hacemos visible en la pantalla
                vistaDePrueba.setVisible(true);
            }
        });
    }
}

