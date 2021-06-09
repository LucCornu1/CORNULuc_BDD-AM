package com.example.cornuluc_persistance_bdd_mysql;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager
{
    private static SharedPreferenceManager instance;
    private static Context ctx;

    private static final String sharedPrefName = "file";
    private static final String keyName = "Log_Nom";
    private static final String keyMail = "Log_Mail";

    private SharedPreferenceManager(Context context)
    {
        ctx = context;
    }

    public static synchronized SharedPreferenceManager getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new SharedPreferenceManager(context);
        }
        return instance;
    }

    public boolean userLogin(String username, String email)
    {
        SharedPreferences MySharedPreferences = ctx.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = MySharedPreferences.edit();

        myEditor.putString(keyName,username);
        myEditor.putString(keyMail,email);
        myEditor.apply();

        return true;
    }

    public boolean isLoggedIn()
    {
        SharedPreferences MySharedPreferences = ctx.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        return MySharedPreferences.getString(keyName, "") != "";
    }

    public boolean logout()
    {
        SharedPreferences MySharedPreferences = ctx.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = MySharedPreferences.edit();

        myEditor.clear();
        myEditor.apply();

        return true;
    }


    public static String getUserName()
    {
        SharedPreferences MySharedPreferences = ctx.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        return MySharedPreferences.getString(keyName, "");
    }

    public static String getUserMail()
    {
        SharedPreferences MySharedPreferences = ctx.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        return MySharedPreferences.getString(keyMail, "");
    }
}
