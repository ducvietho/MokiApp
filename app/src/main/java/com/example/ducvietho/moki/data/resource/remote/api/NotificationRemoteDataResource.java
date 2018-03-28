package com.example.ducvietho.moki.data.resource.remote.api;

import com.example.ducvietho.moki.data.model.Notification;
import com.example.ducvietho.moki.data.model.NotificationResponse;
import com.example.ducvietho.moki.data.resource.remote.NotificationDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ducvietho on 28/03/2018.
 */

public class NotificationRemoteDataResource extends BaseRemoteDataResource implements NotificationDataResource {
    public NotificationRemoteDataResource(MokiApi api) {
        super(api);
    }

    @Override
    public Observable<List<Notification>> getNotification(int idUser) {
        return mApi.getNotification(idUser).map(new Function<NotificationResponse, List<Notification>>() {
            @Override
            public List<Notification> apply(NotificationResponse notificationResponse) throws Exception {
                return notificationResponse.getNotifications();
            }
        });
    }

    @Override
    public Observable<List<Notification>> getMessageNotification(int idUser) {
        return mApi.getMessageNotification(idUser).map(new Function<NotificationResponse, List<Notification>>() {
            @Override
            public List<Notification> apply(NotificationResponse notificationResponse) throws Exception {
                return notificationResponse.getNotifications();
            }
        });
    }
}
