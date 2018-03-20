package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.OrderAddress;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 19/03/2018.
 */

public interface AddressDataResource {
    Observable<List<OrderAddress>> getAddress(int idUser);
}
