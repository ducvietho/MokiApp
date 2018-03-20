package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 17/03/2018.
 */

public class MessageResponse {
    @SerializedName("code")
    private int mCode;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("data")
    private Messages mMessages;

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

    public Messages getMessages() {
        return mMessages;
    }

    public void setMessages(Messages messages) {
        mMessages = messages;
    }
}
