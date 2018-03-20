package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 14/03/2018.
 */

public class Like {
    @SerializedName("like")
    private int mNumberLike;

    public int getNumberLike() {
        return mNumberLike;
    }

    public void setNumberLike(int numberLike) {
        mNumberLike = numberLike;
    }
}
