package com.dym.alarm.datacenter;

import com.dym.alarm.DUMAPP;
import com.dym.alarm.common.Event;
import com.dym.alarm.common.MessageCenter;
import com.dym.alarm.common.Utils;

/**
 * Created by dizhanbin on 17/5/21.
 */

public class EventBusiness {


    public static boolean request(Event event,Object value){

        switch (event)
        {

            case REQ_ALARM_LIST:

                DataRequest.request(Event.REQ_ALARM_LIST,Event.REP_ALARM_LIST,null);
                return true;

            case REQ_ALARM_SAVE:

                DataRequest.request(Event.REQ_ALARM_SAVE,Event.REP_ALARM_SAVE_SUCCESS,value);
                return true;

            case REQ_OPENSOURCE_LIST:

                new Thread(){
                    public void run(){
                        MessageCenter.sendMessage(Event.REP_OPENSOURCE_LIST, Utils.getAssetString(DUMAPP.getInstance()));

                    }
                }.start();

                return true;

        }
        return false;



    }

}
