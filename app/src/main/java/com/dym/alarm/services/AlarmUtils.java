package com.dym.alarm.services;

import android.app.AlarmManager;

import com.dym.alarm.DUMAPP;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by dizhanbin on 17/5/23.
 *
 set(int type,long startTime,PendingIntent pi)：一次性闹钟
 setRepeating(int type，long startTime，long intervalTime，PendingIntent pi)：
 重复性闹钟,和3有区别,3闹钟间隔时间不固定
 setInexactRepeating（int type，long startTime，long intervalTime,PendingIntent pi）：
 重复性闹钟，时间不固定
 cancel(PendingIntent pi)：取消AlarmManager的定时服务
 getNextAlarmClock()：得到下一个闹钟，返回值AlarmManager.AlarmClockInfo
 setAndAllowWhileIdle(int type, long triggerAtMillis, PendingIntent operation)
 和set方法类似，这个闹钟运行在系统处于低电模式时有效
 setExact(int type, long triggerAtMillis, PendingIntent operation)：
 在规定的时间精确的执行闹钟，比set方法设置的精度更高
 setTime(long millis)：设置系统墙上的时间
 setTimeZone(String timeZone)：设置系统持续的默认时区
 setWindow(int type, long windowStartMillis, long windowLengthMillis, PendingIntent operation)：
 设置一个闹钟在给定的时间窗触发。类似于set，该方法允许应用程序精确地控制操作系统调
 整闹钟触发时间的程度。
 关键参数讲解：

 Type(闹钟类型)：
 有五个可选值:
 AlarmManager.ELAPSED_REALTIME:
 闹钟在手机睡眠状态下不可用，该状态下闹钟使用相对时间（相对于系统启动开始），状态值为3;
 AlarmManager.ELAPSED_REALTIME_WAKEUP
 闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟也使用相对时间，状态值为2；
 AlarmManager.RTC
 闹钟在睡眠状态下不可用，该状态下闹钟使用绝对时间，即当前系统时间，状态值为1；
 AlarmManager.RTC_WAKEUP
 表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用绝对时间，状态值为0;
 AlarmManager.POWER_OFF_WAKEUP
 表示闹钟在手机关机状态下也能正常进行提示功能，所以是5个状态中用的最多的状态之一，该状态下闹钟也是用绝对时间，状态值为4；不过本状态好像受SDK版本影响，某些版本并不支持；
 startTime：闹钟的第一次执行时间，以毫秒为单位，可以自定义时间，不过一般使用当前时间。
 需要注意的是,本属性与第一个属性（type）密切相关,如果第一个参数对应的闹钟使用的是相对时间
 （ELAPSED_REALTIME和ELAPSED_REALTIME_WAKEUP），那么本属性就得使用相对时间
 （相对于系统启动时间来说）,比如当前时间就表示为:SystemClock.elapsedRealtime()；
 如果第一个参数对应的闹钟使用的是绝对时间(RTC、RTC_WAKEUP、POWER_OFF_WAKEUP）,
 那么本属性就得使用绝对时间，比如当前时间就表示 为：System.currentTimeMillis()。
 intervalTime：表示两次闹钟执行的间隔时间,也是以毫秒为单位.
 PendingIntent：绑定了闹钟的执行动作，比如发送一个广播、给出提示等等。
 PendingIntent是Intent的封装类。需要注意的是，如果是通过启动服务来实现闹钟提 示的话，PendingIntent对象的获取就应该采用Pending.getService
 (Context c,int i,Intent intent,int j)方法；如果是通过广播来实现闹钟
 提示的话，PendingIntent对象的获取就应该采用 PendingIntent.getBroadcast
 (Context c,int i,Intent intent,int j)方法；如果是采用Activity的方式来实
 现闹钟提示的话，PendingIntent对象的获取就应该采用
 PendingIntent.getActivity(Context c,int i,Intent intent,int j)方法。
 如果这三种方法错用了的话，虽然不会报错，但是看不到闹钟提示效果。
 */

public class AlarmUtils {

    public static void setAlarm(){


        AlarmManager alarmManager = (AlarmManager) DUMAPP.getInstance().getSystemService(ALARM_SERVICE);





    }

}
