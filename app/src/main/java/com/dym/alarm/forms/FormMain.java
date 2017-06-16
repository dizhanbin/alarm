/* create my 17 */
package com.dym.alarm.forms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.dym.alarm.Form;
import com.dym.alarm.R;
import com.dym.alarm.common.AlarmUtil;
import com.dym.alarm.common.Event;
import com.dym.alarm.common.NLog;
import com.dym.alarm.datacenter.DataRequest;
import com.dym.alarm.model.MAlarm;
import com.dym.alarm.views.VHAlarmItem;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class FormMain extends Form implements View.OnClickListener {



    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        NLog.i("FormMain onCreateView..");
        if( mView == null ) {
            mView = inflater.inflate(R.layout.form_main, null);
            setView(mView);
            setFormtype(FormType.FORM_ONLY);

            mRecyclerView = (RecyclerView) ViewInject(R.id.recyclerview);

            //SlideInUpAnimator animator = new SlideInUpAnimator(new OvershootInterpolator(5f));

            SlideInLeftAnimator animator = new SlideInLeftAnimator();
            animator.setInterpolator(new OvershootInterpolator());

            mRecyclerView.setItemAnimator(animator);
            mRecyclerView.getItemAnimator().setAddDuration(1000);
            mRecyclerView.getItemAnimator().setRemoveDuration(1000);
            mRecyclerView.getItemAnimator().setMoveDuration(1000);
            mRecyclerView.getItemAnimator().setChangeDuration(1000);
            initUI();


        }
        sendMessage(Event.REQ_ALARM_LIST);

        return mView;
    }

    @Override
    public void onClick(View view) {

        NLog.i("FormMain.onClick :%s",view);
        if( mView == null)
            return;
        switch (view.getId()){

            case R.id.btn_add:
                sendMessage(Event.FORM_EDIT);
                break;
            case R.id.view_alarm_item:

                NLog.i("view_alarm_item tag:%d",view.getTag());

                sendMessage(Event.FORM_EDIT,mDatas.get((int)view.getTag()));

                break;
            case R.id.switch_on:
            {

                SwitchCompat sw = (SwitchCompat) view;

                int pos =  (int)( view.getTag() );

                mDatas.get(pos).on = sw.isChecked();

                sendMessage(Event.REQ_ALARM_SAVE,mDatas.get(pos));

                if( !mDatas.get(pos).on )
                    AlarmUtil.cancel(getContext(),mDatas.get(pos));
                else
                    AlarmUtil.addAlarm(getContext(),mDatas.get(pos));


            }
            break;
            case R.id.btn_setting:

                sendMessage(Event.FORM_SETTING);
                break;


        }
    }



    private void initUI() {



    }

    @Override
    public boolean onMessage(Event event, Object value) {
        switch (event){
            case REP_ALARM_LIST:
                initRecyclerView((List<MAlarm>)value);
                return true;
        }
        return false;
    }



    List<MAlarm> mDatas = new ArrayList<>();
    private void initRecyclerView(List<MAlarm> value) {

        mDatas.clear();




        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = View.inflate(parent.getContext(),R.layout.view_alarm_item,null);
                VHAlarmItem vh = new VHAlarmItem( view );

                return vh;

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                VHAlarmItem vh = (VHAlarmItem) holder;
                vh.itemView.setTag( position );
                vh.switch_on.setTag( position );
                vh.bind( mDatas.get(position) );

            }

            @Override
            public int getItemCount() {

                return mDatas.size();
            }
        });


        //for(mDatas.addAll(value);)

        for(int i=0;i<value.size();i++){


            MAlarm mAlarm = value.get(i);
            mDatas.add( mAlarm );
            mRecyclerView.getAdapter().notifyItemInserted(i);

            if( mAlarm.on )
                AlarmUtil.addAlarm(getContext(),mAlarm);
            else
                AlarmUtil.cancel(getContext(),mAlarm);


        }


/*
        for(MAlarm mAlarm : mDatas){
            if( mAlarm.on )
                AlarmUtil.addAlarm(getContext(),mAlarm);
            else
                AlarmUtil.cancel(getContext(),mAlarm);

        }
*/

    }
}
