package Views;

import Controllers.CartController;
import Controllers.CheckoutController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Cart extends JFrame {

    private final CartController controller = CartController.getInstance();
    private final JTextArea summaryArea = new JTextArea();
    private JPanel itemsPanel;

    public Cart() {
        super("Carrito de Compras");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(12, 16, 4, 16));
        JLabel title = new JLabel("Carrito de compras");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(34, 97, 74));
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Centro: lista de productos con scroll
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));

        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        JScrollPane listScroll = new JScrollPane(itemsPanel);
        centerPanel.add(listScroll, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Abajo: resumen + botones
        JPanel bottomPanel = new JPanel(new BorderLayout(8, 8));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(8, 16, 12, 16));

        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        summaryArea.setBackground(new Color(247, 244, 239));
        summaryArea.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        summaryArea.setRows(7);
        bottomPanel.add(new JScrollPane(summaryArea), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));

        JButton volverBtn = new JButton("Volver al menú");
        volverBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        volverBtn.addActionListener(e -> dispose());
        buttonsPanel.add(volverBtn);

        JButton clearBtn = new JButton("Vaciar carrito");
        clearBtn.addActionListener(e -> {
            controller.clearCart();
            refreshCart();
        });
        buttonsPanel.add(clearBtn);

        JButton orderBtn = new JButton("Procesar pedido");
        orderBtn.setBackground(new Color(31, 120, 84));
        orderBtn.setForeground(Color.WHITE);
        orderBtn.setFocusPainted(false);
        orderBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        orderBtn.addActionListener(e -> procesarPedido());
        buttonsPanel.add(orderBtn);

        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshCart();
    }

    private void refreshCart() {
        itemsPanel.removeAll();

        var items = controller.getItems();
        for (int i = 0; i < items.size(); i++) {
            var item = items.get(i);
            final int index = i;
            double lineTotal = item.getPrice() * item.getQuantity();

            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                    BorderFactory.createEmptyBorder(6, 10, 6, 10)));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            JLabel lblProducto = new JLabel(item.getProductName() + "  -  $" + String.format("%.2f", item.getPrice()) + " c/u");
            lblProducto.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            row.add(lblProducto, BorderLayout.WEST);

            JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));

            JButton btnMenos = new JButton("-");
            btnMenos.setMargin(new java.awt.Insets(2, 8, 2, 8));
            btnMenos.addActionListener(e -> {
                controller.updateItemQuantity(index, items.get(index).getQuantity() - 1);
                refreshCart();
            });
            controls.add(btnMenos);

            JLabel lblCantidad = new JLabel(String.valueOf(item.getQuantity()), JLabel.CENTER);
            lblCantidad.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblCantidad.setPreferredSize(new Dimension(28, 28));
            controls.add(lblCantidad);

            JButton btnMas = new JButton("+");
            btnMas.setMargin(new java.awt.Insets(2, 8, 2, 8));
            btnMas.addActionListener(e -> {
                controller.updateItemQuantity(index, items.get(index).getQuantity() + 1);
                refreshCart();
            });
            controls.add(btnMas);

            JLabel lblTotal = new JLabel(String.format("$%.2f", lineTotal));
            lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblTotal.setPreferredSize(new Dimension(90, 28));
            lblTotal.setHorizontalAlignment(JLabel.RIGHT);
            controls.add(lblTotal);

            row.add(controls, BorderLayout.EAST);
            itemsPanel.add(row);
        }

        if (items.isEmpty()) {
            JLabel vacio = new JLabel("El carrito está vacío");
            vacio.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            vacio.setForeground(Color.GRAY);
            vacio.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            itemsPanel.add(vacio);
        }

        double subtotal = controller.calculateSubtotal();
        double delivery = controller.calculateDeliveryFee();
        double service = controller.calculateServiceFee();
        double total = controller.calculateTotal();

        StringBuilder sb = new StringBuilder();
        sb.append("Resumen del carrito\n");
        sb.append("===================\n");
        sb.append(String.format("Productos: %d\n", controller.getItemCount()));
        sb.append(String.format("Subtotal:  $%.2f\n", subtotal));
        sb.append(String.format("Envío:     $%.2f\n", delivery));
        sb.append(String.format("Servicio:  $%.2f\n", service));
        sb.append("-------------------\n");
        sb.append(String.format("Total:     $%.2f\n", total));
        summaryArea.setText(sb.toString());

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    private void procesarPedido() {
        if (controller.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Checkout vistaCheckout = new Checkout();
        CheckoutController controlador = new CheckoutController(vistaCheckout, controller.calculateTotal());
        controlador.iniciar();
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cart().setVisible(true));
    }
}
