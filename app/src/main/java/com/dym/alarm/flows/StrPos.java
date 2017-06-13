package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

/**
 * Created by dzb on 16/8/4.
 */
public class StrPos extends FlowPoint {


    static  final String key_child = "child";
    static  final String key_parent = "parent";
    static  final String key_pos = "pos";



    @Override
    public void run(FlowBox flowBox) throws Exception {


        String parent = flowBox.getVarString(params.get(key_parent));
        String child = flowBox.getVarString(params.get(key_child));
        String pos = params.get(key_pos);


        int result = -1;
        if( parent != null || child != null )
        {
            result = parent.indexOf(child);
        }
        flowBox.setValue(pos,result);

        flowBox.notifyFlowContinue();


    }
}
