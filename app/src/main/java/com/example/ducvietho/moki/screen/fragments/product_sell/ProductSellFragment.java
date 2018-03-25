package com.example.ducvietho.moki.screen.fragments.product_sell;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.data.resource.remote.ProductDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.ProductRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.productdetail.ProductDetailActivity;
import com.example.ducvietho.moki.screen.fragments.product_buy.ProductBuyAdapter;
import com.example.ducvietho.moki.utils.OnClickItemProduct;
import com.example.ducvietho.moki.utils.OnClickProcessProduct;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;

import java.util.ArrayList;
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
public class ProductSellFragment extends Fragment implements OnClickItemProduct,OnClickProcessProduct {

    private static final String BUNDLE_STAGE = "state";
    @BindView(R.id.rec_product_sell)
    RecyclerView mRecyclerView;
    View v;
    List<Product> mProducts = new ArrayList<>();
    int state;
    private ProductDataRepository mRepository;
    private CompositeDisposable mDisposable;
    private UserSession mUserSession;
    ProductSellProccessingAdapter adapterProccessing;

    public static ProductSellFragment getInstance(int state) {
        ProductSellFragment fragment = new ProductSellFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_STAGE, state);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_product_sell, container, false);
        ButterKnife.bind(this,v);
        state = getArguments().getInt(BUNDLE_STAGE);
        mRepository = new ProductDataRepository(new ProductRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        mUserSession = new UserSession(v.getContext());
        getProductsSell();
        return v;
    }
    @Override
    public void onClick(Product product) {
        startActivity(new ProductDetailActivity().getIntent(v.getContext(), product.getId()));
    }
    @Override
    public void acceptSell(Product product) {
        acceptSellProduct(product);
    }

    @Override
    public void cancelSell(Product product) {
        cancelSellProduct(product);
    }
    private void getProductsSell(){
        Observable<List<Product>> observable = null;
        if(state==1){
            observable = mRepository.getProductSellProcessing(mUserSession.getUserDetail().getId());
        }else {
            if(state==2){
                observable = mRepository.getProductSellSuccess(mUserSession.getUserDetail().getId());
            }

        }
        final DialogLoading loading  = new DialogLoading(v.getContext());
        loading.showDialog();
        mDisposable.add(observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Product>>() {
                    @Override
                    public void onNext(List<Product> value) {
                        loading.cancelDialog();
                        GridLayoutManager manager = new GridLayoutManager(v.getContext(),1);
                        mRecyclerView.setLayoutManager(manager);
                        mProducts = value;
                        if(state==1){

                             adapterProccessing = new ProductSellProccessingAdapter(mProducts, ProductSellFragment.this);
                            mRecyclerView.setAdapter(adapterProccessing);
                        }else {
                            if(state==2){
                                ProductBuyAdapter adapter = new ProductBuyAdapter(mProducts,ProductSellFragment.this);
                                mRecyclerView.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private void acceptSellProduct(final Product product){
        final DialogLoading loading  = new DialogLoading(v.getContext());
        loading.showDialog();
        mDisposable.add(mRepository.acceptSellProduct(product.getId()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse value) {
                        loading.cancelDialog();
                        mProducts.remove(product);
                        adapterProccessing.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
    private void cancelSellProduct(final Product product){
        final DialogLoading loading  = new DialogLoading(v.getContext());
        loading.showDialog();
        mDisposable.add(mRepository.cancelSellProduct(product.getId()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse value) {
                        loading.cancelDialog();
                        mProducts.remove(product);
                        adapterProccessing.notifyDataSetChanged();
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
