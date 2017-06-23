package com.dym.alarm.services;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dym.alarm.NotifyController;
import com.dym.alarm.common.AlarmUtil;
import com.dym.alarm.common.NLog;
import com.dym.alarm.model.MAlarm;

/**
 * Created by dizhanbin on 17/5/27.
 */

public class AlarmReceiver  extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {


       // NLog.i("AlarmReceiver :%s %s",);



        Intent notify_intent = new Intent(context, NotifyController.class);
        notify_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(notify_intent);

        String json = intent.getStringExtra("json");

        MAlarm alarm = MAlarm.fromJson(json);



        NLog.i("%d AlarmReceiver ",alarm.getId() );
        AlarmUtil.addAlarm(context,alarm);



    }
}
