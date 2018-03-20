package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducvietho on 14/03/2018.
 */

public class Comments {
    @SerializedName("comments")
    private List<Comment> mComments;
    @SerializedName("lastId")
    private int mLastId;

    public List<Comment> getComments() {
        return mComments;
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

    public int getLastId() {
        return mLastId;
    }

    public void setLastId(int lastId) {
        mLastId = lastId;
    }
}
