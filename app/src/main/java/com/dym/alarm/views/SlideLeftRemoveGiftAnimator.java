package com.dym.alarm.views;


import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;

import com.dym.alarm.ActController;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by dizhanbin on 17/6/27.
 */

public class SlideLeftRemoveGiftAnimator extends SlideInLeftAnimator {

    @Override
    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {

        /*
        ViewCompat.animate(holder.itemView)
                .translationX(-holder.itemView.getRootView().getWidth())
                .setDuration(getRemoveDuration())
                .setInterpolator(mInterpolator)
                .setListener(new DefaultRemoveVpaListener(holder))
                .setStartDelay(getRemoveDelay(holder))
                .start();
                */

        ExplosionField explosionField = ExplosionField.attach2Window(ActController.instance);
        explosionField.explode(holder.itemView);


    }

}
