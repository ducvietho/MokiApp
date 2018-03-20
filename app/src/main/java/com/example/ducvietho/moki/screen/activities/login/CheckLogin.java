package com.example.ducvietho.moki.screen.activities.login;

import android.content.Context;
import android.widget.Toast;

import com.example.ducvietho.moki.data.model.User;
import com.example.ducvietho.moki.data.model.UserResponse;
import com.example.ducvietho.moki.data.resource.remote.UserDataRepository;
import com.example.ducvietho.moki.data.resource.remote.UserDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.UserRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.home.HomeActivity;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;
import com.example.ducvietho.moki.utils.dialog.DialogLoginFail;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ducvietho.
 */

public class CheckLogin {
    private Context mContext;
    public User user = null;

    public CheckLogin(Context context) {
        this.mContext = context;
    }

    public void checkUser(final String phonenumber, final String pass) {
        if (phonenumber==null || pass==null) {
            Toast.makeText(mContext, "Please enter phone and password", Toast.LENGTH_LONG).show();
        } else {
            final DialogLoading loading = new DialogLoading(mContext);
            loading.showDialog();
            UserDataResource userDataResource =
                    new UserDataRepository(new UserRemoteDataResource(MokiServiceClient.getInstance()));
            CompositeDisposable mDisposable = new CompositeDisposable();
            mDisposable.add(userDataResource.userLogin(phonenumber, pass).subscribeOn(Schedulers.newThread())
                    .observeOn
                            (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<UserResponse>() {
                        @Override
                        public void onNext(UserResponse value) {
                            if (value.getCode() == 1000) {
                                UserSession userSession = new UserSession(mContext);
                                userSession.createUserLoginSession(value.getUser());
                                mContext.startActivity(new HomeActivity().getIntent(mContext));
                                
//                                sendToken(value.getUser().getToken());
                            }else {
                                loading.cancelDialog();
                                new DialogLoginFail(mContext).showdialog();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    }));
        }

    }

//    public void sendToken(String token) {
//        String fcmToken = FirebaseInstanceId.getInstance().getToken();
//        TokenDataResource resource = new TokenDataRepository(new TokenRemoteDataResource(MokiServiceClient
//                .getInstance()));
//        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        System.out.println("fcm_token:" + fcmToken + "token : " + token);
//        compositeDisposable.add(resource.sendToken(fcmToken, token).subscribeOn(Schedulers.newThread()).observeOn
//                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Token>() {
//            @Override
//            public void onNext(Token value) {
//                Toast.makeText(mContext, value.getMessage(), Toast.LENGTH_LONG).show();
//                System.out.print("Code:" + String.valueOf(value.getCode()));
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        }));
//    }
}
