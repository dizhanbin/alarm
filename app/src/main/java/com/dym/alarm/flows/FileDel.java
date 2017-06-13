package com.dym.alarm.flows;

import android.provider.MediaStore;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;
import com.dym.alarm.common.NLog;

import java.io.File;


/**
 * Created by dizhanbin on 16/11/3.
 <property name="path" title="路径" type="0"  args="" value="" />
 <property name="result" title="是否成功" type="0"  args="" value="$result" />
 <property name="descript" title="描述" type="0" args="" value="删除文件" />
 */

public class FileDel extends FlowPoint {


    final  String key_path = "path";
    final  String key_result = "result";

    @Override
    public void run(FlowBox flowBox) throws Exception {



        String path = flowBox.getVarString(params.get(key_path));
        File file = new File(path);

        int result = 1;
        if( file.exists()   )
        {
            if( !file.isDirectory() ) {
                result = file.delete() ? 1 : 0;

                try {

                    if (path.toLowerCase().endsWith("mp4")) {

                        flowBox.getContext().getContentResolver().delete(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                MediaStore.Video.Media.DATA + "= \"" + path + "\"",
                                null);


                    } else {
                        flowBox.getContext().getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                MediaStore.Images.Media.DATA + "= \"" + path + "\"",
                                null);

                    }
                }catch (Exception e){

                    NLog.e(e);
                }
                NLog.i("delete file %s result:%b",path,result);




            }
//            else {
//                boolean deled = FileUtils.deleteFile(path);
//                result = 1;
//            }
        }else
            NLog.i("delete file %s not exist",path);

        flowBox.setValue(params.get(key_result),result);

        flowBox.notifyFlowContinue();
    }


}
