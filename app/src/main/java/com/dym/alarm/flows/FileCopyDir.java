package com.dym.alarm.flows;

import android.content.Context;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dzb on 16/8/15.
 */
public class FileCopyDir extends FlowPoint {


    static  final  String key_frompath = "frompath";
    static  final  String key_topath = "topath";
    static  final  String key_result = "result";

    @Override
    public void run(FlowBox flowBox) throws Exception {


        String frompath = flowBox.getVarString(params.get(key_frompath));
        String topath = flowBox.getVarString(params.get(key_topath));


        String key = "assets://";
        int index = frompath.indexOf(key);
        if( index > -1 )
        {
            String path = frompath.substring(index+key.length());


            String[] dirs = flowBox.getContext().getAssets().list(path);


            String from,to;
            for(String filename:dirs )
            {
                from = path+"/"+filename;
                to = topath+"/"+filename;

                flowBox.log("copy file :%s ",from);
                if( !copy(flowBox.getContext(),from,to) ) {

                    flowBox.log("**** copy  fail **** ");
                    flowBox.setValue(params.get(key_result), 0);
                    flowBox.notifyFlowContinue();
                    return;
                }

            }

            flowBox.setValue(params.get(key_result), 1);


        }




        flowBox.notifyFlowContinue();
    }


    private boolean copy(Context context , String from, String to) throws IOException {

        InputStream in = context.getResources().getAssets().open(from);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        byte[] data = new byte[1024];

        int length = 0;

        while( (length = in.read(data)) > 0 )
        {
            bout.write(data,0,length);

        }
        in.close();

        File file = new File( to );
        if( !file.getParentFile().exists() ){
            file.getParentFile().mkdirs();
        }

        FileOutputStream fout = new FileOutputStream(to);

        fout.write(bout.toByteArray());
        fout.close();
        return true;

    }


}
