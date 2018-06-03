package com.example.ducvietho.moki.screen.activities.village;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Village;
import com.example.ducvietho.moki.data.resource.remote.AddressDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.AddressRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.district.DistrictActivity;
import com.example.ducvietho.moki.screen.activities.district.DistrictAdapter;
import com.example.ducvietho.moki.screen.activities.search_result.SearchResultActivity;
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

public class VillageActivity extends AppCompatActivity implements View.OnClickListener,OnItemtClick<Village>{
    private static final String EXTRA_ID = "id";
    @BindView(R.id.imgLeft)
    CircleImageView mImgBack;
    @BindView(R.id.list_village)
    StickyListHeadersListView mListView;
    @BindView(R.id.edtKeyword)
    FontEditText mEditText;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    int idDistrict;
    private VillageAdapter mAdapter;
    private AddressDataRepository mRepository;
    private CompositeDisposable mDisposable;
    public static Intent getIntent(Context context,int idDistrict) {
        Intent intent = new Intent(context, VillageActivity.class);
        intent.putExtra(EXTRA_ID,idDistrict);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village);
        ButterKnife.bind(this);
        mImgBack.setOnClickListener(this);
        idDistrict = getIntent().getIntExtra(EXTRA_ID,0);
        mDisposable = new CompositeDisposable();
        mRepository = new AddressDataRepository(new AddressRemoteDataResource(MokiServiceClient.getInstance()));
        getVillages(idDistrict);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchVillages(s.toString(),idDistrict);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getVillagesRefresh(idDistrict);
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
    public void onClick(Village object) {
        Intent intent = getIntent();
        intent.putExtra(Constants.EXTRA_VILLAGE,new Gson().toJson(object));
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
    public void getVillages(int idDistrict){
        final DialogLoading loading = new DialogLoading(VillageActivity.this);
        loading.showDialog();
        mDisposable.add(mRepository.getVillages(idDistrict).subscribeOn(Schedulers.newThread()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Village>>() {
            @Override
            public void onNext(List<Village> value) {
                mRefreshLayout.setRefreshing(false);
                loading.cancelDialog();
                mAdapter = new VillageAdapter(VillageActivity.this,VillageActivity.this,value);
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void onError(Throwable e) {
                loading.cancelDialog();
                Toast.makeText(VillageActivity.this,"Lỗi kết nối !",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {

            }
        }));
    }
    public void getVillagesRefresh(int idDistrict){

        mDisposable.add(mRepository.getVillages(idDistrict).subscribeOn(Schedulers.newThread()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Village>>() {
            @Override
            public void onNext(List<Village> value) {
                mRefreshLayout.setRefreshing(false);
                mAdapter = new VillageAdapter(VillageActivity.this,VillageActivity.this,value);
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
    public void searchVillages(String village,int idDistrict){
        mDisposable.add(mRepository.searchVillages(village,idDistrict).subscribeOn(Schedulers.newThread()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Village>>() {
            @Override
            public void onNext(List<Village> value) {
                mAdapter = new VillageAdapter(VillageActivity.this,VillageActivity.this,value);
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
