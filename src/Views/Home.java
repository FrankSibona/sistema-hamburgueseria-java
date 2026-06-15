// Frontend de la Pagina Principal
package Views;

import Controllers.HomeController;
import Controllers.CheckoutController; 
import Models.Entities.Product;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Home extends JFrame {
    
    private JPanel panelProductos;
    private HomeController controller;
    private List<ProductoSeleccionado> selecciones;

    public Home() {
        setTitle("Bruta Burgers - Menú");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        selecciones = new ArrayList<>();

        // 1. ARRIBA: Título
        JLabel lblTitulo = new JLabel("BRUTA BURGERS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // 2. MEDIO: Lista de Hamburguesas (AHORA SÍ LO CREAMOS PRIMERO)
        panelProductos = new JPanel();
        panelProductos.setLayout(new BoxLayout(panelProductos, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelProductos);
        add(scrollPane, BorderLayout.CENTER);

        // 3. ABAJO: Botón Siguiente
        JButton btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel panelAbajo = new JPanel();
        panelAbajo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelAbajo.add(btnSiguiente);
        add(panelAbajo, BorderLayout.SOUTH);

        // Acción del botón Siguiente
        btnSiguiente.addActionListener(e -> {
            double totalCalculado = 0;
            boolean llevoAlgo = false;

            for (ProductoSeleccionado ps : selecciones) {
                int cantidad = (int) ps.spinner.getValue();
                if (cantidad > 0) {
                    totalCalculado += cantidad * ps.producto.getPrice();
                    llevoAlgo = true;
                }
            }

            if (!llevoAlgo) {
                JOptionPane.showMessageDialog(this, 
                    "¡Che, tenés que elegir al menos una burger para continuar!", 
                    "Carrito vacío", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            Checkout vistaCheckout = new Checkout();
            CheckoutController controladorCheckout = new CheckoutController(vistaCheckout, totalCalculado);
            controladorCheckout.iniciar();
        });

       
        this.controller = new HomeController(this);
    }

    public void mostrarProductosEnPantalla(List<Product> productos) {
        panelProductos.removeAll();
        selecciones.clear();

        for (Product prod : productos) {
            JPanel tarjeta = new JPanel(new BorderLayout());
            tarjeta.setBorder(BorderFactory.createTitledBorder(prod.getName())); 
            
            JPanel panelInfo = new JPanel(new GridLayout(2, 1));
            panelInfo.add(new JLabel(" Ingredientes: " + prod.getDescription()));
            panelInfo.add(new JLabel(" Precio: $" + prod.getPrice()));
            tarjeta.add(panelInfo, BorderLayout.CENTER);

            JPanel panelCantidad = new JPanel(new FlowLayout());
            panelCantidad.add(new JLabel("Cantidad:"));
            SpinnerModel modeloSpinner = new SpinnerNumberModel(0, 0, 50, 1);
            JSpinner spinnerCantidad = new JSpinner(modeloSpinner);
            panelCantidad.add(spinnerCantidad);
            tarjeta.add(panelCantidad, BorderLayout.EAST);

            selecciones.add(new ProductoSeleccionado(prod, spinnerCantidad));
            panelProductos.add(tarjeta);
        }

        panelProductos.revalidate();
        panelProductos.repaint();
    }

    private class ProductoSeleccionado {
        Product producto;
        JSpinner spinner;

        public ProductoSeleccionado(Product producto, JSpinner spinner) {
            this.producto = producto;
            this.spinner = spinner;
        }
    
    }
}