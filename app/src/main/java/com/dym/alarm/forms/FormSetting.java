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
import com.dym.alarm.common.PayHelper;
import com.dym.alarm.common.Utils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;

public class FormSetting extends Form {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if( mView == null ) {
            mView = inflater.inflate(R.layout.form_setting, null);
            setView(mView);

            NativeExpressAdView mAdView = (NativeExpressAdView) mView.findViewById(R.id.adView);

            // Set its video options.
            mAdView.setVideoOptions(new VideoOptions.Builder()
                    .setStartMuted(true)
                    .build());

            // The VideoController can be used to get lifecycle events and info about an ad's video
            // asset. One will always be returned by getVideoController, even if the ad has no video
            // asset.
            final VideoController mVideoController = mAdView.getVideoController();
            mVideoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Log.d(LOG_TAG, "Video playback is finished.");
                    super.onVideoEnd();
                }
            });

            // Set an AdListener for the AdView, so the Activity can take action when an ad has finished
            // loading.
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    if (mVideoController.hasVideoContent()) {
                        //Log.d(LOG_TAG, "Received an ad that contains a video asset.");
                    } else {

                    }
                }
            });

            mAdView.loadAd(new AdRequest.Builder().build());


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

                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getContextString(R.string.share_tip)+ RP.Url.market_https);
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getContextString(R.string.share_tip));
                getContext().startActivity(Intent.createChooser(shareIntent, getString(R.string.share_us)));

                break;
            case R.id.card_purchase:

                PayHelper.getInstance().buyAndDealData();
                //helper.purchase(ActController.instance);

                break;

        }
    }

    @Override
    public void onPush(boolean fromback) {

        setFormtype(FormType.FORM_TOP);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        PayHelper.getInstance().handleActivityResult(requestCode,resultCode,data);
    }
}
