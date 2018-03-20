package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 14/03/2018.
 */

public class LikeReponse {
    @SerializedName("data")
    Like mLike;

    public Like getLike() {
        return mLike;
    }

    public void setLike(Like like) {
        mLike = like;
    }
}
