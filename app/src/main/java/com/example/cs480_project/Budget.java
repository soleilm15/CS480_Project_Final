package com.example.cs480_project;

import java.util.Date;

public class Budget {
    private int id;
    private double amount;
    private String category;
    private Date startDate;
    private Date endDate;

    public Budget(int id, double amount, String category, Date startDate, Date endDate) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String toString() {
        return "Budget{ " +
                "id= " + id +
                ", amount= " + amount +
                ", category= " + category +
                ", startDate= " + startDate +
                ", endDate= " + endDate +
                " }";
    }

    // getters and setters
    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; };

    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
