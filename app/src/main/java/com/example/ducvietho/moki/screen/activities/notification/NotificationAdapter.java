package com.example.ducvietho.moki.screen.activities.notification;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Notification;
import com.example.ducvietho.moki.utils.OnItemtClick;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 28/03/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    private List<Notification> mList;
    private OnItemtClick<Notification> mItemtClick;

    public NotificationAdapter(List<Notification> list, OnItemtClick<Notification> itemtClick) {
        mList = list;
        mItemtClick = itemtClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false);
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
        @BindView(R.id.img_product)
        ImageView mImgProduct;
        @BindView(R.id.tv_name)
        FontTextView mName;
        @BindView(R.id.tv_time)
        FontTextView mTime;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(final Notification notification){
            String [] images = notification.getImage().split(",");
            Picasso.with(itemView.getContext()).load(images[0]).into(mImgProduct);
            mName.setText(notification.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemtClick.onClick(notification);
                }
            });
        }
    }
}
