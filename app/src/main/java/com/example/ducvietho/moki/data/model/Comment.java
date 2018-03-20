package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 14/03/2018.
 */

public class Comment {
    @SerializedName("id")
    private int id;
    @SerializedName("content")
    private String content;
    @SerializedName("poster")
    private User mUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
