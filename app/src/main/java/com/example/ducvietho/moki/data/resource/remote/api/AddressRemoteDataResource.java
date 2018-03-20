package com.example.ducvietho.moki.data.resource.remote.api;

import com.example.ducvietho.moki.data.model.OrderAddress;
import com.example.ducvietho.moki.data.model.OrderAddressResponse;
import com.example.ducvietho.moki.data.resource.remote.AddressDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ducvietho on 19/03/2018.
 */

public class AddressRemoteDataResource extends BaseRemoteDataResource implements AddressDataResource {
    public AddressRemoteDataResource(MokiApi api) {
        super(api);
    }

    @Override
    public Observable<List<OrderAddress>> getAddress(int idUser) {
        return mApi.getAddress(idUser).map(new Function<OrderAddressResponse, List<OrderAddress>>() {
            @Override
            public List<OrderAddress> apply(OrderAddressResponse orderAddressResponse) throws Exception {
                return orderAddressResponse.getAddresses();
            }
        });
    }
}
