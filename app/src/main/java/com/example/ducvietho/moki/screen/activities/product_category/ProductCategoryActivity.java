package com.example.ducvietho.moki.screen.activities.product_category;

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
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.data.model.ProductResponse;
import com.example.ducvietho.moki.data.resource.remote.CategoryDataRepository;
import com.example.ducvietho.moki.data.resource.remote.CategoryDataResource;
import com.example.ducvietho.moki.data.resource.remote.ProductDataRepository;
import com.example.ducvietho.moki.data.resource.remote.ProductDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.CategoryRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.ProductRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.home.HomeActivity;
import com.example.ducvietho.moki.screen.activities.postproduct.PostProductActivity;
import com.example.ducvietho.moki.screen.fragments.category.ProductRecycleAdapter;
import com.example.ducvietho.moki.utils.CacheData;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ProductCategoryActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String EXTRA_CATE = "category";
    private static final String EXTRA_CATE_ID = "category_ID";
    @BindView(R.id.imgLeft)
    CircleImageView mBack;
    @BindView(R.id.tv_category)
    FontTextView tvCategory;
    @BindView(R.id.rec_category)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    private int pageNumber = 2;
    private ProductDataResource mRepository;
    private CompositeDisposable mDisposable;
    private DialogLoading mDialogLoading;
    private UserSession mSession;
    private List<Product> mProducts = new ArrayList<>();
    private ProductRecycleAdapter adapter;
    public static Intent getIntent(Context context, String category,int idCategory) {
        Intent intent = new Intent(context, ProductCategoryActivity.class);
        intent.putExtra(EXTRA_CATE, category);
        intent.putExtra(EXTRA_CATE_ID, idCategory);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        ButterKnife.bind(this);
        String category = getIntent().getStringExtra(EXTRA_CATE);
        final int idCate = getIntent().getIntExtra(EXTRA_CATE_ID,0);
        tvCategory.setText(category);
        mBack.setOnClickListener(this);
        mRepository = new ProductDataRepository(new ProductRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        mSession = new UserSession(ProductCategoryActivity.this);
        getListProduct(idCate, mSession.getUserDetail().getId(), 1);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListProductRefresh(idCate, mSession.getUserDetail().getId(), 1);
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
    public void getListProduct(final int idCate, final int idUser, final int page) {
        mDialogLoading = new DialogLoading(ProductCategoryActivity.this);
        mDialogLoading.showDialog();
        mDisposable.add(mRepository.getProductByCateOnePage(idCate, idUser, page).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<ProductResponse>() {
            @Override
            public void onNext(final ProductResponse value) {
                mDialogLoading.cancelDialog();
                mProducts = value.getProducts().getmProducts();
                GridLayoutManager manager = new GridLayoutManager(ProductCategoryActivity.this, 2);
                mRecyclerView.setLayoutManager(manager);
                adapter = new ProductRecycleAdapter(mProducts, ProductCategoryActivity.this);
                mRecyclerView.setAdapter(adapter);
                adapter.setLoadMoreListener(new ProductRecycleAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        mRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                loadMore(idCate, idUser, value.getProducts().getLast_id());
                                pageNumber++;
                            }
                        });

                    }
                });

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));
    }

    public void getListProductRefresh(final int idCate, final int idUser, final int page) {

        mDisposable.add(mRepository.getProductByCateOnePage(idCate, idUser, page).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<ProductResponse>() {
            @Override
            public void onNext(final ProductResponse value) {
                pageNumber = 2;
                mRefreshLayout.setRefreshing(false);
                mProducts = value.getProducts().getmProducts();
                GridLayoutManager manager = new GridLayoutManager(ProductCategoryActivity.this, 2);
                mRecyclerView.setLayoutManager(manager);
                adapter = new ProductRecycleAdapter(mProducts, ProductCategoryActivity.this);
                mRecyclerView.setAdapter(adapter);
                adapter.setLoadMoreListener(new ProductRecycleAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        mRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                loadMore(idCate, idUser, value.getProducts().getLast_id());
                                pageNumber++;
                            }
                        });

                    }
                });

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));
    }

    public void loadMore(int idCate, int iduser, int lastId) {
        mProducts.add(null);
        adapter.notifyItemInserted(mProducts.size() - 1);
        mDisposable.add(mRepository.getProductByCatePages(idCate, iduser, pageNumber, lastId).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<ProductResponse>() {
            @Override
            public void onNext(ProductResponse value) {

                mProducts.remove(mProducts.size() - 1);
                if (value.getProducts().getmProducts().size() < 20) {
                    adapter.setMoreDataAvailable(false);
                    Toast.makeText(ProductCategoryActivity.this, "No More Data Available", Toast.LENGTH_LONG).show();
                } else {
                    mProducts.addAll(value.getProducts().getmProducts());
                    pageNumber++;
                }
                adapter.notifyDataChanged();
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
