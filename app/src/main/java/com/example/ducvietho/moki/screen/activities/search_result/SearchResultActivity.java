package com.example.ducvietho.moki.screen.activities.search_result;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.data.resource.remote.ProductDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.ProductRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.search.SearchActivity;
import com.example.ducvietho.moki.screen.fragments.category.ProductRecycleAdapter;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String EXTRA_NAME = "nameProduct";
    private static final String EXTRA_PRICE = "price";
    @BindView(R.id.btnNavLeft)
    ImageView imgBack;
    @BindView(R.id.tvName)
    FontTextView tvName;
    @BindView(R.id.rec_result_search)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_no_item)
    LinearLayout layoutNoItem;
    String price,nameProduct;
    ProductDataRepository mRepository;
    CompositeDisposable mDisposable;
    UserSession mUserSession;
    public static Intent getIntent(Context context,String nameProduct,String price) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(EXTRA_NAME,nameProduct);
        intent.putExtra(EXTRA_PRICE,price);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        imgBack.setOnClickListener(this);
        price = getIntent().getStringExtra(EXTRA_PRICE);
        nameProduct = getIntent().getStringExtra(EXTRA_NAME);
        tvName.setText(nameProduct+","+price);
        mRepository = new ProductDataRepository(new ProductRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        mUserSession = new UserSession(SearchResultActivity.this);
        getProductsSearch();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNavLeft:
                finish();
                break;
        }
    }
    public void getProductsSearch(){
        final DialogLoading loading = new DialogLoading(SearchResultActivity.this);
        loading.showDialog();
        mDisposable.add(mRepository.searchProducts(mUserSession.getUserDetail().getId(),nameProduct,price)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Product>>() {
                    @Override
                    public void onNext(List<Product> value) {
                        loading.cancelDialog();
                        if(value.size()>0){

                            GridLayoutManager manager = new GridLayoutManager(SearchResultActivity.this,2);
                            mRecyclerView.setLayoutManager(manager);
                            ProductRecycleAdapter adapter = new ProductRecycleAdapter(value,SearchResultActivity.this);
                            mRecyclerView.setAdapter(adapter);
                        }else {
                            layoutNoItem.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.cancelDialog();
                        Toast.makeText(SearchResultActivity.this,"Lỗi kết nối !",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
