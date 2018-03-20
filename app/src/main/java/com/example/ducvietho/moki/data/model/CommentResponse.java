package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 14/03/2018.
 */

public class CommentResponse {
    @SerializedName("data")
    private Comments mComments;

    public Comments getComments() {
        return mComments;
    }

    public void setComments(Comments comments) {
        mComments = comments;
    }
}
