package com.dym.alarm.views;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dym.alarm.ActController;
import com.dym.alarm.R;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import tyrantgit.explosionfield.ExplosionAnimator;
import tyrantgit.explosionfield.ExplosionField;

import static android.R.attr.duration;
import static android.R.attr.startDelay;

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
        //holder.itemView.setBackgroundResource(R.drawable.btn_def);
        explosionField.explode(holder.itemView);





    }

}
