package com.example.yourheathhero;

public class Product {
    private String imageUrl;
    private String title;
    private String price;

    // Constructor
    public Product(String imageUrl, String title, String price) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.price = price;
    }

    // Getters
    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }
}
