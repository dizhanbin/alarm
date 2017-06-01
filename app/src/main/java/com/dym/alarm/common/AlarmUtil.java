package com.dym.alarm.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.dym.alarm.DUMAPP;

/**
 * Created by dizhanbin on 17/5/27.
 */

public class AlarmUtil {



    public static void addAlarm(){


        AlarmManager am = (AlarmManager) DUMAPP.getInstance().getSystemService(Context.ALARM_SERVICE);


        Intent intent = new Intent("com.dym.alarm.one");
        intent.putExtra("json","json_str");
        PendingIntent sender = PendingIntent.getBroadcast(DUMAPP.getInstance(),1001, intent, PendingIntent
                .FLAG_CANCEL_CURRENT);

        long notify_time = System.currentTimeMillis()+5000;
        am.set(0, notify_time , sender);
        am.set(0, notify_time , sender);




    }
}
