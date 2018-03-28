package com.example.ducvietho.moki.screen.activities.notification;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Notification;
import com.example.ducvietho.moki.data.resource.remote.NotificationDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.NotificationRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.comment.CommentActivity;
import com.example.ducvietho.moki.screen.activities.postproduct.PostProductActivity;
import com.example.ducvietho.moki.utils.OnItemtClick;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener,OnItemtClick<Notification> {
    @BindView(R.id.imgLeft)
    ImageView mImgBack;
    @BindView(R.id.rec_notification)
    RecyclerView mRecyclerView;
    private NotificationDataRepository mRepository;
    private CompositeDisposable mDisposable;
    private UserSession mUserSession;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, NotificationActivity.class);
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
        getNotification();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgLeft:
                finish();
                break;
        }
    }

    @Override
    public void onClick(Notification object) {
        startActivity(new CommentActivity().getIntent(NotificationActivity.this,object.getProductId()));
    }
    private void getNotification(){
        final DialogLoading loading = new DialogLoading(NotificationActivity.this);
        loading.showDialog();
        mDisposable.add(mRepository.getNotification(mUserSession.getUserDetail().getId())
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Notification>>() {
                    @Override
                    public void onNext(List<Notification> value) {
                        loading.cancelDialog();
                        GridLayoutManager manager = new GridLayoutManager(NotificationActivity.this,1);
                        mRecyclerView.setLayoutManager(manager);
                        NotificationAdapter adapter = new NotificationAdapter(value,NotificationActivity.this);
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
}
