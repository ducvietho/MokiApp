package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.Category;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 18/03/2018.
 */

public interface CategoryDataResource {
    Observable<List<Category>> getCategories();
}
