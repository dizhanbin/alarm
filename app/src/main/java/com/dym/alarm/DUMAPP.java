package com.dym.alarm;


import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.dym.alarm.common.FirebaseLock;
import com.dym.alarm.common.NLog;

import java.nio.channels.FileLock;


/**
 * Created by dzb on 16/5/18.
 */
public class DUMAPP extends MultiDexApplication {


    static DUMAPP application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
        RP.startApp();
    }


    public static Context getInstance() {

        return application;
    }

    @Override
    public void onTerminate() {
        RP.closeApp();
        super.onTerminate();
    }
}
