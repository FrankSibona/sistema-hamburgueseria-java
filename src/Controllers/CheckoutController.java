package Controllers;

import Controllers.CartController;
import Controllers.CartController.CartItem;
import Models.Dao.OrderDAO;
import Models.Dao.ProductDAO;
import Models.Entities.Order;
import Views.Checkout;
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
            JOptionPane.showMessageDialog(vista, "El nombre, apellido y teléfono son obligatorios.", "Error", JOptionPane.WARNING_MESSAGE);
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

        CartController carrito = CartController.getInstance();
        ProductDAO productDAO = new ProductDAO();

        for (CartItem item : carrito.getItems()) {
            int stockActual = productDAO.getStockByName(item.getProductName());
            if (stockActual < 0) {
                JOptionPane.showMessageDialog(vista,
                        "No se encontró el producto: " + item.getProductName(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (stockActual < item.getQuantity()) {
                JOptionPane.showMessageDialog(vista,
                        "Stock insuficiente para \"" + item.getProductName() + "\".\n"
                        + "Disponible: " + stockActual + ", pedido: " + item.getQuantity(),
                        "Sin stock", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        StringBuilder productosStr = new StringBuilder();
        for (CartItem item : carrito.getItems()) {
            if (productosStr.length() > 0) productosStr.append(", ");
            productosStr.append(item.getProductName()).append(" x").append(item.getQuantity());
        }

        Order orden = new Order(0, nombre, apellido, telefono, metodoPago, metodoEntrega,
                direccion, casaDepto, obs, productosStr.toString(), totalCarrito, null);

        OrderDAO orderDAO = new OrderDAO();
        int idOrden = orderDAO.create(orden);

        if (idOrden < 0) {
            JOptionPane.showMessageDialog(vista, "Error al guardar el pedido en la base de datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (CartItem item : carrito.getItems()) {
            productDAO.decreaseStock(item.getProductName(), item.getQuantity());
        }

        carrito.clearCart();

        vista.dispose();
        abrirInforme(idOrden);
    }

    private void abrirInforme(int idOrden) {
        try {
            Views.OrderDetails vistaReporte = new Views.OrderDetails();
            vistaReporte.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            OrderDetailsController controlador = new OrderDetailsController(vistaReporte, idOrden);
            controlador.iniciar();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Error al abrir el informe: " + e,
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void iniciar() {
        vista.setVisible(true);
    }
}
