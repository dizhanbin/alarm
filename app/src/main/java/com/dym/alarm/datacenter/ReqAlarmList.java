package com.dym.alarm.datacenter;

import com.dym.alarm.RP;
import com.dym.alarm.model.MAlarm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dizhanbin on 17/5/20.
 */

public class ReqAlarmList implements Request {


    @Override
    public void run(Object aprams,ResponseListener listener) {

        List<MAlarm> list = DataCenter.getDatas();

        if( list.size() == 0 && RP.Data.getStartTimes() == 1  ){
            list.add(newAlarm());
            DataCenter.saveAlarms();
        }

        listener.callback(ResponseListener.RE_OK,list);



    }

    private MAlarm newAlarm(){

        MAlarm a0 = new MAlarm();


        a0.on = true;


        a0.label = "aaa";
        a0.begintime = "10:00";
        a0.repeat_weeks = new ArrayList<>();
        a0.repeat_weeks.add(0);
        a0.repeat_weeks.add(1);

        a0.repeat_week = true;

        a0.repeat_day = true;

        a0.repeat_day_unit = 0;



        a0.endtime = "18:00";

        a0.vibrate = true;

        return a0;

    }


}
