package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 28/03/2018.
 */

public class Notification {
    @SerializedName("id")
    private int mId;
    @SerializedName("product_id")
    private int mProductId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("from_id")
    private int mFromId;
    @SerializedName("to_id")
    private int mToId;
    @SerializedName("read")
    private int mRead;
    @SerializedName("created_at")
    private String mCreated;
    @SerializedName("image")
    private String mImage;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        mProductId = productId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getFromId() {
        return mFromId;
    }

    public void setFromId(int fromId) {
        mFromId = fromId;
    }

    public int getToId() {
        return mToId;
    }

    public void setToId(int toId) {
        mToId = toId;
    }

    public int getRead() {
        return mRead;
    }

    public void setRead(int read) {
        mRead = read;
    }

    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
        mCreated = created;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }
}
