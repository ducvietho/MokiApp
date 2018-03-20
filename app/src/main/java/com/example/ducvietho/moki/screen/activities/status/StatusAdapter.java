package com.example.ducvietho.moki.screen.activities.status;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Brand;
import com.example.ducvietho.moki.data.model.Status;
import com.example.ducvietho.moki.utils.OnItemtClick;
import com.example.ducvietho.moki.utils.customview.FontTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 18/03/2018.
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    private List<Status> mList;
    private OnItemtClick<Status> mOnItemtClick;

    public StatusAdapter(List<Status> list, OnItemtClick<Status> onItemtClick) {
        mList = list;
        mOnItemtClick = onItemtClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status,parent,false);
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
        @BindView(R.id.tvNameStatus)
        FontTextView mName;
        @BindView(R.id.tvDescripStatus)
        FontTextView mDescrip;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(final Status status){
            mName.setText(status.getName());
            mDescrip.setText(status.getDescrip());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemtClick.onClick(status);
                }
            });
        }
    }
}
