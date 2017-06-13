package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.io.File;

/**
 * Created by dzb on 16/8/8.
 *
 <property name="path" title="路径" type="0"  args="" value="" />
 <property name="isexist" title="是否存在结果" type="0"  args="" value="isexist" />
 <property name="name" title="文件名" type="0"  args="" value="" />
 <property name="ext" title="扩展名" type="0"  args="" value="" />
 <property name="createtime" title="创建时间" type="0" args="" value=""/>
 <property name="size" title="文件大小" type="0" args="" value=""/>
 <property name="descript" title="描述" type="0" args="" value="文件属性" />

 *
 */
public class FileExist extends FlowPoint {


    static final String key_path = "path";
    static final String key_isexist = "isexist";
    static final String key_filename = "name";
    static final String key_ext = "ext";
    static final String key_createtime = "createtime";
    static final String key_size = "size";


    @Override
    public void run(FlowBox flowBox) throws Exception {

        int exist = 0;
        String path = null;

        if( flowBox.isVar( params.get(key_path) ) )
        {
            path = flowBox.getValue(params.get(key_path)).toString();
        }
        else
            path = params.get(key_path);

        File file = new File(path);

        exist =  file.exists()==true?1:0;

        flowBox.setValue(params.get(key_isexist),exist);


        String name = params.get(key_filename);
        if( name != null && name.length()>0 )
        {
            flowBox.setValue(name,file.getName());
        }
        String ext = params.get(key_ext);

        if( ext != null && ext.length()>0 )
        {


            int pos = path.lastIndexOf('.');
            if( pos >-1 )
            {
                flowBox.setValue(ext,path.substring(pos+1));

            }

        }


        String size = params.get(key_size);
        if( !isNull(size) )
        {
            flowBox.setValue(size,exist == 0 ? 0 : file.length()/1024);
        }


        String createtime = params.get(key_createtime);
        if( !isNull(createtime) && exist == 1 )
        {
            //ios 10位
           flowBox.setValue(createtime, file.lastModified());

        }




        flowBox.notifyFlowContinue();

    }
}
