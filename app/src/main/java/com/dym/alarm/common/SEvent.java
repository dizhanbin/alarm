package com.dym.alarm.common;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

import java.util.HashMap;

/**
 * Created by dizhanbin on 2017/7/25.
 */

public class SEvent {


    static public void log(String eventName,String key,String value){

        NLog.i("Fabric ename:%s key:%s value:%s",eventName,key,value);


        try{
            Answers.getInstance().logCustom(new CustomEvent(eventName).putCustomAttribute(key,value));


        }catch (Throwable e){
            e.printStackTrace();
        }




    }


}
