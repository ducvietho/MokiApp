package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.News;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 24/03/2018.
 */

public class NewsDataRespository implements NewsDataResource {
    private NewsDataResource mDataResource;

    public NewsDataRespository(NewsDataResource dataResource) {
        mDataResource = dataResource;
    }

    @Override
    public Observable<List<News>> getNews() {
        return mDataResource.getNews();
    }

    @Override
    public Observable<News> getNewsDetail(int idNews) {
        return mDataResource.getNewsDetail(idNews);
    }
}
