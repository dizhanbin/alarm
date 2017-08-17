/* create my 17 */
package com.dym.alarm.forms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dym.alarm.Form;
import com.dym.alarm.R;
import com.dym.alarm.RT;
import com.dym.alarm.ad.AdListener;
import com.dym.alarm.ad.AdLoader;
import com.dym.alarm.common.Event;
import com.dym.alarm.common.PayHelper;
import com.dym.alarm.common.Utils;


public class FormSetting extends Form {


    View view_ad_container;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if( mView == null ) {
            mView = inflater.inflate(R.layout.form_setting, null);
            setView(mView);




            view_ad_container = ViewInject(R.id.ad_container);
            View adView =  ViewInject(R.id.adView);




            AdLoader adLoader = RT.getAdLoader(adView, new AdListener() {
                @Override
                public void onLoad() {
                    view_ad_container.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {

                }

                @Override
                public void onClick() {

                }
            });

            adLoader.load();



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
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getContextString(R.string.share_tip)+"\n"+ RT.market_https);
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getContextString(R.string.share_tip));
                //shareIntent.setPackage(getContextString(R.string.app_name));

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
