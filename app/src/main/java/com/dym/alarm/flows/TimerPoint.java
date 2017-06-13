package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dzb on 16/8/11.
 * <property name="interval" title="间隔（秒）" type="0"  args="" value="" />
 */
public class TimerPoint extends FlowPoint {

    final static String key_interval = "interval";
    @Override
    public void run(final FlowBox flowBox) throws Exception {

        int interval = 0;
        if( flowBox.isVar(params.get(key_interval)) )
        {
            interval = Integer.parseInt( flowBox.getVarString(params.get(key_interval)) );

        }
        else
            interval = Integer.parseInt( params.get(key_interval) );


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                try {

                    flowBox.notifyFlowContinue();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        },interval*1000);


    }


}
