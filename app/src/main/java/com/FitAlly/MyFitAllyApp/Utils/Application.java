package com.FitAlly.MyFitAllyApp.Utils;

import android.content.Context;
import android.content.res.Configuration;


public class Application extends android.app.Application {

    public static final String TAG = Application.class.getSimpleName();

    public Context currentactvity = null; //set the currentid as none
    private static Application singleton;
    public static Application getInstance(){
        return singleton;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        singleton =this;
        ApplicationHelper.initDatabaseHelper(this);

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) { //any user configuration has changed
        super.onConfigurationChanged(newConfig);
    }


}