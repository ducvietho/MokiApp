package com.example.ducvietho.moki.data.resource.remote.api;

import com.example.ducvietho.moki.data.model.Category;
import com.example.ducvietho.moki.data.model.CategoryResponse;
import com.example.ducvietho.moki.data.resource.remote.CategoryDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ducvietho on 18/03/2018.
 */

public class CategoryRemoteDataResource extends BaseRemoteDataResource implements CategoryDataResource {
    public CategoryRemoteDataResource(MokiApi api) {
        super(api);
    }

    @Override
    public Observable<List<Category>> getCategories() {
        return mApi.getCategories().map(new Function<CategoryResponse, List<Category>>() {
            @Override
            public List<Category> apply(CategoryResponse categoryResponse) throws Exception {
                return categoryResponse.getList();
            }
        });
    }
}
