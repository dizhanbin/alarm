package com.dym.alarm.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Sean on 2016/11/17.
 */
//重写Viewpager捕获pointerIndex out of range异常
public class ViewPagerFixed extends android.support.v4.view.ViewPager {

    public ViewPagerFixed(Context context) {
        super(context);
    }

    public ViewPagerFixed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    float preX;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        boolean res = false;
        try {
            res = super.onInterceptTouchEvent(event);
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                preX = event.getX();
            } else {
                if( Math.abs(event.getX() - preX)> 4 ) {

                    return true;
                } else {
                    preX = event.getX();
                }
            }
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }



}
