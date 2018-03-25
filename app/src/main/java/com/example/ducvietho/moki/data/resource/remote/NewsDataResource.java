package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.News;
import com.example.ducvietho.moki.data.model.NewsResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 24/03/2018.
 */

public interface NewsDataResource {
    Observable<List<News>> getNews();
    Observable<News> getNewsDetail(int idNews);
}
