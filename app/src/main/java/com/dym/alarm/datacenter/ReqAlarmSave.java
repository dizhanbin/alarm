package com.dym.alarm.datacenter;

import com.dym.alarm.model.MAlarm;

/**
 * Created by dizhanbin on 17/5/21.
 */

public class ReqAlarmSave implements Request {
    @Override
    public void run(Object aprams, ResponseListener listener) {


        MAlarm alarm = (MAlarm) aprams;


        DataCenter.save(alarm);



        listener.callback(ResponseListener.RE_OK,alarm);


    }
}
