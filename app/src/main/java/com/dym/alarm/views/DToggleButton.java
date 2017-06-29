package com.dym.alarm.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ToggleButton;

/**
 * Created by dizhanbin on 17/5/25.
 */

public class DToggleButton extends ToggleButton {
    public DToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DToggleButton(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width  = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

       // if( width > height ){
            setMeasuredDimension( width,width  );
       // }

    }

    @Override
    public void setTextOn(CharSequence textOn) {
        super.setTextOn(textOn);
        setText(textOn);
    }

}
