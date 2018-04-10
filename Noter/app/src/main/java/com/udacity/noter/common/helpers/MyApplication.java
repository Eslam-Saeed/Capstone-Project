package com.udacity.noter.common.helpers;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.udacity.noter.common.network.ConnectToDB;


/**
 * Created by Eslam on 2/26/2017.
 */

public class MyApplication extends Application {

    private static Gson mGson;
    private static ConnectToDB sConnectToDB;

    @Override
    public void onCreate() {
        super.onCreate();
        mGson = new GsonBuilder().create();
        LocalizationHelper.changeAppLanguage(LocalizationHelper.getLanguage(this), this);
    }

    public static Gson getmGson() {
        return mGson;
    }


    public static MyApplication getInstance(Context context) {
        return ((MyApplication) context.getApplicationContext());
    }

    public static ConnectToDB getConnectToDB() {
        if (sConnectToDB == null)
            return new ConnectToDB();
        return sConnectToDB;
    }
}
