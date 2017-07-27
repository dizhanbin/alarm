package com.dym.alarm.common;


import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dizhanbin on 17/6/1.
 */

public abstract class FHandler {

    public abstract void handle(PVD pvd);

    Timer timer = new Timer();

    Hashtable<Event,TimerTask> stacks = new Hashtable<>();

    public void post(final Event event,final Object value,final long delay){

        //NLog.i("fhandler add %s",event);

        if( stacks.contains(event) ){
            stacks.remove(event).cancel();
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {


                handle(new PVD(event,value,delay));

            }
        };
        timer.schedule(task,delay);

        stacks.put(event,task);


    }

    public static class PVD{
        public Event event;
        public Object value;
        public long delay;

        public PVD(Event e,Object v,long d){
            event = e;
            value = v;
            delay = d;

        }

    }



}
