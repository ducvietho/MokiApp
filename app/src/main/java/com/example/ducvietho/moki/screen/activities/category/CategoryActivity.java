package com.example.ducvietho.moki.screen.activities.category;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Category;
import com.example.ducvietho.moki.data.resource.remote.CategoryDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.CategoryRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.postproduct.PostProductActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.imgLeft)
    CircleImageView back;
    @BindView(R.id.rec_category)
    RecyclerView mRecyclerView;
    private CategoryDataRepository mRepository;
    private CompositeDisposable mDisposable;
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,CategoryActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(CategoryActivity.this);
        mRepository = new CategoryDataRepository(new CategoryRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        getCategory();
        back.setOnClickListener(this);
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
    private void getCategory(){
        mDisposable.add(mRepository.getCategories().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers
                .mainThread()).subscribeWith(new DisposableObserver<List<Category>>() {
            @Override
            public void onNext(List<Category> value) {
                GridLayoutManager manager = new GridLayoutManager(CategoryActivity.this,1);
                mRecyclerView.setLayoutManager(manager);
                CatagoryRecyclerAdapter adapter = new CatagoryRecyclerAdapter(value,CategoryActivity.this);
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
