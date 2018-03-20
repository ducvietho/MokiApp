package com.example.ducvietho.moki.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.ducvietho.moki.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.blackbox_vision.wheelview.view.WheelView;


/**
 * Created by ducvietho on 12/3/2017.
 */

public class DialogWeight {
    @BindView(R.id.wheelComplete)
    WheelView wheelView ;
    @BindView(R.id.btntComplete)
    Button complete ;
    private Context mContext;

    public DialogWeight(Context context) {
        mContext = context;
    }
    public void showDialog(final TextView textView){
        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_weight);
        ButterKnife.bind(this,dialog);
        final String [] weight = new String[]{
                "Trên 0kg đến 0.5kg","Trên 0.5kg đến 1.0kg",
                "Trên 1.0kg đến 1.5kg","Trên 1.5kg đến 2.0kg",
                "Trên 2kg đến 2.5kg","Trên 2.5kg đến 3kg",
                "Trên 3kg đến 3.5kg","Trên 3.5kg đến 4kg",
                "Trên 4kg đến 4.5kg","Trên 4.5kg đến 5kg",
                "Trên 5kg đến 5.5kg","Trên 5.5kg đến 6kg"
        };
        wheelView.setItems(new ArrayList<String>(Arrays.asList(weight)));
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(weight[wheelView.getSelectedItem()]);
                dialog.dismiss();
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


    }
}
