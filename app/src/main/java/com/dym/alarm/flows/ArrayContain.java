package com.dym.alarm.flows;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.util.List;

/**
 * Created by dizhanbin on 16/11/4.
 *
 <property name="data" title="数组" type="0"  args="" value="" />
 <property name="obj" title="对象" type="0"  args="" value="" />
 <property name="result" title="结果" type="0"  args="" value="" />
 */

public class ArrayContain extends FlowPoint {
    @Override
    public void run(FlowBox flowBox) throws Exception {


        int result = 0;

        Object array = flowBox.getValue(params.get("data"));
        Object obj = flowBox.getValue(params.get("obj"));

        if( array != null && obj != null  )
        {

            if(array instanceof  List)
            {

                result = ((List)array).contains(obj)?1:0;

            }

        }

        flowBox.setValue(params.get("result"),result);

        flowBox.notifyFlowContinue();
    }
}
