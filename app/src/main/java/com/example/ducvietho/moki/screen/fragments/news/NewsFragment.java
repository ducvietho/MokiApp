package com.example.ducvietho.moki.screen.fragments.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.News;
import com.example.ducvietho.moki.data.resource.remote.NewsDataRespository;
import com.example.ducvietho.moki.data.resource.remote.api.NewsRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.news_detail.NewsDetailActivity;
import com.example.ducvietho.moki.utils.OnItemtClick;
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
public class NewsFragment extends Fragment implements OnItemtClick<News> {
    private View v;
    @BindView(R.id.rec_news)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    private NewsDataRespository mRespository;
    private CompositeDisposable mDisposable;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this,v);
        mRespository = new NewsDataRespository(new NewsRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        getNews();
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNewsRefresh();
            }
        });
        return v;
    }
    @Override
    public void onClick(News object) {
        startActivity(new NewsDetailActivity().getIntent(v.getContext(),object.getId()));
    }
    public void getNews(){
        final DialogLoading loading = new DialogLoading(v.getContext());
        loading.showDialog();
        mDisposable.add(mRespository.getNews().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers
                .mainThread()).subscribeWith(new DisposableObserver<List<News>>() {
            @Override
            public void onNext(List<News> value) {
                loading.cancelDialog();
                mRefreshLayout.setRefreshing(false);
                GridLayoutManager manager = new GridLayoutManager(v.getContext(),1);
                mRecyclerView.setLayoutManager(manager);
                NewsAdapter adapter = new NewsAdapter(value,NewsFragment.this);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));
    }

    public void getNewsRefresh(){

        mDisposable.add(mRespository.getNews().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers
                .mainThread()).subscribeWith(new DisposableObserver<List<News>>() {
            @Override
            public void onNext(List<News> value) {

                mRefreshLayout.setRefreshing(false);
                GridLayoutManager manager = new GridLayoutManager(v.getContext(),1);
                mRecyclerView.setLayoutManager(manager);
                NewsAdapter adapter = new NewsAdapter(value,NewsFragment.this);
                mRecyclerView.setAdapter(adapter);
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
