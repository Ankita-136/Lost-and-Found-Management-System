package com.lostfound.model;

public class Item {
    private int id;
    private String itemName;
    private String description;
    private String category;
    private String dateAdded; // <-- Ensure this variable is exactly named dateAdded

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    // --- THIS IS THE MISSING METHOD CAUSING THE RED ERROR ---
    public String getDateAdded() { return dateAdded; }
    public void setDateAdded(String dateAdded) { this.dateAdded = dateAdded; }
}