package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.Notification;
import com.example.ducvietho.moki.data.model.NotificationUnread;

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

    @Override
    public Observable<NotificationUnread> getMessageNotificationUnread(int idUser) {
        return mDataResource.getMessageNotificationUnread(idUser);
    }

    @Override
    public Observable<NotificationUnread> getNotificationUnread(int idUser) {
        return mDataResource.getNotificationUnread(idUser);
    }

    @Override
    public Observable<BaseResponse> setNotificationRead(int idUser) {
        return mDataResource.setNotificationRead(idUser);
    }

    @Override
    public Observable<BaseResponse> setMessageNotificationRead(int idNotifi) {
        return mDataResource.setMessageNotificationRead(idNotifi);
    }
}
