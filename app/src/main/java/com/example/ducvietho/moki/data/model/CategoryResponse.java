package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducvietho on 18/03/2018.
 */

public class CategoryResponse {
    @SerializedName("code")
    private int mCode;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("data")
    private List<Category> mList;

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

    public List<Category> getList() {
        return mList;
    }

    public void setList(List<Category> list) {
        mList = list;
    }
}
