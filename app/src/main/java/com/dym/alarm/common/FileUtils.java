package com.dym.alarm.common;

import java.io.File;



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

}
