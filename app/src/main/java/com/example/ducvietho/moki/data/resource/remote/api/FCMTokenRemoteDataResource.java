package com.example.ducvietho.moki.data.resource.remote.api;

import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.resource.remote.FCMTokenDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiApi;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 26/03/2018.
 */

public class FCMTokenRemoteDataResource extends BaseRemoteDataResource implements FCMTokenDataResource {
    public FCMTokenRemoteDataResource(MokiApi api) {
        super(api);
    }

    @Override
    public Observable<BaseResponse> createFCMToken(int idUser, String token) {
        return mApi.createFCMToken(idUser,token);
    }
}
