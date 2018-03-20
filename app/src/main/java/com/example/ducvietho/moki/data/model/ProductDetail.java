package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 14/03/2018.
 */

public class ProductDetail {
    @SerializedName("data")
    private Product mProduct;

    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product product) {
        mProduct = product;
    }
}
