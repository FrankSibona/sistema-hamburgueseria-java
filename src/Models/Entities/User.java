package Models.Entities;

public class User {
    private final int id;
    private String name;
    private String password_hash;
    private String email;
    private String role;
    // Constructor
    public User(int id, String name, String password_hash, String email, String role) {
        this.id = id;
        this.name = name;
        this.password_hash = password_hash;
        this.email = email;
        this.role = role;
    }
    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
