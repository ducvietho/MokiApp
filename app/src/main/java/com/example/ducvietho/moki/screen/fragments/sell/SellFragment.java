package com.example.ducvietho.moki.screen.fragments.sell;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.screen.fragments.product.ProductFragment;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.customview.FontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.tv_product)
    FontTextView mProduct;
    @BindView(R.id.tv_processing)
    FontTextView mProcessing;
    @BindView(R.id.tv_success)
    FontTextView mSuccess;
    View v;
    public SellFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v = inflater.inflate(R.layout.fragment_sell, container, false);
        ButterKnife.bind(this,v);
        startFragment(ProductFragment.getInstance(1,new UserSession(v.getContext()).getUserDetail().getId()));
        mProduct.setOnClickListener(this);
        mProcessing.setOnClickListener(this);
        mSuccess.setOnClickListener(this);
        return v;
    }
    public void startFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment,"findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_product:
                startFragment(ProductFragment.getInstance(1,new UserSession(v.getContext()).getUserDetail().getId()));
                mSuccess.setTextColor(Color.BLACK);
                mSuccess.setBackgroundColor(0xFFFFFF);
                mProcessing.setTextColor(Color.BLACK);
                mProcessing.setBackgroundColor(0xFFFFFF);
                mProduct.setTextColor(Color.WHITE);
                mProduct.setBackgroundResource(R.drawable.background_textview);
                break;
            case R.id.tv_processing:

                mSuccess.setTextColor(Color.BLACK);
                mSuccess.setBackgroundColor(0xFFFFFF);
                mProduct.setTextColor(Color.BLACK);
                mProduct.setBackgroundColor(0xFFFFFF);
                mProcessing.setTextColor(Color.WHITE);
                mProcessing.setBackgroundResource(R.color.app_color);
                break;
            case R.id.tv_success:

                mProduct.setTextColor(Color.BLACK);
                mProduct.setBackgroundColor(0xFFFFFF);
                mProcessing.setTextColor(Color.BLACK);
                mProcessing.setBackgroundColor(0xFFFFFF);
                mSuccess.setTextColor(Color.WHITE);
                mSuccess.setBackgroundResource(R.drawable.background_textview_right);
                break;
        }
    }
}
