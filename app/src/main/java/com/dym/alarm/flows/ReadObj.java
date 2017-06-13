package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.lang.reflect.Field;

/**
 * Created by dzb on 16/7/29.
 *
 * <property name="data" title="数据" type="0"  args="" value="" />
 <property name="field"  title="字段" type="0"  args="" value="" />
 <property name="out" title="保存到" type="0"  args="" value="" />
 */
public class ReadObj extends FlowPoint {


    final static String key_data = "data";
    final static String key_field = "field";
    final static String key_out = "out";

    @Override
    public void run(FlowBox flowBox) throws Exception {


        Object obj = flowBox.getValue(params.get(key_data));
        String field = params.get(key_field);
        String out = params.get(key_out);

        String[] fields = field.split(",");
        String[] outs = out.split(",");


        if( obj != null )
        {
            Class classz = obj.getClass();

            for(int i=0;i<fields.length;i++)
            {

                Field ff = classz.getDeclaredField(fields[i]);
                ff.setAccessible(true);
                Object value = ff.get(obj);
                flowBox.setValue(outs[i],value);

            }

            //flowBox.log( params.get(key_descript)+":%s",value.toString() );

        }

        flowBox.notifyFlowContinue();


    }
}
