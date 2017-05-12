package com.mousebelly.app.userapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.mousebelly.app.userapp.Login.LoginActivity;

/**
 * Created by Kamal Kant on 28-04-2017.
 */

public class LocalSaveModule {
    public static final String Credential = "login";


    public static void storePreferences(Context c, String pwd) {

        SharedPreferences sharedpreferences;
        sharedpreferences = c.getSharedPreferences(Credential, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("UserName", LoginActivity.USERID);
        editor.putString("Password", pwd);

        editor.commit();


    }
}
