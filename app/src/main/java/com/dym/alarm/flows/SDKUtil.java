package com.dym.alarm.flows;

import android.os.Build;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

/**
 * Created by dizhanbin on 16/10/29.
 <property name="sdkint" title="SDK版本(int)" type="0" args="" value="sdkint"/>
 <property name="descript" title="描述" type="0" args="" value="系统信息"/>
 */

public class SDKUtil extends FlowPoint {

    final static String key_sdkint = "sdkint";


    @Override
    public void run(FlowBox flowBox) throws Exception {

        String sdkint = params.get(key_sdkint);

        if( !isNull(sdkint) )
        {
            flowBox.setValue(sdkint, Build.VERSION.SDK_INT);
        }

        flowBox.notifyFlowContinue();
    }
}
