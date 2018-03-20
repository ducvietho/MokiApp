package com.example.ducvietho.moki.data.resource.remote.api;


import com.example.ducvietho.moki.data.resource.remote.api.service.MokiApi;

/**
 * Created by ducvietho on 11/22/2017.
 */

public abstract class BaseRemoteDataResource {
    public MokiApi mApi;

    public BaseRemoteDataResource(MokiApi api) {
        mApi = api;
    }
}
