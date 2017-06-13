package com.dym.alarm.flows;

import com.dym.alarm.common.Event;
import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;
import com.dym.alarm.common.MessageCenter;

/**
 * Created by dzb on 16/9/14.
 * 	<property name="show" title="显示隐藏(true,false)" type="0"  args="" value="true" />
 <property name="tip" title="信息" type="0"  args="" value="" />
 <property name="descript" title="描述" type="0" args="" value="等待框" />
 */
public class DialogWaitting extends FlowPoint {
    @Override
    public void run(FlowBox flowBox) throws Exception {


        String show = params.get("show");

        if( "true".equals(show) )
            MessageCenter.sendMessage(Event.REQ_WAITTING_SHOW);
        else
            MessageCenter.sendMessage(Event.REQ_WAITTING_HIDE);


        flowBox.notifyFlowContinue();
    }
}
