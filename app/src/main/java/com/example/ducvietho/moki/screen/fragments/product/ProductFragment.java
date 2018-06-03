package com.example.ducvietho.moki.screen.fragments.product;


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
import com.example.ducvietho.moki.screen.fragments.category.ProductRecycleAdapter;
import com.example.ducvietho.moki.utils.OnClickItemProduct;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;

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
public class ProductFragment extends Fragment implements OnClickItemProduct {
    private static final String BUNDLE_ID_SELLER = "idSeller";
    private static final String BUNDLE_STAGE = "state";
    @BindView(R.id.rec_product)
    RecyclerView mRecyclerView;
    private int state;
    private ProductDataRepository mRepository;
    private CompositeDisposable mDisposable;
    private UserSession mUserSession;
    private DialogLoading mLoading;
    View v;
    public static ProductFragment getInstance(int state,int idSeller){
        ProductFragment fragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_ID_SELLER, idSeller);
        bundle.putInt(BUNDLE_STAGE,state);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this,v);
        mLoading = new DialogLoading(v.getContext());
        mLoading.showDialog();
        mUserSession = new UserSession(v.getContext());
        mRepository = new ProductDataRepository(new ProductRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        int idSeller = getArguments().getInt(BUNDLE_ID_SELLER,0);
        state = getArguments().getInt(BUNDLE_STAGE,0);
        getProductsByUser(idSeller,mUserSession.getUserDetail().getId());
        return v;
    }
    @Override
    public void onClick(Product product) {
        startActivity(new ProductDetailActivity().getIntent(v.getContext(),product.getId()));
    }
    public void getProductsByUser(int idSeller,int idUser){
        mDisposable.add(mRepository.getProductsByUser(idSeller,idUser).subscribeOn(Schedulers.newThread()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Product>>() {
            @Override
            public void onNext(List<Product> value) {
                mLoading.cancelDialog();
                if(state==1){
                    GridLayoutManager manager = new GridLayoutManager(v.getContext(),1);
                    mRecyclerView.setLayoutManager(manager);
                    ProductAdapterUser adapterFavorite = new ProductAdapterUser(value,ProductFragment.this);
                    mRecyclerView.setAdapter(adapterFavorite);
                }else{
                    GridLayoutManager manager = new GridLayoutManager(v.getContext(),2);
                    mRecyclerView.setLayoutManager(manager);
                    ProductRecycleAdapter adapter = new ProductRecycleAdapter(value,v.getContext());
                    mRecyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onError(Throwable e) {
                mLoading.cancelDialog();
                Toast.makeText(v.getContext(),"Lỗi kết nối !",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onComplete() {

            }
        }));


    }
}
