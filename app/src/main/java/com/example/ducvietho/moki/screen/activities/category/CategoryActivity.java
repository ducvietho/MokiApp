package com.example.ducvietho.moki.screen.activities.category;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Category;
import com.example.ducvietho.moki.data.resource.remote.CategoryDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.CategoryRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.address.AddressActivity;
import com.example.ducvietho.moki.screen.activities.postproduct.PostProductActivity;
import com.example.ducvietho.moki.screen.activities.search_result.SearchResultActivity;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.imgLeft)
    CircleImageView back;
    @BindView(R.id.rec_category)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    private CategoryDataRepository mRepository;
    private CompositeDisposable mDisposable;
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,CategoryActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(CategoryActivity.this);
        mRepository = new CategoryDataRepository(new CategoryRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        getCategory();
        back.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCategory();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgLeft:
                finish();
                break;
            default:
                break;
        }
    }
    private void getCategory(){
        final DialogLoading loading = new DialogLoading(CategoryActivity.this);
        loading.showDialog();
        mDisposable.add(mRepository.getCategories().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers
                .mainThread()).subscribeWith(new DisposableObserver<List<Category>>() {
            @Override
            public void onNext(List<Category> value) {
                loading.cancelDialog();
                mRefreshLayout.setRefreshing(false);
                GridLayoutManager manager = new GridLayoutManager(CategoryActivity.this,1);
                mRecyclerView.setLayoutManager(manager);
                CatagoryRecyclerAdapter adapter = new CatagoryRecyclerAdapter(value,CategoryActivity.this);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable e) {
                loading.cancelDialog();
                Toast.makeText(CategoryActivity.this,"Lỗi kết nối !",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
