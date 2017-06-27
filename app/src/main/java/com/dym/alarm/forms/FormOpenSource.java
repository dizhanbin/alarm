/* create my 17 */
package com.dym.alarm.forms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dym.alarm.Form;
import com.dym.alarm.R;
import com.dym.alarm.common.Event;
import com.dym.alarm.common.NLog;
import com.dym.alarm.common.Utils;
import com.dym.alarm.model.MAlarm;
import com.dym.alarm.model.MOpenSource;
import com.dym.alarm.views.VHAlarmItem;
import com.google.android.gms.vision.text.Text;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class FormOpenSource extends Form implements View.OnClickListener {


    RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if( mView == null ) {
            mView = inflater.inflate(R.layout.form_opensource, null);
            setView(mView);

            mRecyclerView = (RecyclerView) ViewInject(R.id.recyclerview);

            SlideInLeftAnimator animator = new SlideInLeftAnimator();
            animator.setInterpolator(new OvershootInterpolator());

            mRecyclerView.setItemAnimator(animator);
            mRecyclerView.getItemAnimator().setAddDuration(500);
            mRecyclerView.getItemAnimator().setRemoveDuration(300);
            mRecyclerView.getItemAnimator().setMoveDuration(300);
            mRecyclerView.getItemAnimator().setChangeDuration(300);

        }



        initUI();
        return mView;

    }


    List<MOpenSource> mDatas = new ArrayList<>();

    private void initUI() {



        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = View.inflate(parent.getContext(),R.layout.view_opensource_item,null);
                SourceVH vh = new SourceVH( view );

                return vh;

            }



            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                NLog.i("onBindViewHolder %d",position);

                SourceVH vh = (SourceVH) holder;
                vh.bind(mDatas.get(position),FormOpenSource.this);
                // vh.switch_on.setTag( position );
                // vh.btn_del.setTag(position);


            }

            @Override
            public int getItemCount() {

                return mDatas.size();
            }
        });




    }

    @Override
    public void onPush(boolean fromback) {


        if( !fromback )
            sendMessage(Event.REQ_OPENSOURCE_LIST);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_back:


                sendMessage(Event.REQ_FORM_BACK);
                break;
            case R.id.view_opensource_item:

                MOpenSource mos = (MOpenSource) view.getTag();
                Utils.openUrl(mos.url);

                break;


        }
    }

    @Override
    public boolean onMessage(Event event, Object value) {
        switch (event){

            case REP_OPENSOURCE_LIST:

                if( value instanceof  List)
                for(MOpenSource mos : (List<MOpenSource>)value){
                    mDatas.add(0,mos);
                    mRecyclerView.getAdapter().notifyItemInserted(0);
                }
                return true;


        }
        return false;
    }
}

class SourceVH extends RecyclerView.ViewHolder{


    TextView text_name,text_author,text_descript;

    public SourceVH(View itemView) {
        super(itemView);

        text_name = (TextView) itemView.findViewById(R.id.text_name);
        text_author = (TextView) itemView.findViewById(R.id.text_author);
        text_descript = (TextView) itemView.findViewById(R.id.text_descript);

    }
    public void bind(MOpenSource mos,View.OnClickListener listener){

        text_name.setText(mos.name);
        text_author.setText(mos.author);
        text_descript.setText(mos.descript_str);
        itemView.setOnClickListener(listener);
        itemView.setTag(mos);

    }

}
