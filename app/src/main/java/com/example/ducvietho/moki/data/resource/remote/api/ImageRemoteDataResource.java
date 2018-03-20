package com.example.ducvietho.moki.data.resource.remote.api;

import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.resource.remote.ImageDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiApi;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Created by ducvietho on 20/03/2018.
 */

public class ImageRemoteDataResource extends BaseRemoteDataResource implements ImageDataResource {
    public ImageRemoteDataResource(MokiApi api) {
        super(api);
    }

    @Override
    public Observable<BaseResponse> uploadImageProduct(MultipartBody.Part file) {
        return mApi.uploadImageProduct(file);
    }
}
