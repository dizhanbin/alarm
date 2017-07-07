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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.dym.alarm.ActController;
import com.dym.alarm.Form;
import com.dym.alarm.R;
import com.dym.alarm.RP;
import com.dym.alarm.common.Event;
import com.dym.alarm.common.IBind;
import com.dym.alarm.common.NLog;
import com.dym.alarm.common.Utils;
import com.dym.alarm.model.MSound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class FormSoundSet extends Form implements MediaPlayer.OnPreparedListener {




    List<String> your_sounds = new ArrayList<>();
    List<MSound> dev_sounds;// = new ArrayList<>();

    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if( mView == null ) {
            mView = inflater.inflate(R.layout.form_sound_set, null);
            setView(mView);
            mRecyclerView = (RecyclerView) ViewInject(R.id.recyclerview);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        }

        dev_sounds = Utils.scanAlarms();
        dev_sounds.add(0,MSound.sient());
        initDeviceSound();



        return mView;

    }

    private void initDeviceSound() {

        NLog.i("dd dev sound size:%d",dev_sounds.size());
        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = View.inflate(getContext(),R.layout.view_sound_item_devicesound,null);
                VHDeviceSound vh = new VHDeviceSound(view);
                return vh;


            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                ((IBind)holder).onBind(dev_sounds.get(position),position);
                holder.itemView.setTag( dev_sounds.get(position) );

            }

            @Override
            public int getItemCount() {
                //NLog.i("dev sound size:%d",dev_sounds.size());
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
            case R.id.image_select_play:
            {

                MSound ms = (MSound) getRootParentTag(R.id.view_item_devsound,view);


                Uri uri = Uri.fromFile(new File( ms.path ) );

                NLog.i("select path:%s",ms.path);


                startAlarm(uri);

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

                        NLog.i("select from google");
                        startAlarm( uri );
                    }
                }
                else
                {
                    Uri uri = data.getData();
                    if (uri != null) {

                        startAlarm( uri );


                    }
                }


            }

        }

    }
    MediaPlayer mMediaPlayer;
    private void startAlarm(Uri uri) {

        mMediaPlayer = MediaPlayer.create(getContext(), uri);
       // mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setLooping(true);
//        try {
//            //mMediaPlayer.prepare();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        mMediaPlayer.start();
    }


    @Override
    public void onPrepared(MediaPlayer mp) {


        mp.start();

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

        }

         //image_select.setVisibility( ms.selected ? View.VISIBLE : View.INVISIBLE);



    }



}



