package com.dym.alarm.common;

/**
 * Created by dizhanbin on 16/12/14.
 */

public interface ObjectCallBack<K,V,C> {

    public void onCallBack(K k, V v);
    public C isOK();

}
