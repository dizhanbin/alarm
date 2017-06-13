package com.dym.alarm.common;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.provider.Settings;

/**
 * Created by dizhanbin on 17/6/5.
 */

public class SystemUtil {


    public static boolean isADBEnable(Context context){



        boolean enableAdb = (Settings.Secure.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, 0) > 0);
        return  enableAdb;

    }

    public static boolean isPowerOff(Context context){

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
        return !isScreenOn;

    }
    public  static boolean isScreenLock(Context context){

        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();

        return flag;//如果flag为true，表示有两种状态：a、屏幕是黑的  b、目前正处于解锁状态  。


    }

}
