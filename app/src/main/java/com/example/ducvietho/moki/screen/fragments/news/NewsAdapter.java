package com.example.ducvietho.moki.screen.fragments.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.News;
import com.example.ducvietho.moki.utils.OnItemtClick;
import com.example.ducvietho.moki.utils.customview.FontTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 24/03/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<News> mList;
    private OnItemtClick<News> mOnItemtClick;

    public NewsAdapter(List<News> list, OnItemtClick<News> onItemtClick) {
        mList = list;
        mOnItemtClick = onItemtClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_time)
        FontTextView mTime;
        @BindView(R.id.tv_title)
        FontTextView mTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(final News news){
            Date date;
            Date current = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = df.parse(news.getDay());

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
                mTime.setText(String.valueOf(years)+" "+itemView.getContext().getResources().getString(R.string.years_ago));
            }else{
                if(months>0){
                    mTime.setText(String.valueOf(months)+" "+itemView.getContext().getResources().getString(R.string
                            .months_ago));
                }else {
                    if(weeks>0){
                        mTime.setText(String.valueOf(weeks)+" "
                                +itemView.getContext().getResources().getString(R.string.weeks_ago));
                    }else {
                        if(days>0){
                            mTime.setText(String.valueOf(days)+" "
                                    +itemView.getContext().getResources().getString(R.string.days_ago));
                        }else {
                            if(hours>0){
                                mTime.setText(String.valueOf(hours)+" "
                                        +itemView.getContext().getResources().getString(R.string.hours_ago));
                            }else {
                                if(minute>0){
                                    mTime.setText(String.valueOf(minute)+" "
                                            +itemView.getContext().getResources().getString(R.string.minutes_ago));
                                }else {
                                    mTime.setText(itemView.getContext().getResources().getString(R.string.just_now));
                                }

                            }

                        }
                    }
                }
            }
            mTitle.setText(news.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemtClick.onClick(news);
                }
            });
        }
    }
}
