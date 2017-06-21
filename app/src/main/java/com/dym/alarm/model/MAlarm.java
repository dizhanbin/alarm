package com.dym.alarm.model;

import android.text.format.DateFormat;

import com.alibaba.fastjson.JSON;
import com.dym.alarm.RP;
import com.dym.alarm.common.NLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dizhanbin on 17/5/20.
 */

public class MAlarm {

    public boolean on;

    public String label;
    public String begintime;
    public boolean repeat_week;
    public List<Integer> repeat_weeks = new ArrayList<>() ;

    public String sound;
    public boolean vibrate;

    public boolean repeat_day;
    public int repeat_day_unit;//0 m minute h hour
    public String endtime;
    public int repeat_day_value=1;//重复值

    public String createtime;//yyyy-mm-dd hh:mm:ss


    public MAlarm(){

        on = true;
        label = "label";
        begintime = "08:00";
        endtime = "18:00";
        repeat_weeks = new ArrayList<>();

        createtime =  DateFormat.format("yyyy-MM-dd hh:mm:ss",System.currentTimeMillis()).toString();

    }

    public int getId(){
        return createtime.hashCode();
    }
    public String getDayRepeatUnitStr(){

        return  repeat_day_unit == 0 ? "M" : "H";

    }

    public String getDescript(){

        StringBuffer sb = new StringBuffer();

        sb.append(repeat_week?"repeat":"not repeat");

        if( repeat_week ) {
            sb.append("(");
            for (int i : repeat_weeks)
                sb.append(RP.Const.weeks[i]).append(",");
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
        }
        sb.append("\n");

        if( repeat_day )
            sb.append("begintime: ").append(begintime).append("\n");
        else
            sb.append("alarm time: ").append(begintime);
        if( repeat_day ){

            sb.append("one alarm/").append(repeat_day_value ).append(repeat_day_unit==0?" minute":" hour").append(repeat_day_value>1?"s":"").append("\n");
            sb.append("endtime:    ").append(endtime).append("  ");

        }



        return sb.toString();

    }


    public boolean isRepeatDay(){

        for(int i : repeat_weeks ){
            if( i+1  == RP.today )
                return true;
        }
        return false;

    }

    public boolean isRepeatDay(int day){
        return ( day == RP.today );
    }


    public int getNextRepeatDay(){

        for(int i=0;i<repeat_weeks.size();i++){

            if( repeat_weeks.get(i) +1  == RP.today ){

                if( i+1 < repeat_weeks.size() ){

                    return repeat_weeks.get(i+1)+1;
                }

            }
        }
        return 0;
        //return repeat_weeks.get(0)+1;

    }


    public long getNextTime(){


        //if( !on )
        //    return 0;

        long notify_time = getDayNextTime(RP.today);

        if( notify_time > 0 )
            return notify_time;

        if( repeat_week ){
           notify_time = getDayNextTime( getNextRepeatDay() );
        }

        return notify_time;

    }

    long getMillion(String hm){

        return getMillion(Calendar.getInstance(),hm);

    }
    long getMillion(Calendar calendar,String hm){


        int h = Integer.parseInt( hm.substring(0,2) );
        int m = Integer.parseInt( hm.substring(3,5) );

        calendar.set(Calendar.HOUR_OF_DAY,h);
        calendar.set(Calendar.MINUTE,m);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);

        return calendar.getTimeInMillis();


    }





    public long getDayNextTime(int day){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,day);

        long begin_time = getMillion(calendar,begintime);
        long end_tiem = getMillion(calendar,endtime);

        long current = System.currentTimeMillis();
        long notify_time = begin_time;

        if( repeat_day ){

            if( begin_time < current  )
            {
                if(  current > end_tiem)
                    return 0;

                switch (repeat_day_unit )
                {
                    case 0 ://minute


                        //NLog.i("%d current time",current);
                        //NLog.i("%d notify time",notify_time);
                        while(notify_time<current){

                            notify_time += repeat_day_value * 60 * 1000l;

                        }
                        return notify_time;

                    case 1 :
                        while(notify_time<current){
                            notify_time += repeat_day_value * 60 * 60 * 1000l;

                        }
                        return notify_time;

                }

            }
            else
                return begin_time;

        }
        else{

            if( begin_time < System.currentTimeMillis() )
                return 0;
            else
                return begin_time;

        }

        return 0;
    }



    public String toJson(){

        return JSON.toJSONString(this);

    }
    public static MAlarm fromJson(String json){

        return JSON.parseObject(json,MAlarm.class);

    }


}
