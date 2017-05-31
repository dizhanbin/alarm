package com.dym.alarm.views;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.dym.alarm.R;
import com.dym.alarm.model.MAlarm;

/**
 * Created by dizhanbin on 17/5/19.
 */

public class VHAlarmItem extends ViewHolder {


    public TextView text_label;
    public TextView text_descript;


    public SwitchCompat switch_on;
    public VHAlarmItem(View itemView) {
        super(itemView);

        text_label = (TextView) itemView.findViewById(R.id.text_alarm_label);
        switch_on = (SwitchCompat) itemView.findViewById(R.id.switch_on);
        text_descript = (TextView) itemView.findViewById(R.id.text_descript);
    }


    public void bind(MAlarm mAlarm) {

        text_label.setText(mAlarm.label);
        switch_on.setChecked( mAlarm.on );
        text_descript.setText(mAlarm.getDescript());

    }
}
