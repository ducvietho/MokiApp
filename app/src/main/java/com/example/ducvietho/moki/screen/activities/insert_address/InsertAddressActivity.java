package com.example.ducvietho.moki.screen.activities.insert_address;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.District;
import com.example.ducvietho.moki.data.model.OrderAddress;
import com.example.ducvietho.moki.data.model.Village;
import com.example.ducvietho.moki.data.resource.remote.AddressDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.AddressRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.address.AddressActivity;
import com.example.ducvietho.moki.screen.activities.district.DistrictActivity;
import com.example.ducvietho.moki.screen.activities.search_result.SearchResultActivity;
import com.example.ducvietho.moki.screen.activities.village.VillageActivity;
import com.example.ducvietho.moki.utils.Constants;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.customview.AutoHighLightTextview;
import com.example.ducvietho.moki.utils.customview.FontEditText;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;
import com.example.ducvietho.moki.utils.dialog.DialogNotInfor;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class InsertAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_DISTRICT = 1;
    private static final int REQUEST_CODE_VILLAGE = 2;
    @BindView(R.id.imgLeft)
    CircleImageView mImageView;
    @BindView(R.id.tvDistrict)
    FontTextView tvDistrict;
    @BindView(R.id.tvVillage)
    FontTextView tvVillage;
    @BindView(R.id.tvProvince)
    FontTextView mProvince;
    @BindView(R.id.edStreet)
    FontEditText mStreet;
    @BindView(R.id.tvAddAddress)
    AutoHighLightTextview tvInsertAddress;
    private CompositeDisposable mDisposable;
    private AddressDataRepository mRepository;
    District mNameDistrict;
    String mNameVillage;


    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, InsertAddressActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_address);
        ButterKnife.bind(InsertAddressActivity.this);
        mDisposable = new CompositeDisposable();
        mRepository = new AddressDataRepository(new AddressRemoteDataResource(MokiServiceClient.getInstance()));
        mImageView.setOnClickListener(this);
        tvDistrict.setOnClickListener(this);
        tvVillage.setOnClickListener(this);
        tvInsertAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgLeft:
                finish();
                break;
            case R.id.tvDistrict:
                startActivityForResult(new DistrictActivity().getIntent(InsertAddressActivity.this), REQUEST_CODE_DISTRICT);
                break;
            case R.id.tvVillage:
                if(!tvDistrict.getText().toString().matches("")){
                    startActivityForResult(new VillageActivity().getIntent(InsertAddressActivity.this,mNameDistrict.getId()),
                            REQUEST_CODE_VILLAGE);
                }else{
                    new DialogNotInfor(InsertAddressActivity.this).showDialog(getResources().getString(R.string.address_notifi));
                }

                break;
            case R.id.tvAddAddress:
                insertAddress();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DISTRICT) {
            if (resultCode == Activity.RESULT_OK) {
                String infor = data.getStringExtra(Constants.EXTRA_DISTRICT);
                District district = new Gson().fromJson(infor, District.class);
                mNameDistrict = district;
                tvDistrict.setText(district.getName());
                tvVillage.setText("");
            }
        }
        if (requestCode == REQUEST_CODE_VILLAGE) {
            if (resultCode == Activity.RESULT_OK) {
                String infor = data.getStringExtra(Constants.EXTRA_VILLAGE);
                Village village = new Gson().fromJson(infor, Village.class);
                mNameVillage = village.getName();
                tvVillage.setText(village.getName());
            }
        }
    }
    public void insertAddress(){
       if(tvDistrict.getText().toString().matches("")){
           new DialogNotInfor(InsertAddressActivity.this).showDialog("Bạn chưa chọn Quận, Huyện");
       }else{
           if(tvVillage.getText().toString().matches("")){
               new DialogNotInfor(InsertAddressActivity.this).showDialog("Bạn chưa chọn Phường, Xã, Thị Trấn");
           }else{
               if(mStreet.getText().toString().matches("")){
                   new DialogNotInfor(InsertAddressActivity.this).showDialog("Bạn chưa nhập Số Nhà, Xóm, Tổ ");
               }else{
                   final DialogLoading loading = new DialogLoading(InsertAddressActivity.this);
                   loading.showDialog();
                   mDisposable.add(mRepository.insertAddress(new UserSession(InsertAddressActivity.this).getUserDetail().getId(),
                           mProvince.getText().toString(),tvDistrict.getText().toString(),tvVillage.getText().toString()
                           ,mStreet.getText().toString()).subscribeOn(Schedulers.newThread()).observeOn
                           (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<BaseResponse>() {
                       @Override
                       public void onNext(BaseResponse value) {
                           OrderAddress address = new OrderAddress(new UserSession(InsertAddressActivity.this).getUserDetail().getId(),
                                   mProvince.getText().toString(),tvDistrict.getText().toString(),tvVillage.getText().toString()
                                   ,mStreet.getText().toString());
                           Intent intent = getIntent();
                           intent.putExtra(Constants.EXTRA_ADDRESS_ÌNFOR,new Gson().toJson(address));
                           setResult(Activity.RESULT_OK,intent);
                           finish();
                           loading.cancelDialog();
                       }

                       @Override
                       public void onError(Throwable e) {
                           loading.cancelDialog();
                           Toast.makeText(InsertAddressActivity.this,"Lỗi kết nối !",Toast.LENGTH_LONG).show();
                       }

                       @Override
                       public void onComplete() {

                       }
                   }));
               }
           }
       }
    }
}
