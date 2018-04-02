package com.example.ducvietho.moki.screen.activities.news_detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.News;
import com.example.ducvietho.moki.data.resource.remote.NewsDataRespository;
import com.example.ducvietho.moki.data.resource.remote.api.NewsRemoteDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiServiceClient;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_ID_NEWS = "idNews";
    @BindView(R.id.imgLeft)
    ImageView mBack;
    @BindView(R.id.tv_title)
    FontTextView tvTitle;
    @BindView(R.id.tv_time)
    FontTextView mTime;
    @BindView(R.id.tv_described)
    FontTextView tvDescribed;
    int idNews;
    private NewsDataRespository mRespository;
    private CompositeDisposable mDisposable;
    public static Intent getIntent(Context context, int idNews){
        Intent intent = new Intent(context,NewsDetailActivity.class);
        intent.putExtra(EXTRA_ID_NEWS,idNews);

        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(NewsDetailActivity.this);
        idNews = getIntent().getIntExtra(EXTRA_ID_NEWS,0);
        mRespository = new NewsDataRespository(new NewsRemoteDataResource(MokiServiceClient.getInstance()));
        mDisposable = new CompositeDisposable();
        mBack.setOnClickListener(this);
        getNewsDetail();
    }
    public void getNewsDetail(){
        final DialogLoading loading = new DialogLoading(NewsDetailActivity.this);
        loading.showDialog();
        mDisposable.add(mRespository.getNewsDetail(idNews).subscribeOn(Schedulers.newThread()).observeOn
                (AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<News>() {
            @Override
            public void onNext(News value) {
                Date date;
                Date current = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    date = df.parse(value.getDay());

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
                tvTitle.setText(value.getTitle());
                tvDescribed.setText(value.getDescribed());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgLeft:
                finish();
                break;
        }
    }
}
