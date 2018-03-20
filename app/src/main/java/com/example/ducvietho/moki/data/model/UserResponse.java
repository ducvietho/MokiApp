package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 11/22/2017.
 */

public class UserResponse {
    @SerializedName("data")
    private User mUser;
    @SerializedName("code")
    private int mCode;
    @SerializedName("message")
    private String mMessage;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
