package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho.
 */

public class Category {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("has_category")
    private String has_category;

    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public String getHas_category() {
        return has_category;
    }

    public void setHas_category(String has_category) {
        this.has_category = has_category;
    }
}
