package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.Notification;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 28/03/2018.
 */

public class NotificationDataRepository implements NotificationDataResource {
    private NotificationDataResource mDataResource;

    public NotificationDataRepository(NotificationDataResource dataResource) {
        mDataResource = dataResource;
    }

    @Override
    public Observable<List<Notification>> getNotification(int idUser) {
        return mDataResource.getNotification(idUser);
    }

    @Override
    public Observable<List<Notification>> getMessageNotification(int idUser) {
        return mDataResource.getMessageNotification(idUser);
    }
}
