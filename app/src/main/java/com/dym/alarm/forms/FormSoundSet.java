/* create my 17 */
package com.dym.alarm.forms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.dym.alarm.Form;
import com.dym.alarm.R;
import com.dym.alarm.common.Event;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class FormSoundSet extends Form {



    RecyclerView recyclerView;

    List<String> self_sounds = new ArrayList<>();
    List<String> dev_sounds = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if( mView == null ) {
            mView = inflater.inflate(R.layout.form_sound_set, null);
            setView(mView);


            recyclerView = (RecyclerView) ViewInject(R.id.recyclerview);




            setAdapter();


        }

        return mView;

    }

    private void setAdapter() {

        SlideInLeftAnimator animator = new SlideInLeftAnimator();
        animator.setInterpolator(new OvershootInterpolator());

        recyclerView.setItemAnimator(animator);
        recyclerView.getItemAnimator().setAddDuration(1000);
        recyclerView.getItemAnimator().setRemoveDuration(1000);
        recyclerView.getItemAnimator().setMoveDuration(1000);
        recyclerView.getItemAnimator().setChangeDuration(1000);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));




        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }

            @Override
            public int getItemViewType(int position) {

                if( position == 0 )
                    return 0;//your sound
                else if( position <= self_sounds.size()     )
                    return 1;//sound
                else if( position == self_sounds.size() + 1 )
                    return 2;// add new
                else if( position == self_sounds.size() + 2 )
                    return 3;  // device sound
                else
                    return 4;


            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_back:
                sendMessage(Event.REQ_FORM_BACK);
                break;


        }
    }
}
