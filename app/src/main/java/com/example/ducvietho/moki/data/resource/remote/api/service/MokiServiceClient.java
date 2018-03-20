package com.example.ducvietho.moki.data.resource.remote.api.service;


import com.example.ducvietho.moki.utils.Constants;

/**
 * Created by ducvietho on 11/21/2017.
 */

public class MokiServiceClient {
    private static MokiApi sMokiApi;

    public static MokiApi getInstance() {
        if (sMokiApi == null) {
            sMokiApi = ServiceClient.createService(Constants.URL_ENCODE, MokiApi.class);
        }
        return sMokiApi;
    }
}
