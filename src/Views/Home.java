// Frontend de la Pagina Principal
package Views;

import Controllers.CartController;
import Controllers.HomeController;
import Controllers.CheckoutController;
import Models.Entities.Product;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class Home extends JFrame {

    private JPanel panelProductos;
    private HomeController controller;

    public Home() {
        setTitle("Bruta Burgers - Menú");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. ARRIBA: Título
        JLabel lblTitulo = new JLabel("BRUTA BURGERS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // 2. MEDIO: Lista de Hamburguesas
        panelProductos = new JPanel();
        panelProductos.setLayout(new BoxLayout(panelProductos, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelProductos);
        add(scrollPane, BorderLayout.CENTER);

        // 3. ABAJO: Botones
        JButton btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setFont(new Font("Arial", Font.BOLD, 16));
        JButton btnCarrito = new JButton("Ver Carrito");
        btnCarrito.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel panelAbajo = new JPanel();
        panelAbajo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelAbajo.add(btnCarrito);
        panelAbajo.add(btnSiguiente);
        add(panelAbajo, BorderLayout.SOUTH);

        btnCarrito.addActionListener(e -> {
            Cart cart = new Cart();
            cart.setVisible(true);
        });

        // Acción del botón Siguiente
        btnSiguiente.addActionListener(e -> {
            CartController carrito = CartController.getInstance();
            if (carrito.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this,
                    "¡Che, tenés que elegir al menos una burger para continuar!",
                    "Carrito vacío",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            Checkout vistaCheckout = new Checkout();
            CheckoutController controladorCheckout = new CheckoutController(vistaCheckout, carrito.calculateTotal());
            controladorCheckout.iniciar();
        });


        this.controller = new HomeController(this);
    }

    public void mostrarProductosEnPantalla(List<Product> productos) {
        panelProductos.removeAll();

        for (Product prod : productos) {
            JPanel tarjeta = new JPanel(new BorderLayout());
            tarjeta.setBorder(BorderFactory.createTitledBorder(prod.getName()));

            JPanel panelInfo = new JPanel(new GridLayout(2, 1));
            panelInfo.add(new JLabel(" Ingredientes: " + prod.getDescription()));
            panelInfo.add(new JLabel(" Precio: $" + prod.getPrice()));
            tarjeta.add(panelInfo, BorderLayout.CENTER);

            JPanel panelDerecho = new JPanel();
            panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));

            JButton btnAgregar = new JButton("Agregar al carrito");
            Product productoActual = prod;
            btnAgregar.addActionListener(ev -> {
                CartController.getInstance().addProduct(productoActual, 1);
                JOptionPane.showMessageDialog(this,
                        productoActual.getName() + " agregado al carrito",
                        "Agregado", JOptionPane.INFORMATION_MESSAGE);
            });
            JPanel panelBtnAgregar = new JPanel(new FlowLayout());
            panelBtnAgregar.add(btnAgregar);
            panelDerecho.add(panelBtnAgregar);

            JButton btnDetalles = new JButton("Ver detalles");
            btnDetalles.addActionListener(ev -> {
                String detalles = "Nombre: " + productoActual.getName()
                        + "\nDescripción: " + productoActual.getDescription()
                        + "\nPrecio: $" + productoActual.getPrice()
                        + "\nCategoría: " + productoActual.getCategory()
                        + "\nStock: " + productoActual.getStock();
                JOptionPane.showMessageDialog(this, detalles,
                        productoActual.getName(), JOptionPane.INFORMATION_MESSAGE);
            });
            JPanel panelBtnDetalles = new JPanel(new FlowLayout());
            panelBtnDetalles.add(btnDetalles);
            panelDerecho.add(panelBtnDetalles);

            tarjeta.add(panelDerecho, BorderLayout.EAST);
            panelProductos.add(tarjeta);
        }

        panelProductos.revalidate();
        panelProductos.repaint();
    }
}
