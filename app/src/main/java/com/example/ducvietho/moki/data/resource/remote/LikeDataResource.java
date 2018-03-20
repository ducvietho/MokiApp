package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.Like;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 14/03/2018.
 */

public interface LikeDataResource {
    Observable<Like> likeProduct(int idProduct,int idUser);
    Observable<Like> unlikeProduct(int idProduct,int idUser);
}
