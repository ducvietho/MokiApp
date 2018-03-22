package com.example.ducvietho.moki.screen.activities.address;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.OrderAddress;
import com.example.ducvietho.moki.utils.Constants;
import com.example.ducvietho.moki.utils.OnItemtClick;
import com.example.ducvietho.moki.utils.customview.FontTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 19/03/2018.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private List<OrderAddress> mAddresses;
    private OnItemtClick<OrderAddress> mClick;

    public AddressAdapter(List<OrderAddress> addresses, OnItemtClick<OrderAddress> click) {
        mAddresses = addresses;
        mClick = click;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mAddresses.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mAddresses == null ? 0 : mAddresses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_tick)
        ImageView mImgTick;
        @BindView(R.id.img_option)
        ImageView mImgOption;
        @BindView(R.id.tv_street)
        FontTextView mStreet;
        @BindView(R.id.tv_village)
        FontTextView mVillage;
        @BindView(R.id.tv_district)
        FontTextView mDistrict;
        @BindView(R.id.tv_province)
        FontTextView mProvince;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final OrderAddress address, int position) {
            SharedPreferences preferences = ((Activity) itemView.getContext()).getSharedPreferences(Constants.PREF_ADDRESS, Context.MODE_PRIVATE);
            int choice = preferences.getInt(Constants.EXTRA_ADDRESS_POS, -1);
            if (choice > -1) {
                if (choice == position) {
                    mImgTick.setVisibility(View.VISIBLE);
                }
            }
            mStreet.setText(address.getStreet());
            mVillage.setText(address.getVillage());
            mDistrict.setText(address.getDistrict());
            mProvince.setText(address.getProvince());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClick.onClick(address);
                }
            });
        }
    }
}
