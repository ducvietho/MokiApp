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

public interface AddressDataResource {
    Observable<List<OrderAddress>> getAddress(int idUser);
    Observable<List<District>> getDistricts();
    Observable<List<District>> searchDistricts(String district);
    Observable<List<Village>> getVillages(int idDistrict);
    Observable<List<Village>> searchVillages(String village,int idDistrict);
    Observable<BaseResponse> insertAddress(int idUser,String province,String district,String village,String street);
}
