package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 17/03/2018.
 */

public class Message {
    @SerializedName("id")
    private int mId;
    @SerializedName("conversation_id")
    private int idConversation;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("read")
    private int mRead;
    @SerializedName("created_at")
    private String mCreated;
    @SerializedName("sender")
    private User mSender;

    public int getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public User getSender() {
        return mSender;
    }

    public void setSender(User sender) {
        mSender = sender;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public int getRead() {
        return mRead;
    }

    public void setRead(int read) {
        mRead = read;
    }

    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
        mCreated = created;
    }

}
