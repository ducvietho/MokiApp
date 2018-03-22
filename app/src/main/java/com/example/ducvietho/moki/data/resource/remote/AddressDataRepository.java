package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.District;
import com.example.ducvietho.moki.data.model.DistrictResponse;
import com.example.ducvietho.moki.data.model.OrderAddress;
import com.example.ducvietho.moki.data.model.Village;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 19/03/2018.
 */

public class AddressDataRepository implements AddressDataResource {
    private AddressDataResource mDataResource;

    public AddressDataRepository(AddressDataResource dataResource) {
        mDataResource = dataResource;
    }

    @Override
    public Observable<List<OrderAddress>> getAddress(int idUser) {
        return mDataResource.getAddress(idUser);
    }

    @Override
    public Observable<List<District>> getDistricts() {
        return mDataResource.getDistricts();
    }

    @Override
    public Observable<List<District>> searchDistricts(String district) {
        return mDataResource.searchDistricts(district);
    }

    @Override
    public Observable<List<Village>> getVillages(int idDistrict) {
        return mDataResource.getVillages(idDistrict);
    }

    @Override
    public Observable<List<Village>> searchVillages(String village, int idDistrict) {
        return mDataResource.searchVillages(village,idDistrict);
    }

    @Override
    public Observable<BaseResponse> insertAddress(int idUser, String province, String district, String village, String street) {
        return mDataResource.insertAddress(idUser,province,district,village,street);
    }
}
