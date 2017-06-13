package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by dzb on 16/7/6.
 *
 *  <property key="out"  value="$file_result"/>//输出字符
    <property key="filepath"  value="$file"/>  //assets://flow/a.xml
 */
public class FileIn extends FlowPoint {


    final String key_filepath = "filepath";//数据存放变量
    final String key_out = "out";//json字符输出到


    @Override
    public void run(FlowBox flowBox) throws Exception {


        String filepath = flowBox.getVarString( params.get(key_filepath) );



        String key = "assets://";
        int index = filepath.indexOf(key);
        if( index > -1 )
        {

            String path = filepath.substring(index+key.length());
            InputStream in = flowBox.getContext().getResources().getAssets().open(path);

            ByteArrayOutputStream bout = new ByteArrayOutputStream();

            byte[] data = new byte[1024];

            int length = 0;

            while( (length = in.read(data)) > 0 )
            {
                bout.write(data,0,length);

            }
            in.close();


            //String result = new String(bout.toByteArray(),"utf-8");

            flowBox.setValue( params.get(key_out),new String (bout.toByteArray(),"utf-8")  );
            bout.close();

        }
        else
        {

            File filef = new File(filepath);


            int result = 0;
            if (filef.exists()) {
                //result = FileUtil.copy(frompath, topath) ? 1 : 0;


                try {

                    FileInputStream fin = new FileInputStream(filef);
                    ByteArrayOutputStream bout = new ByteArrayOutputStream();

                    byte[] bytes = new byte[1024 * 10];

                    int len = 0;

                    while ((len = fin.read(bytes)) > 0) {

                        bout.write(bytes, 0, len);
                    }

                    fin.close();


                    flowBox.setValue( params.get(key_out),bout.toByteArray());


                    result = 1;

                }catch (Exception e){

                    //e.printStackTrace();
                    result = 0;

                }




            }



        }

        flowBox.notifyFlowContinue();


    }
}
