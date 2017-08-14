/* create my 17 */
package com.dym.alarm.forms;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.dym.alarm.Form;
import com.dym.alarm.R;
import com.dym.alarm.RP;
import com.dym.alarm.RT;
import com.dym.alarm.ad.AdListener;
import com.dym.alarm.ad.AdLoader;
import com.dym.alarm.common.AlarmUtil;
import com.dym.alarm.common.DDialog;
import com.dym.alarm.common.Event;
import com.dym.alarm.common.NLog;
import com.dym.alarm.common.ViewMoveTouchListener;
import com.dym.alarm.model.MAlarm;
import com.dym.alarm.views.DGridLayoutManager;
import com.dym.alarm.views.SlideLeftRemoveGiftAnimator;
import com.dym.alarm.views.VHAlarmItem;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import tyrantgit.explosionfield.ExplosionField;

public class FormMain extends Form implements View.OnClickListener {



    RecyclerView mRecyclerView;

    ExplosionField  mExplosionField;

    View btn_add;

    boolean need_reload_list;
    boolean had_load;
    View view_tip;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        NLog.i("FormMain onCreateView..");
        if( mView == null ) {
            mView = inflater.inflate(R.layout.form_main, null);
            setView(mView);
            setFormtype(FormType.FORM_ONLY);


            btn_add = ViewInject(R.id.btn_add);

            mRecyclerView = (RecyclerView) ViewInject(R.id.recyclerview);


            //SlideInUpAnimator animator = new SlideInUpAnimator(new OvershootInterpolator(5f));

            SlideInLeftAnimator animator = new SlideLeftRemoveGiftAnimator();
            animator.setInterpolator(new OvershootInterpolator());

            view_tip = ViewInject(R.id.alarm_add_tip);
            view_tip.setVisibility(View.VISIBLE);


            mRecyclerView.setItemAnimator(animator);
            mRecyclerView.getItemAnimator().setAddDuration(500);
            mRecyclerView.getItemAnimator().setRemoveDuration(300);
            mRecyclerView.getItemAnimator().setMoveDuration(300);
            mRecyclerView.getItemAnimator().setChangeDuration(300);
            initUI();


        }


