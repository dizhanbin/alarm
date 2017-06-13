package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.util.List;

/**
 * Created by dzb on 16/7/28.
 */
public class RecycleDo extends FlowPoint {


    int index ;

    final static String key_data = "data";
    final static String key_out = "out";
    final static String key_over = "over";

    @Override
    public void run(FlowBox flowBox) throws Exception {

            Object obj = flowBox.getValue( params.get(key_data) );




            if( obj instanceof List)
            {
                List list = (List) obj;
                flowBox.log("RecycleDo index:%d count:%d",index,list.size());
                if( list.size() > index) {
                    Object data =   list.get(index++);
                    flowBox.setValue( params.get(key_out),data );
                    flowBox.setValue(params.get(key_over),0);

                }
                else{
                    index = 0;
                    flowBox.setValue(params.get(key_over),1);

                }

            }
            else {
                index = 0;
                flowBox.setValue(params.get(key_over), 1);
            }
            flowBox.notifyFlowContinue();


    }



}
