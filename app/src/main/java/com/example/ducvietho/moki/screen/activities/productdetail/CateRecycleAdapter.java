package com.example.ducvietho.moki.screen.activities.productdetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.ducvietho.moki.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducviet.
 */

public class CateRecycleAdapter extends RecyclerView.Adapter<CateRecycleAdapter.ViewHolder> {
    List<String> cateList;

    public CateRecycleAdapter(List<String> cateList) {
        this.cateList = cateList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cate_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtView.setText(cateList.get(position));
    }

    @Override
    public int getItemCount() {
        return cateList == null ? 0 : cateList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cate_name)
        TextView txtView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
