package com.example.ducvietho.moki.data.resource.remote.api;

import com.example.ducvietho.moki.data.model.News;
import com.example.ducvietho.moki.data.model.NewsResponse;
import com.example.ducvietho.moki.data.resource.remote.NewsDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ducvietho on 24/03/2018.
 */

public class NewsRemoteDataResource extends BaseRemoteDataResource implements NewsDataResource {
    public NewsRemoteDataResource(MokiApi api) {
        super(api);
    }

    @Override
    public Observable<List<News>> getNews() {
        return mApi.getNews().map(new Function<NewsResponse, List<News>>() {
            @Override
            public List<News> apply(NewsResponse newsResponse) throws Exception {
                return newsResponse.getList();
            }
        });
    }

    @Override
    public Observable<News> getNewsDetail(int idNews) {
        return mApi.getNewsDetail(idNews).map(new Function<NewsResponse, News>() {
            @Override
            public News apply(NewsResponse newsResponse) throws Exception {
                return newsResponse.getList().get(0);
            }
        });
    }
}
