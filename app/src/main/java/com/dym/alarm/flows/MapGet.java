package com.dym.alarm.flows;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.util.Map;

/**
 * Created by dzb on 16/9/19.
 */
public class MapGet extends FlowPoint {
    final static String key_data = "data";
    final static String key_values = "values";


    @Override
    public void run(FlowBox flowBox) throws Exception {


        Map datas = (Map) flowBox.getValue( this.params.get(key_data) );

        if( datas == null ) {

            flowBox.log("map取值 data=null");
            flowBox.notifyFlowContinue();
            return;
        }

        String[] values = this.params.get(key_values).split(",");

        for(String kv:values){
            String[] kvs = kv.split(":");
            flowBox.setValue(kvs[0], datas.get( kvs[1] ));
        }


        flowBox.notifyFlowContinue();

    }
}
