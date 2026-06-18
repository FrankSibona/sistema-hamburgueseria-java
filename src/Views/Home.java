// Frontend de la Pagina Principal
package Views;

import Controllers.CartController;
import Controllers.HomeController;
import Controllers.CheckoutController;
import Models.Entities.Product;
import java.awt.*;
import java.io.File;
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
        panelProductos.setLayout(new GridLayout(0, 3, 15, 15));
        panelProductos.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        for (Product prod : productos) {
            JPanel tarjeta = new JPanel();
            tarjeta.setPreferredSize(new Dimension(200, 280));
            tarjeta.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            tarjeta.setLayout(null);

            // Foto del producto
            JLabel lblFoto = new JLabel("", SwingConstants.CENTER);
            lblFoto.setBounds(10, 10, 180, 120);
            lblFoto.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            String rutaImagenBase = "src/images/";
            String nombreImagenDB = prod.getImagePath();

            if (nombreImagenDB != null && !nombreImagenDB.isEmpty()) {
                String rutaCompleta = rutaImagenBase + nombreImagenDB;
                File archivoFoto = new File(rutaCompleta);

                if (archivoFoto.exists()) {
                    try {
                        ImageIcon iconOriginal = new ImageIcon(rutaCompleta);
                        Image imgEscalada = iconOriginal.getImage().getScaledInstance(
                                lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH);
                        lblFoto.setIcon(new ImageIcon(imgEscalada));
                    } catch (Exception e) {
                        lblFoto.setText("Error Imagen");
                        lblFoto.setForeground(Color.RED);
                    }
                } else {
                    lblFoto.setText("<html><center>FOTO NO<br>ENCONTRADA</center></html>");
                    lblFoto.setForeground(Color.GRAY);
                    lblFoto.setBackground(new Color(240, 240, 240));
                    lblFoto.setOpaque(true);
                }
            } else {
                lblFoto.setText("Sin Foto");
                lblFoto.setForeground(Color.LIGHT_GRAY);
                lblFoto.setBackground(new Color(240, 240, 240));
                lblFoto.setOpaque(true);
            }
            tarjeta.add(lblFoto);

            // Nombre
            JLabel lblNombre = new JLabel(prod.getName());
            lblNombre.setBounds(10, 140, 180, 20);
            lblNombre.setFont(new Font("Tahoma", Font.BOLD, 14));
            tarjeta.add(lblNombre);

            // Descripcion
            JLabel lblDesc = new JLabel("<html>" + prod.getDescription() + "</html>");
            lblDesc.setBounds(10, 160, 180, 40);
            tarjeta.add(lblDesc);

            // Precio
            JLabel lblPrecio = new JLabel("$" + prod.getPrice());
            lblPrecio.setBounds(10, 210, 180, 20);
            lblPrecio.setFont(new Font("Tahoma", Font.BOLD, 16));
            tarjeta.add(lblPrecio);

            // Boton agregar al carrito
            JButton btnAgregar = new JButton("Agregar al Carrito");
            btnAgregar.setBounds(10, 240, 180, 30);
            Product productoActual = prod;
            btnAgregar.addActionListener(ev -> {
                CartController.getInstance().addProduct(productoActual, 1);
                JOptionPane.showMessageDialog(this,
                        productoActual.getName() + " agregado al carrito",
                        "Agregado", JOptionPane.INFORMATION_MESSAGE);
            });
            tarjeta.add(btnAgregar);

            panelProductos.add(tarjeta);
        }

        panelProductos.revalidate();
        panelProductos.repaint();
    }
}
