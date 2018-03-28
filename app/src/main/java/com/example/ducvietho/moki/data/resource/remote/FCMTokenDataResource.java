package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.BaseResponse;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 26/03/2018.
 */

public interface FCMTokenDataResource {
    Observable<BaseResponse> createFCMToken(int idUser,String token);
}
