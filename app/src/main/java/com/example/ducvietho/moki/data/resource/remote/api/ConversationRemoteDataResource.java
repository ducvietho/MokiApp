package com.example.ducvietho.moki.data.resource.remote.api;

import com.example.ducvietho.moki.data.model.MessageResponse;
import com.example.ducvietho.moki.data.model.Messages;
import com.example.ducvietho.moki.data.resource.remote.ConversationDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiApi;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ducvietho on 17/03/2018.
 */

public class ConversationRemoteDataResource extends BaseRemoteDataResource implements ConversationDataResource {
    public ConversationRemoteDataResource(MokiApi api) {
        super(api);
    }

    @Override
    public Observable<Messages> getMessagesConversation(int idProduct, int idUser1, int idUser2) {
        return mApi.getMessagesConversation(idProduct,idUser1,idUser2).map(new Function<MessageResponse, Messages>() {
            @Override
            public Messages apply(MessageResponse messageResponse) throws Exception {
                return messageResponse.getMessages();
            }
        });
    }

    @Override
    public Observable<MessageResponse> setMessageConversation(int idCoversation, int idUser, String message) {
        return mApi.setMessageConversation(idCoversation,idUser,message);
    }
}
