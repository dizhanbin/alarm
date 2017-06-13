package com.dym.alarm.flows;



import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dizhanbin on 16/10/11.
 <property name="format" title="格式化为" type="0"  args="" value="" />
 <property name="outvar" title="赋值给" type="0"  args="" value="" />

 <property name="descript" title="描述" type="0" args="" value="格式化当前时间" />
 */

public class StrFormatTime extends FlowPoint {


    final static String key_format = "format";
    final static String key_outvar = "outvar";
    @Override
    public void run(FlowBox flowBox) throws Exception {


        String format = params.get(key_format);
        String var = params.get(key_outvar);

        if( "android".equals(format) )
        {
            flowBox.setValue(var, System.currentTimeMillis());

        }else if( "ios".equals(format) )
        {

            flowBox.setValue(var, System.currentTimeMillis()/1000);

        }
        else {

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            flowBox.setValue(var, sdf.format(new Date()));
        }


        flowBox.notifyFlowContinue();
    }
}
