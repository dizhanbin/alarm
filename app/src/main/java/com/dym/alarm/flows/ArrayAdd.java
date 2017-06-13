package com.dym.alarm.flows;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dzb on 16/9/20.
 *
<property name="data" title="数组" type="0"  args="" value="" />
 <property name="obj" title="对象" type="0"  args="" value="" />
 <property name="arrayone" title="数组作为单个对象" type="0"  args="" value="" />
 <property name="descript" title="描述" type="0" args="" value="数组添加" />
 */
public class ArrayAdd extends FlowPoint {
    @Override
    public void run(FlowBox flowBox) throws Exception {

        List list = (List) flowBox.getValue(params.get("data"));
        if( list == null )
        {
            list = new ArrayList();
            flowBox.setValue(params.get("data"),list);

        }

        boolean arrayone = "true".equals(flowBox.getVarString(params.get("arrayone")));

        synchronized(list ) {

            Object obj = flowBox.getValue(params.get("obj"));

            flowBox.log("Array add obj:%s",obj);
            if (obj == null) {
                flowBox.notifyFlowContinue();
                return;
            } else if (obj instanceof List && !arrayone) {

                list.addAll((List) obj);

            } else
                list.add(obj);
        }

        flowBox.notifyFlowContinue();
    }
}
