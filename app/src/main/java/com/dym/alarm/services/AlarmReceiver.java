package com.dym.alarm.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dym.alarm.common.NLog;

/**
 * Created by dizhanbin on 17/5/27.
 */

public class AlarmReceiver  extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {


        NLog.i("AlarmReceiver :%s %s",intent,intent.getStringExtra("json"));

    }
}
