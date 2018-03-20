package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BO-BK on 22-Nov-17.
 */

public class ProductResponse {
    @SerializedName("data")
    private Products products ;
    @SerializedName("code")
    private int mCode;
    @SerializedName("message")
    private String mMessage;


    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public int getmCode() {
        return mCode;
    }

    public void setmCode(int mCode) {
        this.mCode = mCode;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
