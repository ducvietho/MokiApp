package com.example.ducvietho.moki.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.ducvietho.moki.R;

import butterknife.ButterKnife;

/**
 * Created by ducvietho on 25/03/2018.
 */

public class DialoagBuySuccess {
    private Context mContext;
    private Dialog dialog;
    public DialoagBuySuccess(Context context) {
        dialog = new Dialog(context);
        mContext = context;
    }
    public void showDialog(){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_not_infor);
        dialog.setCancelable(false);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },1500);
    }
}
