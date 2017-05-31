package com.dym.alarm.common;

import com.dym.alarm.DUMAPP;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by dizhanbin on 17/5/27.
 */

public class AnalyseUtil {


    static Tracker mTracker;
    public static void googleTracker( String category, String action, String label){

        if( mTracker == null ){
            mTracker = GoogleAnalytics.getInstance(DUMAPP.getInstance()).newTracker("dymalarm-bb067");
        }
        NLog.i("FBEvent postNewEvent key:%s action:%s label:%s",category,action,label);
        if( mTracker != null )
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label).build()
        );

    }


    public static void addEvent(String category,String key,String value){

        googleTracker(category,key,value);

    }
}
