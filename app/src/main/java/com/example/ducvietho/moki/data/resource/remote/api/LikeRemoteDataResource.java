package com.example.ducvietho.moki.data.resource.remote.api;

import com.example.ducvietho.moki.data.model.Like;
import com.example.ducvietho.moki.data.model.LikeReponse;
import com.example.ducvietho.moki.data.resource.remote.LikeDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiApi;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ducvietho on 14/03/2018.
 */

public class LikeRemoteDataResource extends BaseRemoteDataResource implements LikeDataResource {
    public LikeRemoteDataResource(MokiApi api) {
        super(api);
    }

    @Override
    public Observable<Like> likeProduct(int idProduct, int idUser) {
        return mApi.likeProduct(idProduct,idUser).map(new Function<LikeReponse, Like>() {
            @Override
            public Like apply(LikeReponse likeReponse) throws Exception {
                return likeReponse.getLike();
            }
        });
    }

    @Override
    public Observable<Like> unlikeProduct(int idProduct, int idUser) {
        return mApi.unlikeProduct(idProduct, idUser).map(new Function<LikeReponse, Like>() {
            @Override
            public Like apply(LikeReponse likeReponse) throws Exception {
                return likeReponse.getLike();
            }
        });
    }
}
