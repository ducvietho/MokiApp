package com.example.ducvietho.moki.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
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

public class DialogSize {
   @BindView(R.id.wheelWide)
    WheelView wheelView;
    @BindView(R.id.wheelLong)
     WheelView wheelView1 ;
    @BindView(R.id.wheelHigh)
    WheelView wheelView2 ;
    @BindView(R.id.btComplete)
    Button complete ;
    @BindView(R.id.tvSize)
    TextView tvSize ;
    private Context mContext;

    public DialogSize(Context context) {
        mContext = context;
    }
    public void showDialog(final TextView textView) {
        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_size);
        ButterKnife.bind(this,dialog);
        final String[] size = new String[]{"1cm", "2cm", "3cm", "4cm", "5cm", "6cm", "7cm", "8cm", "9cm", "10cm", "11cm", "12cm", "13cm", "14cm", "15cm", "16cm", "17cm", "18cm", "19cm", "20cm", "21cm", "22cm", "23cm", "24cm", "25cm", "26cm", "27cm", "28cm", "29cm", "30cm", "31cm", "32cm", "33cm", "34cm", "35cm", "36cm", "37cm", "38cm", "39cm", "40cm", "41cm", "42cm", "43cm", "44cm", "45cm", "46cm", "47cm", "48cm", "49cm", "50cm", "51cm", "52cm", "53cm", "54cm", "55cm", "56cm", "57cm", "58cm", "59cm", "60cm", "61cm", "62cm", "63cm", "64cm", "65cm", "66cm", "67cm", "68cm", "69cm", "70cm", "71cm", "72cm", "73cm", "74cm", "75cm", "76cm", "77cm", "78cm", "79cm", "80cm",};
        wheelView.setItems(new ArrayList<String>(Arrays.asList(size)));
        wheelView1.setItems(new ArrayList<String>(Arrays.asList(size)));
        wheelView2.setItems(new ArrayList<String>(Arrays.asList(size)));

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(size[wheelView.getSelectedItem()]+"x"+size[wheelView1.getSelectedItem()
                        ]+"x"+size[wheelView2.getSelectedItem()]);
                dialog.dismiss();
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

}
