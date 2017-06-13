package com.dym.alarm.flows;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dzb on 16/9/20.
 * <element id="0121" name="整数处理" iconpath=":/image/string.png"  descript="字符处理\n 字符连接 变量以{}相隔 $json_path = {$field}.xml" c_android="com.northking.bpo.flows.IntUtil"	 c_ios="IntUtil">
 <property name="condition" title="条件" type="0"  args="" value="" />
 <property name="descript" title="描述" type="0" args="" value="整数处理" />
 </element>
 */
public class IntUtil extends FlowPoint {


    final static String key_condition = "condition";


    Pattern pattern_field = Pattern.compile("\\{\\$[0-9a-zA-Z_]+\\}");

    @Override
    public void run(FlowBox flowBox) throws Exception {


        String value =  params.get(key_condition);

        if( value == null ) {
            value = "";
        }

        String[] strs = value.split("\\+=|-=|=");

        String key = strs[0];

        Matcher matcher = pattern_field.matcher(strs[1]);


        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String field = matcher.group();
            Object obj = flowBox.getValue(field.substring(1,field.length()-1));
            if (obj != null) {
                matcher.appendReplacement(sb, obj.toString());
            }
        }
        matcher.appendTail(sb);
        strs[1] = sb.toString();



        if( value.indexOf("+=") > -1 )
        {

            String kv = (String)flowBox.getValue(key);
            if( kv == null )
                kv = "";

            String result =String.valueOf( Integer.parseInt(kv) + Integer.parseInt(strs[1]) );

            flowBox.log("%s %s %s == %s",key,"+=",strs[1],result);
            flowBox.setValue( key,result  );
        }
        else if( value.indexOf('=') >-1 )
        {

            flowBox.setValue( key,strs[1]  );

        }

        flowBox.notifyFlowContinue();



    }


}
