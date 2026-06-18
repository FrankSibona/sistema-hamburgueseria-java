package Models.Entities;

public class Product {
    private int id;
    private String name;
    private int stock;
    private String description;
    private double price;
    private String category;
    private String imagePath;
    private int cantidad;

    public Product() {
    }

    public Product(int id, String name, int stock, String description, double price, String category) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public Product(int id, String name, int stock, String description, double price, String category, String imagePath) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
