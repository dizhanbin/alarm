package com.dym.alarm.datacenter;

/**
 * Created by dizhanbin on 17/5/20.
 */

public interface ResponseListener {

    public static  final int RE_OK = 0;
    public static  final int RE_ERROR_NO = 1;
    public static  final int RE_ERROR_ERROR = 2;

    public void callback(int type,Object value);
}
