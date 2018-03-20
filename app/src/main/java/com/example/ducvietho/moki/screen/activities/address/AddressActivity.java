package com.example.ducvietho.moki.screen.activities.address;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.OrderAddress;
import com.example.ducvietho.moki.data.resource.remote.AddressDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.AddressRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.postproduct.PostProductActivity;
import com.example.ducvietho.moki.utils.Constants;
import com.example.ducvietho.moki.utils.OnItemtClick;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener, OnItemtClick<OrderAddress> {
    @BindView(R.id.imgLeft)
    CircleImageView mImageView;
    @BindView(R.id.rec_address)
    RecyclerView mRecyclerView;
    private AddressAdapter mAdapter;
    private List<OrderAddress> mAddresses = new ArrayList<>();
    private UserSession mUserSession;
    private AddressDataRepository mDataRepository;
    private CompositeDisposable mDisposable;
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AddressActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(AddressActivity.this);
        mUserSession = new UserSession(AddressActivity.this);
        mDataRepository = new AddressDataRepository(new AddressRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        mImageView.setOnClickListener(this);
        getAddress(mUserSession.getUserDetail().getId());
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
    public void onClick(OrderAddress address) {
        int position = mAddresses.indexOf(address);
        SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_ADDRESS,MODE_PRIVATE).edit();
        editor.putInt(Constants.EXTRA_ADDRESS,position);
        editor.apply();
        Intent intent = getIntent();
        String add = new Gson().toJson(address);
        intent.putExtra("address",add);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    private void getAddress(int idUser) {
        final DialogLoading loading = new DialogLoading(AddressActivity.this);
        loading.showDialog();
        mDisposable.add(mDataRepository.getAddress(idUser).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<OrderAddress>>() {
            @Override
            public void onNext(List<OrderAddress> value) {
                loading.cancelDialog();
                GridLayoutManager manager = new GridLayoutManager(AddressActivity.this, 1);
                mRecyclerView.setLayoutManager(manager);
                mAddresses = value;
                mAdapter = new AddressAdapter(mAddresses, AddressActivity.this);
                mRecyclerView.setAdapter(mAdapter);
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
