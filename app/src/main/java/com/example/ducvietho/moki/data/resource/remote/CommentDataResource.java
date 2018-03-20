package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.Comments;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 14/03/2018.
 */

public interface CommentDataResource {
    Observable<Comments> getCommentsByProduct(int idProduct);
    Observable<Comments> postCommentProduct(int idProduct,int iduser,String content,int lastId);
}