        return mView;
    }

    @Override
    public void onPush(boolean fromback) {

        btn_add.setScaleX(1);
        super.onPush(fromback);
        if( !fromback )
            sendMessage(Event.REQ_ALARM_LIST);
    }

    @Override
    public void onClick(final View view) {

        NLog.i("FormMain.onClick :%s",view);
        if( mView == null)
            return;
        switch (view.getId()){

            case R.id.btn_add:
                need_reload_list = true;
                sendMessage(Event.FORM_EDIT);
                break;
            case R.id.view_alarm_item: {

                int pos = mDatas.indexOf(getRootParentTag(R.id.view_alarm_item, view));
                need_reload_list = true;
                sendMessage(Event.FORM_EDIT, mDatas.get(pos));
            }
                break;
            case R.id.switch_on:
            {

                if( view instanceof  SwitchCompat ) {

                    need_reload_list = false;

                    SwitchCompat sw = (SwitchCompat) view;

                    int pos = mDatas.indexOf(getRootParentTag(R.id.view_alarm_item, view));

                    mDatas.get(pos).on = sw.isChecked();

                    sendMessage(Event.REQ_ALARM_SAVE, mDatas.get(pos));

                    if (!mDatas.get(pos).on)
                        AlarmUtil.cancel(getContext(), mDatas.get(pos));
                    else
                        AlarmUtil.addAlarm(getContext(), mDatas.get(pos));

                    mRecyclerView.getAdapter().notifyItemChanged(pos);

                }

            }
            break;
            case R.id.btn_setting:

                need_reload_list = false;

                sendMessage(Event.FORM_SETTING);
                break;
            case R.id.btn_del: {

                need_reload_list = false;

                Object obj = getRootParentTag(R.id.view_alarm_item, view);

                final int pos = mDatas.indexOf(obj);

                log("remove alaram obj pos:%d  obj:%s",pos,obj);

                if( pos == -1 )
                    return;


                new DDialog.Builder(getContext()).setContentView(R.layout.dialog_confirm).setInitListener(new DDialog.ViewInitListener() {
                    @Override
                    public void ViewInit(View view) {

                        TextView text_title = (TextView) view.findViewById(R.id.text_title);
                        TextView text_msg = (TextView) view.findViewById(R.id.text_msg);


                        text_title.setText("Confirm");
                        text_msg.setText("delete the alarm?");


                        TextView text_ok = (TextView) view.findViewById(R.id.btn_ok);
                        TextView text_cancel = (TextView) view.findViewById(R.id.btn_cancel);


                        text_ok.setText("Delete");
                        text_cancel.setText("Cancel");



                    }
                }).addButtonListener(R.id.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        sendMessage(Event.REQ_ALARM_REMOVE,mDatas.get(pos));
                        mDatas.remove(pos);
                        mRecyclerView.getAdapter().notifyItemRemoved(pos);

                        mainThreadExecuteDelay(new Thread(){
                            public void run(){
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                            }
                        },600);




                    }
                }).addButtonListener(R.id.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).setCancelable(true).create().show();


            }
                break;


        }
    }




    private void initUI() {

        btn_add.setOnTouchListener(new ViewMoveTouchListener());

    }

    @Override
    public boolean onMessage(Event event, Object value) {
        switch (event){
            case REP_ALARM_LIST:
                initRecyclerView((List<MAlarm>)value);
                return true;
            case REP_ALARM_SAVE_SUCCESS:
                if( need_reload_list )
                    sendMessage(Event.REQ_ALARM_LIST);
                return true;
            case REP_BUY_SUCCESS:
                mRecyclerView.getAdapter().notifyDataSetChanged();
                return true;
        }
        return false;
    }



    List<MAlarm> mDatas = new ArrayList<>();
    private void initRecyclerView(List<MAlarm> value) {

        mDatas.clear();
        mRecyclerView.setLayoutManager(new DGridLayoutManager(getContext(),1));
        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                switch (viewType) {
                    case 0 :
                        View view = View.inflate(parent.getContext(), R.layout.view_alarm_item, null);
                        VHAlarmItem vh = new VHAlarmItem(view);
                        return vh;
                    case 1 :
                        View adview = View.inflate(parent.getContext(), R.layout.ad_alarm_item, null);
                        ViewHolder advh = new ViewHolder(adview) {
                        };
                        return advh;
                }

                return null;

            }


            @Override
            public int getItemViewType(int position) {

                if( RP.Data.isVip() || !RT.VISIBLE_ALARM_ITEM_AD)
                    return 0;
                return getItemCount() -1 == position ? 1 : 0;
            }



            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

                NLog.i("onBindViewHolder %d  size:%d",position,mDatas.size());

                if( holder instanceof  VHAlarmItem ) {



                    VHAlarmItem vh = (VHAlarmItem) holder;
                    vh.itemView.setTag(mDatas.get(position));
                    vh.bind(mDatas.get(position));




                }else{




                    View  mAdView = holder.itemView.findViewById(R.id.adView);



                    AdLoader adLoader = RT.getAdLoader(mAdView, new AdListener() {
                        @Override
                        public void onLoad() {

                        }

                        @Override
                        public void onError() {

                        }

                        @Override
                        public void onClick() {

                        }
                    });

                    adLoader.load();




                }

            }

            @Override
            public int getItemCount() {

                int item_count = RP.Data.isVip() || !RT.VISIBLE_ALARM_ITEM_AD ? mDatas.size() :  (mDatas.size()>0? mDatas.size()+1 : 0 );
                if( item_count ==0 ){
                    rotateAddButton();
                    view_tip.setVisibility(View.VISIBLE);
                }
                else{
                    view_tip.setVisibility(View.GONE);
                    rotateAddButtonCancel();
                }
                //log("update recycle count:%d",item_count);
                return item_count;

            }
        });


        if( had_load ) {
            mDatas.addAll(value);
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
        else
            for (int i = 0; i < value.size(); i++) {
                had_load = true;

                MAlarm mAlarm = value.get(i);
                mDatas.add(mAlarm);
                mRecyclerView.getAdapter().notifyItemInserted(i);

                if (mAlarm.on)
                    AlarmUtil.addAlarm(getContext(), mAlarm);
                else
                    AlarmUtil.cancel(getContext(), mAlarm);


            }




    }




    ObjectAnimator btn_add_scale;
    private void rotateAddButton(){

        if( btn_add_scale == null ) {
            log("run rotate");

            int time = 2000;
            btn_add_scale = ObjectAnimator.ofFloat(btn_add, "scaleX", 1, 1.5f,1);
            btn_add_scale.setRepeatCount(ObjectAnimator.INFINITE);
            btn_add_scale.setDuration(time);
            btn_add_scale.start();

        }

    }

    private void rotateAddButtonCancel(){

        if( btn_add_scale != null ) {
            btn_add_scale.cancel();
            btn_add_scale = null;
        }
        btn_add.setScaleX(1);

    }

}
