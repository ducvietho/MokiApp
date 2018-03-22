package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.data.model.ProductResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 13/03/2018.
 */

public class ProductDataRepository implements ProductDataResource {
    private ProductDataResource mDataResource;

    public ProductDataRepository(ProductDataResource dataResource) {
        mDataResource = dataResource;
    }

    @Override
    public Observable<ProductResponse> getProductByCateOnePage(int idCate, int idUser, int page) {
        return mDataResource.getProductByCateOnePage(idCate,idUser,page);
    }

    @Override
    public Observable<ProductResponse> getProductByCatePages(int idCate, int idUser, int page, int lastId) {
        return mDataResource.getProductByCatePages(idCate,idUser,page,lastId);
    }

    @Override
    public Observable<Product> getProductDetail(int idProduct,int idUser) {
        return mDataResource.getProductDetail(idProduct,idUser);
    }

    @Override
    public Observable<List<Product>> getProductsFavorite(int idUser) {
        return mDataResource.getProductsFavorite(idUser);
    }

    @Override
    public Observable<List<Product>> getProductsByUser(int idSeller,int idUser) {
        return mDataResource.getProductsByUser(idSeller,idUser);
    }

    @Override
    public Observable<BaseResponse> createProduct(int idUser, String name, int price, String described, int idCate,
                                                  String image, String address, String dimension, String weight,
                                                  String status) {
        return mDataResource.createProduct(idUser,name,price,described,idCate,image,address,dimension,weight,status);
    }
}
