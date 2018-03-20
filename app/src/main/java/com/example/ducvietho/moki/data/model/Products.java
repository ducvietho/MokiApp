package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducvietho.
 */

public class Products {
    @SerializedName("products")
    private List<Product> mProducts;
    @SerializedName("last_id")
    private int last_id;
    @SerializedName("new_item")
    private int new_item;

    public List<Product> getmProducts() {
        return mProducts;
    }

    public void setmProducts(List<Product> mProducts) {
        this.mProducts = mProducts;
    }

    public int getLast_id() {
        return last_id;
    }

    public void setLast_id(int last_id) {
        this.last_id = last_id;
    }

    public int getNew_item() {
        return new_item;
    }

    public void setNew_item(int new_item) {
        this.new_item = new_item;
    }
}
