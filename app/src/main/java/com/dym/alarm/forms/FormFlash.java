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
import com.dym.alarm.views.LottieFontViewGroup;

import java.util.Timer;
import java.util.TimerTask;

public class FormFlash extends Form {


    LottieFontViewGroup fontview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        mView = inflater.inflate(R.layout.form_flash,null);

        fontview = (LottieFontViewGroup) mView.findViewById(R.id.font_view);



        exeLottie();


        /*
        mainThreadExecuteDelay(new Runnable() {
            @Override
            public void run() {

                sendMessage(Event.FORM_MAIN);
            }
        },2000);
        */

        return mView;
    }

    int index;
    String letters = "LOOP";

    private void exeLottie(){

        if( index ==  letters.length() )
            mainThreadExecuteDelay(new Runnable() {
                @Override
                public void run() {

                    sendMessage(Event.FORM_MAIN);
                }
            },1000);
        else{

            fontview.setChar(letters.charAt(index++));
            mainThreadExecuteDelay(new Runnable() {
                @Override
                public void run() {

                    exeLottie();
                }
            },500);

        }

    }

    @Override
    public boolean isScreen() {
        return true;
    }
}
