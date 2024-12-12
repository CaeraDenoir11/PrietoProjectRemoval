package com.ucb.eldroid.prietoprojectremoval.data.model;

import com.google.gson.annotations.SerializedName;

public class WishlistItem {
    @SerializedName("_id") // Map MongoDB "_id" field
    private String id;

    private String name;
    private String details;
    private String price;

    public WishlistItem(String name, String details, String price) {
        this.name = name;
        this.details = details;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
