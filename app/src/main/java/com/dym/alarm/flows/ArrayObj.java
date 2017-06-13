package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.util.List;

/**
 * Created by dzb on 16/8/3.
 *
 *
 <property name="data" title="数组" type="0"  args="" value="" />
 <property name="index" title="索引" type="0"  args="" value="" />
 <property name="value" title="保存到" type="0"  args="" value="" />
 */
public class ArrayObj extends FlowPoint {


    static final String key_data = "data";
    static final String key_index = "index";
    static final String key_value = "value";

    @Override
    public void run(FlowBox flowBox) throws Exception {


        Object obj =  flowBox.getValue(params.get(key_data));

        int index = -1;
        if (flowBox.isVar(params.get(key_index))) {
            index = Integer.parseInt(flowBox.getValue(params.get(key_index)).toString());
        } else
            index = Integer.parseInt(params.get(key_index));

        if( obj instanceof  Object[] ) {

            synchronized (obj) {
                Object[] arrays = (Object[]) obj;
                Object value = arrays[index];
                flowBox.setValue(params.get(key_value), value);
            }

        }
        else if( obj instanceof List){

            synchronized (obj) {
                List arrays = (List) obj;

                flowBox.setValue(params.get(key_value), arrays.get(index));
            }

        }
        else
            flowBox.log("ArrayObj not implement the type %s",obj);

        flowBox.notifyFlowContinue();
    }
}
