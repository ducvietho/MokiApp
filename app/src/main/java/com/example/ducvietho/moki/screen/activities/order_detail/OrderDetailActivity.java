package com.example.ducvietho.moki.screen.activities.order_detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.OrderAddress;
import com.example.ducvietho.moki.data.model.OrderDetail;
import com.example.ducvietho.moki.data.resource.remote.ProductDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.ProductRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.buy.BuyActivity;
import com.example.ducvietho.moki.screen.activities.chat.ChatActivity;
import com.example.ducvietho.moki.screen.activities.notification.NotificationActivity;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.txtName)
    FontTextView mName;
    @BindView(R.id.txtPrice)
    FontTextView mPrice;
    @BindView(R.id.btnNavLeft)
    ImageButton mBtnBack;
    @BindView(R.id.img_product)
    ImageView mImageProduct;
    @BindView(R.id.imgAvatar)
    CircleImageView mAvatar;
    @BindView(R.id.tvBuyer)
    FontTextView mNameBuyer;
    @BindView(R.id.tvAddress)
    FontTextView mAddress;
    @BindView(R.id.tvPhone)
    FontTextView mPhone;
    @BindView(R.id.btCall)
    CircleImageView mCall;
    ProductDataRepository mRepository;
    CompositeDisposable mDisposable;
    private static final String EXTRA_ID = "idProduct";
    String phone;
    public static Intent getIntent(Context context, int idProduct) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(EXTRA_ID, idProduct);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        mRepository = new ProductDataRepository(new ProductRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        getOrderDetail(id);
        mBtnBack.setOnClickListener(this);
        mCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNavLeft:
                finish();
                break;
            case R.id.btCall:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void getOrderDetail(int idProdcut) {
        final DialogLoading loading = new DialogLoading(OrderDetailActivity.this);
        loading.showDialog();
        mDisposable.add(mRepository.getOrderDetail(idProdcut).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<OrderDetail>() {
            @Override
            public void onNext(OrderDetail value) {
                loading.cancelDialog();
                mName.setText(value.getName());
                String[] image = value.getImage().split(",");
                Picasso.with(OrderDetailActivity.this).load(image[0]).into(mImageProduct);
                String price = String.valueOf(value.getPrice());
                if (price.length() <= 6) {
                    price = new StringBuilder(price).insert(price.length() - 3, ",").toString();
                    mPrice.setText(price + " VNĐ");
                } else {
                    price = new StringBuilder(price).insert(price.length() - 3, ",").toString();
                    price = new StringBuilder(price).insert(price.length() - 7, ",").toString();
                    mPrice.setText(price + " VNĐ");
                }
                Picasso.with(OrderDetailActivity.this).load(value.getBuyer().getAvatar()).into(mAvatar);
                mNameBuyer.setText(value.getBuyer().getName());
                mAddress.setText(value.getAddShip());
                mPhone.setText(value.getBuyer().getPhone());
                phone = value.getBuyer().getPhone();

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
