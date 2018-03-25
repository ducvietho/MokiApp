package com.example.ducvietho.moki.screen.fragments.product_buy;

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
 * Created by ducvietho on 23/03/2018.
 */

public class ProductBuyAdapter extends RecyclerView.Adapter<ProductBuyAdapter.ViewHolder> {
    private List<Product> mProducts;
    private OnClickItemProduct mOnClick;

    public ProductBuyAdapter(List<Product> products, OnClickItemProduct onClick) {
        mProducts = products;
        mOnClick = onClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_buy,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_product)
        ImageView mImageView;
        @BindView(R.id.tv_name)
        TextView mTitle;
        @BindView(R.id.tv_price)
        TextView mPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Product product) {
            String[] images = product.getImage().split(",");
            Picasso.with(itemView.getContext()).load(images[0]).placeholder(R.drawable.no_image).into(mImageView);
            mTitle.setText(product.getName());
            String price = String.valueOf(product.getPrice());
            if(price.length()<=6){
                price = new StringBuilder(price).insert(price.length()-3,",")
                        .toString();
                mPrice.setText(price);
            }else{
                price = new StringBuilder(price).insert(price.length()-3,",")
                        .toString();
                price = new StringBuilder(price).insert(price.length()-7,",")
                        .toString();
                mPrice.setText(price);
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
