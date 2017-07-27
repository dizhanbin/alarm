/* create my 17 */
package com.dym.alarm.forms;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.provider.DocumentFile;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dym.alarm.ActController;
import com.dym.alarm.Form;
import com.dym.alarm.R;
import com.dym.alarm.RP;
import com.dym.alarm.common.Event;
import com.dym.alarm.common.FHandler;
import com.dym.alarm.common.FileUtils;
import com.dym.alarm.common.IBind;
import com.dym.alarm.common.MessageCenter;
import com.dym.alarm.common.NLog;
import com.dym.alarm.common.Utils;
import com.dym.alarm.model.MAlarm;
import com.dym.alarm.model.MSound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class FormSoundSet extends Form implements MediaPlayer.OnCompletionListener {





    List<MSound> dev_sounds;// = new ArrayList<>();

    RecyclerView mRecyclerView;
    RecyclerView recyclerview_yours;

    MSound mSelectItem;
    MAlarm model;


    FHandler fhandler = new FHandler() {
        @Override
        public void handle(PVD pvd) {

            switch (pvd.event){

                case REQ_SOUND_TEST_STOP:
                    do_stop_medaiplayer();
                    break;

            }

        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if( mView == null ) {
            mView = inflater.inflate(R.layout.form_sound_set, null);
            setView(mView);
            mRecyclerView = (RecyclerView) ViewInject(R.id.recyclerview);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));


            recyclerview_yours = (RecyclerView) ViewInject(R.id.recyclerview_yours);
            recyclerview_yours.setLayoutManager(new GridLayoutManager(getContext(),1));


        }

        dev_sounds = Utils.scanAlarms();
        dev_sounds.add(0,MSound.sient());
        initDeviceSound();
        initYourSound();

        return mView;

    }

    private void initYourSound() {


        //RP.Lo
        //  JSON.toJSONString(your_sounds);

        //String jsonstr = FileUtils.readFile(RP.Local.path_yoursournds);

        log("model.your_sounds.size:%d",model.your_sounds.size());

        for(MSound ms : model.your_sounds){

            if( ms.path != null && ms.path.equals(model.sound) ) {
                ms.selected = true;
                mSelectItem = ms;
            }
        }



        recyclerview_yours.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = View.inflate(getContext(),R.layout.view_sound_item_devicesound,null);
                VHDeviceSound vh = new VHDeviceSound(view);
                return vh;

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                MSound ms = model.your_sounds.get(position);
                ms.selected = mSelectItem == ms;
                ((IBind)holder).onBind(ms,position);
                holder.itemView.setTag( model.your_sounds.get(position) );

            }

            @Override
            public int getItemCount() {
                return model.your_sounds.size();
            }

        });




    }
    private void saveYourSound(){

        log("save your sournd save path:%s",RP.Local.path_yoursournds);
       // FileUtils.writeFile(RP.Local.path_yoursournds,JSON.toJSONString(your_sounds));
        sendMessage(Event.REQ_ALARM_SAVE,model);


        recyclerview_yours.getAdapter().notifyDataSetChanged();
    }

    private void initDeviceSound() {

        for(MSound ms : dev_sounds){

            if( ms.path != null && ms.path.equals(model.sound) ) {
                ms.selected = true;
                mSelectItem = ms;
            }
        }



        log("dd dev sound size:%d",dev_sounds.size());
        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = View.inflate(getContext(),R.layout.view_sound_item_devicesound,null);
                VHDeviceSound vh = new VHDeviceSound(view);
                return vh;

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                MSound ms = dev_sounds.get(position);
                ms.selected = mSelectItem == ms;
                ((IBind)holder).onBind(ms,position);
                holder.itemView.setTag( dev_sounds.get(position) );

            }

            @Override
            public int getItemCount() {
                return dev_sounds.size();
            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_back:
                sendMessage(Event.REQ_FORM_BACK);
                break;
            case R.id.btn_add:

                Utils.openGoogleDriver();
                break;

            case R.id.view_item_sound:
            {

                if( mSelectItem != null )
                    mSelectItem.selected = false;
                mSelectItem = (MSound) view.getTag();
                log("click item :%s",view.getTag());

                mSelectItem.selected = true;
                recyclerview_yours.getAdapter().notifyDataSetChanged();
                mRecyclerView.getAdapter().notifyDataSetChanged();
                do_stop_medaiplayer();
                if( mSelectItem.type != 2 ) {

                    Uri uri = Uri.fromFile(new File(mSelectItem.path));
                    startAlarm(uri);
                    model.sound = mSelectItem.path;

                }
                else
                    model.sound = null;
                sendMessage(Event.REQ_ALARM_SAVE,model);
                sendMessage(Event.REQ_SOUND_CHANGED,mSelectItem);

            }
            break;


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == RP.CK.K_GOOGLEDRIVER) {


            if (resultCode == Activity.RESULT_OK) {

                ClipData cdata = data.getClipData();
                if( cdata != null ) {
                    for (int i = 0; i < cdata.getItemCount(); i++) {
                        Uri uri = cdata.getItemAt(i).getUri();

                        DocumentFile file = DocumentFile.fromSingleUri(getContext(), uri);
                        log("select from google");
                        MSound ms = new MSound();
                        ms.path = RP.Local.path_yoursournds_dir+"/"+file.getName();
                        ms.name = file.getName();
                        ms.type = 1;
                        model.your_sounds.add(ms);
                        if( FileUtils.copy(getContext(),uri,ms.path) )
                        {
                            log("copy file ok");
                        }
                        else
                            log("copy file error");
                    }
                    saveYourSound();
                }
                else
                {
                    Uri uri = data.getData();
                    if (uri != null) {
                       // startAlarm( uri );
                        DocumentFile file = DocumentFile.fromSingleUri(getContext(), uri);
                        MSound ms = new MSound();
                        ms.path = RP.Local.path_yoursournds_dir+"/"+file.getName();
                        ms.name = file.getName();
                        ms.type = 1;
                        model.your_sounds.add(ms);
                        FileUtils.copy(getContext(),uri,ms.path);
                        saveYourSound();
                    }
                }


            }

        }

    }
    MediaPlayer mMediaPlayer;
    private void startAlarm(Uri uri) {

        if( mMediaPlayer != null ){
           do_stop_medaiplayer();
        }
        mMediaPlayer = MediaPlayer.create(getContext(), uri);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
        log("media length:%d",mMediaPlayer.getDuration());

       // MessageCenter.sendMessage(Event.REQ_SOUND_TEST_STOP,null,mMediaPlayer.getDuration()-200);

        fhandler.post(Event.REQ_SOUND_TEST_STOP,null,mMediaPlayer.getDuration()-200);
    }




    @Override
    public void onCompletion(MediaPlayer mp) {

        log("mediaplay complete");
        try {
            mp.stop();
            mp.release();
            mMediaPlayer = null;
        }catch (Exception e){
            e.printStackTrace();

        }

    }

    @Override
    public boolean onMessage(Event event, Object value) {
        switch (event){

            case REQ_SOUND_TEST_STOP:
                do_stop_medaiplayer();
                return true;
            case REP_ALARM_SAVE_SUCCESS:
                return true;

        }
        return false;
    }

    @Override
    public void onStop() {
        log("onstop");
        do_stop_medaiplayer();
        super.onStop();
    }

    private void do_stop_medaiplayer() {

        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            if( mMediaPlayer != null )
                mMediaPlayer.release();
        }catch (Exception e){
            NLog.e(e);
        }
        mMediaPlayer = null;

    }


    @Override
    public void onValue(Object value) {


        model = (MAlarm) value;

    }
}

