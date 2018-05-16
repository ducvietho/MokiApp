package com.example.ducvietho.moki.screen.activities.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.Notification;
import com.example.ducvietho.moki.data.resource.remote.NotificationDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.NotificationRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.chat.ChatActivity;
import com.example.ducvietho.moki.screen.activities.comment.CommentActivity;
import com.example.ducvietho.moki.screen.activities.productdetail.ProductDetailActivity;
import com.example.ducvietho.moki.utils.OnItemtClick;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener, OnItemtClick<Notification> {
    private static final String EXTRA_TYPE = "type";
    @BindView(R.id.imgLeft)
    ImageView mImgBack;
    @BindView(R.id.rec_notification)
    RecyclerView mRecyclerView;
    @BindView(R.id.tvTitle)
    FontTextView mTitle;
    private NotificationDataRepository mRepository;
    private CompositeDisposable mDisposable;
    private UserSession mUserSession;
    NotificationAdapter adapter;
    List<Notification> mList = new ArrayList<>();
    int type;

    public static Intent getIntent(Context context, int type) {
        Intent intent = new Intent(context, NotificationActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        mImgBack.setOnClickListener(this);
        mRepository = new NotificationDataRepository(new NotificationRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        mUserSession = new UserSession(NotificationActivity.this);
        type = getIntent().getIntExtra(EXTRA_TYPE, 0);
        switch (type) {
            case 0:
                getNotification();
                break;
            case 1:
                getMessageNotification();
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgLeft:
                finish();
                break;
        }
    }

    @Override
    public void onClick(Notification notification) {

        switch (notification.getType()) {
            case 0:
                startActivity(new CommentActivity().getIntent(NotificationActivity.this, notification.getProductId()));
                break;
            case 1:
                startActivity(new ProductDetailActivity().getIntent(NotificationActivity.this, notification.getProductId()));
                break;
            case 2:
                setMessageRead(notification.getId());
                if (notification.getIsSeller() == 1) {
                    startActivity(new ChatActivity().getIntent(NotificationActivity.this, notification.getToId(), notification.getFromId(), notification.getProductId()));
                } else {
                    startActivity(new ChatActivity().getIntent(NotificationActivity.this, notification.getFromId(), notification.getToId(), notification.getProductId()));
                }
                break;
        }

    }

    private void getNotification() {
        final DialogLoading loading = new DialogLoading(NotificationActivity.this);
        loading.showDialog();
        mDisposable.add(mRepository.getNotification(mUserSession.getUserDetail().getId()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Notification>>() {
            @Override
            public void onNext(List<Notification> value) {
                loading.cancelDialog();
                mList = value;
                mTitle.setText("Thông báo");
                GridLayoutManager manager = new GridLayoutManager(NotificationActivity.this, 1);
                mRecyclerView.setLayoutManager(manager);
                adapter = new NotificationAdapter(mList, NotificationActivity.this);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));

    }

    private void getMessageNotification() {
        final DialogLoading loading = new DialogLoading(NotificationActivity.this);
        loading.showDialog();
        mDisposable.add(mRepository.getMessageNotification(mUserSession.getUserDetail().getId()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Notification>>() {
            @Override
            public void onNext(List<Notification> value) {
                loading.cancelDialog();
                mList = value;
                mTitle.setText("Tin nhắn");
                GridLayoutManager manager = new GridLayoutManager(NotificationActivity.this, 1);
                mRecyclerView.setLayoutManager(manager);
                adapter = new NotificationAdapter(mList, NotificationActivity.this);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));

    }

    private void setMessageRead(int idNotifi) {
        mDisposable.add(mRepository.setMessageNotificationRead(idNotifi).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<BaseResponse>() {
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
    }
}
