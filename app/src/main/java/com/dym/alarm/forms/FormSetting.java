/* create my 17 */
package com.dym.alarm.forms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dym.alarm.Form;
import com.dym.alarm.R;
import com.dym.alarm.common.Event;
import com.dym.alarm.common.Utils;

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
            case R.id.card_opensource:
                setFormtype(FormType.FORM_ONLY);
                sendMessage(Event.FORM_OPENSOURCE);
                break;
            case R.id.card_sendemail:
                setFormtype(FormType.FORM_ONLY);
                Utils.sendTo(getContext());
                break;
            case R.id.card_help:
                setFormtype(FormType.FORM_ONLY);
                sendMessage(Event.FORM_HELP);
                break;
            case R.id.card_rateus:
                Utils.openMarket();
                break;
            case R.id.card_about:
                sendMessage(Event.FORM_ABOUT);
                break;

        }
    }

    @Override
    public void onPush(boolean fromback) {

        setFormtype(FormType.FORM_TOP);

    }
}
