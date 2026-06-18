package Models.Entities;

public class Product {
    private int id;
    private String name;
    private int stock;
    private String description;
    private double price;
    private String category;
    private String imagePath; // Faltaba declarar la variable acá

    // Constructor vacío (Siempre es buena práctica tenerlo)
    public Product() {
    }

    // Constructor de 6 parámetros (Para NO romper el CartController de tus compañeros)
    public Product(int id, String name, int stock, String description, double price, String category) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    // Constructor de 7 parámetros (El tuyo, con la foto incluida)
    public Product(int id, String name, int stock, String description, double price, String category, String imagePath) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imagePath = imagePath; // Faltaba guardar el dato acá
    }

    // Getters y Setters
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
}