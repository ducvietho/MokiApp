package com.example.ducvietho.moki.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.ducvietho.moki.R;


/**
 * Created by ducvietho.
 */

public class DialogExit {
    Context context;

    public DialogExit(Context context) {
        this.context = context;
    }

    public void showdialog() {
        final Dialog d = new Dialog(context);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_exit);
        Button btnCancel = (Button) d.findViewById(R.id.cancel_exit);
        Button btnAccept = (Button) d.findViewById(R.id.accept_exit);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                context.startActivity(intent);
                System.exit(0);
            }
        });
        d.show();
    }
}
