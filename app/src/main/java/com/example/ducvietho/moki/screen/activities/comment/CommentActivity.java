package com.example.ducvietho.moki.screen.activities.comment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Comment;
import com.example.ducvietho.moki.data.model.Comments;
import com.example.ducvietho.moki.data.resource.remote.CommentDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.CommentRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.product_seller.ProductSellerActivity;
import com.example.ducvietho.moki.utils.OnItemtClick;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.ducvietho.moki.utils.Constants.EXTRA_ID;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener,OnItemtClick<Comment> {

    @BindView(R.id.imgLeft)
    CircleImageView back;
    @BindView(R.id.edtComment)
    EditText edComment;
    @BindView(R.id.btnDelete)
    Button delete ;
    @BindView(R.id.rec_comment)
    RecyclerView mRecyclerComment;
    @BindView(R.id.btnSent)
    Button btnSend;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    private int lastId;
    int idProduct;
    private UserSession mUserSession;
    private List<Comment> mList = new ArrayList<>();
    private CommentDataRepository mDataRepository;
    private CompositeDisposable mDisposable;
    private CommentRecyclerAdapter adapter;

    public Intent getIntent(Context context, int id) {
        Intent i = new Intent(context, CommentActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(EXTRA_ID, id);
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(CommentActivity.this);
        idProduct = getIntent().getIntExtra(EXTRA_ID,0);
        mDataRepository = new CommentDataRepository(new CommentRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        mUserSession = new UserSession(CommentActivity.this);
        init();
        initComment(idProduct);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initComment(idProduct);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgLeft:
                finish();
                break;
            case R.id.btnDelete:
                edComment.setText("");
                break;
            case R.id.btnSent:
                if(mUserSession.getUserDetail().getToken()==null){
                    mUserSession.logoutUser(1);

                }else{
                    commentProduct(idProduct,mUserSession.getUserDetail().getId(),edComment.getText().toString());
                    edComment.setText("");

                }

        }
    }
    @Override
    public void onClick(Comment comment) {
        startActivity(new ProductSellerActivity().getIntent(CommentActivity.this,comment.getUser().getId()));
    }
    private void commentProduct(int idProduct,int idUser,String content){
        final DialogLoading loading = new DialogLoading(CommentActivity.this);
        loading.showDialog();
        mDisposable.add(mDataRepository.postCommentProduct(idProduct,idUser,content,lastId).subscribeOn(Schedulers
                .newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Comments>() {
            @Override
            public void onNext(Comments value) {
                mRefreshLayout.setRefreshing(false);
                lastId = value.getLastId();
                mList.addAll(0,value.getComments());
                adapter.notifyDataSetChanged();
                loading.cancelDialog();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));

    }
    private void init(){

        edComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    delete.setVisibility(View.GONE);
                }
                else {
                    delete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void initComment(int idProduct){
        mDisposable.add(mDataRepository.getCommentsByProduct(idProduct).subscribeOn(Schedulers.newThread()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Comments>() {
            @Override
            public void onNext(Comments value) {
                lastId = value.getLastId();
                mList = value.getComments();
                GridLayoutManager manager = new GridLayoutManager(CommentActivity.this,1);
                mRecyclerComment.setLayoutManager(manager);
                adapter  =new CommentRecyclerAdapter(mList,CommentActivity.this,CommentActivity.this);
                mRecyclerComment.setAdapter(adapter);
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
