package com.dym.alarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.dym.alarm.common.NLog;

import java.util.Calendar;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by dizhanbin on 17/5/4.
 */

public class RP {

    static {

        Calendar calendar = Calendar.getInstance();
        today = calendar.get(Calendar.DAY_OF_WEEK);

    }

    public static class CK{

        public static final int K_NORMAL_COLOR = 0xff0B4E6F;
        public static final int K_PINFORM_COLOR = 0xff1d262a;
        public static final int K_POP_COLOR = 0x66000000;
        public static final int K_DIALOG_COLOR = 0xFF223953;

    }


    public static int today;

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



    public static class NetState{


        public static final int NETWORKTYPE_INVALID = 0;

        public static final int NETWORKTYPE_GSM = 1;

        public static final int NETWORKTYPE_WIFI = 2;

        public static int state;

        public static boolean isValide(){

            return NETWORKTYPE_INVALID != state;
        }
        public static boolean isWifi(){
            return state == NETWORKTYPE_WIFI;
        }
        public static boolean isGsm(){
            return state == NETWORKTYPE_GSM;
        };


        public static int scanNetState(){


            int value = NETWORKTYPE_INVALID;

            ConnectivityManager manager = (ConnectivityManager) DUMAPP.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                int type = networkInfo.getType();
                if (type == ConnectivityManager.TYPE_WIFI ) {
                    value = NETWORKTYPE_WIFI;
                } else {
                    value = NETWORKTYPE_GSM;
                }
            } else {
                value = NETWORKTYPE_INVALID;
            }

            state = value;
            return value;

        }

        /* 检查网络状态并同步 */
        public static void scanNetTypeAndSync() {


            int last = state;
            scanNetState();
            if( last != state ) {
                ///@// TODO: 17/6/5
            }
            NLog.i("Net state is:%d",state);

        }



    }

}
