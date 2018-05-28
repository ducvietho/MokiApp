package com.example.ducvietho.moki.screen.activities.productdetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Category;
import com.example.ducvietho.moki.screen.activities.product_category.ProductCategoryActivity;
import com.example.ducvietho.moki.utils.OncClickItemCate;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducviet.
 */

public class CateRecycleAdapter extends RecyclerView.Adapter<CateRecycleAdapter.ViewHolder> {
    List<String> cateList;
    private OncClickItemCate mOncClick;
    public CateRecycleAdapter(List<String> cateList,OncClickItemCate oncClick) {
        this.cateList = cateList;
        mOncClick = oncClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cate_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(cateList.get(position));
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
        public void bind(final String string){
            txtView.setText(string);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOncClick.onClick(string);
                }
            });
        }
    }
}
