package com.dym.alarm;

import android.content.Context;
import android.view.View;

import com.dym.alarm.ad.AdListener;
import com.dym.alarm.ad.AdLoader;
import com.dym.alarm.ad.GoogleAdLoader;
import com.dym.alarm.common.PayHelper;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by dizhanbin on 2017/8/2.
 */

public class RT {


    public static final String GOOGLE_PAY_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgXvH9ZgUc9SYOdfRdlxo4HIDm6h9ZeQbYCda+PsCHhh3pJ/4GTaKzzibL+0yN9LDIhoHDKdobVZvCmnsoYH79sQXQjUTmGfvMocE3tFY/NaNP1qDcWCIIOUQiHSyaX71Vitq10gmQa2hsXDTl4hBucXjzp49FvTCq1CG+Nm4Dxjf5UWxHyIWKIVzPhsULutFA7UBxO1odQ3mdoyneNQBsUQfxi120AJiRaksojlKGDf9urmGN2NhC1v5dbIPgOrHlrCPtbVezglcOnyAMVPDhqVbCFRJ82vyORnK4Z8fM/i2a93suM8mZ0K5nJqrJdrh4Iau97hb/S/o3vRwJxLrgQIDAQAB";

    public static final String GOOGLE_PAY_PRODUCT_REMOVEAD = "alarm_ad_remove";

    public static boolean VISIBLE_ALARM_ITEM_AD = false;

    public static boolean VISIBLE_AD = false;


    public static String market_https = "https://play.google.com/store/apps/details?id=" + DUMAPP.getInstance().getPackageName();


    public static AdLoader getAdLoader(View view, AdListener listener){

        return new GoogleAdLoader(view,listener);
    }

    public static void bus_init() {

        PayHelper.getInstance();

        //admob init
        MobileAds.initialize(DUMAPP.getInstance(), "ca-app-pub-2800914329604494~1692373124content_copy");

    }

    public static String getShareText() {
       return  RP.UI.getString(R.string.share_tip)+"\n"+ RT.market_https;
    }
}
