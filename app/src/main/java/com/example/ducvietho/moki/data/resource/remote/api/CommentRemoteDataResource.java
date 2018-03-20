package com.example.ducvietho.moki.data.resource.remote.api;

import com.example.ducvietho.moki.data.model.CommentResponse;
import com.example.ducvietho.moki.data.model.Comments;
import com.example.ducvietho.moki.data.resource.remote.CommentDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiApi;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ducvietho on 14/03/2018.
 */

public class CommentRemoteDataResource extends BaseRemoteDataResource implements CommentDataResource {
    public CommentRemoteDataResource(MokiApi api) {
        super(api);
    }

    @Override
    public Observable<Comments> getCommentsByProduct(int idProduct) {
        return mApi.getCommentsByProduct(idProduct).map(new Function<CommentResponse, Comments>() {
            @Override
            public Comments apply(CommentResponse commentResponse) throws Exception {
                return commentResponse.getComments();
            }
        });
    }

    @Override
    public Observable<Comments> postCommentProduct(int idProduct, int iduser, String content, int lastId) {
        return mApi.postCommentProduct(idProduct,iduser,content,lastId).map(new Function<CommentResponse, Comments>() {
            @Override
            public Comments apply(CommentResponse commentResponse) throws Exception {
                return commentResponse.getComments();
            }
        });
    }
}
