package com.example.ducvietho.moki.screen.activities.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.User;
import com.example.ducvietho.moki.data.model.UserResponse;
import com.example.ducvietho.moki.data.resource.remote.FCMTokenDataRepository;
import com.example.ducvietho.moki.data.resource.remote.UserDataRepository;
import com.example.ducvietho.moki.data.resource.remote.UserDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.FCMTokenRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.UserRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.home.HomeActivity;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.customview.AutoHighLightTextview;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;
import com.example.ducvietho.moki.utils.dialog.DialogLoginFail;
import com.example.ducvietho.moki.utils.dialog.DialogNoLogin;
import com.example.ducvietho.moki.utils.dialog.DialogNotInfor;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTRA_STATE_NO_LOGIN= "state";
    @BindView(R.id.btnCancel)
    AutoHighLightTextview mCancel;
    @BindView(R.id.btnSignIn)
    AutoHighLightTextview mBtSignIn;
    @BindView(R.id.phone_number)
    EditText mPhone;
    @BindView(R.id.password)
    EditText mPass;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);
        UserSession userSession = new UserSession(this);
        boolean checkLogin = userSession.checkUserLogin();
        id = getIntent().getIntExtra(EXTRA_STATE_NO_LOGIN,0);
        if(checkLogin==true){
            startActivity(new HomeActivity().getIntent(LoginActivity.this));
        }
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new HomeActivity().getIntent(LoginActivity.this));
                UserSession userSession = new UserSession(LoginActivity.this);
                User user = new User();
                userSession.createUserLoginSession(user);
            }
        });
        mBtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPhone.getText().toString().equals("")||mPass.getText().toString().equals("")){
                    new DialogNotInfor(LoginActivity.this)
                            .showDialog("Hãy nhập đầy đủ thông tin số điện thoại và mật khẩu");
                }else {
                    checkUser(mPhone.getText().toString(),mPass.getText().toString(),id);
                }

            }
        });

        if(id==1){
            new DialogNoLogin(LoginActivity.this).showDialog();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        System.exit(0);
    }

    public Intent getIntent(Context context,int state) {
        Intent i = new Intent(context, LoginActivity.class);
        i.putExtra(EXTRA_STATE_NO_LOGIN,state);
        context.startActivity(i);
        return i;
    }
    public void checkUser(final String phonenumber, final String pass, final int state) {
        if (phonenumber==null || pass==null) {
            Toast.makeText(LoginActivity.this, "Please enter phone and password", Toast.LENGTH_LONG).show();
        } else {
            final DialogLoading loading = new DialogLoading(LoginActivity.this);
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
                                UserSession userSession = new UserSession(LoginActivity.this);
                                userSession.createUserLoginSession(value.getUser());
                                if(state==1){
                                    finish();

                                }else{
                                    startActivity(new HomeActivity().getIntent(LoginActivity.this));
                                }
                                sendToken(value.getUser().getId());
                            }else {
                                loading.cancelDialog();
                                new DialogLoginFail(LoginActivity.this).showdialog();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    }));
        }

    }
    private void sendToken(int idUser){
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        FCMTokenDataRepository repository  = new FCMTokenDataRepository(new FCMTokenRemoteDataResource
                (MokiServiceClient.getInstance()));
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(repository.createFCMToken(idUser,fcmToken).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse value) {
                        Toast.makeText(LoginActivity.this, value.getMessage(), Toast.LENGTH_LONG).show();
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
