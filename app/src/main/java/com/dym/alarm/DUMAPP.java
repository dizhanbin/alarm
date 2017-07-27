package com.dym.alarm;


import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.dym.alarm.common.FirebaseLock;
import io.fabric.sdk.android.Fabric;

import com.dym.alarm.common.NLog;

import io.fabric.sdk.android.Fabric;
import java.nio.channels.FileLock;


/**
 * Created by dzb on 16/5/18.
 */
public class DUMAPP extends MultiDexApplication {


    static DUMAPP application;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

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


    void initAnswer(){



    }
}
