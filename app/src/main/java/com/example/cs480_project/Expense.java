package com.example.cs480_project;

import java.util.Arrays;

public class Expense {
    private int id;
    private double amount;
    private String category;
    private String date;
    private String description;
    private byte[] receiptImage;
    private int budgetId;

    public Expense(double amount, double v, String category, String date, String description, byte[] receiptImage, int budgetId) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public String toString() {
        return "Expense{ " +
                "id= " + id +
                ", amount= " + amount +
                ", category= " + category +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", receiptImage= " + Arrays.toString(receiptImage) +
                ", budgetId= " + budgetId +
                " }";
    }

    // getters and setters
    public long getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getReceiptImage() {
        return receiptImage;
    }
    public void setReceiptImage(byte[] receiptImage) {
        this.receiptImage = receiptImage;
    }

    public long getBudgetId() {
        return budgetId;
    }
    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }
}
