package com.example.ducvietho.moki.screen.fragments.category;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.data.model.ProductResponse;
import com.example.ducvietho.moki.data.resource.remote.ProductDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.ProductRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.home.HomeActivity;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    public static final String EXTRA_POS = "position";
    @BindView(R.id.rec_catagory)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.layout_no_product)
    LinearLayout mLayout;
    private List<Product> mProducts = new ArrayList<>();
    private View v;
    private UserSession mSession;
    private ProductDataRepository mRepository;
    private CompositeDisposable mDisposable;
    private DialogLoading mDialogLoading;
    private ProductRecycleAdapter adapter;
    private int pageNumber = 2;

    public static CategoryFragment newInstance(int postion) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_POS, postion);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, v);

        mRepository = new ProductDataRepository(new ProductRemoteDataResource(MokiServiceClient.getInstance()));
        mSession = new UserSession(v.getContext());
        mDisposable = new CompositeDisposable();
        final int idCate = getArguments().getInt(EXTRA_POS);
        getListProduct(idCate, mSession.getUserDetail().getId(), 1);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListProductRefresh(idCate, mSession.getUserDetail().getId(), 1);
            }
        });
        return v;
    }

    public void getListProduct(final int idCate, final int idUser, final int page) {

        mDisposable.add(mRepository.getProductByCateOnePage(idCate, idUser, page).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<ProductResponse>() {
            @Override
            public void onNext(final ProductResponse value) {

                HomeActivity.mDialogLoading.cancelDialog();
                mProducts = value.getProducts().getmProducts();
                GridLayoutManager manager = new GridLayoutManager(v.getContext(), 2);
                mRecyclerView.setLayoutManager(manager);
                adapter = new ProductRecycleAdapter(mProducts, v.getContext());
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
                GridLayoutManager manager = new GridLayoutManager(v.getContext(), 2);
                mRecyclerView.setLayoutManager(manager);
                adapter = new ProductRecycleAdapter(mProducts, v.getContext());
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
                    Toast.makeText(v.getContext(), "No More Data Available", Toast.LENGTH_LONG).show();
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
