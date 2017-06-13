package com.dym.alarm.common;

import android.os.Bundle;

import com.dym.alarm.DUMAPP;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by dizhanbin on 17/5/27.
 */

public class AnalyseUtil {


    static Tracker mTracker;
    public static void googleTracker( String category, String action, String label){

       /* if( mTracker == null ){
            mTracker = GoogleAnalytics.getInstance(DUMAPP.getInstance()).newTracker("dymalarm-bb067");
        }
        NLog.i("FBEvent postNewEvent key:%s action:%s label:%s",category,action,label);
        if( mTracker != null )
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label).build()
        );
        */

        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(DUMAPP.getInstance());


        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, category);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, action);
        bundle.putString(FirebaseAnalytics.Param.CONTENT, label);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);




    }


    public static void addEvent(String category,String key,String value){

        googleTracker(category,key,value);

    }
}
