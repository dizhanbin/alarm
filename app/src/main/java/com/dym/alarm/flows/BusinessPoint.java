package com.dym.alarm.flows;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;


/**
 * Created by dizhanbin on 16/11/18.

 <property name="method" title="方法名 1,0" type="0" args="" value=""/>
 <property name="args" title="参数,相隔" type="0" args="" value=""/>
 <property name="result" title="结果" type="0" args="" value=""/>
 <property name="descript" title="描述" type="0" args="" value="业务逻辑"/>





 */

public class BusinessPoint extends FlowPoint {


    final String key_method = "method";
    final String key_args = "args";
    final String key_result = "result";


    @Override
    public void run(final FlowBox flowBox) throws Exception {

        String method = params.get(key_method);

        boolean sync = false;
        switch (method)
        {
            case "get_today"://获取当天

            break;
            case ""://


            break;



        }


        if( !sync )
            flowBox.notifyFlowContinue();


    }


}
