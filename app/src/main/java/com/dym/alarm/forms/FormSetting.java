/* create my 17 */
package com.dym.alarm.forms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dym.alarm.ActController;
import com.dym.alarm.Form;
import com.dym.alarm.R;
import com.dym.alarm.RP;
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
                setFormtype(FormType.FORM_ONLY);
                sendMessage(Event.FORM_ABOUT);
                break;
            case R.id.card_share:

                Intent shareIntent = new Intent();

                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "A heath alarm "+ RP.Url.market_https);
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "A heath alarm");
                getContext().startActivity(Intent.createChooser(shareIntent, "Share using"));

                break;

        }
    }

    @Override
    public void onPush(boolean fromback) {

        setFormtype(FormType.FORM_TOP);

    }
}
