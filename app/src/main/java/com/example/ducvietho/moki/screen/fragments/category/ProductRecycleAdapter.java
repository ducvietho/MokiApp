package com.example.ducvietho.moki.screen.fragments.category;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Product;
import com.example.ducvietho.moki.screen.activities.productdetail.ProductDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho.
 */

public class ProductRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final int TYPE_PRODUCT = 0;
    public final int TYPE_LOAD = 1;
    private List<Product> mProducts;
    private Context mContext;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    public ProductRecycleAdapter(List<Product> products, Context context) {
        mProducts = products;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PRODUCT) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            return new ViewHolder(v);
        } else if (viewType == TYPE_LOAD) {
            return new LoadHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        } else if (getItemViewType(position) == TYPE_PRODUCT) {
            ((ProductRecycleAdapter.ViewHolder) holder).bind(mProducts.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mProducts.get(position) == null ? TYPE_LOAD : TYPE_PRODUCT;
    }

    @Override
    public int getItemCount() {
        return mProducts.size() == 0 ? 0 : mProducts.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_product)
        ImageView mImageView;
        @BindView(R.id.title)
        TextView mName;
        @BindView(R.id.price)
        TextView mPrice;
        @BindView(R.id.comment)
        TextView mNumberComment;
        @BindView(R.id.like)
        TextView mNumberLike;
        @BindView(R.id.img_like)
        ImageView mImageViewLike;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Product product) {
            mName.setText(product.getName());
            mPrice.setText(String.valueOf(product.getPrice() / 1000) + "K");
            mNumberLike.setText(String.valueOf(product.getLike()));
            mNumberComment.setText(String.valueOf(product.getComment()));
            String[] images = product.getImage().split(",");
            Picasso.with(itemView.getContext()).load(images[0]).placeholder(R.drawable.no_image).into(mImageView);
            if(product.getIs_liked()==1){
               mImageViewLike.setImageResource(R.drawable.grid_heart_on);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemView.getContext().startActivity(new ProductDetailActivity().getIntent(itemView.getContext(), product.getId()));
                }
            });
        }
    }

    class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }


    interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
