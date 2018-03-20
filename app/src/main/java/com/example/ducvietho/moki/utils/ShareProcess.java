package com.example.ducvietho.moki.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by ducvietho on 15/03/2018.
 */

public class ShareProcess {
    private Context mContext;

    public ShareProcess(Context context) {
        mContext = context;
    }

    public void shareAppMoki(){

    }
    public void shareContentProduct(){
        String imageToShare = "http://s1.dmcdn.net/hxdt6/x720-qef.jpg"; //Image You wants to share
        String title = "Title to share"; //Title you wants to share
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        shareIntent.setType("*/*");
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putExtra(Intent.EXTRA_TEXT, imageToShare);
        mContext.startActivity(Intent.createChooser(shareIntent, "Select App to Share Text and Image"));
    }
}
