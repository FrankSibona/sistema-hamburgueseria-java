package Models.Entities;

import java.sql.Timestamp;

public class Order {

    private final int id;

    private String customerName;
    private String customerLastname;
    private String phone;

    private String paymentMethod;
    private String deliveryMethod;

    private String address;
    private String house;
    private String description;

    private String products;

    private double total;

    private Timestamp createdAt;


    // Constructor completo
    public Order(int id,
                String customerName,
                String customerLastname,
                String phone,
                String paymentMethod,
                String deliveryMethod,
                String address,
                String house,
                String description,
                String products,
                double total,
                Timestamp createdAt) {

        this.id = id;
        this.customerName = customerName;
        this.customerLastname = customerLastname;
        this.phone = phone;
        this.paymentMethod = paymentMethod;
        this.deliveryMethod = deliveryMethod;
        this.address = address;
        this.house = house;
        this.description = description;
        this.products = products;
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

    public String getPhone() {
        return phone;
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

    public String getHouse() {
        return house;
    }

    public String getDescription() {
        return description;
    }

    public String getProducts() {
        return products;
    }

    public double getTotal() {
        return total;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerLastname(String customerLastname) {
        this.customerLastname = customerLastname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void setHouse(String house) {
        this.house = house;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}