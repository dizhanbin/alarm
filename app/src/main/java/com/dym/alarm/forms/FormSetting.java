/* create my 17 */
package com.dym.alarm.forms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dym.alarm.Form;
import com.dym.alarm.R;
import com.dym.alarm.common.Event;
import com.dym.alarm.common.NLog;

public class FormSetting extends Form {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if( mView == null ) {
            mView = inflater.inflate(R.layout.form_setting, null);
            setView(mView);
        }

        return mView;

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
