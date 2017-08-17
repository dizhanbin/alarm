package com.dym.alarm.common;

import android.content.Intent;


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




   static PayHelper pHelper;
   public static  PayHelper getInstance(){

       if( pHelper == null ){
            pHelper = new PayHelper();
       }
       pHelper.initHelper();

       return pHelper;

   }
   int init_ok = -1;
   private void initHelper(){


   }

   public void destory(){



   }

   public void check(final  PayListener listener)  {




   }


   public void query(final  PayListener listener){





   }



   public void buyAndDealData(){




   }

    public void buy(final PayListener listener) {




    }

    public boolean handleActivityResult(int requestCode, int resultCode, Intent data) {

        return false;
    }
}
