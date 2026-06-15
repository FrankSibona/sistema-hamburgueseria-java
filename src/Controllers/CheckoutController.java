package Controllers;

import Views.Checkout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
 


public class CheckoutController {

    private Checkout vista;
    private double totalCarrito;

    public CheckoutController(Checkout vista, double totalCarrito) {
        this.vista = vista;
        this.totalCarrito = totalCarrito;
        
        this.vista.lblTotal.setText(String.format("Total: $ %.2f   ", totalCarrito));
        
        iniciarEventos();
    }

    private void iniciarEventos() {
        // --- EVENTOS PARA MOSTRAR/OCULTAR ALIAS ---
        vista.rbEfectivo.addActionListener(e -> {
            vista.panelAlias.setVisible(false);
            actualizarPantalla();
        });

        vista.rbTransferencia.addActionListener(e -> {
            vista.panelAlias.setVisible(true);
            actualizarPantalla();
        });

        // --- EVENTOS PARA MOSTRAR/OCULTAR DELIVERY ---
        vista.rbRetiro.addActionListener(e -> {
            vista.panelDelivery.setVisible(false);
            actualizarPantalla();
        });

        vista.rbDelivery.addActionListener(e -> {
            vista.panelDelivery.setVisible(true);
            actualizarPantalla();
        });

        // --- EVENTOS DE BOTONES ---
        vista.btnCancelar.addActionListener(e -> vista.dispose());
        
        vista.btnConfirmar.addActionListener(e -> procesarPedido());
    }

    //Método para que la ventana se reacomode cuando mostramos paneles nuevos
    private void actualizarPantalla() {
        SwingUtilities.updateComponentTreeUI(vista);
        vista.pack(); // Ajusta el tamaño de la ventana al contenido visible
    }

    private void procesarPedido() {
        // 1. Validar campos obligatorios siempre visibles
        String nombre = vista.txtNombre.getText().trim();
        String apellido = vista.txtApellido.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El nombre y apellido son obligatorios.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String metodoPago = vista.rbEfectivo.isSelected() ? "Efectivo" : "Transferencia";
        String metodoEntrega = vista.rbRetiro.isSelected() ? "Retira por local" : "Delivery";

        // 2. Validar campos de delivery si seleccionó delivery
        String direccion = "";
        String casaDepto = "";
        String obs = "";

        if (vista.rbDelivery.isSelected()) {
            direccion = vista.txtDireccion.getText().trim();
            casaDepto = vista.txtCasaDepto.getText().trim();
            obs = vista.txtObservaciones.getText().trim();

            if (direccion.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Debe ingresar una dirección para el delivery.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // --- ACÁ VA LA LÓGICA DE BASE DE DATOS (INSERT) ---
        // Tenemos que mandar todas estas variables al PedidoDAO
        
        // Mensaje de éxito de prueba
        String resumen = "Pedido confirmado a nombre de: " + nombre + " " + apellido + "\n"
                       + "Pago: " + metodoPago + "\n"
                       + "Entrega: " + metodoEntrega;
                       
        if(vista.rbDelivery.isSelected()){
             resumen += "\nEnviar a: " + direccion + " (" + casaDepto + ")";
        }

        JOptionPane.showMessageDialog(vista, resumen, "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
        vista.dispose();
    }

    public void iniciar() {
        vista.setVisible(true);
    }
    // --- MÉTODO TEMPORAL PARA PROBAR LA PANTALLA ---
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
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