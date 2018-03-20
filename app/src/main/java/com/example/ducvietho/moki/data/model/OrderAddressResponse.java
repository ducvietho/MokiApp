package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducvietho on 19/03/2018.
 */

public class OrderAddressResponse {
    @SerializedName("code")
    private int mCode;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("data")
    private List<OrderAddress> mAddresses;

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<OrderAddress> getAddresses() {
        return mAddresses;
    }

    public void setAddresses(List<OrderAddress> addresses) {
        mAddresses = addresses;
    }
}
