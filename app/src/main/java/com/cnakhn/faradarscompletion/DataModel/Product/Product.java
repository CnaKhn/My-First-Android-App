package com.cnakhn.faradarscompletion.DataModel.Product;

public class Product {
    public static final String KEY_ID = "productId";
    public static final String KEY_NAME = "name";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_INSTRUCTIONS = "instructions";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_PRICE = "price";

    private long id;
    private String category, name, instructions, photo;
    private double price;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product Name: " + name;
    }
}
