package com.dym.alarm.common;

import com.dym.alarm.DUMAPP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/**
 * Created by dizhanbin on 17/5/24.
 */

public class FirebaseLock {


    static FileLock fileLock;

    public static boolean trylock(){

        String path = DUMAPP.getInstance().getApplicationContext().getFilesDir().getAbsolutePath();

        NLog.i("FirebaseLock path:%s",path);

        String filepath = path +"/lock.dat";

        //String filepath = "/sdcard/lock.data";
        if( !new File(filepath).exists() ){

            try {
                FileOutputStream fout = new FileOutputStream(filepath);
                fout.write("fotoable".getBytes());
                fout.close();

            }catch (Exception e){
                NLog.e(e);

            }

        }

        try {
            fileLock =  new RandomAccessFile(filepath,"rw").getChannel().tryLock(0,2,false);
            return ( fileLock != null );
        } catch (IOException e) {
            NLog.e(e);
            return false;
        }



    }

    public static void release(){

        if( fileLock != null )
            try {
                fileLock.release();
            }catch (Exception e){
                NLog.e(e);
            }

    }

}
