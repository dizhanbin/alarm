package com.dym.alarm.flows;


import com.alibaba.fastjson.JSON;
import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

/**
 * Created by dzb on 16/7/6.
 *
 *  <property key="in"  value="$http_result"/>
    <property key="out"  value="$json_result"/>
 *
 */
public class JSONOut extends FlowPoint {


    final String key_in = "in";//数据存放变量
    final String key_out = "out";//json字符输出到



    @Override
    public void run(FlowBox flowBox) throws Exception {


        Object obj = flowBox.getValue( params.get( key_in ) );
        if( obj != null )
            flowBox.setValue(params.get( key_out ), JSON.toJSONString(obj));
        else
        {
            flowBox.log("JSONOut out json string object[%s] is null....",key_in);
            //flowBox.error();
        }

        flowBox.notifyFlowContinue();

    }




}
