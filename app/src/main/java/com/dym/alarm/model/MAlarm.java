package com.dym.alarm.model;

import android.content.res.Configuration;
import android.text.format.DateFormat;

import com.alibaba.fastjson.JSON;
import com.dym.alarm.ActController;
import com.dym.alarm.DUMAPP;
import com.dym.alarm.R;
import com.dym.alarm.RP;
import com.dym.alarm.common.NLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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


    public String getEndtimeDisplay(){

      return  "00:00".equals(endtime) ? "24:00" : endtime;


    }


    public List<MSound> your_sounds = new ArrayList<>();


    public MAlarm(){

        on = true;
        label = getString(R.string.alarm_label);
        begintime = "08:00";
        endtime = "18:00";
        repeat_weeks = new ArrayList<>();

        createtime =  DateFormat.format("yyyy-MM-dd hh:mm:ss",System.currentTimeMillis()).toString();

    }

    public int getId(){
        return createtime.hashCode();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getId() == obj.hashCode();
    }

    public String getDayRepeatUnitStr(){

        return  repeat_day_unit == 0 ? "M" : "H";

    }

    private String getString(int rid){

        return DUMAPP.getInstance().getString(rid);
    }

    public String getDescript(){


        StringBuffer sb = new StringBuffer();

        sb.append(repeat_week? getString(R.string.des_repeat): getString( R.string.des_no_repeat ) );

        if( repeat_week ) {
            sb.append("(");
            for (int i : repeat_weeks)
                sb.append(RP.Const.weeks[i]).append(",");
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
        }
        sb.append("\n");

        if( repeat_day )
            sb.append(getString(R.string.des_begintime)).append(begintime).append("\n");
        else {
            sb.append(getString(R.string.des_alarm_time)).append(begintime);

        }
        if( repeat_day ){

            sb.append(getString(R.string.des_one_alarm)).append(repeat_day_value ).append(repeat_day_unit==0?getString(R.string.des_unit):getString(R.string.des_unit_hour)).append(repeat_day_value>1&&!RP.Locale.isChinese()?"s":"").append("\n");
            sb.append(getString(R.string.des_endtime)).append(getEndtimeDisplay()).append("  ");

        }


        if( on ) {
            long nexttime = getNextTime();
            if (nexttime > 0) {

                Date date_next = new Date(nexttime);
                sb.append("\n").append(getString(R.string.des_next_alarm)).append(date_next.toLocaleString());
            }
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

        if( RP.today == 7 ){
            if( repeat_weeks.size()>0)
                return repeat_weeks.get(0)+1;
        }
        else{

            for(int d : repeat_weeks)
            {
                if( d+1 == RP.today+1  )
                    return d+1;
            }

        }


        if( repeat_weeks.size() > 0 ){
            if( repeat_weeks.get(0)+1 != RP.today )
                return repeat_weeks.get(0)+1;
        }

        return 0;
    }


    public long getNextTime(){


        //if( !on )
        //    return 0;

       // NLog.log(getClass(),"getNextTime today:%d",RP.today);

        long notify_time = getDayNextTime(RP.today);

        if( notify_time > 0 )
            return notify_time;

        if( repeat_week ){

            int next_repeat_day = getNextRepeatDay();

           // NLog.log(getClass(),"next repeat day:%d",next_repeat_day);

            if( next_repeat_day > 0 ) {

                notify_time = getDayNextTime(next_repeat_day);
            }
            else
                return 0;
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
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,000);

        return calendar.getTimeInMillis();


    }


    private void printTime(String tag,long t){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        NLog.log(getClass(),"%s:%s",tag,sdf.format(new Date(t)) );

    }


    public long getDayNextTime(int day){

        Calendar calendar = Calendar.getInstance();


        if( day < RP.today )
            calendar.add(Calendar.DAY_OF_WEEK,7-RP.today+day);
        else
            calendar.set(Calendar.DAY_OF_WEEK,day);

        long begin_time = getMillion(calendar,begintime);
        long end_tiem = getMillion(calendar,("00:00".equals(endtime)?"23:59" :endtime));

       // NLog.log(getClass(),"day :%d",day);
       // printTime("day begin:",begin_time);
       // printTime("day end:",end_tiem);


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

    public String toString(){

        return createtime;
    }




}
