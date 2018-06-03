package com.example.ducvietho.moki.screen.fragments.favorite;

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
import com.example.ducvietho.moki.screen.activities.search_result.SearchResultActivity;
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
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment implements OnClickItemProduct {
    @BindView(R.id.rec_favorite)
    RecyclerView mRecyclerView;
    private ProductDataRepository mRepository;
    private CompositeDisposable mDisposable;
    private UserSession mUserSession;
    private DialogLoading mLoading;
    View v;
    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this,v);
        mLoading = new DialogLoading(v.getContext());
        mLoading.showDialog();
        mUserSession = new UserSession(v.getContext());
        mRepository = new ProductDataRepository(new ProductRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        getProductsFavorite(mUserSession.getUserDetail().getId());
        return v;
    }


    @Override
    public void onClick(Product product) {
        startActivity(new ProductDetailActivity().getIntent(v.getContext(),product.getId()));
    }
    private void getProductsFavorite(int idUser){
        mDisposable.add(mRepository.getProductsFavorite(idUser).subscribeOn(Schedulers.newThread()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Product>>() {
            @Override
            public void onNext(List<Product> value) {
                mLoading.cancelDialog();
                GridLayoutManager manager = new GridLayoutManager(v.getContext(),1);
                mRecyclerView.setLayoutManager(manager);
                RecyclerAdapterFavorite adapterFavorite = new RecyclerAdapterFavorite(value,FavoriteFragment.this);
                mRecyclerView.setAdapter(adapterFavorite);
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
