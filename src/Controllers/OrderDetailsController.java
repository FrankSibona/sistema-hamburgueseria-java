//Backend de los Informes
package Controllers;
import Views.OrderDetails;
import Models.Dao.OrderDAO;
public class OrderDetailsController {
    private OrderDetails vista;
    private int orderId;

    public OrderDetailsController(OrderDetails vista, int orderId) {
        this.vista = vista;
        this.orderId = orderId;
        
        iniciarEventos(orderId);
    }
    public void iniciarEventos(int orderId){
        OrderDAO orderDAO = new OrderDAO();
        var order = orderDAO.findById(orderId);
        if (order == null) {
            javax.swing.JOptionPane.showMessageDialog(vista,
                "No se encontró la orden #" + orderId,
                "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        vista.getLabelTitle().setText(order.getCustomerName() + " " + order.getCustomerLastname() + " #" + order.getId() + " - $" + order.getTotal());
        vista.getListProducts().setListData(order.getProducts().split(", "));
        vista.getListInfo().setListData(new String[]{
            "Teléfono: " + order.getPhone(),
            "Pago: " + order.getPaymentMethod(),
            "Entrega: " + order.getDeliveryMethod(),
            "Dirección: " + order.getAddress() + " " + order.getHouse(),
            "Descripción: " + order.getDescription()
        });
    }
    public void iniciar() {
        vista.setVisible(true);
    }

}
