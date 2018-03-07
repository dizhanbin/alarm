package com.dym.alarm.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.dym.alarm.BuildConfig;
import com.dym.alarm.DUMAPP;
import com.dym.alarm.RP;
import com.dym.alarm.model.MAlarm;
import com.dym.alarm.services.AlarmReceiver;

import java.util.Calendar;

/**
 * Created by dizhanbin on 17/5/27.
 */

public class AlarmUtil {

    final static String url_alarm = "content://dymalarm/";

    public static void addAlarm(Context context, MAlarm alarm) {

        long time = alarm.getNextTime();

        NLog.i("notify time:%d %s",time,alarm);

        if (time > 0) {

            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(RP.Key.key_intent_alarmone);
            intent.setClass(context, AlarmReceiver.class);
            intent.putExtra("json", alarm.toJson());
           // intent.setData(Uri.parse(url_alarm+alarm.getId()));
           //intent.setClass(context,AlarmUtil.class);
            PendingIntent sender = PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent
                    .FLAG_CANCEL_CURRENT);

            ;
           // am.cancel(sender);
            am.setExact(AlarmManager.RTC_WAKEUP, time, sender);

        }
        else{

            NLog.i("invalide alarm is on:%b",alarm.on);
        }

        if (BuildConfig.DEBUG && time > 0) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            NLog.i("notify time:%d-%02d-%02d %02d:%02d  request id:%d",
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH)+1,
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    alarm.getId()

            );

        }


    }

    public static void cancel(Context context, MAlarm alarm) {


            NLog.i("notify cancel:%s",alarm);

            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(RP.Key.key_intent_alarmone);
            intent.setClass(context,AlarmReceiver.class);
            intent.putExtra("json", alarm.toJson());
            PendingIntent sender = PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent
                    .FLAG_CANCEL_CURRENT);
            ;

            sender.cancel();
            am.cancel(sender);


    }
}
