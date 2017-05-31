package com.dym.alarm;

import android.content.Context;
import android.content.SharedPreferences;

import com.dym.alarm.common.NLog;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by dizhanbin on 17/5/4.
 */

public class RP {

    public static class CK{

        public static final int K_NORMAL_COLOR = 0xff0B4E6F;
        public static final int K_PINFORM_COLOR = 0xff1d262a;
        public static final int K_POP_COLOR = 0x66000000;
        public static final int K_DIALOG_COLOR = 0xFF223953;

    }


    public static class Statusbar{


        public static int last_statusbar_color;

        public static void setStatusbarColor(int color) {

            StatusBarCompat.setStatusBarColor(ActController.instance, color);

            last_statusbar_color = color;




        }


    }

    public static class Data {



        public static int getStartTimes() {

            SharedPreferences sp = getPreferences();
            return sp.getInt(Key.key_starttimes,0);

        }




    }


    public static SharedPreferences getPreferences(){

        return DUMAPP.getInstance().getSharedPreferences("DYM", Context.MODE_PRIVATE);

    }

    public static void startApp(){

        int times = Data.getStartTimes();

        NLog.i("App start times:%d",times);

        times++;
        SharedPreferences sp = getPreferences();
        sp.edit().putInt(Key.key_starttimes,times).commit();




    }

    public static void closeApp(){




    }



    public class Key{

        public final static String key_starttimes = "StartTimes";


    }


    public static class Const{

        public final static String[] weeks = new String[]{
          "Sun","Mon","Tue","Wed","Thu","Fri","Sat"
        };

    }

}
