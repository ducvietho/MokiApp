package com.example.ducvietho.moki.data.model;

/**
 * Created by ducvietho on 18/03/2018.
 */

public class Status {
    private String mName;
    private String mDescrip;

    public Status(String name, String descrip) {
        mName = name;
        mDescrip = descrip;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescrip() {
        return mDescrip;
    }

    public void setDescrip(String descrip) {
        mDescrip = descrip;
    }
}
