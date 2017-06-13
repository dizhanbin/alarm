package com.dym.alarm.flows;

import android.util.Base64;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

/**
 * Created by dizhanbin on 16/10/13.
 <property name="from" title="字符" type="0"  args="" value="" />
 <property name="to" title="赋值给" type="0"  args="" value="" />

 <property name="descript" title="描述" type="0" args="" value="Base64" />
 */

public class StrBase64 extends FlowPoint {



    final static String key_from = "from";
    final static String key_to = "to";
    @Override
    public void run(FlowBox flowBox) throws Exception {


        String from = flowBox.getVarString(params.get(key_from));
        String result =  new String(Base64.encode(from.getBytes("utf-8"),Base64.NO_WRAP),"utf-8");

        flowBox.log("base64:%s",result);

        flowBox.setValue(params.get(key_to),result);

        flowBox.notifyFlowContinue();
    }
}
