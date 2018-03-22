package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.BaseResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Created by ducvietho on 20/03/2018.
 */

public class ImageDataRepository implements ImageDataResource {
    private ImageDataResource mDataResource;

    public ImageDataRepository(ImageDataResource dataResource) {
        mDataResource = dataResource;
    }

    @Override
    public Observable<BaseResponse> uploadImageProduct(MultipartBody.Part file) {
        return mDataResource.uploadImageProduct(file) ;
    }

    @Override
    public Observable<BaseResponse> uploadImageUser(MultipartBody.Part file) {
        return mDataResource.uploadImageUser(file);
    }
}
