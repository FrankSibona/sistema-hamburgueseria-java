/* App temporal que dejo de ejemplo

import view.MainFrame;
import controller.ProductController;
import controller.OrderController;
import controller.ReportController;

public class App {

    public static void main(String[] args) {

        // 1. Crear vista principal
        MainFrame mainView = new MainFrame();

        // 2. Crear controllers
        ProductController productController = new ProductController(mainView);
        OrderController orderController = new OrderController(mainView);
        ReportController reportController = new ReportController(mainView);

        // 3. Inicializar UI
        mainView.setVisible(true);
    }
}

*/