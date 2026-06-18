package Views;

import Controllers.CartController;
import Models.Entities.Product;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Vista Swing del carrito.
 * Esta clase representa únicamente la interfaz gráfica del carrito.
 * La lógica del negocio está centralizada en CartController.
 */
public class Cart extends JFrame {

    private final CartController controller = new CartController();
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> cartList = new JList<>(listModel);
    private final JTextArea summaryArea = new JTextArea();
    private final JTextField productField = new JTextField("Hamburguesa Clásica");
    private final JTextField priceField = new JTextField("8.50");
    private final JTextField quantityField = new JTextField("1");

    public Cart() {
        super("Carrito de Compras - Java Swing");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(980, 620);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(12, 16, 8, 16));

        JLabel title = new JLabel("Carrito de compras - MVC Java tradicional");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(34, 97, 74));
        header.add(title, BorderLayout.WEST);

        JLabel info = new JLabel("Vista Swing • Lógica en CartController");
        info.setForeground(new Color(120, 70, 20));
        info.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.add(info, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        JPanel main = new JPanel(new GridLayout(1, 2, 14, 14));
        main.setBorder(BorderFactory.createEmptyBorder(8, 16, 16, 16));

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createTitledBorder("Productos del carrito"));

        cartList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cartList.setFixedCellHeight(32);
        JScrollPane listScroll = new JScrollPane(cartList);
        listScroll.setPreferredSize(new Dimension(420, 320));
        left.add(listScroll);

        JPanel addPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        addPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        addPanel.add(new JLabel("Producto"));
        addPanel.add(productField);
        addPanel.add(new JLabel("Precio"));
        addPanel.add(priceField);
        addPanel.add(new JLabel("Cantidad"));
        addPanel.add(quantityField);
        left.add(addPanel);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        JButton addBtn = new JButton("Agregar producto");
        addBtn.addActionListener(e -> addProductFromForm());
        actions.add(addBtn);

        JButton removeBtn = new JButton("Quitar selección");
        removeBtn.addActionListener(e -> removeSelectedItem());
        actions.add(removeBtn);

        JButton clearBtn = new JButton("Vaciar carrito");
        clearBtn.addActionListener(e -> {
            controller.clearCart();
            refreshCart();
        });
        actions.add(clearBtn);
        left.add(actions);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(BorderFactory.createTitledBorder("Resumen del pedido"));

        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        summaryArea.setBackground(new Color(247, 244, 239));
        summaryArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        summaryArea.setText("Agrega productos para ver el resumen del carrito.\n");
        JScrollPane summaryScroll = new JScrollPane(summaryArea);
        summaryScroll.setPreferredSize(new Dimension(400, 180));
        right.add(summaryScroll);

        JButton orderBtn = new JButton("Procesar pedido");
        orderBtn.setBackground(new Color(31, 120, 84));
        orderBtn.setForeground(Color.WHITE);
        orderBtn.setFocusPainted(false);
        orderBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        orderBtn.setPreferredSize(new Dimension(0, 42));
        orderBtn.addActionListener(e -> procesarPedido());
        right.add(orderBtn);

        JLabel note = new JLabel("Esta interfaz no usa HTML, CSS ni TypeScript; todo está en Java Swing.");
        note.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        note.setForeground(new Color(80, 95, 91));
        note.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        right.add(note);

        main.add(left);
        main.add(right);
        add(main, BorderLayout.CENTER);

        refreshCart();
    }

    private void addProductFromForm() {
        try {
            String name = productField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa un nombre de producto.");
                return;
            }

            Product product = new Product(0, name, 0, "", price, "");
            controller.addProduct(product, quantity);
            refreshCart();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio y cantidad deben ser valores válidos.");
        }
    }

    private void removeSelectedItem() {
        int selectedIndex = cartList.getSelectedIndex();
        if (selectedIndex < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto del carrito para quitarlo.");
            return;
        }

        controller.removeItem(selectedIndex);
        refreshCart();
    }

    private void procesarPedido() {
        String customerName = JOptionPane.showInputDialog(this, "Nombre del cliente para el pedido:", "Cliente");
        String message = controller.processOrder(customerName);
        JOptionPane.showMessageDialog(this, message);
        refreshCart();
    }

    
     
     
    private void refreshCart() {
        listModel.clear();

        for (var item : controller.getItems()) {
            double lineTotal = item.getPrice() * item.getQuantity();
            listModel.addElement(String.format("%s  x%d   $%.2f", item.getProductName(), item.getQuantity(), lineTotal));
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cart().setVisible(true));
    }
}