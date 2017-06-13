package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by dzb on 16/8/8.
 *
 <property name="frompath" title="拷贝路径" type="0"  args="" value="" />
 <property name="topath" title="目的路径" type="0"  args="" value="" />
 <property name="result" title="是否成功" type="0"  args="" value="$copyresult" />
 */
public class FileCopy extends FlowPoint {


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
            InputStream in = flowBox.getContext().getResources().getAssets().open(path);

            ByteArrayOutputStream bout = new ByteArrayOutputStream();

            byte[] data = new byte[1024*10];

            int length = 0;

            while( (length = in.read(data)) > 0 )
            {
                bout.write(data,0,length);

            }
            in.close();


            FileOutputStream fout = new FileOutputStream(topath);
            fout.write(bout.toByteArray());
            fout.close();

            flowBox.setValue(params.get(key_result), 1);

        }
        else {

            int result = copy(frompath,topath)?1:0;

            flowBox.setValue(params.get(key_result), result);

        }
        flowBox.notifyFlowContinue();
    }

    public static boolean copy(String frompath,String topath ){


        File filef = new File(frompath);
        File filet = new File(topath);

        if (filef.exists()) {

            File parent = filet.getParentFile();
            if( !parent.exists() )
                parent.mkdirs();
            try {

                FileInputStream fin = new FileInputStream(filef);
                FileOutputStream fout = new FileOutputStream(filet);

                byte[] bytes = new byte[1024 * 100];

                int len = 0;

                while ((len = fin.read(bytes)) > 0) {

                    fout.write(bytes, 0, len);
                }

                fin.close();
                fout.close();
                return true;

            }catch (Exception e){

                //e.printStackTrace();


            }




        }
        return false;


    }
}
