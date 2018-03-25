package com.example.ducvietho.moki.screen.fragments.buy;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.screen.fragments.product.ProductFragment;
import com.example.ducvietho.moki.screen.fragments.product_buy.ProductBuyFragment;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.customview.FontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.tv_processing)
    FontTextView mProcessing;
    @BindView(R.id.tv_success)
    FontTextView mSuccess;
    View v;

    public BuyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_buy, container, false);
        ButterKnife.bind(this,v);
        mProcessing.setOnClickListener(this);
        mSuccess.setOnClickListener(this);
        startFragment(ProductBuyFragment.getInstance(1));
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_processing:
                startFragment(ProductBuyFragment.getInstance(1));
                mSuccess.setTextColor(Color.BLACK);
                mSuccess.setBackgroundColor(0xFFFFFF);
                mProcessing.setTextColor(Color.WHITE);
                mProcessing.setBackgroundResource(R.drawable.background_textview);
                break;
            case R.id.tv_success:
                startFragment(ProductBuyFragment.getInstance(2));
                mProcessing.setTextColor(Color.BLACK);
                mProcessing.setBackgroundColor(0xFFFFFF);
                mSuccess.setTextColor(Color.WHITE);
                mSuccess.setBackgroundResource(R.drawable.background_textview_right);
                break;

        }
    }
        public void startFragment(Fragment fragment){
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment,"findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }
}
