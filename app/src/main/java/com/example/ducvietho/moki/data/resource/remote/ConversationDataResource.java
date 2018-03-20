package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.MessageResponse;
import com.example.ducvietho.moki.data.model.Messages;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 17/03/2018.
 */

public interface ConversationDataResource {
    Observable<Messages> getMessagesConversation(int idProduct,int idUser1,int idUser2);
    Observable<MessageResponse> setMessageConversation(int idCoversation,int idUser,String message);
}
