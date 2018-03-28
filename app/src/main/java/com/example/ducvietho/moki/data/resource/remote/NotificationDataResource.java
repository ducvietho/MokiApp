package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.Notification;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 28/03/2018.
 */

public interface NotificationDataResource {
    Observable<List<Notification>> getNotification(int idUser);
    Observable<List<Notification>> getMessageNotification(int idUser);
}
