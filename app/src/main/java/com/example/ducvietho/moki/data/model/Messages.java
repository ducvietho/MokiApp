package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ducvietho on 17/03/2018.
 */

public class Messages {
    @SerializedName("conversation_id")
    private int mIdConver;
    @SerializedName("messages")
    private List<Message> mList;

    public int getIdConver() {
        return mIdConver;
    }

    public void setIdConver(int idConver) {
        mIdConver = idConver;
    }

    public List<Message> getList() {
        return mList;
    }

    public void setList(List<Message> list) {
        mList = list;
    }
}
