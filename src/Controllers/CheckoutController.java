package Controllers;

import Views.Checkout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import Models.Dao.OrderDAO;
import java.util.List;
import Models.Entities.Product;

 

public class CheckoutController {

    private Checkout vista;
    private double totalCarrito;
    private List<Product> listaCarrito;

    public CheckoutController(Checkout vista, double totalCarrito, List<Product> listaCarrito) {
        this.vista = vista;
        this.totalCarrito = totalCarrito;
        this.listaCarrito = listaCarrito;
        
        this.vista.lblTotal.setText(String.format("Total: $ %.2f   ", totalCarrito));
        
        iniciarEventos();
    }

    private void iniciarEventos() {
        vista.rbEfectivo.addActionListener(e -> {
            vista.panelAlias.setVisible(false);
            actualizarPantalla();
        });

        vista.rbTransferencia.addActionListener(e -> {
            vista.panelAlias.setVisible(true);
            actualizarPantalla();
        });

        vista.rbRetiro.addActionListener(e -> {
            vista.panelDelivery.setVisible(false);
            actualizarPantalla();
        });

        vista.rbDelivery.addActionListener(e -> {
            vista.panelDelivery.setVisible(true);
            actualizarPantalla();
        });

        vista.btnCancelar.addActionListener(e -> vista.dispose());
        
        vista.btnConfirmar.addActionListener(e -> procesarPedido());
    }

    private void actualizarPantalla() {
        SwingUtilities.updateComponentTreeUI(vista);
        vista.pack(); 
    }

    private void procesarPedido() {
        String nombre = vista.txtNombre.getText().trim();
        String apellido = vista.txtApellido.getText().trim();
        String telefono = vista.txtTelefono.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El nombre y apellido son obligatorios.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String metodoPago = vista.rbEfectivo.isSelected() ? "Efectivo" : "Transferencia";
        String metodoEntrega = vista.rbRetiro.isSelected() ? "Retira por local" : "Delivery";

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

        OrderDAO dao = new OrderDAO();
        boolean guardado = dao.registrarVenta(
            nombre, apellido, telefono, metodoPago, metodoEntrega, 
            (direccion + " " + casaDepto + " " + obs).trim(), 
            totalCarrito, listaCarrito
        );

        if (guardado) {
            JOptionPane.showMessageDialog(vista, "¡Pedido confirmado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.dispose();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al guardar el pedido en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        String resumen = "Pedido confirmado a nombre de: " + nombre + " " + apellido + "\n"
                       + "Telefono: " + telefono + "\n"
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
}