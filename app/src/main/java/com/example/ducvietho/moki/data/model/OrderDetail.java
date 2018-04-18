package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 18/04/2018.
 */

public class OrderDetail {
    @SerializedName("id")
    private int id;
    @SerializedName("image")
    private String mImage;
    @SerializedName("price")
    private int mPrice;
    @SerializedName("name")
    private String mName;
    @SerializedName("ships_from")
    private String mAddShip;
    @SerializedName("buyer")
    private User mBuyer;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public String getAddShip() {
        return mAddShip;
    }

    public void setAddShip(String addShip) {
        mAddShip = addShip;
    }

    public User getBuyer() {
        return mBuyer;
    }

    public void setBuyer(User buyer) {
        mBuyer = buyer;
    }
}
