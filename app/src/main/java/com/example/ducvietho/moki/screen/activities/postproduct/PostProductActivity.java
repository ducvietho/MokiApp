package com.example.ducvietho.moki.screen.activities.postproduct;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.BaseResponse;
import com.example.ducvietho.moki.data.model.Category;
import com.example.ducvietho.moki.data.model.OrderAddress;
import com.example.ducvietho.moki.data.resource.remote.ImageDataRepository;
import com.example.ducvietho.moki.data.resource.remote.ProductDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.ProductRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.ImageRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.address.AddressActivity;
import com.example.ducvietho.moki.screen.activities.camera.CameraActivity;
import com.example.ducvietho.moki.screen.activities.category.CategoryActivity;
import com.example.ducvietho.moki.screen.activities.status.StatusActivity;
import com.example.ducvietho.moki.utils.customview.AutoHighLightTextview;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.example.ducvietho.moki.utils.dialog.DialogSize;
import com.example.ducvietho.moki.utils.dialog.DialogWeight;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostProductActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_PATH = "path";
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_CATEGORY = 2;
    private static final int REQUEST_CODE_STATUS = 3;
    private static final int REQUEST_CODE_ADDRESS = 4;
    @BindView(R.id.imgLeft)
    ImageView mImageView;
    @BindView(R.id.rec_image)
    RecyclerView mRecyclerView;
    @BindView(R.id.img_camera)
    ImageView mCamera;
    @BindView(R.id.edtProductName)
    EditText edProductName;
    @BindView(R.id.edtDescription)
    EditText edDescription;
    @BindView(R.id.tvCategory)
    FontTextView tvCategory;
    @BindView(R.id.tvStatus)
    FontTextView tvStatus;
    @BindView(R.id.tvWeight)
    FontTextView tvWeight;
    @BindView(R.id.tvSize)
    FontTextView tvSize;
    @BindView(R.id.edtPrice)
    EditText edPrice;
    @BindView(R.id.tvTotalPrice)
    FontTextView tvPrice;
    @BindView(R.id.tvPostProduct)
    AutoHighLightTextview tvPost;
    @BindView(R.id.tvSellAddress)
    FontTextView tvAddress;
    int idCate;
    private List<String> mStrings = new ArrayList<>();
    private List<String> mUrlImage = new ArrayList<>();
    private ImageProductRecyclerAdapter mAdapter;
    private ImageDataRepository mDataRepository;
    private CompositeDisposable mDisposable;
    private ProductDataRepository mProductDataRepository;
    private FirebaseStorage mStorage;
    private StorageReference mReference;
    private boolean isUploaded;

    public static Intent getIntent(Context context, String path) {
        Intent intent = new Intent(context, PostProductActivity.class);
        intent.putExtra(EXTRA_PATH, path);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_product);
        ButterKnife.bind(PostProductActivity.this);
        mStorage = FirebaseStorage.getInstance();
        mReference = mStorage.getReference();
        mDataRepository = new ImageDataRepository(new ImageRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        mProductDataRepository = new ProductDataRepository(new ProductRemoteDataResource(MokiServiceClient
                .getInstance()));
        LinearLayoutManager manager = new LinearLayoutManager(PostProductActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(manager);
        String url = getIntent().getStringExtra(EXTRA_PATH);
        mStrings.add(url);
        mAdapter = new ImageProductRecyclerAdapter(mStrings, PostProductActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mImageView.setOnClickListener(this);
        mCamera.setOnClickListener(this);
        tvCategory.setOnClickListener(this);
        tvSize.setOnClickListener(this);
        tvStatus.setOnClickListener(this);
        tvWeight.setOnClickListener(this);
        tvPost.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        tvPost.setOnClickListener(this);
        edPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    tvPrice.setText(edPrice.getText().toString());
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgLeft:
                finish();
                break;
            case R.id.img_camera:
                startActivityForResult(new CameraActivity().getIntent(PostProductActivity.this, 2), REQUEST_CODE_CAMERA);
                break;
            case R.id.tvStatus:
                startActivityForResult(new StatusActivity().getIntent(PostProductActivity.this), REQUEST_CODE_STATUS);

                break;
            case R.id.tvWeight:
                new DialogWeight(PostProductActivity.this).showDialog(tvWeight);
                break;
            case R.id.tvSize:
                new DialogSize(PostProductActivity.this).showDialog(tvSize);
                break;
            case R.id.tvCategory:
                startActivityForResult(new CategoryActivity().getIntent(PostProductActivity.this), REQUEST_CODE_CATEGORY);
                break;
            case R.id.tvSellAddress:
                startActivityForResult(new AddressActivity().getIntent(PostProductActivity.this), REQUEST_CODE_ADDRESS);
                break;
            case R.id.tvPostProduct:
                for (int i=0;i<mStrings.size();i++){
                    uploadImageProduct(mStrings.get(i));
                }


                break;
            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (resultCode==Activity.RESULT_OK){
                    if (mStrings.size() >= 3) {
                        mCamera.setVisibility(View.GONE);
                    }
                    String path = data.getStringExtra(EXTRA_PATH);
                    mStrings.add(path);
                    mAdapter.notifyDataSetChanged();
                }

                break;

            case REQUEST_CODE_CATEGORY:
                if (resultCode == Activity.RESULT_OK) {
                    String category = data.getStringExtra("catagory");
                    Category cate = new Gson().fromJson(category, Category.class);
                    idCate = cate.getId();
                    tvCategory.setText(cate.getName());
                }
                break;

            case REQUEST_CODE_STATUS:
                if (resultCode == Activity.RESULT_OK) {
                    String status = data.getStringExtra("status");
                    tvStatus.setText(status);
                }
                break;
            case REQUEST_CODE_ADDRESS:
                if (resultCode == Activity.RESULT_OK) {
                    String add = data.getStringExtra("address");
                    OrderAddress address = new Gson().fromJson(add, OrderAddress.class);
                    tvAddress.setText(address.getStreet() + ", " + address.getVillage() + ", " + address.getDistrict() + ", " + "" + address.getProvince());
                }
                break;

        }

    }

    public void uploadImage(String url) {

        File file = new File(url);
        Uri filePath = Uri.parse(url);
        if (filePath != null) {
            StorageReference ref = mReference.child("images").child(file.getName());
            try {
                InputStream stream = new FileInputStream(file);
                UploadTask uploadTask = ref.putStream(stream);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mUrlImage.add(taskSnapshot.getDownloadUrl().toString());
                        Toast.makeText(PostProductActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PostProductActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
    private void uploadImageProduct(String url){
        File file = new File(url);
        if(file.exists()){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("input_img",file.getAbsolutePath(),requestFile);
            mDisposable.add(mDataRepository.uploadImageProduct(multipartBody).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<BaseResponse>() {
                        @Override
                        public void onNext(BaseResponse value) {
                            if(value.getmCode()==200){
                                Toast.makeText(PostProductActivity.this,"Upload success",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(PostProductActivity.this,"Upload failure",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(PostProductActivity.this,"Error:"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    }));

        }
    }
}
