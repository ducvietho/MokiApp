package com.example.ducvietho.moki.screen.activities.buy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.OrderAddress;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.data.resource.remote.ProductDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.ProductRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.address.AddressActivity;
import com.example.ducvietho.moki.screen.activities.home.HomeActivity;
import com.example.ducvietho.moki.utils.Constants;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.example.ducvietho.moki.utils.dialog.DialoagBuySuccess;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;
import com.example.ducvietho.moki.utils.dialog.DialogNotInfor;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BuyActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_ID_PRODUCT = "product";
    private static final int REQUEST_CODE_ADDRESS= 1;
    @BindView(R.id.txtName)
    FontTextView mName;
    @BindView(R.id.txtPrice)
    FontTextView mPrice;
    @BindView(R.id.btnNavLeft)
    ImageButton mBtnBack;
    @BindView(R.id.img_product)
    ImageView mImageProduct;
    @BindView(R.id.tvSum)
    FontTextView mSum;
    @BindView(R.id.tvUser)
    FontTextView mCustomer;
    @BindView(R.id.tvAddress)
    FontTextView tvAddress;
    @BindView(R.id.btBuy)
    FontTextView btBuy;
    private ProductDataRepository mRepository;
    private CompositeDisposable mDisposable;
    private DialogLoading mLoading;
    private UserSession mUserSession;
    int idProduct;
    public static Intent getIntent(Context context, int idProduct) {
        Intent intent = new Intent(context, BuyActivity.class);

        intent.putExtra(EXTRA_ID_PRODUCT, idProduct);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        ButterKnife.bind(BuyActivity.this);
        mBtnBack.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        btBuy.setOnClickListener(this);
        mRepository = new ProductDataRepository(new ProductRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        mLoading = new DialogLoading(BuyActivity.this);
        mUserSession = new UserSession(BuyActivity.this);
        idProduct = getIntent().getIntExtra(EXTRA_ID_PRODUCT,0);
        initProduct(mUserSession.getUserDetail().getId(),idProduct);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnNavLeft:
                finish();
                break;
            case R.id.tvAddress:
                startActivityForResult(new AddressActivity().getIntent(BuyActivity.this),REQUEST_CODE_ADDRESS);
                break;
            case R.id.btBuy:
                buyProduct();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_ADDRESS:
                if (resultCode == Activity.RESULT_OK) {
                    String add = data.getStringExtra("address");
                    OrderAddress address = new Gson().fromJson(add, OrderAddress.class);
                    tvAddress.setText(address.getStreet() + ", " + address.getVillage() + ", " + address.getDistrict() + ", " + "" + address.getProvince());
                }
                break;
        }
    }

    private void initProduct(int idUser, int idProduct) {
        mCustomer.setText(mUserSession.getUserDetail().getName());
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_ADDRESS,MODE_PRIVATE);
        String addressInfor = sharedPreferences.getString(Constants.EXTRA_ADDRESS_ÌNFOR,"");
        if(!addressInfor.matches("")){
            OrderAddress address = new Gson().fromJson(addressInfor,OrderAddress.class);
            tvAddress.setText(address.getStreet() + ", " + address.getVillage() + ", " + address.getDistrict() + ", " + "" + address.getProvince());

        }
        mDisposable.add(mRepository.getProductDetail(idProduct, idUser).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Product>() {
            @Override
            public void onNext(Product value) {
                mLoading.cancelDialog();
                String [] images = value.getImage().split(",");
                Picasso.with(BuyActivity.this).load(images[0]).into(mImageProduct);
                mName.setText(value.getName());
                String price = String.valueOf(value.getPrice());
                if(price.length()<=6){
                    price = new StringBuilder(price).insert(price.length()-3,",")
                            .toString();
                    mPrice.setText(price+" VNĐ");
                    mSum.setText(price+" VNĐ");
                }else{
                    price = new StringBuilder(price).insert(price.length()-3,",")
                            .toString();
                    price = new StringBuilder(price).insert(price.length()-7,",")
                            .toString();
                    mPrice.setText(price+" VNĐ");
                    mSum.setText(price+" VNĐ");
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
    public void buyProduct(){
        if(tvAddress.getText().toString().matches("")){
            new DialogNotInfor(BuyActivity.this).showDialog("Bạn chưa chọn địa chỉ để ship hàng");
        }else {
            DialogLoading loading = new DialogLoading(BuyActivity.this);
            loading.showDialog();
            mDisposable.add(mRepository.buyProduct(idProduct,mUserSession.getUserDetail().getId(),tvAddress.getText()
                    .toString()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<BaseResponse>() {
                        @Override
                        public void onNext(BaseResponse value) {
                            if(value.getCode()==200){
                                new DialoagBuySuccess(BuyActivity.this).showDialog();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                      startActivity(new HomeActivity().getIntent(BuyActivity.this));
                                    }
                                },1500);
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
    }
}
