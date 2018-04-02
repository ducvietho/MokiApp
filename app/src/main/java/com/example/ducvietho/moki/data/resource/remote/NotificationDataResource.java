package com.example.ducvietho.moki.data.resource.remote;

import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.Notification;
import com.example.ducvietho.moki.data.model.NotificationUnread;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 28/03/2018.
 */

public interface NotificationDataResource {
    Observable<List<Notification>> getNotification(int idUser);
    Observable<List<Notification>> getMessageNotification(int idUser);
    Observable<NotificationUnread> getMessageNotificationUnread(int idUser);
    Observable<NotificationUnread> getNotificationUnread(int idUser);
    Observable<BaseResponse> setNotificationRead(int idUser);
    Observable<BaseResponse> setMessageNotificationRead(int idNotifi);
}