class VHDeviceSound extends RecyclerView.ViewHolder implements IBind{


    TextView title;
    ImageView image_select;


    public VHDeviceSound(View itemView) {

        super(itemView);
        title = (TextView) itemView.findViewById(R.id.text_title);
        image_select = (ImageView) itemView.findViewById(R.id.image_select);


    }


    @Override
    public void onBind(Object obj, int positon) {

        MSound ms = (MSound) obj;
        title.setText( ms.name );

        switch ( ms.type ){

            case 0 : {

                Drawable drawable = ActController.instance.getResources().getDrawable(android.R.drawable.ic_btn_speak_now);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                title.setCompoundDrawables(drawable, null, null, null);
            }
                break;
            case 2: {
                Drawable drawable = ActController.instance.getResources().getDrawable(android.R.drawable.ic_lock_silent_mode);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                title.setCompoundDrawables(drawable, null, null, null);
            }
                break;
            case 1: {

                Drawable drawable = ActController.instance.getResources().getDrawable(android.R.drawable.ic_btn_speak_now);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                title.setCompoundDrawables(drawable, null, null, null);
            }
            break;

        }

        image_select.setVisibility( ms.selected ? View.VISIBLE : View.INVISIBLE);


        //quantum_ic_play_arrow_grey600_48

        //quantum_ic_pause_grey600_48





    }



}



