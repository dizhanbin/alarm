package com.dym.alarm.common;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dzb on 16/5/18.
 */
public class MessageCenter {



    static IToDo todo;

    public static void register(IToDo td){

        todo = td;

    }


    public static void sendMessage(Event event,Object value)
    {
        if( todo != null && todo.isValidate() ) {
            EventFilter.filter(event,value);
            todo.todo(event, value);
        }else {
            NLog.i("filtered event:%s",event);
        }
    }
    public static void sendMessage(Event event)
    {
        if( todo != null && todo.isValidate() ) {
            EventFilter.filter(event,null);
            todo.todo(event, null);
        }else {
            NLog.i("filtered event:%s",event);
        }
    }

    public static void unregister() {

        todo = null;

    }

    public static void sendMessage(final Event event,final Object value,int delay){

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
               sendMessage(event,value);
            }
        },delay);
    }
}
