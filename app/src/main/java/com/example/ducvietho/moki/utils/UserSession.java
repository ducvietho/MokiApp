package com.example.ducvietho.moki.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.example.ducvietho.moki.data.model.User;
import com.example.ducvietho.moki.screen.activities.login.LoginActivity;
import com.google.gson.Gson;

/**
 * Created by ducvietho.
 */

public class UserSession {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "UserSession";
    private static final String IS_USER_LOGIN = "isUserLogin";
    private static final String USER_INFO = "user";
    private static final String KEY_TOKEN = "toke";

    public UserSession(Context mcontext) {
        this.context = mcontext;
        this.sharedPreferences = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createUserLoginSession(User user) {
        editor.putBoolean(IS_USER_LOGIN, true);
        Gson gson = new Gson();
        String objUser = gson.toJson(user);
        editor.putString(USER_INFO, objUser);
        editor.commit();
    }

    public boolean checkUserLogin() {
        if (sharedPreferences.getBoolean(IS_USER_LOGIN, false)) {
            return true;
        }
        return false;
    }

    public User getUserDetail() {
        String objUser = sharedPreferences.getString(USER_INFO, null);
        System.out.println("ObjUser: " + objUser);
        Gson gson = new Gson();
        User user = gson.fromJson(objUser, User.class);
        System.out.println("User: " + user.getName());
        return user;
    }

    public void logoutUser(int state) {
        editor.clear();
        editor.commit();
        Intent intent = new LoginActivity().getIntent(context,state);
        context.startActivity(intent);
    }
}
