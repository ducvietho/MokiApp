package com.example.ducvietho.moki.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.ducvietho.moki.R;


/**
 * Created by ducvietho on 12/4/2017.
 */

public class DialogLoading {
    private Context mContext;
    private Dialog mDialog;
    private int currentIndex;
    private int endIndex = 31;
    private int startIndex = 0;

    public DialogLoading(Context context) {
        mContext = context;
        mDialog = new Dialog(mContext);
    }
    public void showDialog(){

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.layout_loading);
        mDialog.setCancelable(false);
        final int [] images = new int[]{
                R.drawable.pr0,R.drawable.pr1,R.drawable.pr2,R.drawable.pr3,R.drawable.pr4,R.drawable.pr5,
                R.drawable.pr6,R.drawable.pr7,R.drawable.pr8,R.drawable.pr9,R.drawable.pr10,
                R.drawable.pr11,R.drawable.pr12,R.drawable.pr13,R.drawable.pr14,R.drawable.pr15,
                R.drawable.pr16,R.drawable.pr17,R.drawable.pr18,R.drawable.pr19,R.drawable.pr20,
                R.drawable.pr21,R.drawable.pr22,R.drawable.pr23,R.drawable.pr24,R.drawable.pr25,
                R.drawable.pr26,R.drawable.pr27,R.drawable.pr28,R.drawable.pr29,R.drawable.pr30,
                R.drawable.pr31,R.drawable.pr32
        };
        ImageView imageView = (ImageView)mDialog.findViewById(R.id.img_loading);
        nextImage(imageView,images);
        mDialog.show();
    }
    public void cancelDialog(){
        mDialog.dismiss();
    }
    public void nextImage(final ImageView image1, final int [] images){
        image1.setImageResource(images[currentIndex]);
        Animation rotateimage = AnimationUtils.loadAnimation(mContext, R.anim.custom_anim);
        image1.startAnimation(rotateimage);
        currentIndex++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentIndex>endIndex){
                    currentIndex = 0;
                    currentIndex++;
                    previousImage(image1,images);
                }else{
                    nextImage(image1,images);
                }

            }
        },1);

    }
    public void previousImage(final ImageView image1, final int [] images){
        image1.setImageResource(images[currentIndex]);
        Animation rotateimage = AnimationUtils.loadAnimation(mContext, R.anim.custom_anim);
        image1.startAnimation(rotateimage);
        currentIndex--;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentIndex<startIndex){
                    currentIndex++;
                    nextImage(image1,images);
                }else{
                    previousImage(image1,images);
                }
            }
        },1);

    }
}
