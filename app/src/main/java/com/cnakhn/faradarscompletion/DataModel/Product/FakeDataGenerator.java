package com.cnakhn.faradarscompletion.DataModel.Product;

import android.widget.ImageView;

public class FakeDataGenerator {
    String name, category, instructions, price;

    public FakeDataGenerator() {

    }

    public FakeDataGenerator(String name, String category, String instructions, String price) {
        this.name = name;
        this.category = category;
        this.instructions = instructions;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
