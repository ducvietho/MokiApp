package com.example.ducvietho.moki.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ducvietho on 18/04/2018.
 */

public class OrderDetailResponse {
    @SerializedName("data")
    private OrderDetail mOrderDetail;

    public OrderDetail getOrderDetail() {
        return mOrderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        mOrderDetail = orderDetail;
    }
}
