package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

/**
 * Created by dzb on 16/8/3.
 <property name="data" title="字符串" type="0"  args="" value="" />
 <property name="regex" title="分隔字符" type="0"  args="" value="" />
 <property name="value" title="保存到" type="0"  args="" value="" />
 <property name="descript" title="描述" type="0" args="" value="字符分隔成数组" />
 */
public class StrSplite extends FlowPoint {



    static  final String key_data="data";
    static  final String key_regex="regex";
    static  final String key_value="value";


    @Override
    public void run(FlowBox flowBox) throws Exception {

        String str = params.get(key_data);
        if( flowBox.isVar(str) )
        {
            str = flowBox.getValue(str).toString();
        }
        String[] strs = str.split( params.get(key_regex) );
        flowBox.setValue(params.get(key_value),strs);

        flowBox.notifyFlowContinue();

    }
}
