package com.example.ducvietho.moki.screen.fragments.favorite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.utils.OnClickItemProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 15/03/2018.
 */

public class RecyclerAdapterFavorite extends RecyclerView.Adapter<RecyclerAdapterFavorite.ViewHolder>{
    private List<Product> mProducts;
    private OnClickItemProduct mOnClick;

    public RecyclerAdapterFavorite(List<Product> products, OnClickItemProduct onClick) {
        mProducts = products;
        mOnClick = onClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts==null?0:mProducts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_product)
        ImageView mImageView;
        @BindView(R.id.tv_name)
        TextView mTitle;
        @BindView(R.id.tv_price)
        TextView mPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(final Product product){
            String [] images = product.getImage().split(",");
            Picasso.with(itemView.getContext()).load(images[0]).placeholder(R.drawable.no_image).into(mImageView);
            mTitle.setText(product.getName());
            int thousand = product.getPrice()/1000;
            int mili = thousand/1000;
            if(mili==0){
                mPrice.setText(String.valueOf(thousand)+",000");
            }else {

            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClick.onClick(product);
                }
            });
        }
    }
}
