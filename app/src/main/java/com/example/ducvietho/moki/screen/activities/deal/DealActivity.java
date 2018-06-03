package com.example.ducvietho.moki.screen.activities.deal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.data.resource.remote.ProductDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.ProductRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.comment.CommentActivity;
import com.example.ducvietho.moki.screen.activities.search_result.SearchResultActivity;
import com.example.ducvietho.moki.screen.fragments.product_buy.ProductBuyAdapter;
import com.example.ducvietho.moki.screen.fragments.product_sell.ProductSellFragment;
import com.example.ducvietho.moki.screen.fragments.product_sell.ProductSellProccessingAdapter;
import com.example.ducvietho.moki.utils.OnClickProcessProduct;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.ducvietho.moki.utils.Constants.EXTRA_ID;

public class DealActivity extends AppCompatActivity implements View.OnClickListener,OnClickProcessProduct {
    @BindView(R.id.btnNavLeft)
    ImageView btBack;
    @BindView(R.id.rec_sell)
    RecyclerView mRecyclerView;
    List<Product> mProducts = new ArrayList<>();
    private ProductDataRepository mRepository;
    private CompositeDisposable mDisposable;
    private UserSession mUserSession;
    ProductSellProccessingAdapter adapterProccessing;

    public Intent getIntent(Context context) {
        Intent i = new Intent(context, DealActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);
        ButterKnife.bind(this);
        btBack.setOnClickListener(this);
        mRepository = new ProductDataRepository(new ProductRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        mUserSession = new UserSession(DealActivity.this);
        final DialogLoading loading = new DialogLoading(DealActivity.this);
        loading.showDialog();
        mDisposable.add(mRepository.getProductSellProcessing(mUserSession.getUserDetail().getId()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Product>>() {
            @Override
            public void onNext(List<Product> value) {
                loading.cancelDialog();
                GridLayoutManager manager = new GridLayoutManager(DealActivity.this, 1);
                mRecyclerView.setLayoutManager(manager);
                mProducts = value;

                adapterProccessing = new ProductSellProccessingAdapter(mProducts, DealActivity.this);
                mRecyclerView.setAdapter(adapterProccessing);

            }

            @Override
            public void onError(Throwable e) {
                loading.cancelDialog();
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNavLeft:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void acceptSell(final Product product) {
        final DialogLoading loading = new DialogLoading(DealActivity.this);
        loading.showDialog();
        mDisposable.add(mRepository.acceptSellProduct(product.getId()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<BaseResponse>() {
            @Override
            public void onNext(BaseResponse value) {
                loading.cancelDialog();
                mProducts.remove(product);
                adapterProccessing.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                loading.cancelDialog();
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void cancelSell(final Product product) {
        final DialogLoading loading = new DialogLoading(DealActivity.this);
        loading.showDialog();
        mDisposable.add(mRepository.cancelSellProduct(product.getId()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<BaseResponse>() {
            @Override
            public void onNext(BaseResponse value) {
                loading.cancelDialog();
                mProducts.remove(product);
                adapterProccessing.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                loading.cancelDialog();
                Toast.makeText(DealActivity.this,"Lỗi kết nối !",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
