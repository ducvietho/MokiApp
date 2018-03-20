package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.MessageResponse;
import com.example.ducvietho.moki.data.model.Messages;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 17/03/2018.
 */

public class ConversationDataRepository implements ConversationDataResource {
    private ConversationDataResource mDataResource;

    public ConversationDataRepository(ConversationDataResource dataResource) {
        mDataResource = dataResource;
    }

    @Override
    public Observable<Messages> getMessagesConversation(int idProduct, int idUser1, int idUser2) {
        return mDataResource.getMessagesConversation(idProduct,idUser1,idUser2);
    }

    @Override
    public Observable<MessageResponse> setMessageConversation(int idCoversation, int idUser, String message) {
        return mDataResource.setMessageConversation(idCoversation,idUser,message);
    }
}
