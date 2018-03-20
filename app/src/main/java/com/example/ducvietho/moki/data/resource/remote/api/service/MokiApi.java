package com.example.ducvietho.moki.data.resource.remote.api.service;


import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.CategoryResponse;
import com.example.ducvietho.moki.data.model.CommentResponse;
import com.example.ducvietho.moki.data.model.LikeReponse;
import com.example.ducvietho.moki.data.model.MessageResponse;
import com.example.ducvietho.moki.data.model.OrderAddressResponse;
import com.example.ducvietho.moki.data.model.ProductDetail;
import com.example.ducvietho.moki.data.model.ProductResponse;
import com.example.ducvietho.moki.data.model.UserResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by ducvietho on 11/21/2017.
 */

public interface MokiApi {
    @POST("category/get")
    Observable<CategoryResponse> getCategories();
    @FormUrlEncoded
    @POST("user/login")
    Observable<UserResponse> userLogin(@Field("phonenumber") String phonenumber, @Field("password") String pass);
    @FormUrlEncoded
    @POST("products/category")
    Observable<ProductResponse> getProductByCateOnePage(@Field("id_category") int idCate,@Field("idUser") int idUser,
                                                        @Field("page") int page);
    @FormUrlEncoded
    @POST("products/category")
    Observable<ProductResponse> getProductByCatePages(@Field("id_category") int idCate,@Field("idUser") int idUser,
                                                      @Field("page") int page,@Field("last_id") int lastId);
    @FormUrlEncoded
    @POST("getCommentsByProduct")
    Observable<CommentResponse> getCommentsByProduct (@Field("product_id")int id);
    @FormUrlEncoded
    @POST("productdetail")
    Observable<ProductDetail> getProductDetail(@Field("id_product") int idProduct,@Field("id_user") int idUser);
    @FormUrlEncoded
    @POST("postCommentProduct")
    Observable<CommentResponse> postCommentProduct(@Field("product_id") int idProduct,@Field("user_id")int idUser,
                                                   @Field("content")String content,@Field("last_id")int lastId);
    @FormUrlEncoded
    @POST("like/product")
    Observable<LikeReponse> likeProduct(@Field("product_id") int idProduct,@Field("user_id") int idUser);
    @FormUrlEncoded
    @POST("unlike/product")
    Observable<LikeReponse> unlikeProduct(@Field("product_id") int idProduct,@Field("user_id") int idUser);
    @FormUrlEncoded
    @POST("products/favorite")
    Observable<ProductResponse> getProductsFavorite(@Field("user_id") int idUser);
    @FormUrlEncoded
    @POST("products/seller")
    Observable<ProductResponse> getProductsByUser(@Field("seller_id")int idSeller,@Field("user_id") int idUser);
    @FormUrlEncoded
    @POST("conversation/messages")
    Observable<MessageResponse> getMessagesConversation(@Field("product_id") int idProduct, @Field("user_id1") int idUser1,
                                                        @Field("user_id2") int idUser2);
    @FormUrlEncoded
    @POST("conversation/set_message")
    Observable<MessageResponse> setMessageConversation(@Field("conversation_id") int idConver,@Field("user_id") int idUser,
                                                 @Field("message") String message);
    @FormUrlEncoded
    @POST("user/get_user_info")
    Observable<UserResponse> getUserInfor(@Field("user_id") int idUser);
    @FormUrlEncoded
    @POST("address/get")
    Observable<OrderAddressResponse> getAddress(@Field("user_id") int idUser);
    @Multipart
    @POST("upload/image/product")
    Observable<BaseResponse> uploadImageProduct(@Part MultipartBody.Part file);
}
