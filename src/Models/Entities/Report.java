package Models.Entities;

import java.sql.Timestamp;

public class Report {

    private final int id;
    private Timestamp createdAt;
    private int userId;
    private String reportType;
    private String description;

    // Constructor
    public Report(int id, Timestamp createdAt, int userId, String reportType, String description) {
        this.id = id;
        this.createdAt = createdAt;
        this.userId = userId;
        this.reportType = reportType;
        this.description = description;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public String getReportType() {
        return reportType;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}