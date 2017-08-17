package com.dym.alarm.ad;

import android.view.View;


import java.lang.ref.WeakReference;

/**
 * Created by dizhanbin on 2017/8/10.
 */

public abstract class AdLoader {

    protected AdListener listener;


    WeakReference<View> adViewRef;

    public AdLoader(View adView,AdListener listener){


        if( adView != null )
            adViewRef = new WeakReference<View>(adView);
        this.listener = listener;

    }



    public View getAdView(){

        if( adViewRef != null){
            return adViewRef.get();
        }
        return null;
    }

    public abstract void load();

}
