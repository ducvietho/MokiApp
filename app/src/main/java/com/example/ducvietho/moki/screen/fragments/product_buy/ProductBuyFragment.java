package com.example.ducvietho.moki.screen.fragments.product_buy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.data.resource.remote.ProductDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.ProductRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.productdetail.ProductDetailActivity;
import com.example.ducvietho.moki.utils.OnClickItemProduct;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductBuyFragment extends Fragment implements OnClickItemProduct {
    private static final String BUNDLE_STAGE = "state";
    View v;
    int state;
    ProductDataRepository mRepository;
    CompositeDisposable mDisposable;
    UserSession mUserSession;
    @BindView(R.id.rec_product_buy)
    RecyclerView mRecyclerView;

    public static ProductBuyFragment getInstance(int state) {
        ProductBuyFragment fragment = new ProductBuyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_STAGE, state);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_product_buy, container, false);
        ButterKnife.bind(this, v);
        state = getArguments().getInt(BUNDLE_STAGE, 0);
        mRepository = new ProductDataRepository(new ProductRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        mUserSession = new UserSession(v.getContext());
        getProductBuy();
        return v;
    }

    @Override
    public void onClick(Product product) {
        startActivity(new ProductDetailActivity().getIntent(v.getContext(), product.getId()));
    }

    private void getProductBuy() {
        Observable<List<Product>> observable = null;
        if (state == 1) {
            observable = mRepository.getProductBuyProcessing(mUserSession.getUserDetail().getId());
        } else {
            observable = mRepository.getProductBuySuccess(mUserSession.getUserDetail().getId());
        }
        final DialogLoading loading  = new DialogLoading(v.getContext());
        loading.showDialog();
        mDisposable.add(observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Product>>() {
            @Override
            public void onNext(List<Product> value) {
                GridLayoutManager layoutManager = new GridLayoutManager(v.getContext(),1);
                mRecyclerView.setLayoutManager(layoutManager);
                ProductBuyAdapter adapter = new ProductBuyAdapter(value,ProductBuyFragment.this);
                mRecyclerView.setAdapter(adapter);
                loading.cancelDialog();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(v.getContext(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
