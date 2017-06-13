package com.dym.alarm.flows;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/13.
 * <p/>
 * <property name="data" title="数据" type="0"  args="" value="" />
 * <property name="values" title="数据(,相隔)" type="0"  args="" value="" />
 * <p/>
 * <property name="descript" title="描述" type="0" args="" value="组装map	字典" />
 *
 *
 *
 * a:1,b:2,c:$abc
 */


public class MapSet extends FlowPoint {

    final static String key_data = "data";
    final static String key_values = "values";


    @Override
    public void run(FlowBox flowBox) throws Exception {


        Map datas = (Map) flowBox.getValue( this.params.get(key_data) );

        if( datas == null ) {
            datas = new HashMap();
            flowBox.setValue(this.params.get(key_data),datas);
        }

        String[] values = this.params.get(key_values).split(",");

        for(String kv:values){

            String[] kvs = kv.split(":");

            Object obj = flowBox.getValue( kvs[1] );

            if( obj instanceof String)
            {
                if( obj != null )
                {
                    String str = (String) obj;
                    if( str.startsWith("int"))
                    {
                        obj = Integer.parseInt( str.substring(4,str.length()-1) );

                    }

                }
            }

            datas.put( flowBox.getVarString(kvs[0]), obj);

        }


        flowBox.notifyFlowContinue();

    }
}
