package com.dym.alarm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.media.TimedText;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dym.alarm.common.DDialog;
import com.dym.alarm.common.NLog;
import com.dym.alarm.model.MAlarm;

import java.io.File;
import java.util.Date;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class NotifyController extends Activity implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {



    private View mContentView;


    MediaPlayer mMediaPlayer;
    Vibrator vibrator;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.







            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notify);


        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);







        mContentView = findViewById(R.id.notify_content);


       // mHideHandler.postDelayed(mHidePart2Runnable,1000);


        Intent intent = getIntent();

        String json = intent.getStringExtra("json");

        final MAlarm alarm = MAlarm.fromJson(json);


        NLog.i("sound json:%s  ",json);

        NLog.i("alarm  Object:%s  ",alarm);


        new DDialog.Builder(this)
                .setStyle(R.style.BDialog)
                .setContentView(R.layout.dialog_notify).setCancelable(true)
                .setInitListener(new DDialog.ViewInitListener() {
                    @Override
                    public void ViewInit(View view) {

                        TextView text_name = (TextView) view.findViewById(R.id.text_name);

                        text_name.setText(alarm.label);



                    }
                })
                .addButtonListener(R.id.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();
                        //text_begin_time.setText(  );
                    }
                })

                .setOnDimissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                })
                .create().show();



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
            long [] pattern = {1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000};
            vibrator.vibrate(pattern,10);

        }


    }

    @Override
    protected void onStop() {
        if( vibrator != null )
            vibrator.cancel();
        do_stop_medaiplayer();
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



}
