package com.example.ducvietho.moki.screen.activities.productimage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.data.resource.remote.ProductDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.ProductRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.fragments.home.ViewPagerAdapter;
import com.example.ducvietho.moki.screen.fragments.zoom_image.ImageZoomFragment;
import com.example.ducvietho.moki.utils.UserSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailZoomActivity extends AppCompatActivity {
    @BindView(R.id.pager_zoom)
    ViewPager viewPager;
    @BindView(R.id.im_close)
    ImageView view;
    public static final String EXTRA_ID = "id";
    private UserSession mUserSession;
    private ProductDataRepository mRepository;
    private CompositeDisposable mDisposable;
    public Intent getIntent(Context context, int id) {
        Intent intent = new Intent(context, DetailZoomActivity.class);
        intent.putExtra(EXTRA_ID, id);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_zoom);
        ButterKnife.bind(DetailZoomActivity.this);

        Intent i = getIntent();
        final int id = i.getIntExtra(EXTRA_ID, 0);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);
            }
        });
        mUserSession = new UserSession(DetailZoomActivity.this);
        mRepository = new ProductDataRepository(new ProductRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        mDisposable.add(mRepository.getProductDetail(id,mUserSession.getUserDetail().getId()).subscribeOn(Schedulers
                .newThread()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Product>() {
            @Override
            public void onNext(Product value) {
                String [] images = value.getImage().split(",");
                List<String> list = new ArrayList<String>(Arrays.asList(images));
                setupViewPager(viewPager,list);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));
    }

    private void setupViewPager(ViewPager viewPager, List<String> strings) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < strings.size(); i++) {
            adapter.addFragment(ImageZoomFragment.newInstance(strings.get(i)), "");

        }
        viewPager.setAdapter(adapter);
    }
}
