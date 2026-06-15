package Views;

import javax.swing.*;
import java.awt.*;

public class Checkout extends JFrame {

    // Campos permanentes
    public JTextField txtNombre;
    public JTextField txtApellido;
    
    // Opciones de Pago
    public JRadioButton rbEfectivo;
    public JRadioButton rbTransferencia;
    public JPanel panelAlias; // Panel que se oculta/muestra
    public JLabel lblAliasDatos;
    
    // Opciones de Entrega
    public JRadioButton rbRetiro;
    public JRadioButton rbDelivery;
    public JPanel panelDelivery; // Panel que se oculta/muestra
    
    // Campos de Delivery
    public JTextField txtDireccion;
    public JTextField txtCasaDepto;
    public JTextArea txtObservaciones;
    
    // Botones finales
    public JButton btnConfirmar;
    public JButton btnCancelar;
    public JLabel lblTotal;

    public Checkout() {
        setTitle("Finalizar Compra");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Usamos un contenedor principal con BoxLayout vertical
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- 1. DATOS PERSONALES ---
        JPanel pnlDatos = new JPanel(new GridLayout(2, 2, 5, 5));
        pnlDatos.setBorder(BorderFactory.createTitledBorder("Datos Personales"));
        pnlDatos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        pnlDatos.add(txtNombre);
        pnlDatos.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        pnlDatos.add(txtApellido);
        panelPrincipal.add(pnlDatos);

        // --- 2. MÉTODO DE PAGO ---
        JPanel pnlPago = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlPago.setBorder(BorderFactory.createTitledBorder("Método de Pago"));
        rbEfectivo = new JRadioButton("Efectivo", true); // Seleccionado por defecto
        rbTransferencia = new JRadioButton("Transferencia");
        ButtonGroup bgPago = new ButtonGroup();
        bgPago.add(rbEfectivo);
        bgPago.add(rbTransferencia);
        pnlPago.add(rbEfectivo);
        pnlPago.add(rbTransferencia);
        panelPrincipal.add(pnlPago);

        // Panel de Alias (Oculto por defecto)
        panelAlias = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblAliasDatos = new JLabel("<html><b>Alias:</b> hamburgueseria.mp<br><b>CBU:</b> 0000111122223333444455</html>");
        lblAliasDatos.setForeground(new Color(0, 102, 204));
        panelAlias.add(lblAliasDatos);
        panelAlias.setVisible(false); // Arranca oculto
        panelPrincipal.add(panelAlias);

        // --- 3. MÉTODO DE ENTREGA ---
        JPanel pnlEntrega = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlEntrega.setBorder(BorderFactory.createTitledBorder("Método de Entrega"));
        rbRetiro = new JRadioButton("Retirar por el local", true); // Seleccionado por defecto
        rbDelivery = new JRadioButton("Delivery");
        ButtonGroup bgEntrega = new ButtonGroup();
        bgEntrega.add(rbRetiro);
        bgEntrega.add(rbDelivery);
        pnlEntrega.add(rbRetiro);
        pnlEntrega.add(rbDelivery);
        panelPrincipal.add(pnlEntrega);

        // Panel de Datos de Delivery (Oculto por defecto)
        panelDelivery = new JPanel(new GridLayout(3, 2, 5, 5));
        panelDelivery.setBorder(BorderFactory.createTitledBorder("Datos de Envío"));
        
        panelDelivery.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelDelivery.add(txtDireccion);
        
        panelDelivery.add(new JLabel("Casa / Depto:"));
        txtCasaDepto = new JTextField();
        panelDelivery.add(txtCasaDepto);
        
        panelDelivery.add(new JLabel("Observaciones (Timbre, etc):"));
        txtObservaciones = new JTextArea(2, 20);
        txtObservaciones.setLineWrap(true);
        panelDelivery.add(new JScrollPane(txtObservaciones));
        
        panelDelivery.setVisible(false); // Arranca oculto
        panelPrincipal.add(panelDelivery);

        // --- 4. TOTAL Y BOTONES ---
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total: $ 0.00   ");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        
        btnCancelar = new JButton("Cancelar");
        btnConfirmar = new JButton("Confirmar Pedido");
        btnConfirmar.setBackground(new Color(0, 153, 51));
        btnConfirmar.setForeground(Color.WHITE);
        
        pnlBotones.add(lblTotal);
        pnlBotones.add(btnCancelar);
        pnlBotones.add(btnConfirmar);
        
        // Agregar todo a la ventana
        add(panelPrincipal, BorderLayout.CENTER);
        add(pnlBotones, BorderLayout.SOUTH);
    }
}