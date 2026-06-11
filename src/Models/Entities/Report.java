package Models.Entities;
import java.time.LocalDate;
public class Report {
    private final int id;
    private LocalDate created_at;
    private int user_id;
    private String report_type;
    private String description;
    
    // Constructor
    public Report(int id, LocalDate created_at, int user_id, String report_type, String description) {
        this.id = id;
        this.created_at = created_at;
        this.user_id = user_id;
        this.report_type = report_type;
        this.description = description;
    }
    // Getters and Setters
    public int getId() {
        return id;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}