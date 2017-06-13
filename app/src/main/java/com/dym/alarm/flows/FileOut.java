package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.io.FileOutputStream;

/**
 * Created by dzb on 16/7/6.
 *
 *  <property key="in"  value="$http_result"/>
    <property key="filepath"  value="$json_result"/>
 */
public class FileOut extends FlowPoint {


    final String key_in = "in";//数据存放变量
    final String key_out = "filepath";//json字符输出到


    @Override
    public void run(FlowBox flowBox) throws Exception {

        Object obj = flowBox.getValue( params.get(key_in)  );

        byte[] datas = null;
        if( obj instanceof  String )
        {

            datas = ((String) obj).getBytes("utf-8");


        }
        else if( obj instanceof  byte[] ){

            datas = (byte[])obj;


        }
        if( datas != null ) {

            String filename =  params.get(key_out);
            if( filename.charAt(0) ==  '$'  )
                filename = (String)flowBox.getValue(filename);

            FileOutputStream fileout = new FileOutputStream(filename);
            fileout.write(datas, 0, datas.length);
            fileout.close();
        }
        flowBox.notifyFlowContinue();


    }
}
