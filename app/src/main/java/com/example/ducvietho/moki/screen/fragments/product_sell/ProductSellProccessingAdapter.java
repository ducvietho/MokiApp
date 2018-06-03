package com.example.ducvietho.moki.screen.fragments.product_sell;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.data.resource.remote.ProductDataRepository;
import com.example.ducvietho.moki.utils.OnClickProcessProduct;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 23/03/2018.
 */

public class ProductSellProccessingAdapter extends RecyclerView.Adapter<ProductSellProccessingAdapter.ViewHolder> {
    private List<Product> mProducts;
    private OnClickProcessProduct mClickProcessProduct;

    public ProductSellProccessingAdapter(List<Product> products, OnClickProcessProduct clickProcessProduct) {
        mProducts = products;
        mClickProcessProduct = clickProcessProduct;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_sell_processing, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts == null ? 0 : mProducts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_product)
        ImageView mImageView;
        @BindView(R.id.tv_name)
        TextView mTitle;
        @BindView(R.id.tv_price)
        TextView mPrice;
        @BindView(R.id.tvAccept)
        FontTextView mAccept;
        @BindView(R.id.tvCancel)
        FontTextView mCancel;
        @BindView(R.id.tv_message)
        FontTextView mMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Product product) {
            String[] images = product.getImage().split(",");
            mMessage.setText(product.getMessage());
            Picasso.with(itemView.getContext()).load(images[0]).placeholder(R.drawable.no_image).into(mImageView);
            mTitle.setText(product.getName());
            String price = String.valueOf(product.getPrice());
            if (price.length() <= 6) {
                price = new StringBuilder(price).insert(price.length() - 3, ",").toString();
                mPrice.setText(price);
            } else {
                price = new StringBuilder(price).insert(price.length() - 3, ",").toString();
                price = new StringBuilder(price).insert(price.length() - 7, ",").toString();
                mPrice.setText(price);
            }
            mAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickProcessProduct.acceptSell(product);

                }
            });
            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickProcessProduct.cancelSell(product);
                }
            });

        }
    }
}
