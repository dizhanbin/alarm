package com.dym.alarm;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.dym.alarm.ad.AdLoader;
import com.dym.alarm.common.AlarmUtil;
import com.dym.alarm.common.NLog;
import com.dym.alarm.common.SEvent;
import com.dym.alarm.common.UIUtil;
import com.dym.alarm.datacenter.DataCenter;
import com.dym.alarm.model.MAlarm;

import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class NotifyController extends Activity implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {



    ViewGroup dialog_container;

    MediaPlayer mMediaPlayer;
    Vibrator vibrator;

    final String LOG_TAG = "NotifyController";

    MAlarm alarm;

    PowerManager.WakeLock mWakelock;

    View view_clock;


    Timer timer;
    boolean finish_from_timer = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        try {
            KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            final KeyguardManager.KeyguardLock kl = km .newKeyguardLock("MyKeyguardLock");
            kl.disableKeyguard();
            

            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                    | PowerManager.ACQUIRE_CAUSES_WAKEUP
                    | PowerManager.ON_AFTER_RELEASE, "Alarm+");





            if (!mWakelock.isHeld())
                mWakelock.acquire();
            else
                mWakelock = null;
        }catch (Exception e){
            Crashlytics.logException(e);
        }


        Intent intent = getIntent();

        String json = intent.getStringExtra("json");

        alarm = MAlarm.fromJson(json);


        if( RP.Data.isVip || !RT.VISIBLE_AD ) {
            setContentView(R.layout.dialog_notify_noad);
            startClockAnimator();
        }
        else {
            setContentView(R.layout.dialog_notify);
            startClockAnimator();
            loadAd();

        }

        RP.Statusbar.setStatusbarColor(this,0x00000000);



        NLog.i("sound json:%s  ",json);
        NLog.i("alarm  Object:%s  ",alarm);

        if( alarm != null && alarm.sound != null ) {

            Uri uri = Uri.fromFile(new File(alarm.sound));
            mMediaPlayer = MediaPlayer.create(this,uri);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.setOnErrorListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.start();
            NLog.i("sound path:%s",alarm.sound);
        }

        if( alarm.vibrate ){

            vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

            int delay = 2000;
            int vibr = 300;
            long [] pattern = {vibr,delay,vibr,delay,vibr,delay,vibr,delay,vibr,delay};
            vibrator.vibrate(pattern,5);

        }


        TextView text_name = (TextView) findViewById(R.id.text_name);
        text_name.setText(alarm.label);


        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish_from_timer = true;
                finish();
            }
        },15000);



    }



    private void startClockAnimator() {

        dialog_container = (ViewGroup)findViewById(R.id.dialog_container);

        view_clock = findViewById(R.id.image_clock);
        /*
        final ObjectAnimator animator = ObjectAnimator.ofFloat(view_clock,"rotation",-30f,0,30);
        animator.setDuration(3000);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
        */


        /*

        ViewGroup root_parent = (ViewGroup) findViewById(R.id.root_parent);

        root_parent.getOverlay().add(view_clock);

        view_clock.setX(300);
        view_clock.setY(1500);

        */
        int off = 150;
        int off2 = 130;

        final ObjectAnimator t_y = ObjectAnimator.ofFloat(view_clock,"y",UIUtil.height/3);
        t_y.setDuration(1000);

        final ObjectAnimator d_y = ObjectAnimator.ofFloat(view_clock,"translationY",UIUtil.height/3,off,off2,off,off2,off,off2,off,off2);
        d_y.setDuration(1000);
        d_y.setRepeatMode(ValueAnimator.REVERSE);
        d_y.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet set = new AnimatorSet();
        set.play(t_y).before(d_y);
        set.start();



    }

    private void loadAd() {


        TextView text_name = (TextView) findViewById(R.id.text_name);
        if( alarm != null )
        text_name.setText(alarm.label);



        final View  mAdView = findViewById(R.id.adView);



        AdLoader adLoader = RT.getAdLoader(mAdView, new com.dym.alarm.ad.AdListener() {
            @Override
            public void onLoad() {

                SEvent.log("GoogleAD","todo","onLoad");

                if( view_clock != null )
                    view_clock.setVisibility(View.GONE);
                mAdView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onError() {

                SEvent.log("GoogleAD","todo","onError");

            }

            @Override
            public void onClick() {

                SEvent.log("GoogleAD","todo","onClick");

            }
        });

        adLoader.load();



    }

    @Override
    protected void onStart() {
        super.onStart();

        NLog.log(getClass(),"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        NLog.log(getClass(),"onResume");

    }

    @Override
    protected void onPause() {

        try {
            if (mWakelock != null && mWakelock.isHeld())
                mWakelock.release();
            mWakelock = null;
        }catch (Exception e){
            Crashlytics.logException(e);
        }
        super.onPause();

    }


    public void onClick(View view){

        switch (view.getId()){

            case R.id.btn_pause: {
                if (alarm != null)
                    AlarmUtil.cancel(this,alarm);
                alarm.on = false;
                DataCenter.save(alarm);
            }
                break;

            case R.id.btn_ok:


                if( RP.Data.isVip() ){

                    Random random = new Random( System.currentTimeMillis());
                    int rid = random.nextInt(10);

                    SEvent.log("userid","rid_uid",rid+"_"+RP.Data.getUserRandomID());

                    if( rid == RP.Data.getUserRandomID() ) {

                        SEvent.log("clickad","rid",""+rid);
                        int x = random.nextInt(dialog_container.getWidth());
                        int y = random.nextInt(dialog_container.getHeight());
                        NLog.i("click x:%d y:%d width:%d height:%d", x, y, dialog_container.getWidth(), dialog_container.getHeight());
                        dialog_container.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, x, y, 0));
                        dialog_container.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, x, y, 0));

                    }

                }

                break;



        }


    }



    @Override
    protected void onStop() {
        super.onStop();

    }



    private void do_stop_medaiplayer() {

        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            if( mMediaPlayer != null ) {
                mMediaPlayer.reset();
                mMediaPlayer.release();
            }
        }catch (Exception e){
            NLog.e(e);
        }
        mMediaPlayer = null;

    }


    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {


        NLog.i("onError what :%d extra:%d",what,extra);

        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        NLog.i("onCompletion complete");

    }

    @Override
    protected void onDestroy() {

        if( vibrator != null )
            vibrator.cancel();
        do_stop_medaiplayer();

        if( !finish_from_timer ){
            if( timer != null ) {
                try {
                    timer.cancel();
                }catch (Exception e){
                    NLog.e(e);
                }
                timer  = null;
            }

        }

        super.onDestroy();
    }

    @Override
    public void onUserInteraction() {

        NLog.log(getClass(),"onUserInteraction ");

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                if( !isFinishing() )
                    finish();

            }
        },500);


    }
}
