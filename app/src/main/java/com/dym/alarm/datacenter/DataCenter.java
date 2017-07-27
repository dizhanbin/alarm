package com.dym.alarm.datacenter;

import com.alibaba.fastjson.JSON;
import com.dym.alarm.DUMAPP;
import com.dym.alarm.common.FileUtils;
import com.dym.alarm.common.NLog;
import com.dym.alarm.model.MAlarm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dizhanbin on 17/5/23.
 */

public class DataCenter {


    static String data_path = DUMAPP.getInstance().getApplicationContext().getFilesDir().getAbsolutePath()+"/data.json";


    static List<MAlarm> datas = new ArrayList<>();


    static {

        loadAlarms();

    }


    public static List<MAlarm> getDatas(){

        return datas;

    }

    public static List<MAlarm> loadAlarms(){


        if( !new File(data_path).exists() )
            return datas;

        try {
            FileInputStream fis = new FileInputStream(data_path);

            ByteArrayOutputStream bout = new ByteArrayOutputStream();

            byte[] bs = new byte[1024];

            int len = 0;

            while( (len = fis.read(bs) )>0 )
            {
                bout.write(bs,0,len);
            }

            fis.close();

            String str = new String(bout.toByteArray(),"utf-8");

            datas.addAll(JSON.parseArray(str,MAlarm.class));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return datas;


    }

    public static void saveAlarms()
    {

        String strs = JSON.toJSONString(datas);


        try {
            FileOutputStream fout = new FileOutputStream(data_path);
            fout.write(strs.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void save(MAlarm alarm){


        if( !datas.contains(alarm) ){
            NLog.i("not contain alarm add");
            datas.add(alarm);
        }
        saveAlarms();

    }

}
