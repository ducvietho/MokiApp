package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 19/03/2018.
 */

public class OrderAddress {
    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int idUSer;
    @SerializedName("province")
    private String mProvince;
    @SerializedName("district")
    private String mDistrict;
    @SerializedName("village")
    private String mVillage;
    @SerializedName("street")
    private String mStreet;
    @SerializedName("default")
    private int mDefault;

    public OrderAddress( int idUSer, String province, String district, String village, String street) {

        this.idUSer = idUSer;
        mProvince = province;
        mDistrict = district;
        mVillage = village;
        mStreet = street;

    }

    public int getDefault() {
        return mDefault;
    }

    public void setDefault(int aDefault) {
        mDefault = aDefault;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUSer() {
        return idUSer;
    }

    public void setIdUSer(int idUSer) {
        this.idUSer = idUSer;
    }

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String province) {
        mProvince = province;
    }

    public String getDistrict() {
        return mDistrict;
    }

    public void setDistrict(String district) {
        mDistrict = district;
    }

    public String getVillage() {
        return mVillage;
    }

    public void setVillage(String village) {
        mVillage = village;
    }

    public String getStreet() {
        return mStreet;
    }

    public void setStreet(String street) {
        mStreet = street;
    }
}
