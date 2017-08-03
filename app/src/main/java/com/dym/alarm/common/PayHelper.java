package com.dym.alarm.common;

import android.content.Intent;
import android.github.inapp.IabHelper;
import android.github.inapp.IabResult;
import android.github.inapp.Inventory;
import android.github.inapp.Purchase;

import com.crashlytics.android.Crashlytics;
import com.dym.alarm.ActController;
import com.dym.alarm.DUMAPP;
import com.dym.alarm.RP;
import com.dym.alarm.RT;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by dizhanbin on 17/1/10.
 *
 *
 *
 * start －> query -> buy
 *
 *
 *
 *
 */

public class PayHelper {


   static IabHelper helper;


   static PayHelper pHelper;
   public static  PayHelper getInstance(){

       if( pHelper == null ){
            pHelper = new PayHelper();
       }
       pHelper.initHelper();

       return pHelper;

   }

    boolean init_ok;
   private void initHelper(){

       if( helper == null ) {

           helper = new IabHelper(DUMAPP.getInstance(), RT.GOOGLE_PAY_KEY);
           helper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
               @Override
               public void onIabSetupFinished(IabResult result) {

                   init_ok = result.isSuccess();

                   NLog.log("PayHelper","initHelper :%s :%s",init_ok,result.getMessage());

               }
           });

       }

   }

   public void destory(){


       if (helper != null) {
           try {
               helper.dispose();
           } catch (Exception e) {
               NLog.e(e);
               Crashlytics.logException(e);
           }
       }
       helper = null;

   }

   public void check(final  PayListener listener)  {

       final List<String> product_list = new ArrayList<>();
       product_list.add( RT.GOOGLE_PAY_PRODUCT_REMOVEAD);

       try {


           helper.queryInventoryAsync(true, product_list, null, new IabHelper.QueryInventoryFinishedListener() {
               @Override
               public void onQueryInventoryFinished(IabResult result, Inventory inv) {


                   if (result.isSuccess()) {

                       NLog.i(" query success ");

                       List<Purchase> purchases = new ArrayList<Purchase>();

                       for(String k : product_list){


                           Purchase purchase = inv.getPurchase(k);


                           if( purchase != null ){

                               purchases.add(purchase);


                           }


                       }

                       if( purchases.size() > 0 ){

                           Collections.sort(purchases, new Comparator<Purchase>() {
                               @Override
                               public int compare(Purchase o1, Purchase o2) {
                                   return (int) (o1.getPurchaseTime()-o2.getPurchaseTime());
                               }
                           });

                           listener.pay_do(PayListener.PR.PRODUT_HAD,purchases.get(purchases.size()-1));

                       }
                       else
                           listener.pay_do(PayListener.PR.PRODUT_NONE,null);


                   }
                   else{

                       listener.pay_do(PayListener.PR.QUERY_FAIL,null);

                   }



               }
           });



       }catch (Exception e){

           NLog.e(e);
       }



   }


   public void query(final  PayListener listener){


       final List<String> product_list = new ArrayList<>();
       product_list.add( RT.GOOGLE_PAY_PRODUCT_REMOVEAD);

       try {


           helper.queryInventoryAsync(true, product_list, null, new IabHelper.QueryInventoryFinishedListener() {
               @Override
               public void onQueryInventoryFinished(IabResult result, Inventory inv) {


                   if (result.isSuccess()) {

                       NLog.i(" query success ");

                       List<Purchase> purchases = new ArrayList<Purchase>();

                       for(String k : product_list){


                           Purchase purchase = inv.getPurchase(k);


                           if( purchase != null ){

                               //purchases.add(purchase);\
                               if( purchase.isAutoRenewing() )
                               {
                                   listener.pay_do(PayListener.PR.VIP_USER,purchase);
                                   return;
                               }
                               else
                                   purchases.add(purchase);


                           }


                       }

                       if( purchases.size() > 0 ){



                       }
                       else
                           listener.pay_do(PayListener.PR.FREE_USER,null);


                   }
                   else{

                       listener.pay_do(PayListener.PR.QUERY_FAIL,null);

                   }



               }
           });



       }catch (Exception e){

           NLog.e(e);
       }




   }



   public void buyAndDealData(){


       while (!init_ok){
           try {
               Thread.sleep(100);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }

       buy(new PayListener() {
           @Override
           public void pay_do(PR pr, Object value) {

               switch (pr){

                   case BUY_SUCCESS:
                   {


                       Purchase info = (Purchase) value;

                       if (info.getSku().equals(RT.GOOGLE_PAY_PRODUCT_REMOVEAD)) {
                           if (info.getDeveloperPayload() != null) {
                               if (info.getDeveloperPayload().equals(RT.GOOGLE_PAY_PRODUCT_REMOVEAD)) {
                                   MessageCenter.sendMessage(Event.REP_BUY_SUCCESS,info);
                               }

                           }
                       }


                   }
                   break;
                   case BUY_FAIL:

                       MessageCenter.sendMessage(Event.REP_BUY_FAIL,null);
                       break;

               }

           }
       });


   }

    public void buy(final PayListener listener) {


        try {


            helper.launchSubscriptionPurchaseFlow(ActController.instance, RT.GOOGLE_PAY_PRODUCT_REMOVEAD, 2, new IabHelper.OnIabPurchaseFinishedListener() {
                @Override
                public void onIabPurchaseFinished(IabResult result, Purchase info) {

                    if (result.isSuccess()) {

                        SEvent.log("buy", "buysuccess", "");
                        listener.pay_do(PayListener.PR.BUY_SUCCESS,info);


                    } else {

                        SEvent.log("buy", "buyfail", "");
                        listener.pay_do(PayListener.PR.BUY_FAIL,null);

                    }


                }
            }, RT.GOOGLE_PAY_PRODUCT_REMOVEAD);


        } catch (Exception e) {

            Crashlytics.logException(e);
            NLog.e(e);
        }


    }

    public boolean handleActivityResult(int requestCode, int resultCode, Intent data) {
        if( helper != null )
            return helper.handleActivityResult(requestCode,resultCode,data);
        return false;
    }
}
