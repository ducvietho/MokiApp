package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.OrderDetail;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.data.model.ProductResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 13/03/2018.
 */

public interface ProductDataResource {
    Observable<ProductResponse> getProductByCateOnePage(int idCate, int idUser, int page);
    Observable<ProductResponse> getProductByCatePages(int idCate, int idUser, int page, int lastId);
    Observable<Product> getProductDetail(int idProduct,int idUser);
    Observable<List<Product>> getProductsFavorite(int idUser);
    Observable<List<Product>> getProductsByUser(int idSeller,int idUser);
    Observable<BaseResponse> createProduct(int idUser,String name,int price,String described,int idCate,String image,
                                           String address,String dimension,String weight,String status);
    Observable<List<Product>> getProductBuyProcessing(int idCustomer);
    Observable<List<Product>> getProductBuySuccess(int idCustomer);
    Observable<List<Product>> getProductSellProcessing(int idUser);
    Observable<List<Product>> getProductSellSuccess(int idUser);
    Observable<BaseResponse> acceptSellProduct(int idProduct);
    Observable<BaseResponse> buyProduct(int idProduct,int idCustomer,String shipAddress);
    Observable<BaseResponse> cancelSellProduct(int idProduct);
    Observable<List<Product>> searchProducts(int idUser,String nameProduct,String price);
    Observable<OrderDetail> getOrderDetail(int idProduct);
}
