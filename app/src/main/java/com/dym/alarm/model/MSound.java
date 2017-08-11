package com.dym.alarm.model;

import com.dym.alarm.R;
import com.dym.alarm.RP;

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
        ms.name = RP.UI.getString(R.string.edit_slient);
        return ms;

    }

    public String toString(){

        return String.format("type:%d name:%s selected:%b path:%s",type,name,selected,path);

    }
}
