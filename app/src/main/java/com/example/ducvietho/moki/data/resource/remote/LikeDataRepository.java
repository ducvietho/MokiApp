package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.Like;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 14/03/2018.
 */

public class LikeDataRepository implements LikeDataResource {
    private LikeDataResource mDataResource;

    public LikeDataRepository(LikeDataResource dataResource) {
        mDataResource = dataResource;
    }

    @Override
    public Observable<Like> likeProduct(int idProduct, int idUser) {
        return mDataResource.likeProduct(idProduct,idUser);
    }

    @Override
    public Observable<Like> unlikeProduct(int idProduct, int idUser) {
        return mDataResource.unlikeProduct(idProduct,idUser);
    }
}
