package Controllers;

import Models.Entities.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CartController {

    private static final double DELIVERY_FEE = 4.50;
    private static final double SERVICE_FEE = 2.00;

    private static CartController instance;

    private final List<CartItem> items;

    public CartController() {
        this.items = new ArrayList<>();
    }

    public static CartController getInstance() {
        if (instance == null) {
            instance = new CartController();
        }
        return instance;
    }

    public static class CartItem {
        private String productName;
        private double price;
        private int quantity;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public void addProduct(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }

        for (CartItem item : items) {
            if (item.getProductName().equalsIgnoreCase(product.getName())) {
                item.setQuantity(item.getQuantity() + quantity);
                item.setPrice(product.getPrice());
                return;
            }
        }

        CartItem item = new CartItem();
        item.setProductName(product.getName());
        item.setQuantity(quantity);
        item.setPrice(product.getPrice());
        items.add(item);
    }

    public void addProduct(String name, double price, int quantity) {
        Product product = new Product(0, name, 0, "", price, "");
        addProduct(product, quantity);
    }

    public void updateItemQuantity(int index, int newQuantity) {
        if (index >= 0 && index < items.size()) {
            if (newQuantity <= 0) {
                items.remove(index);
            } else {
                items.get(index).setQuantity(newQuantity);
            }
        }
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public void clearCart() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public int getItemCount() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    public double calculateSubtotal() {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public double calculateDeliveryFee() {
        return calculateSubtotal() > 0 ? DELIVERY_FEE : 0.0;
    }

    public double calculateServiceFee() {
        return calculateSubtotal() > 0 ? SERVICE_FEE : 0.0;
    }

    public double calculateTotal() {
        return calculateSubtotal() + calculateDeliveryFee() + calculateServiceFee();
    }

    public String processOrder(String customerName) {
        if (items.isEmpty()) {
            return "El carrito está vacío. Agrega productos antes de procesar el pedido.";
        }

        String clientName = customerName == null || customerName.trim().isEmpty()
                ? "Cliente"
                : customerName.trim();

        double total = calculateTotal();
        clearCart();

        return "Pedido registrado correctamente para " + clientName + ". Total: $" + String.format("%.2f", total) + ".";
    }
}