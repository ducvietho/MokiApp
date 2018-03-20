package com.example.ducvietho.moki.utils;

/**
 * Created by ducvietho.
 */

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.text.DecimalFormat;

public class Utils {
    public static int getScreenWidth(Context context){
        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        return metrics.widthPixels;
    }

    public static int getScreenHeigh(Context context){
        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;
    }

    public static String formatMoneyWhileTexting(String money) {
        if (money == null) {
            return null;
        }
        try {
            String formated_money = new DecimalFormat("#,###,###").format(Long.valueOf(money.replace(".", "").replace(",", "")).longValue());
            if (formated_money != null) {
                return formated_money.replace(".", ",");
            }
            return formated_money;
        } catch (NumberFormatException e) {
            return "";
        }
    }

    public static String formatMoney(String money) {
        if (money == null) {
            return null;
        }
        try {
            long lMoney = Long.valueOf(money.replace(".", "").replace(",", "")).longValue();
            if (lMoney % 1000 != 0) {
                lMoney += 1000 - (lMoney % 1000);
            }
            String formated_money = new DecimalFormat("#,###,### VNĐ").format(lMoney);
            if (formated_money != null) {
                return formated_money.replace(".", ",");
            }
            return formated_money;
        } catch (Exception e) {
            return null;
        }
    }


}
