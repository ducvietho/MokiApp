package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.BaseResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Created by ducvietho on 20/03/2018.
 */

public interface ImageDataResource {
    Observable<BaseResponse> uploadImageProduct(MultipartBody.Part file);
}
