package com.dym.alarm.common;

import android.view.MotionEvent;
import android.view.View;


/**
 * Created by dizhanbin on 16/12/1.
 */

public class ViewMoveTouchListener implements View.OnTouchListener {


    int lastX,lastY,s_x,s_y;
    boolean moved;
    @Override
    public boolean onTouch(View view, MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moved = false;
                s_x = lastX = (int) event.getRawX();
                s_y = lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;

                int left = view.getLeft() + dx;
                int top = view.getTop() + dy;
                int right = view.getRight() + dx;
                int bottom = view.getBottom() + dy;


                // 设置不能出界
                if (left < 0) {
                    left = 0;
                    right = left + view.getWidth();
                }

                if (right > UIUtil.width) {
                    right = UIUtil.width;
                    left = right - view.getWidth();
                }

                if (top < 0) {
                    top = 0;
                    bottom = top + view.getHeight();
                }

                if (bottom > UIUtil.height) {
                    bottom = UIUtil.height;
                    top = bottom -view.getHeight();
                }
                view.layout(left, top, right, bottom);

                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                moved = true;
                break;
            case MotionEvent.ACTION_UP:


                if( Math.abs( lastX-s_x  ) < 10 && Math.abs( lastY-s_y  ) < 10   )
                {
                  return false;
                }
                //NLog.i("abs width:%d height:%d",(lastX-s_x),(lastY-s_y));
                break;

        }
        return moved;
    }



}
