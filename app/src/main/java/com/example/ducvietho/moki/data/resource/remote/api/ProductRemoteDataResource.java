package com.example.ducvietho.moki.data.resource.remote.api;

import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.OrderDetail;
import com.example.ducvietho.moki.data.model.OrderDetailResponse;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.data.model.ProductDetail;
import com.example.ducvietho.moki.data.model.ProductResponse;
import com.example.ducvietho.moki.data.resource.remote.ProductDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ducvietho on 13/03/2018.
 */

public class ProductRemoteDataResource extends BaseRemoteDataResource implements ProductDataResource{
    public ProductRemoteDataResource(MokiApi api) {
        super(api);
    }

    @Override
    public Observable<ProductResponse> getProductByCateOnePage(int idCate, int idUser, int page) {
        return mApi.getProductByCateOnePage(idCate,idUser,page);
    }

    @Override
    public Observable<ProductResponse> getProductByCatePages(int idCate, int idUser, int page, int lastId) {
        return mApi.getProductByCatePages(idCate, idUser, page, lastId);
    }

    @Override
    public Observable<Product> getProductDetail(int idProduct, int idUser) {
        return mApi.getProductDetail(idProduct,idUser).map(new Function<ProductDetail, Product>() {
            @Override
            public Product apply(ProductDetail productDetail) throws Exception {
                return productDetail.getProduct();
            }
        });
    }

    @Override
    public Observable<List<Product>> getProductsFavorite(int idUser) {
        return mApi.getProductsFavorite(idUser).map(new Function<ProductResponse, List<Product>>() {
            @Override
            public List<Product> apply(ProductResponse productResponse) throws Exception {
                return productResponse.getProducts().getmProducts();
            }
        });
    }

    @Override
    public Observable<List<Product>> getProductsByUser(int idSeller,int idUser) {
        return mApi.getProductsByUser(idSeller,idUser).map(new Function<ProductResponse, List<Product>>() {
            @Override
            public List<Product> apply(ProductResponse productResponse) throws Exception {
                return productResponse.getProducts().getmProducts();
            }
        });
    }

    @Override
    public Observable<BaseResponse> createProduct(int idUser, String name, int price, String described, int idCate,
                                                  String image, String address, String dimension, String weight,
                                                  String status) {
        return mApi.createProduct(idUser,name,price,described,idCate,image,address,dimension,weight,status);
    }

    @Override
    public Observable<List<Product>> getProductBuyProcessing(int idCustomer) {
        return mApi.getProductBuyProcessing(idCustomer).map(new Function<ProductResponse, List<Product>>() {
            @Override
            public List<Product> apply(ProductResponse productResponse) throws Exception {
                return productResponse.getProducts().getmProducts();
            }
        });
    }

    @Override
    public Observable<List<Product>> getProductBuySuccess(int idCustomer) {
        return mApi.getProductBuySuccess(idCustomer).map(new Function<ProductResponse, List<Product>>() {
            @Override
            public List<Product> apply(ProductResponse productResponse) throws Exception {
                return productResponse.getProducts().getmProducts();
            }
        });
    }

    @Override
    public Observable<List<Product>> getProductSellProcessing(int idUser) {

        return mApi.getProductSellProcessing(idUser).map(new Function<ProductResponse, List<Product>>() {
            @Override
            public List<Product> apply(ProductResponse productResponse) throws Exception {
                return productResponse.getProducts().getmProducts();
            }
        });
    }

    @Override
    public Observable<List<Product>> getProductSellSuccess(int idUser) {
        return mApi.getProductSellSuccess(idUser).map(new Function<ProductResponse, List<Product>>() {
            @Override
            public List<Product> apply(ProductResponse productResponse) throws Exception {
                return productResponse.getProducts().getmProducts();
            }
        });
    }

    @Override
    public Observable<BaseResponse> acceptSellProduct(int idProduct) {
        return mApi.acceptSellProdct(idProduct);
    }

    @Override
    public Observable<BaseResponse> buyProduct(int idProduct, int idCustomer, String shipAddress) {
        return mApi.buyProdct(idProduct,idCustomer,shipAddress);
    }

    @Override
    public Observable<BaseResponse> cancelSellProduct(int idProduct) {
        return mApi.cancelSellProdct(idProduct);
    }

    @Override
    public Observable<List<Product>> searchProducts(int idUser, String nameProduct, final String price) {
        return mApi.searchProducts(idUser,nameProduct,price).map(new Function<ProductResponse, List<Product>>() {
            @Override
            public List<Product> apply(ProductResponse productResponse) throws Exception {
                return productResponse.getProducts().getmProducts();
            }
        });
    }

    @Override
    public Observable<OrderDetail> getOrderDetail(int idProduct) {
        return mApi.getOrderDetail(idProduct).map(new Function<OrderDetailResponse, OrderDetail>() {
            @Override
            public OrderDetail apply(OrderDetailResponse orderDetailResponse) throws Exception {
                return orderDetailResponse.getOrderDetail();
            }
        });
    }
}
