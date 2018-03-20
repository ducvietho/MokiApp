package com.example.ducvietho.moki.screen.activities.category;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Category;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 12/1/2017.
 */

public class CatagoryRecyclerAdapter extends RecyclerView.Adapter<CatagoryRecyclerAdapter.ViewHolder> {
    private List<Category> mList;
    private Context mContext;

    public CatagoryRecyclerAdapter(List<Category> list, Context context) {
        mList = list;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(mList.get(position).getName());
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = new Gson().toJson(mList.get(position));
                Intent intent =  ((Activity) mContext).getIntent();
                intent.putExtra("catagory", category);
                ((Activity) mContext).setResult(Activity.RESULT_OK, intent);
                ((Activity) mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNameCata)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
