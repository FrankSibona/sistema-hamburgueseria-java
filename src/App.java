import Controllers.CheckoutController;
import Controllers.OrderDetailsController;
import Models.Connection.DBConnection;
public class App {

    public static void main(String[] args) {
        DBConnection.getConnection();
/*
            //-- PRUEBA DE INFORME DE ORDENES ---
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Views.OrderDetails vistaDePrueba = new Views.OrderDetails();
                    int id = 2;
                    OrderDetailsController controlador = new OrderDetailsController(vistaDePrueba, id);
                    controlador.iniciar();
                }
            });
*/


      
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 1. Creamos la vista
                Views.Checkout vistaDePrueba = new Views.Checkout();
                
                // 2. Simulamos que el carrito tenía $ 8500.50 en hamburguesas
                double totalSimulado = 8500.50; 
                
                // 3. Arrancamos el controlador
                CheckoutController controlador = new CheckoutController(vistaDePrueba, totalSimulado);
                controlador.iniciar();
            }
        });

    }
}

