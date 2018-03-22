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
import com.example.ducvietho.moki.utils.customview.FontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 20/03/2018.
 */

public class DialogNotInfor {
    @BindView(R.id.tv_notifi)
    FontTextView mNotifi;
    @BindView(R.id.btn_close)
    Button btnClose;
    private Context mContext;
    private Dialog dialog;

    public DialogNotInfor(Context context) {
        mContext = context;
        dialog = new Dialog(mContext);
    }
    public void showDialog(String string){

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_not_infor);
        dialog.setCancelable(false);
        ButterKnife.bind(this, dialog);
        mNotifi.setText(string);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },3000);
    }

}
