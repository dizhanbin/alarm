/* create my 17 */
package com.dym.alarm.forms;

import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.DrawableUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.dym.alarm.ActController;
import com.dym.alarm.Form;
import com.dym.alarm.common.AlarmUtil;
import com.dym.alarm.common.AnalyseUtil;
import com.dym.alarm.common.DDialog;
import com.dym.alarm.common.FileUtils;
import com.dym.alarm.model.MAlarm;
import com.dym.alarm.R;
import com.dym.alarm.common.Event;
import com.dym.alarm.common.NLog;
import com.dym.alarm.model.MSound;

import java.io.File;

import tyrantgit.explosionfield.ExplosionField;

public class FormEdit extends Form {



    ToggleButton repeat_day_m;
    ToggleButton repeat_day_h;


    View repeat_day_group;
    View repeat_week_group;
    View group_end_time;

    CheckBox checkbox_rep_day;
    CheckBox checkbox_rep_week;


    MAlarm model;


    EditText edit_label;


    TextView text_begin_time;
    TextView text_end_time;

    TextView text_alarm_descript;

    TextView text_sound;

    CheckBox checkbox_vibrate;

    //


    int[] repeat_week_ids = new int[]{
            R.id.tb_rep0,
            R.id.tb_rep1,
            R.id.tb_rep2,
            R.id.tb_rep3,
            R.id.tb_rep4,
            R.id.tb_rep5,
            R.id.tb_rep6,
    };


