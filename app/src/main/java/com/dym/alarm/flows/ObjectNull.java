package com.dym.alarm.flows;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

/**
 * Created by dizhanbin on 16/10/28.


 <property name="param" title="变量" type="0"  args="" value="" />
 <property name="isnull" title="存储变量" type="0"  args="" value="$isnull" />
 <property name="descript" title="描述" type="0" args="" value="是否为空" />

 */

public class ObjectNull extends FlowPoint {

    final static String key_param = "param";
    final static String key_isnull = "isnull";



    @Override
    public void run(FlowBox flowBox) throws Exception {


        String param = params.get(key_param);


        Object obj = flowBox.getValue(param);


        int result = 0;

        if( obj != null  )
        {
            if( obj instanceof String ) {
                if (((String) obj).length() > 0)
                    result = 1;
            }
            else
                result = 1;

        }

        flowBox.setValue(params.get(key_isnull),result);

        flowBox.notifyFlowContinue();
    }
}
