package com.dym.alarm.common;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.provider.DocumentFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * Created by dizhanbin on 16/12/28.
 */

public class FileUtils {

    public static boolean deleteFile(String path) {
        if (path == null || path.length() == 0) {
            return true;
        }

        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    public static String readFile(String path) {

        File file = new File(path);
        if (file.exists()) {

            try {

                FileInputStream fin = new FileInputStream(file);
                ByteArrayOutputStream bout = new ByteArrayOutputStream();


                byte[] data = new byte[1024];

                int len = 0;

                while ((len = fin.read(data)) > 0) {

                    bout.write(data, 0, len);

                }

                fin.close();

                return new String(bout.toByteArray(), "utf-8");
            } catch (Exception e) {

                NLog.e(e);

                return null;
            }

        }
        return null;

    }

    public static boolean writeFile(String path, String content) {

        try {
            File file = new File(path);
            if( !file.getParentFile().exists() )
                file.getParentFile().mkdirs();

            FileOutputStream fout = new FileOutputStream(file);

            fout.write(content.getBytes("utf-8"));

            fout.close();


            return true;
        } catch (Exception e) {
            NLog.e(e);

            return false;
        }

    }
    public static boolean copy(Context content, Uri uri , String path){



        try {

            ParcelFileDescriptor pfd = content.getContentResolver().
                    openFileDescriptor(uri, "r");

            FileInputStream fis = new FileInputStream( pfd.getFileDescriptor() );
            FileOutputStream fout = new FileOutputStream(path);

            byte[] data = new byte[1024];

            int len;
            while( (len=fis.read(data))>0 ){
                fout.write(data,0,len);
            }

            fis.close();
            fout.close();


            return true;

        }catch (Exception  e){

            e.printStackTrace();
        }

        return false;
    }

}
