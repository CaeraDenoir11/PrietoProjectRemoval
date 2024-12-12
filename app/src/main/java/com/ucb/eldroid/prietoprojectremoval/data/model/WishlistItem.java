package com.ucb.eldroid.prietoprojectremoval.data.model;

public class WishlistItem {
    private String name;
    private String details;
    private String price;

    public WishlistItem(String name, String details, String price) {
        this.name = name;
        this.details = details;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getPrice() {
        return price;
    }
}
