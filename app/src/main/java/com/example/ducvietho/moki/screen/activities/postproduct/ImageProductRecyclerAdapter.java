package com.example.ducvietho.moki.screen.activities.postproduct;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ducvietho.moki.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 12/3/2017.
 */

public class ImageProductRecyclerAdapter extends RecyclerView.Adapter<ImageProductRecyclerAdapter.ViewHolder> {
    private List<String> mUrl;
    private Context mContext;

    public ImageProductRecyclerAdapter(List<String> url, Context context) {
        mUrl = url;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mUrl.get(position));

    }

    @Override
    public int getItemCount() {

        return mUrl==null?0:mUrl.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_product_sale)
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(String string){
            Picasso.with(mContext).load(new File(string)).into(mImageView);
        }
    }
}
