package com.dym.alarm.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dym.alarm.ActController;
import com.dym.alarm.NotifyController;
import com.dym.alarm.common.AlarmUtil;
import com.dym.alarm.common.NLog;
import com.dym.alarm.datacenter.DataCenter;
import com.dym.alarm.model.MAlarm;

import java.util.List;

/**
 * Created by dizhanbin on 2017/7/20.
 */

public class BootReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        NLog.i("receiver broadcast boot");
       // Intent notify_intent = new Intent(context, ActController.class);
       // notify_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // context.startActivity(notify_intent);

        List<MAlarm> list = DataCenter.getDatas();
        for(MAlarm mAlarm : list) {
            if (mAlarm.on)
                AlarmUtil.addAlarm(context, mAlarm);
            else
                AlarmUtil.cancel(context, mAlarm);
        }



    }
}
