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
        cargarProductos();
    }

    public void cargarProductos() {
        // 1. El controlador va a la base de datos (DAO) y se trae todas las hamburguesas
        List<Product> listaProductos = productDAO.findAll();

        // 2. Le pasamos esa lista llena de datos al método
        vistaHome.mostrarProductosEnPantalla(listaProductos);
    }
}

