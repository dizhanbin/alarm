package com.dym.alarm.common;
public class FlowBoxs{
    public static String getBox(Event event){
        switch(event){
         case REQ_ALARM_NEXT: return "flows/alarm_set.xml";
        }
        return null;
    }
}
