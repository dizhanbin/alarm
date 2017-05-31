package com.dym.alarm.model;

import com.dym.alarm.RP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dizhanbin on 17/5/20.
 */

public class MAlarm {

    public boolean on;

    public String label;
    public String begintime;
    public boolean repeat_week;
    public List<Integer> repeat_weeks ;

    public String sound;
    public boolean vibrate;

    public boolean repeat_day;
    public int repeat_day_unit;//0 second m minute h hour
    public String endtime;
    public int repeat_day_value=1;//重复值



    public MAlarm(){

        on = true;
        label = "label";
        begintime = "8:00";
        endtime = "18:00";
        repeat_weeks = new ArrayList<>();

    }

    public String getDayRepeatUnitStr(){

        return repeat_day_unit == 0 ? "S" : repeat_day_unit == 1 ? "M" : "H";

    }

    public String getDescript(){

        StringBuffer sb = new StringBuffer();
        sb.append("begintime: ").append(begintime).append("  ")
        .append(repeat_week?"repeat":"not repeat");

        if( repeat_week ) {
            sb.append("(");
            for (int i : repeat_weeks)
                sb.append(RP.Const.weeks[i]).append(",");
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
        }
        sb.append("\n");
        if( repeat_day ){




        }



        return sb.toString();

    }

    public long getNextTime(){

        if( repeat_week ){




        }
        return 0;

    }


}
