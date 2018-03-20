package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BO-BK on 22-Nov-17.
 */

public class Size {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
