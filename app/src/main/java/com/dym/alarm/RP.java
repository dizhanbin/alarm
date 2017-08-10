package com.dym.alarm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.dym.alarm.common.NLog;

import java.io.File;
import java.util.Calendar;
import java.util.Random;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by dizhanbin on 17/5/4.
 */

public class RP {

    static {

        Calendar calendar = Calendar.getInstance();
        today = calendar.get(Calendar.DAY_OF_WEEK);


        File file_yoursound_dir = new File(Local.path_yoursournds_dir);
        file_yoursound_dir.mkdirs();


        Data.loadVipData();

    }

    public static class CK{

        public static final int K_NORMAL_COLOR = 0xff0B4E6F;
        public static final int K_PINFORM_COLOR = 0xff1d262a;
        public static final int K_POP_COLOR = 0x66000000;
        public static final int K_DIALOG_COLOR = 0xFF223953;

        public static final int K_GOOGLEDRIVER = 1004;
    }


    public static int today;

    public static class Statusbar{


        public static int last_statusbar_color;

        public static void setStatusbarColor(int color) {

            StatusBarCompat.setStatusBarColor(ActController.instance, color);
            last_statusbar_color = color;

        }

        public static void setStatusbarColor(Activity activity,int color) {

            StatusBarCompat.translucentStatusBar(activity);


        }


    }

    public static class Data {


        static boolean isVip;
        static int userRandomID;

        public static int getStartTimes() {

            SharedPreferences sp = getPreferences();
            return sp.getInt(Key.key_starttimes,0);

        }

        public static boolean isVip(){
            return isVip;
        }
        public static void setIsVip(boolean vip){
            isVip = vip;
            if( isVip ){
                getPreferences().edit().putBoolean("vip",true).commit();
            }
        }


        public static void loadVipData() {
            isVip = getPreferences().getBoolean("vip",false);
        }


        public static int getUserRandomID(){

            userRandomID = getPreferences().getInt("UserRandomID",0);
            if( userRandomID == 0 ){

                Random random = new Random(System.currentTimeMillis());
                userRandomID =  random.nextInt(10);
                getPreferences().edit().putInt("UserRandomID",userRandomID).commit();

            }
            return userRandomID;

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

    public static class Local{

        public static String path_yoursournds = DUMAPP.getInstance().getFilesDir().getAbsolutePath()+"/yoursounds.dat";

        public static String path_yoursournds_dir = DUMAPP.getInstance().getFilesDir().getAbsolutePath()+"/yoursounds";

    }


    public static class Url{

        public static String market = "market://details?id=" +  ActController.instance.getPackageName();

        public static String market_https = "https://play.google.com/store/apps/details?id=" +  ActController.instance.getPackageName();


    }


    public static class Locale {

        static int ischinese = -1;

        public static boolean isChinese() {

            if( ischinese == -1 ) {

                Configuration config = DUMAPP.getInstance().getResources().getConfiguration();
                if (config != null && config.locale != null && config.locale.getCountry().toString().indexOf("CN") > -1) {
                    ischinese = 1;
                }
                else
                    ischinese = 0;
            }
            return ischinese==1;
        }
    }
}
