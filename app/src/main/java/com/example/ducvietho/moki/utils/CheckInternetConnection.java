package com.example.ducvietho.moki.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.ducvietho.moki.utils.dialog.DialogNoInternet;

/**
 * Created by ducvietho on 19/04/2018.
 */

public class CheckInternetConnection {
    private Context context;

    public CheckInternetConnection(Context context) {
        this.context = context;
    }
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }else{

            return false;
        }

    }
}