    String last_repeat_day;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        NLog.i("FormEdit onCreateView.");
        if (mView == null) {
            mView = inflater.inflate(R.layout.form_edit, null);
            setView(mView);

            repeat_day_m = (ToggleButton) ViewInject(R.id.repeat_day_minute);
            repeat_day_h = (ToggleButton) ViewInject(R.id.repeat_day_hour);

            repeat_day_group = ViewInject(R.id.repeat_day_group);
            repeat_week_group = ViewInject(R.id.repeat_week_group);

            checkbox_rep_day = (CheckBox) ViewInject(R.id.checkbox_repeat_day);
            checkbox_rep_week = (CheckBox) ViewInject(R.id.checkbox_repeat_week);

            edit_label = (EditText) ViewInject(R.id.edit_label);


            text_begin_time = (TextView) ViewInject(R.id.text_begin_time);
            text_end_time = (TextView) ViewInject(R.id.text_end_time);


            checkbox_vibrate = (CheckBox) ViewInject(R.id.checkbox_vibrate);

            group_end_time = ViewInject(R.id.group_end_time);


            text_alarm_descript = (TextView) ViewInject(R.id.text_alarm_descript);

            text_sound = (TextView) ViewInject(R.id.text_sound);

            initUI();

        }
        return mView;
    }

    private void setWeekToggleChecked(int index) {


        ToggleButton tb = (ToggleButton) ViewInject(repeat_week_ids[index]);
        tb.setChecked(true);

    }

    private void initUI() {


        edit_label.setText(model.label);

        checkbox_rep_week.setChecked(model.repeat_week);
        repeat_week_group.setVisibility(model.repeat_week ? View.VISIBLE : View.GONE);

        for (int i : model.repeat_weeks)
            setWeekToggleChecked(i);

        checkbox_rep_day.setChecked(model.repeat_day);
        repeat_day_group.setVisibility(model.repeat_day ? View.VISIBLE : View.GONE);
        group_end_time.setVisibility(model.repeat_day ? View.VISIBLE : View.GONE);


       // if( model.repeat_day) {
            last_repeat_day = model.repeat_day_value + model.getDayRepeatUnitStr();

            setRepeatDay(last_repeat_day);
        //}

        log("last repeat day:%s",last_repeat_day);





        text_begin_time.setText(model.begintime);
        text_end_time.setText(model.endtime);
        checkbox_vibrate.setChecked(model.vibrate);


        text_alarm_descript.setText(model.getDescript());



        updateSoundView();

    }

    private void updateSoundView(){


        if( model.sound == null ){


            Drawable drawable_left = getContext().getResources().getDrawable(R.drawable.quantum_ic_volume_off_grey600_36);
            drawable_left.setBounds(0, 0, drawable_left.getMinimumWidth(), drawable_left.getMinimumHeight());
            text_sound.setCompoundDrawablesRelative(drawable_left,null,null,null);
            text_sound.setText(R.string.edit_slient);


        }
        else{

            Drawable drawable = ActController.instance.getResources().getDrawable(android.R.drawable.ic_btn_speak_now);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            text_sound.setCompoundDrawables(drawable, null, null, null);
            text_sound.setText(FileUtils.getFileNameNoExt( model.sound ) );
        }

    }



    @Override
    public void onClick(View view) {
        if (mView == null)
            return;
        switch (view.getId()) {

            case R.id.btn_back:
                sendMessage(Event.REQ_FORM_BACK);
                break;
            case R.id.repeat_day_minute:
            case R.id.repeat_day_hour:

                repeat_change(view);
                break;
            case R.id.checkbox_repeat_day: {
                AppCompatCheckBox box = (AppCompatCheckBox) view;
                repeat_day_group.setVisibility(box.isChecked() ? View.VISIBLE : View.GONE);
                group_end_time.setVisibility(box.isChecked() ? View.VISIBLE : View.GONE);
            }
            break;
            case R.id.checkbox_repeat_week: {
                AppCompatCheckBox box = (AppCompatCheckBox) view;
                repeat_week_group.setVisibility(box.isChecked() ? View.VISIBLE : View.GONE);
            }
            break;

            case R.id.btn_save:

                AnalyseUtil.addEvent("formedit","operator","click_save");
                saveAlarm();

                if( model.on )
                    AlarmUtil.addAlarm(getContext(), model );

                break;
            case R.id.text_begin_time: {

                new DDialog.Builder(getContext()).setContentView(R.layout.dialog_time_select).setCancelable(true)
                        .setInitListener(new DDialog.ViewInitListener() {
                            @Override
                            public void ViewInit(View view) {


                                TimePicker tp = (TimePicker) view.findViewById(R.id.timePicker);
                                tp.setIs24HourView(true);
                                if (model.begintime != null) {
                                    int pi = model.begintime.indexOf(':');
                                    tp.setCurrentHour(Integer.parseInt(model.begintime.substring(0, pi)));
                                    tp.setCurrentMinute(Integer.parseInt(model.begintime.substring(pi + 1)));
                                }

                            }
                        })
                        .addButtonListener(R.id.btn_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                TimePicker tp = (TimePicker) DDialog.ddialog.findViewById(R.id.timePicker);
                                NLog.i("hour :%d minute :%d ", tp.getCurrentHour(), tp.getCurrentMinute());
                                text_begin_time.setText(String.format("%02d:%02d", tp.getCurrentHour(), tp.getCurrentMinute()));

                                dialog.dismiss();
                                //text_begin_time.setText(  );
                            }
                        })
                        .addButtonListener(R.id.btn_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();

            }
            break;
            case R.id.text_end_time: {
                new DDialog.Builder(getContext()).setContentView(R.layout.dialog_time_select).setCancelable(true)
                        .setInitListener(new DDialog.ViewInitListener() {
                            @Override
                            public void ViewInit(View view) {


                                TimePicker tp = (TimePicker) view.findViewById(R.id.timePicker);
                                tp.setIs24HourView(true);
                                if (model.endtime != null) {
                                    int pi = model.endtime.indexOf(':');
                                    tp.setCurrentHour(Integer.parseInt(model.endtime.substring(0, pi)));
                                    tp.setCurrentMinute(Integer.parseInt(model.endtime.substring(pi + 1)));

                                }

                            }
                        })
                        .addButtonListener(R.id.btn_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                TimePicker tp = (TimePicker) DDialog.ddialog.findViewById(R.id.timePicker);
                                NLog.i("hour :%d minute :%d ", tp.getCurrentHour(), tp.getCurrentMinute());

                                int h = tp.getCurrentHour();
                                int minute = tp.getCurrentMinute();

                                if( h == 0 && minute == 0  ){

                                    h = 24;
                                }
                                text_end_time.setText(String.format("%02d:%02d", h, tp.getCurrentMinute()));

                                dialog.dismiss();
                                //text_begin_time.setText(  );
                            }
                        })
                        .addButtonListener(R.id.btn_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })

                        .create().show();
            }
            break;
            case R.id.text_sound:

                setFormtype(FormType.FORM_ONLY);
                sendMessage(Event.FORM_SOUND_SET,model);
                break;
            case R.id.btn_del:

                new DDialog.Builder(getContext()).setContentView(R.layout.dialog_confirm).setInitListener(new DDialog.ViewInitListener() {
                    @Override
                    public void ViewInit(View view) {

                        TextView text_title = (TextView) view.findViewById(R.id.text_title);
                        TextView text_msg = (TextView) view.findViewById(R.id.text_msg);


                        text_title.setText(R.string.dialog_title_confirm);
                        text_msg.setText(R.string.msg_delete_alarm);


                        TextView text_ok = (TextView) view.findViewById(R.id.btn_ok);
                        TextView text_cancel = (TextView) view.findViewById(R.id.btn_cancel);


                        text_ok.setText(R.string.btn_delete);
                        text_cancel.setText(R.string.btn_cancel);



                    }
                }).addButtonListener(R.id.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sendMessage(Event.REQ_ALARM_REMOVE,model);
                        dialog.dismiss();

                        ExplosionField explosionField = ExplosionField.attach2Window(ActController.instance);

                        explosionField.explode(FormEdit.this.mView);



                        sendMessage(Event.REQ_FORM_BACK);



                    }
                }).addButtonListener(R.id.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).setCancelable(true)
                .create().show();




                break;



        }

    }

    private void saveAlarm() {


        model.label = edit_label.getText().toString();

        model.begintime = text_begin_time.getText().toString();
        model.endtime = text_end_time.getText().toString();

        model.repeat_week = checkbox_rep_week.isChecked();
        model.repeat_day = checkbox_rep_day.isChecked();


        model.repeat_weeks.clear();
        for (int i = 0; i < repeat_week_ids.length; i++) {
            ToggleButton tb = (ToggleButton) ViewInject(repeat_week_ids[i]);
            if (tb.isChecked())
                model.repeat_weeks.add(i);

        }

        model.vibrate = checkbox_vibrate.isChecked();


        if (repeat_day_m.isChecked()) {
            model.repeat_day_unit = 0;
            String sv = repeat_day_m.getTextOn().toString();
            model.repeat_day_value = Integer.parseInt(sv.substring(0, sv.length() - 1));
        } else if (repeat_day_h.isChecked()) {
            model.repeat_day_unit = 1;
            String sv = repeat_day_h.getTextOn().toString();
            model.repeat_day_value = Integer.parseInt(sv.substring(0, sv.length() - 1));
        }

        if( model.repeat_day ){

            last_repeat_day = model.getDayRepeatUnitStr();

        }


        sendMessage(Event.REQ_ALARM_SAVE, model);


    }


    private void repeat_change(View sel) {


        repeat_day_m.setChecked(repeat_day_m == sel);
        repeat_day_h.setChecked(repeat_day_h == sel);

        final int index =  (repeat_day_m == sel ? 0 : 1);

        int max = 0;
        int min = 1;
        int value = min;

        switch (index) {


            case 0: {
                max = 100;
                int v_len = repeat_day_m.getTextOn().length();
                if (v_len > 1) {

                    value = Integer.parseInt(repeat_day_m.getTextOn().toString().substring(0, v_len - 1));
                }
            }
            break;
            case 1: {
                max = 12;
                int v_len = repeat_day_h.getTextOn().length();
                if (v_len > 1) {

                    value = Integer.parseInt(repeat_day_h.getTextOn().toString().substring(0, v_len - 1));
                }
            }
            break;


        }


        final int n_max = max;
        final int n_min = min;
        final int n_value = value;


        new DDialog.Builder(getContext()).setContentView(R.layout.dialog_number_pick).setCancelable(true)
                .setInitListener(new DDialog.ViewInitListener() {
                    @Override
                    public void ViewInit(View view) {
                        NumberPicker np = (NumberPicker) view.findViewById(R.id.numberpicker);

                        np.setMaxValue(n_max);
                        np.setMinValue(n_min);
                        np.setValue(n_value);


                    }
                })
                .addButtonListener(R.id.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        NumberPicker np = (NumberPicker) DDialog.ddialog.findViewById(R.id.numberpicker);
                        if (np != null) {
                            switch (index) {

                                case 0:
                                    repeat_day_m.setTextOn(np.getValue() + getContextString(R.string.repeat_day_unit_m));
                                    last_repeat_day = repeat_day_m.getTextOn().toString();
                                    break;
                                case 1:
                                    repeat_day_h.setTextOn(np.getValue() + getContextString(R.string.repeat_day_unit_h));
                                    last_repeat_day = repeat_day_h.getTextOn().toString();
                                    break;
                            }
                        }


                        dialog.dismiss();
                    }
                })
                .addButtonListener(R.id.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        setRepeatDay(last_repeat_day);
                        dialog.dismiss();
                    }
                })

                .setCancelable(false)
                .create().show();


    }

    @Override
    public void onValue(Object value) {


        if ( value == null) {
            if( model == null )
                model = new MAlarm();
        }
        else
            model = (MAlarm) value;

    }

    @Override
    public boolean onMessage(Event event, Object value) {
        switch (event) {

            case REP_ALARM_SAVE_SUCCESS:
                sendMessage(Event.REQ_FORM_BACK);
                return false;
            case REQ_SOUND_CHANGED:
                updateSoundView();
                return  true;

        }
        return false;
    }

    public void setRepeatDay(String v_u) {

        if( v_u == null )
            v_u = getContextString(R.string.repeat_day_unit_m);
        int index =  v_u.endsWith(getContextString(R.string.repeat_day_unit_m)) ? 0 : 1;

        repeat_day_m.setChecked(false);
        repeat_day_h.setChecked(false);

        switch (index) {


            case 0:
                repeat_day_m.setChecked(true);
                if (v_u.length() > 1)
                    repeat_day_m.setTextOn(v_u);
                else
                    repeat_day_m.setTextOn(getContextString(R.string.repeat_day_unit_m));
                break;
            case 1:
                repeat_day_h.setChecked(true);
                if (v_u.length() > 1)
                    repeat_day_h.setTextOn(v_u);
                else
                    repeat_day_h.setTextOn(getContextString(R.string.repeat_day_unit_h));
                break;

        }


    }


    @Override
    public void onPush(boolean fromback) {
        setFormtype(FormType.FORM_TOP);
    }
}
