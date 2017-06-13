package com.dym.alarm.flows;

import com.dym.alarm.common.Event;
import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;
import com.dym.alarm.common.MessageCenter;

/**
 * Created by dzb on 16/9/14.
 *
 * <property name="str" title="提示信息" type="0"  args="" value="" />

 <property name="descript" title="描述" type="0" args="" value="提示框toast" />
 */
public class DialogToast extends FlowPoint {
    @Override
    public void run(FlowBox flowBox) throws Exception {

        String tip = flowBox.getVarString(params.get("str"));



        MessageCenter.sendMessage(Event.REQ_TOAST,tip);



        flowBox.notifyFlowContinue();
    }
}
