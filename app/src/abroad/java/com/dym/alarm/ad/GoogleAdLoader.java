package com.dym.alarm.ad;

import android.util.Log;
import android.view.View;

import com.dym.alarm.common.SEvent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;

/**
 * Created by dizhanbin on 2017/8/10.
 */

public class GoogleAdLoader extends AdLoader {


    public GoogleAdLoader(View adView, AdListener listener) {
        super(adView, listener);
    }

    @Override
    public void load() {


        NativeExpressAdView mAdView = (NativeExpressAdView)getAdView();

        mAdView.setVideoOptions(new VideoOptions.Builder()
                .setStartMuted(true)
                .build());


        final VideoController mVideoController = mAdView.getVideoController();
        mVideoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            @Override
            public void onVideoEnd() {

                super.onVideoEnd();
            }
        });

        // Set an AdListener for the AdView, so the Activity can take action when an ad has finished
        // loading.
        mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                /*
                if (mVideoController.hasVideoContent()) {
                    Log.d(LOG_TAG, "Received an ad that contains a video asset has.");
                } else {
                    Log.d(LOG_TAG, "Received an ad that contains a video asset nohas.");
                }
                */

                listener.onLoad();

            }

            public void onAdOpened() {


                //Log.d(LOG_TAG, "Received an ad that contains a video opened.");
            }


            @Override
            public void onAdFailedToLoad(int i) {

                SEvent.log("ADGoogle","loaderror",String.valueOf(i));
                listener.onError();

            }

            public void onAdClicked() {

               // Log.d(LOG_TAG, "Received an ad that contains a video clicked.");
                listener.onClick();
                SEvent.log("ADGoogle","click","notify_click");
            }

        });

        mAdView.loadAd(new AdRequest.Builder().build());


    }
}
