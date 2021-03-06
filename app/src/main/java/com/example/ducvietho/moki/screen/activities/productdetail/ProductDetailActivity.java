package com.example.ducvietho.moki.screen.activities.productdetail;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Comment;
import com.example.ducvietho.moki.data.model.Comments;
import com.example.ducvietho.moki.data.model.Like;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.data.resource.remote.CommentDataRepository;
import com.example.ducvietho.moki.data.resource.remote.LikeDataRepository;
import com.example.ducvietho.moki.data.resource.remote.ProductDataRepository;
import com.example.ducvietho.moki.data.resource.remote.api.CommentRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.LikeRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.ProductRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.screen.activities.chat.ChatActivity;
import com.example.ducvietho.moki.screen.activities.comment.CommentActivity;
import com.example.ducvietho.moki.screen.activities.comment.CommentRecyclerAdapter;
import com.example.ducvietho.moki.screen.activities.product_category.ProductCategoryActivity;
import com.example.ducvietho.moki.screen.activities.product_seller.ProductSellerActivity;
import com.example.ducvietho.moki.screen.activities.search_result.SearchResultActivity;
import com.example.ducvietho.moki.screen.fragments.home.ViewPagerAdapter;
import com.example.ducvietho.moki.screen.fragments.image_product.ImageProductFragment;
import com.example.ducvietho.moki.utils.OnItemtClick;
import com.example.ducvietho.moki.utils.OncClickItemCate;
import com.example.ducvietho.moki.utils.PullOverListen;
import com.example.ducvietho.moki.utils.ShareProcess;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;
import com.nirhart.parallaxscroll.views.ParallaxScrollView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.ducvietho.moki.utils.Constants.EXTRA_ID_PRODUCT;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener ,OnItemtClick<Comment>,OncClickItemCate{

    @BindView(R.id.product_scroll)
    ParallaxScrollView productScroll;
    @BindView(R.id.product_image_slider)
    ViewPager viewPager;
    @BindView(R.id.imgLeft)
    ImageView mImgBack;
    @BindView(R.id.tvTitle)
    FontTextView mTitle;
    @BindView(R.id.prev_btn)
    View mViewLeft;
    @BindView(R.id.next_btn)
    View mViewRight;
    @BindView(R.id.tv_like)
    TextView mLike;
    @BindView(R.id.tv_comment)
    TextView mComment;
    @BindView(R.id.img_unlike)
    ImageView mImageUnLike;
    @BindView(R.id.img_like)
    ImageView mImageLike;
    @BindView(R.id.img_avatar)
    CircleImageView mAvatar;
    @BindView(R.id.txt_username)
    FontTextView mUsername;
    @BindView(R.id.tv_point)
    TextView mPoint;
    @BindView(R.id.tv_sum_product)
    TextView mSumProduct;
    @BindView(R.id.tv_desci)
    TextView mDescribed;
    @BindView(R.id.cate_list)
    RecyclerView mCate;
    @BindView(R.id.tv_weight)
    FontTextView mWeight;
    @BindView(R.id.tv_size)
    FontTextView mDimen;
    @BindView(R.id.tv_state)
    FontTextView mStatus;
    @BindView(R.id.tv_address)
    FontTextView mAddress;
    @BindView(R.id.recycler_comment)
    RecyclerView mRecyclerComment;
    @BindView(R.id.comment_tv)
    FontTextView mBtnComent;
    @BindView(R.id.product_price)
    FontTextView mPrice;
    @BindView(R.id.tv_edit)
    FontTextView mEdit;
    @BindView(R.id.tv_buy)
    FontTextView mBuy;
    @BindView(R.id.layout_share)
    LinearLayout mShare;
    @BindView(R.id.layout_seller)
    LinearLayout mSeller;
    @BindView(R.id.layout_product)
    RelativeLayout mLayout;
    @BindView(R.id.tv_time)
    FontTextView mTime;
    private List<String> mStringList = new ArrayList<>();
    private CateRecycleAdapter mAdapter;
    private UserSession mUserSession;
    private ProductDataRepository mRepository;
    private CommentDataRepository mDataRepository;
    private LikeDataRepository mLikeDataRepository;
    private CompositeDisposable mDisposable;
    int idProduct;
    int idCate;
    public static Intent getIntent(Context context, int idProduct) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(EXTRA_ID_PRODUCT, idProduct);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(ProductDetailActivity.this);
        mUserSession = new UserSession(ProductDetailActivity.this);
        idProduct = getIntent().getIntExtra(EXTRA_ID_PRODUCT, 0);
        mRepository = new ProductDataRepository(new ProductRemoteDataResource(MokiServiceClient.getInstance()));
        mDataRepository = new CommentDataRepository(new CommentRemoteDataResource(MokiServiceClient.getInstance()));
        mLikeDataRepository = new LikeDataRepository(new LikeRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        mImageLike.setOnClickListener(this);
        mImageUnLike.setOnClickListener(this);
        mShare.setOnClickListener(this);
        initComment(idProduct);
        getProductDetail(idProduct, mUserSession.getUserDetail().getId());
        setupImageParalax();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_like:
                if(mUserSession.getUserDetail().getToken()==null){
                    mUserSession.logoutUser(1);

                }else {
                    likeProduct(idProduct,mUserSession.getUserDetail().getId());
                }
                break;
            case R.id.img_unlike:
                if(mUserSession.getUserDetail().getToken()==null){
                    mUserSession.logoutUser(1);

                }else {
                    unlikeProduct(idProduct,mUserSession.getUserDetail().getId());
                }
                break;
            case R.id.layout_share:
                new ShareProcess(ProductDetailActivity.this).shareContentProduct();
                break;
        }
    }
    @Override
    public void onClick(Comment comment) {
        startActivity(new ProductSellerActivity().getIntent(ProductDetailActivity.this,comment.getUser().getId()));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);
    }
    @Override
    public void onClick(String string) {
        startActivity(new ProductCategoryActivity().getIntent(ProductDetailActivity.this,string,idCate));
    }
    private void likeProduct(int idProduct,int idUser){
        final DialogLoading loading = new DialogLoading(ProductDetailActivity.this);
        loading.showDialog();
        mDisposable.add(mLikeDataRepository.likeProduct(idProduct,idUser).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Like>() {
                    @Override
                    public void onNext(Like value) {
                        mLike.setText(String.valueOf(value.getNumberLike()) + " thích");
                        mImageLike.setVisibility(View.GONE);
                        mImageUnLike.setVisibility(View.VISIBLE);
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
    private void unlikeProduct(int idProduct,int idUser){
        final DialogLoading loading = new DialogLoading(ProductDetailActivity.this);
        loading.showDialog();
        mDisposable.add(mLikeDataRepository.unlikeProduct(idProduct,idUser).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Like>() {
                    @Override
                    public void onNext(Like value) {
                        mLike.setText(String.valueOf(value.getNumberLike()) + " thích");
                        mImageLike.setVisibility(View.VISIBLE);
                        mImageUnLike.setVisibility(View.GONE);
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
    private void getProductDetail(final int idProduct, int idUser) {
        final DialogLoading loading = new DialogLoading(ProductDetailActivity.this);
        loading.showDialog();
        mDisposable.add(mRepository.getProductDetail(idProduct, idUser).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Product>() {
            @Override
            public void onNext(Product value) {

                loading.cancelDialog();
                initProduct(value);
            }

            @Override
            public void onError(Throwable e) {
                loading.cancelDialog();
                Toast.makeText(ProductDetailActivity.this,"Lỗi kết nối !",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    private void initProduct(final Product product) {
        idCate = product.getIdCate();
        Date date;
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = df.parse(product.getCreated());

        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date: ", e);
        }
        long minute = (current.getTime() - date.getTime())
                / ( 60 * 1000);
        long hours = (current.getTime() - date.getTime())
                / ( 3600 * 1000);
        long days = hours/24;
        long weeks = days/7;
        long months = days/30;
        long years = months/12;
        if(years>0){
            mTime.setText(String.valueOf(years)+" "+getResources().getString(R.string.years_ago));
        }else{
            if(months>0){
                mTime.setText(String.valueOf(months)+" "+getResources().getString(R.string
                        .months_ago));
            }else {
                if(weeks>0){
                    mTime.setText(String.valueOf(weeks)+" "
                            +getResources().getString(R.string.weeks_ago));
                }else {
                    if(days>0){
                        mTime.setText(String.valueOf(days)+" "
                                +getResources().getString(R.string.days_ago));
                    }else {
                        if(hours>0){
                            mTime.setText(String.valueOf(hours)+" "
                                    +getResources().getString(R.string.hours_ago));
                        }else {
                            if(minute>0){
                                mTime.setText(String.valueOf(minute)+" "
                                        +getResources().getString(R.string.minutes_ago));
                            }else {
                                mTime.setText(getResources().getString(R.string.just_now));
                            }

                        }

                    }
                }
            }
        }
        if (product.getUser().getId() == mUserSession.getUserDetail().getId()) {
            mLayout.setVisibility(View.GONE);
        }else{
            if(product.getIsSold()>0){
                mLayout.setVisibility(View.GONE);
            }
        }
        String price = String.valueOf(product.getPrice());
        if(price.length()<=6){
            price = new StringBuilder(price).insert(price.length()-3,",")
                    .toString();
            mPrice.setText(price+" VNĐ");
        }else{
            price = new StringBuilder(price).insert(price.length()-3,",")
                    .toString();
            price = new StringBuilder(price).insert(price.length()-7,",")
                    .toString();
            mPrice.setText(price+" VNĐ");
        }
        if (product.getComment() == 0) {
            mBtnComent.setText(getResources().getString(R.string.no_comment));
        } else {
            mBtnComent.setText(getResources().getString(R.string.commented));
        }
        mTitle.setText(product.getName());
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);
            }
        });
        if (product.getIs_liked() == 0) {
            mImageUnLike.setVisibility(View.GONE);
        } else {
            mImageLike.setVisibility(View.GONE);
            mImageUnLike.setVisibility(View.VISIBLE);
        }
        Picasso.with(ProductDetailActivity.this).load(product.getUser().getAvatar()).placeholder(R.drawable.icon_no_avatar).into(mAvatar);
        mDescribed.setText(product.getDescribed());
        mUsername.setText(product.getUser().getName());
        mPoint.setText("Điểm: " + String.valueOf(product.getUser().getRate()));
        mSumProduct.setText("Sản phẩm: " + String.valueOf(product.getUser().getNumberProduct()));
        mLike.setText(String.valueOf(product.getLike()) + " thích");
        mComment.setText(String.valueOf(product.getComment()) + " bình luận");
        mWeight.setText(product.getWeight());
        mDimen.setText(product.getDimension());
        mStatus.setText(product.getStatus());
        mAddress.setText(product.getAddress());
        String[] images = product.getImage().split(",");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < images.length; i++) {
            adapter.addFragment(ImageProductFragment.newInstance(images[i], product.getId()), "");
        }
        viewPager.setAdapter(adapter);
        if (viewPager.getAdapter().getCount() == 1) {
            mViewRight.setVisibility(View.GONE);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position== 0) {
                    mViewLeft.setVisibility(View.GONE);
                    mViewRight.setVisibility(View.VISIBLE);

                } else {
                    if (position > 0 && position < viewPager.getAdapter().getCount() - 1) {
                        mViewRight.setVisibility(View.VISIBLE);
                        mViewLeft.setVisibility(View.VISIBLE);
                    }
                    if (position == (viewPager.getAdapter().getCount() - 1)) {
                        mViewRight.setVisibility(View.GONE);
                        mViewLeft.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                int i = viewPager.getCurrentItem();
                if (i == 0) {
                    mViewLeft.setVisibility(View.GONE);
                    mViewRight.setVisibility(View.VISIBLE);

                } else {
                    if (i > 0 && i < viewPager.getAdapter().getCount() - 1) {
                        mViewRight.setVisibility(View.VISIBLE);
                        mViewLeft.setVisibility(View.VISIBLE);
                    }
                    if (i == (viewPager.getAdapter().getCount() - 1)) {
                        mViewRight.setVisibility(View.GONE);
                        mViewLeft.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        mViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                int i = viewPager.getCurrentItem();
                if (i == 0) {
                    mViewLeft.setVisibility(View.GONE);
                    mViewRight.setVisibility(View.VISIBLE);

                } else {
                    if (i > 0 && i < viewPager.getAdapter().getCount() - 1) {
                        mViewRight.setVisibility(View.VISIBLE);
                        mViewLeft.setVisibility(View.VISIBLE);
                    }
                    if (i == (viewPager.getAdapter().getCount() - 1)) {
                        mViewRight.setVisibility(View.GONE);
                        mViewLeft.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        mBtnComent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new CommentActivity().getIntent(ProductDetailActivity.this, product.getId()));
            }
        });
        mBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUserSession.getUserDetail().getToken()==null){
                    mUserSession.logoutUser(1);
                }else{
                    startActivity(new ChatActivity().getIntent(ProductDetailActivity.this,product.getUser().getId(),
                            mUserSession.getUserDetail().getId(),idProduct));
                }

            }
        });
        mSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new ProductSellerActivity()
                        .getIntent(ProductDetailActivity.this,product.getUser().getId()));
            }
        });
        GridLayoutManager manager = new GridLayoutManager(ProductDetailActivity.this, 1);
        mCate.setLayoutManager(manager);
        mStringList.add(product.getCategory());
        mAdapter = new CateRecycleAdapter(mStringList,this);
        mCate.setAdapter(mAdapter);
    }

    private void initComment(int idProduct) {
        mDisposable.add(mDataRepository.getCommentsByProduct(idProduct).subscribeOn(Schedulers.newThread()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Comments>() {
            @Override
            public void onNext(Comments value) {
                GridLayoutManager manager = new GridLayoutManager(ProductDetailActivity.this, 1);
                mRecyclerComment.setLayoutManager(manager);
                CommentRecyclerAdapter adapter = new CommentRecyclerAdapter(value.getComments(),
                        ProductDetailActivity.this,ProductDetailActivity.this);
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

    private void setupImageParalax() {

        new PullOverListen(productScroll) {
            boolean isAnimating = false;
            int currentHeight, currentWidth;
            int delta = 0;
            ValueAnimator lastAnim;

            @Override
            protected void onStart() {
                if (lastAnim != null) {
                    lastAnim.end();
                }
                currentHeight = viewPager.getLayoutParams().height;
                currentWidth = viewPager.getLayoutParams().width;

            }

            @Override
            protected void onPull(int d) {
                if (d <= 0) {
                    return;
                }
                if (d != delta) {
                    ViewGroup.LayoutParams params = viewPager.getLayoutParams();
                    params.height = currentHeight + d / 3;
                    viewPager.setLayoutParams(params);
                    delta = d;
                }
                return;
            }

            @Override
            protected void onFinish(int d) {
//                Log.d("Scroll", "finish d:");
                viewPager.clearAnimation();
                ValueAnimator anim = ValueAnimator.ofInt(currentHeight, 700);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int val = (Integer) valueAnimator.getAnimatedValue();
                        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
                        params.height = val;
                        viewPager.setLayoutParams(params);
                    }
                });
                anim.setDuration(800);
                anim.setInterpolator(new TimeInterpolator() {
                    @Override
                    public float getInterpolation(float v) {
                        float p = (float) Math.cos((1 - v) * Math.toRadians(90));
                        return (float) Math.pow(p, 1f / 2);
                    }
                });
                if (lastAnim != null) {
                    lastAnim.end();
                }
                anim.start();
                anim.addListener(new ValueAnimator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animator) {
                        isAnimating = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isAnimating = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                lastAnim = anim;

            }

            @Override
            protected boolean isStart() {
                return productScroll.getScrollY() == 0 || isAnimating;
            }
        };
    }

}
