package com.dym.alarm.flows;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by dzb on 16/5/26.
 *

 <property key="key"  value="jsonResult.rows"/>
 <property key="class"  value="com.my17.client.models.MHotel"/>
 <property key="in"  value="$http_result"/>
 <property key="out"  value="$json_result"/>
 */
public class JSONParse extends FlowPoint {

    final String key_in = "in";
    final String key_class = "class";
    final String key_out = "out";
    final String key_keys = "key";

    @Override
    public void run(FlowBox flowBox) throws Exception {

        Object json_str;
        if( flowBox.isVar( params.get(key_in) ) )
        {
            json_str = flowBox.getValue( params.get(key_in) );

        }
        else
            json_str = params.get(key_in);

       // L17.Log.i("JSONParse json:%s",json_str);


        Object jparent = null ;
        if( json_str instanceof  String )
            jparent = JSON.parse( (String)json_str );//,Feature.DisableASM,Feature.DisableCircularReferenceDetect);
        else if( json_str instanceof  byte[] ){
            jparent = JSON.parse( (byte[])json_str);//,Feature.DisableASM,Feature.DisableCircularReferenceDetect);
        }


        //jsonResult.rows




        String keystr = params.get(key_keys);



        if( keystr != null && keystr.length() > 0 ) {
            String[] keys = keystr.split("\\.");
            for (String k : keys) {
                if (jparent instanceof JSONObject) {
                    jparent = ((JSONObject) jparent).get(k);
                } else {


                    flowBox.error();
                    return;
                }

            }
        }


        String classname = params.get(key_class);

        flowBox.log("JSONParse class:%s ",classname);



        if( classname.length() == 0 ) {

            //flowBox.log("JSONParse jparent:%s ",jparent);
            flowBox.setValue( params.get(key_out),jparent);
            flowBox.notifyFlowContinue();
            return;

        }
        else if( classname.indexOf('.')<0 ) {
            classname = "com.dym.alarm.model." + classname;
        }

        Class classz = Class.forName( classname );
        if( jparent instanceof JSONArray)
        {

            JSONArray array = (JSONArray) jparent;
            List list = new ArrayList();

            for(int i=0;i<array.size();i++){

                list.add(array.getObject(i,classz));

            }
            flowBox.setValue( params.get(key_out),list);

        }
        else{

            JSONObject jsonObject = (JSONObject) jparent;
            Object result = JSON.toJavaObject(jsonObject,classz);
            flowBox.setValue(params.get(key_out),result);

        }

        flowBox.notifyFlowContinue();
    }
}
