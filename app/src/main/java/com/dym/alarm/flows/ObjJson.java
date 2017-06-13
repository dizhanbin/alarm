package com.dym.alarm.flows;

import com.alibaba.fastjson.JSON;
import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;


/**
 * Created by dzb on 16/8/16.
 <property name="classz" title="对象类型" type="1"  args="$model" value="" />
 <property name="json" title="json字符串" type="0"  args="" value="" />
 <property name="objjson" title="存储变量" type="0"  args="" value="$objjson" />
 */
public class ObjJson extends FlowPoint {

    static final String key_classz = "classz";
    static final String key_json = "json";
    static final String key_objjson = "objjson";


    @Override
    public void run(FlowBox flowBox) throws Exception {

        String classname = params.get(key_classz);
        if( classname.indexOf('.')<0 )
            classname ="com.my17.client.models."+classname;
        Class classz = Class.forName( classname );

        String json = flowBox.getVarString(params.get(key_json));

        Object value = JSON.parseObject(json,classz);

        flowBox.setValue(params.get(key_objjson),value);

        flowBox.notifyFlowContinue();


    }
}
