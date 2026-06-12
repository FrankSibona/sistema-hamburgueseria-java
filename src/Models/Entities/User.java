package Models.Entities;

public class User {

    private final int id;
    private String name;
    private String passwordHash;
    private String email;
    private String role;

    // Constructor
    public User(int id, String name, String passwordHash, String email, String role) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
}