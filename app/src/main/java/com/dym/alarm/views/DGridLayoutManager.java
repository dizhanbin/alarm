package com.dym.alarm.views;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.dym.alarm.common.NLog;

/**
 * Created by dizhanbin on 2017/8/3.
 */

public class DGridLayoutManager extends GridLayoutManager {
    public DGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public DGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (Exception e) {
            NLog.e(e);
        }
    }
}
