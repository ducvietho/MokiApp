package com.example.ducvietho.moki.screen.activities.district;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.District;
import com.example.ducvietho.moki.data.resource.remote.AddressDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.AddressRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.utils.Constants;
import com.example.ducvietho.moki.utils.OnItemtClick;
import com.example.ducvietho.moki.utils.customview.FontEditText;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class DistrictActivity extends AppCompatActivity implements View.OnClickListener,OnItemtClick<District> {

    @BindView(R.id.imgLeft)
    CircleImageView mImgBack;
    @BindView(R.id.list_district)
    StickyListHeadersListView mListView;
    @BindView(R.id.edtKeyword)
    FontEditText mEditText;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    private DistrictAdapter mAdapter;
    private AddressDataRepository mRepository;
    private CompositeDisposable mDisposable;
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, DistrictActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        ButterKnife.bind(DistrictActivity.this);
        mImgBack.setOnClickListener(this);
        mDisposable = new CompositeDisposable();
        mRepository = new AddressDataRepository(new AddressRemoteDataResource(MokiServiceClient.getInstance()));
        getDistricts();
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchDistricts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDistrictsRefresh();
            }
        });
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
    public void onClick(District object) {
        Intent intent = getIntent();
        intent.putExtra(Constants.EXTRA_DISTRICT,new Gson().toJson(object));
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
    public void getDistricts(){
        final DialogLoading loading = new DialogLoading(DistrictActivity.this);
        loading.showDialog();
        mDisposable.add(mRepository.getDistricts().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<District>>() {
                    @Override
                    public void onNext(List<District> value) {
                        mRefreshLayout.setRefreshing(false);
                        loading.cancelDialog();
                        mAdapter = new DistrictAdapter(DistrictActivity.this,value,DistrictActivity.this);
                        mListView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
    public void getDistrictsRefresh(){

        mDisposable.add(mRepository.getDistricts().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<District>>() {
                    @Override
                    public void onNext(List<District> value) {
                        mRefreshLayout.setRefreshing(false);
                        mAdapter = new DistrictAdapter(DistrictActivity.this,value,DistrictActivity.this);
                        mListView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
    public void searchDistricts(String district){
        mDisposable.add(mRepository.searchDistricts(district).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers
                .mainThread())
                .subscribeWith(new DisposableObserver<List<District>>() {
                    @Override
                    public void onNext(List<District> value) {
                        mAdapter = new DistrictAdapter(DistrictActivity.this,value,DistrictActivity.this);
                        mListView.setAdapter(mAdapter);
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
