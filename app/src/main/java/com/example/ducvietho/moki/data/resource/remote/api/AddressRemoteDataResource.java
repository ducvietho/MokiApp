package com.example.ducvietho.moki.data.resource.remote.api;

import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.District;
import com.example.ducvietho.moki.data.model.DistrictResponse;
import com.example.ducvietho.moki.data.model.OrderAddress;
import com.example.ducvietho.moki.data.model.OrderAddressResponse;
import com.example.ducvietho.moki.data.model.Village;
import com.example.ducvietho.moki.data.model.VillageResponse;
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

    @Override
    public Observable<List<District>> getDistricts() {
        return mApi.getDistricts().map(new Function<DistrictResponse, List<District>>() {
            @Override
            public List<District> apply(DistrictResponse districtResponse) throws Exception {
                return districtResponse.getDistricts();
            }
        });
    }

    @Override
    public Observable<List<District>> searchDistricts(final String district) {
        return mApi.searchDistricts(district).map(new Function<DistrictResponse, List<District>>() {
            @Override
            public List<District> apply(DistrictResponse districtResponse) throws Exception {
                return districtResponse.getDistricts();
            }
        });
    }

    @Override
    public Observable<List<Village>> getVillages(int idDistrict) {
        return mApi.getVillages(idDistrict).map(new Function<VillageResponse, List<Village>>() {
            @Override
            public List<Village> apply(VillageResponse villageResponse) throws Exception {
                return villageResponse.getVillages();
            }
        });
    }

    @Override
    public Observable<List<Village>> searchVillages(final String village, int idDistrict) {
        return mApi.searchVillages(village,idDistrict).map(new Function<VillageResponse, List<Village>>() {
            @Override
            public List<Village> apply(VillageResponse villageResponse) throws Exception {
                return villageResponse.getVillages();
            }
        });
    }

    @Override
    public Observable<BaseResponse> insertAddress(int idUser, String province, String district, String village, String street) {
        return mApi.insertAddress(idUser,province,district,village,street);
    }
}
