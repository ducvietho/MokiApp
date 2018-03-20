package com.example.ducvietho.moki.screen.activities.product_seller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.User;
import com.example.ducvietho.moki.data.resource.remote.UserDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.UserRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.fragments.product.ProductFragment;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ProductSellerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_ID_SELLER = "idSeller";
    @BindView(R.id.tv_product)
    FontTextView mProduct;
    @BindView(R.id.tv_following)
    FontTextView mFollowing;
    @BindView(R.id.tv_follower)
    FontTextView mFollower;
    @BindView(R.id.imgLeft)
    CircleImageView mBack;
    @BindView(R.id.img_avatar)
    CircleImageView mAvatar;
    @BindView(R.id.tv_products)
    FontTextView mProducts;
    @BindView(R.id.tv_point)
    FontTextView mPoint;
    @BindView(R.id.tv_name)
    FontTextView mName;
    private UserDataRepository mRepository;
    private CompositeDisposable mDisposable;
    int idSeller;
    public static Intent getIntent(Context context, int idSeller) {
        Intent intent = new Intent(context, ProductSellerActivity.class);
        intent.putExtra(EXTRA_ID_SELLER, idSeller);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_seller);
        ButterKnife.bind(ProductSellerActivity.this);
        idSeller = getIntent().getIntExtra(EXTRA_ID_SELLER,0);
        startFragment(ProductFragment.getInstance(2,idSeller));
        mRepository = new UserDataRepository(new UserRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        mProduct.setOnClickListener(this);
        mFollowing.setOnClickListener(this);
        mFollower.setOnClickListener(this);
        mBack.setOnClickListener(this);
        getUserInfor(idSeller);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_product:
                startFragment(ProductFragment.getInstance(2,idSeller));
                mFollower.setTextColor(Color.BLACK);
                mFollower.setBackgroundColor(0xFFFFFF);
                mFollowing.setTextColor(Color.BLACK);
                mFollowing.setBackgroundColor(0xFFFFFF);
                mProduct.setTextColor(Color.WHITE);
                mProduct.setBackgroundResource(R.drawable.background_textview);
                break;
            case R.id.tv_following:

                mFollower.setTextColor(Color.BLACK);
                mFollower.setBackgroundColor(0xFFFFFF);
                mProduct.setTextColor(Color.BLACK);
                mProduct.setBackgroundColor(0xFFFFFF);
                mFollowing.setTextColor(Color.WHITE);
                mFollowing.setBackgroundResource(R.color.app_color);
                break;
            case R.id.tv_follower:
                mProduct.setTextColor(Color.BLACK);
                mProduct.setBackgroundColor(0xFFFFFF);
                mFollowing.setTextColor(Color.BLACK);
                mFollowing.setBackgroundColor(0xFFFFFF);
                mFollower.setTextColor(Color.WHITE);
                mFollower.setBackgroundResource(R.drawable.background_textview_right);
                break;
            case R.id.imgLeft:
                finish();
                break;
        }
    }

    public void startFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }
    private void getUserInfor(int idUser){
        mDisposable.add(mRepository.getUserInfor(idUser).subscribeOn(Schedulers.newThread()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<User>() {
            @Override
            public void onNext(User value) {
                Picasso.with(ProductSellerActivity.this).load(value.getAvatar())
                        .placeholder(R.drawable.icon_no_avatar).into(mAvatar);
                mProducts.setText(String.valueOf(value.getNumberProduct()));
                mPoint.setText(String.valueOf(value.getRate()));
                mName.setText(value.getName());
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
