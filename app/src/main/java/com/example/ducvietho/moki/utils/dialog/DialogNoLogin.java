package com.example.ducvietho.moki.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.ducvietho.moki.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 14/03/2018.
 */

public class DialogNoLogin {
    @BindView(R.id.btn_close)
    Button btnClose;
    private Context mContext;

    public DialogNoLogin(Context context) {
        mContext = context;
    }
    public void showDialog(){
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_not_login);
        dialog.setCancelable(false);
        ButterKnife.bind(this, dialog);
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
    }
}
