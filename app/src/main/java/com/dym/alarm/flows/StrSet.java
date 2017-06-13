package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dzb on 16/8/4.
 */
public class StrSet extends FlowPoint {


    static  final String key_condition = "condition";

    Pattern pattern_field = Pattern.compile("\\{[\\$#][0-9a-zA-Z_]+\\}");

    @Override
    public void run(FlowBox flowBox) throws Exception {


        String conditon = params.get(key_condition);

        int index = conditon.indexOf('=');


        String[] strs = new String[]{conditon.substring(0,index),conditon.substring(index+1)};

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

        flowBox.setValue(key,strs[1]);

        flowBox.log("strset :"+strs[1]);


        flowBox.notifyFlowContinue();
    }
}
