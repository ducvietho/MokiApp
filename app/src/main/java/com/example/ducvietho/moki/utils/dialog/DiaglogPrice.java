package com.example.ducvietho.moki.utils.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.utils.customview.FontTextView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.blackbox_vision.wheelview.view.WheelView;


/**
 * Created by ducvietho on 12/1/2017.
 */

public class DiaglogPrice {
    final String[] min = new String[]{"10,000 VND", "50,000 VND", "100,000 VND", "200,000 VND", "400,000 VND",
            "800,000 VND", "1,000,000 VND", "2,000,000 VND", "4,000,000 VND", "8,000,000 VND", "10,000,000 VND"};
    final String[] max = new String[]{"50,000 VND", "100,000 VND", "200,000 VND", "400,000 VND", "800,000 VND",
            "1,000,000 VND", "2,000,000 VND", "4,000,000 VND", "8,000,000 VND", "10,000,000 VND", "20,000,000 VND"};
    final String[] minInstead = new String[]{"10 K", "50 K", "100 K", "200 K", "400 K", "800 K", "1,000 K",
            "2,000 K", "4,000 K", "8,000 K", "10,000 K"};
    final String[] maxInstead = new String[]{"50 K", "100 K", "200 K", "400 K", "800 K", "1,000 K", "2,000 K",
            "4,000 K", "8,000 K", "10,000 K", "20,000 K"};
    @BindView(R.id.btnExit)
    Button btExit ;
    @BindView(R.id.btntChoose)
    Button btChoose ;
    @BindView(R.id.leftWheel)
    WheelView minPrice ;
    @BindView(R.id.rightWheel)
     WheelView maxPrice ;
    private Context mContext;

    public DiaglogPrice(Context context) {
        mContext = context;
    }

    public void showDialog(final FontTextView view ) {
        final Dialog dialog = new Dialog(mContext);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_price);
        ButterKnife.bind(this,dialog);
        minPrice.setItems(new ArrayList<String>(Arrays.asList(min)));
        maxPrice.setItems(new ArrayList<String>(Arrays.asList(max)));
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((Activity) mContext).overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);

            }
        });
        btChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priceMin = minInstead[minPrice.getSelectedItem()];
                String priceMax = maxInstead[maxPrice.getSelectedItem()];
                dialog.dismiss();
                view.setText(priceMin + " - " + priceMax);
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        ((Activity) mContext).overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
    }


}
