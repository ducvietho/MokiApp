package com.example.ducvietho.moki.screen.activities.comment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Comment;
import com.example.ducvietho.moki.utils.OnItemtClick;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenvanbo on 11/24/2017.
 */

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {
    private List<Comment> mComments;
    private Context mContext;
    private OnItemtClick<Comment> mClick;

    public CommentRecyclerAdapter(List<Comment> comments, Context context, OnItemtClick<Comment> click) {
        mComments = comments;
        mContext = context;
        mClick = click;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_comment_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       holder.bind(mComments.get(position));
    }

    @Override
    public int getItemCount() {
        return mComments == null ? 0 : mComments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgAvatar)
        CircleImageView mView;
        @BindView(R.id.txtName)
        TextView mName;
        @BindView(R.id.txtComment)
        TextView mComment;
        @BindView(R.id.txtTime)
        TextView mTime;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
        public void bind(final Comment comment){
            Picasso.with(mContext).load(comment.getUser().getAvatar()).into(mView);
            mName.setText(comment.getUser().getName());
            mComment.setText(comment.getContent());

            Date date;
            Date current = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = df.parse(comment.getTime());

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
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   mClick.onClick(comment);
                }
            });
        }
    }
}
