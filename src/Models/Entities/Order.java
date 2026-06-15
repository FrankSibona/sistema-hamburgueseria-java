package Models.Entities;

import java.sql.Timestamp;

public class Order {

    private final int id;
    private String customerName;
    private String customerLastname;
    private String paymentMethod;
    private String deliveryMethod;
    private String address;
    private double total;
    private Timestamp createdAt;

    // Constructor completo
    public Order(int id, String customerName, String customerLastname,
                String paymentMethod, String deliveryMethod,
                String address, double total, Timestamp createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.customerLastname = customerLastname;
        this.paymentMethod = paymentMethod;
        this.deliveryMethod = deliveryMethod;
        this.address = address;
        this.total = total;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerLastname() {
        return customerLastname;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public String getAddress() {
        return address;
    }

    public double getTotal() {
        return total;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // Setters (sin id)
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerLastname(String customerLastname) {
        this.customerLastname = customerLastname;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}