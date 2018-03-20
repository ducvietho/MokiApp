package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.Comments;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 14/03/2018.
 */

public class CommentDataRepository implements CommentDataResource {
    private CommentDataResource mDataResource;

    public CommentDataRepository(CommentDataResource dataResource) {
        mDataResource = dataResource;
    }

    @Override
    public Observable<Comments> getCommentsByProduct(int idProduct) {
        return mDataResource.getCommentsByProduct(idProduct);
    }

    @Override
    public Observable<Comments> postCommentProduct(int idProduct, int iduser, String content, int lastId) {
        return mDataResource.postCommentProduct(idProduct,iduser,content,lastId);
    }
}
