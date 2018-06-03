package com.example.ducvietho.moki.utils.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.NotificationData;
import com.example.ducvietho.moki.data.resource.remote.NotificationDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.NotificationRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.chat.ChatActivity;
import com.example.ducvietho.moki.screen.activities.comment.CommentActivity;
import com.example.ducvietho.moki.screen.activities.deal.DealActivity;
import com.example.ducvietho.moki.screen.activities.productdetail.ProductDetailActivity;
import com.example.ducvietho.moki.utils.Constants;
import com.example.ducvietho.moki.utils.UserSession;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @ducvietho
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyGcmListenerService";
    private UserSession mSession;

    @Override
    public void onMessageReceived(RemoteMessage message) {
        mSession = new UserSession(this);
        String image = message.getNotification().getIcon();
        String title = message.getNotification().getTitle();
        String text = message.getNotification().getBody();
        String sound = message.getNotification().getSound();
        int id = 0;
        final Map<String, String> data = message.getData();
        String noti = data.get("notifi");
        com.example.ducvietho.moki.data.model.Notification notification = new Gson().fromJson(noti, com.example.ducvietho.moki.data.model.Notification.class);
        Object obj = message.getData().get("id");
        if (obj != null) {
            id = Integer.valueOf(obj.toString());
        }

        this.sendNotification(new NotificationData(image, id, title, text, sound), notification);
    }

    private void sendNotification(final NotificationData notificationData, final com.example.ducvietho.moki.data.model.Notification notification) {
        Intent intent = new Intent();
        PendingIntent pendingIntent = null;
        NotificationDataRepository repository = new NotificationDataRepository(new NotificationRemoteDataResource
                (MokiServiceClient.getInstance()));
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(repository.setMessageNotificationRead(notification.getId()).subscribeOn(Schedulers.newThread())
                .observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<BaseResponse>() {
            @Override
            public void onNext(BaseResponse value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));
        switch (notification.getType()) {
            case 0:

                intent = new Intent(this,CommentActivity.class);
                intent.putExtra(Constants.EXTRA_ID,notification.getProductId());
                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                break;
            case 1:
                intent = new Intent(this,ProductDetailActivity.class);
                intent.putExtra(Constants.EXTRA_ID_PRODUCT,notification.getProductId());

                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                break;
            case 2:
                if (notification.getIsSeller() == 1) {
                    intent = new Intent(this,ChatActivity.class);
                    intent.putExtra(Constants.EXTRA_ID_SELLER,notification.getToId());
                    intent.putExtra(Constants.EXTRA_ID_CUSTOMER,notification.getFromId());
                    intent.putExtra(Constants.EXTRA_ID_PRODUCT,notification.getProductId());

                } else {
                    intent = new Intent(this,ChatActivity.class);
                    intent.putExtra(Constants.EXTRA_ID_SELLER,notification.getFromId());
                    intent.putExtra(Constants.EXTRA_ID_CUSTOMER,notification.getToId());
                    intent.putExtra(Constants.EXTRA_ID_PRODUCT,notification.getProductId());      }

                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                break;
            case 3:
                intent = new Intent(this, DealActivity.class);
                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                break;
            default:
                break;

        }
        NotificationCompat.Builder notificationBuilder = null;
        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notificationData.getTitle())
                .setContentText(notificationData.getTextMessage())
                .setSound(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.jingle_bells_sms))
                .setAutoCancel(true).setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);

        if (notificationBuilder != null) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationData.getId(), notificationBuilder.build());
        } else {
            Log.d(TAG, "");
        }
    }
}
