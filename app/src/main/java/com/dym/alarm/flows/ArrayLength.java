package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.util.List;

/**
 * Created by dzb on 16/8/16.
 *
 <property name="param" title="数据" type="0"  args="" value="" />
 <property name="length" title="存储变量" type="0"  args="" value="$length" />
 */
public class ArrayLength extends FlowPoint {

    static final String key_param = "param";
    static final String key_length = "length";


    @Override
    public void run(FlowBox flowBox) throws Exception {

        Object obj =  flowBox.getValue(params.get(key_param));

        if( obj == null )
        {
            flowBox.log("ArrayLength data is null ");
            flowBox.setValue(params.get(key_length), 0);

        }
        else if( obj instanceof  Object[] ) {

            Object[] arrays = (Object[])obj;

            flowBox.setValue(params.get(key_length), arrays.length);

        }
        else if( obj instanceof List){

            List arrays = (List) obj;
            flowBox.setValue(params.get(key_length), arrays.size());

        }
        else
            flowBox.log("ArrayObj not implement the type %s",obj);


        flowBox.notifyFlowContinue();
    }
}
