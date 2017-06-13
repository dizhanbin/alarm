package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dzb on 16/7/6.
 *
 * <property key="condition"  value="$json_result+=abc"/>
 */
public class StringUtil extends FlowPoint {


    final static String key_condition = "condition";
    final static String key_isnumber = "isnumber";


    Pattern pattern_field = Pattern.compile("\\{\\$#[0-9a-zA-Z_]+\\}");

    @Override
    public void run(FlowBox flowBox) throws Exception {


        String value =  params.get(key_condition);

        boolean isnumber = "true".equals(params.get(key_isnumber));

        if( value == null ) {
            value = "";
        }

        String[] strs = value.split("\\+=|-=|=|/=|\\*=");

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

            String kv = flowBox.getValue(key).toString();


            if( isnumber )
            {

                if( kv == null )
                    kv = "0";
                int result = Integer.parseInt(kv) + Integer.parseInt(strs[1]);

                flowBox.log("%s %s %s == %d", key, "+=", strs[1], result);

                flowBox.setValue( key ,result  );
            }
            else {
                if( kv == null )
                    kv = "";
                String result = kv + strs[1];
                flowBox.log("%s %s %s == %s", key, "+=", strs[1], result);
                flowBox.setValue(key, result);
            }
        }
        else if( value.indexOf("-=") >-1 )
        {


            String kv = flowBox.getValue(key).toString();


            if( isnumber )
            {

                if( kv == null )
                    kv = "0";
                int result = Integer.parseInt(kv) - Integer.parseInt(strs[1]);

                flowBox.log("%s %s %s == %d", key, "-=", strs[1], result);

                flowBox.setValue( key ,result  );
            }
            else {
                if( kv == null )
                    kv = "";
                String result = kv.replaceAll(strs[1],"");
                flowBox.log("%s %s %s == %s", key, "replace", strs[1], result);
                flowBox.setValue(key, result);
            }

        }
        else if(value.indexOf("/=") >-1)
        {

            String kv = flowBox.getValue(key).toString();

            if( kv == null )
                kv = "0";

            int result = Integer.parseInt(kv) / Integer.parseInt(strs[1]);
            flowBox.log("%s %s %s == %d", key, "/=", strs[1], result);

            flowBox.setValue( key ,result  );
        }
        else if(value.indexOf("*=") >-1)
        {

            String kv = flowBox.getValue(key).toString();

            if( kv == null )
                kv = "0";

            int result = Integer.parseInt(kv) * Integer.parseInt(strs[1]);
            flowBox.log("%s %s %s == %d", key, "*=", strs[1], result);

            flowBox.setValue( key ,result  );
        }
        else if( value.indexOf('=') >-1 )
        {

            flowBox.setValue( key,strs[1]  );

        }


        flowBox.notifyFlowContinue();



    }

}
