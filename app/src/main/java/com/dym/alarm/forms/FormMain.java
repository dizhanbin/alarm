/* create my 17 */
package com.dym.alarm.forms;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.dym.alarm.ActController;
import com.dym.alarm.Form;
import com.dym.alarm.R;
import com.dym.alarm.common.AlarmUtil;
import com.dym.alarm.common.DDialog;
import com.dym.alarm.common.Event;
import com.dym.alarm.common.NLog;
import com.dym.alarm.common.UIUtil;
import com.dym.alarm.common.ViewMoveTouchListener;
import com.dym.alarm.model.MAlarm;
import com.dym.alarm.views.SlideLeftRemoveGiftAnimator;
import com.dym.alarm.views.VHAlarmItem;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import tyrantgit.explosionfield.ExplosionField;

public class FormMain extends Form implements View.OnClickListener {



    RecyclerView mRecyclerView;

    ExplosionField  mExplosionField;

    View btn_add;

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
        super.onPush(fromback);
       // if( !fromback )
            sendMessage(Event.REQ_ALARM_LIST);
    }

    @Override
    public void onClick(final View view) {

        NLog.i("FormMain.onClick :%s",view);
        if( mView == null)
            return;
        switch (view.getId()){

            case R.id.btn_add:
                sendMessage(Event.FORM_EDIT);
                break;
            case R.id.view_alarm_item: {

                int pos = mDatas.indexOf(getRootParentTag(R.id.view_alarm_item, view));

                sendMessage(Event.FORM_EDIT, mDatas.get(pos));
            }
                break;
            case R.id.switch_on:
            {

                SwitchCompat sw = (SwitchCompat) view;

                int pos =  mDatas.indexOf(  getRootParentTag(R.id.view_alarm_item,view)  );

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
            case R.id.btn_del: {


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



                        int pos = mDatas.indexOf(getRootParentTag(R.id.view_alarm_item, view));
                        if( pos == -1 )
                            return;
                        sendMessage(Event.REQ_ALARM_REMOVE,mDatas.get(pos));
                        mDatas.remove(pos);
                        mRecyclerView.getAdapter().notifyItemRemoved(pos);


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
        }
        return false;
    }



    List<MAlarm> mDatas = new ArrayList<>();
    private void initRecyclerView(List<MAlarm> value) {

        mDatas.clear();




        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

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

                return getItemCount() -1 == position ? 1 : 0;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

                NLog.i("onBindViewHolder %d",position);

                if( holder instanceof  VHAlarmItem ) {
                    VHAlarmItem vh = (VHAlarmItem) holder;
                    vh.itemView.setTag(mDatas.get(position));
                    vh.bind(mDatas.get(position));
                }else{
                    /*
                    NativeExpressAdView adView = (NativeExpressAdView)holder.itemView.findViewById(R.id.adView);
                    adView.setAdUnitId("ca-app-pub-2800914329604494/7372252828");
                    adView.setAdSize(new AdSize(AdSize.FULL_WIDTH, UIUtil.dip2px(getContext(),200)));
                    AdRequest request = new AdRequest.Builder()

                            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)

                           // .addTestDevice("d8aedad4c0e4c421")
                            .build();
                    //request.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);


                    adView.loadAd(request);

                    */

                    NativeExpressAdView  mAdView = (NativeExpressAdView) holder.itemView.findViewById(R.id.adView);

                    // Set its video options.
                    mAdView.setVideoOptions(new VideoOptions.Builder()
                            .setStartMuted(true)
                            .build());

                    // The VideoController can be used to get lifecycle events and info about an ad's video
                    // asset. One will always be returned by getVideoController, even if the ad has no video
                    // asset.
                    final VideoController mVideoController = mAdView.getVideoController();
                    mVideoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                        @Override
                        public void onVideoEnd() {
                           // Log.d(LOG_TAG, "Video playback is finished.");
                            super.onVideoEnd();
                        }
                    });

                    // Set an AdListener for the AdView, so the Activity can take action when an ad has finished
                    // loading.
                    mAdView.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            if (mVideoController.hasVideoContent()) {
                                //Log.d(LOG_TAG, "Received an ad that contains a video asset.");
                            } else {

                            }
                        }
                    });

                    mAdView.loadAd(new AdRequest.Builder().build());




                }

            }

            @Override
            public int getItemCount() {

                return mDatas.size()+1;
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
