package com.dym.alarm.common;

/**
 * Created by dizhanbin on 17/2/16.
 */

public class FLock {

     volatile  boolean islock;

    Object mlock = new Object();
    public synchronized void lock()
    {



            if (islock) {

                try {
                    mlock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            islock = true;


    }

    public synchronized void unlock(){

        try {
            islock = false;
            mlock.notify();
        }catch (Exception e){
            islock = false;
        }

    }






}
