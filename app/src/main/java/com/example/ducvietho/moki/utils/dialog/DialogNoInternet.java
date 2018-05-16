package com.example.ducvietho.moki.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.ducvietho.moki.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by ducviet.
 */

public class DialogNoInternet {
    @BindView(R.id.button_nointernet)
    Button button;
    Context context;

    public DialogNoInternet(Context context) {
        this.context = context;
    }

    public void showdialog() {
        final Dialog d = new Dialog(context);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_nointernet);
        ButterKnife.bind(this, d);
        final Window window = d.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        d.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                d.dismiss();
            }
        },3000);
    }
}
