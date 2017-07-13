package com.dym.alarm.model;

/**
 * Created by dizhanbin on 17/7/3.
 */

public class MSound {


    public String name;
    public int type;//0  system    sound  1 user sound 2 slient
    public String path;
    public boolean selected;


    public static MSound sient(){

        MSound ms = new MSound();
        ms.type =  2;
        ms.name = "Slient";
        return ms;

    }
}
