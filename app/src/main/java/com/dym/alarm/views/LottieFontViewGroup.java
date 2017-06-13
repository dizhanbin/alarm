package com.dym.alarm.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.dym.alarm.R;
import com.dym.alarm.common.NLog;
import com.dym.alarm.common.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottieFontViewGroup extends LinearLayout {
  private final Map<String, LottieComposition> compositionMap = new HashMap<>();
  private final List<View> views = new ArrayList<>();

  @Nullable private LottieAnimationView cursorView;

  public LottieFontViewGroup(Context context) {
    super(context);
    //init();
  }

  public LottieFontViewGroup(Context context, AttributeSet attrs) {
    super(context, attrs);
    setGravity(Gravity.CENTER);
    setOrientation(HORIZONTAL);
    //init();
  }

  public LottieFontViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    //init();
    setGravity(Gravity.CENTER);
    setOrientation(HORIZONTAL);
  }




  @Override
  public void addView(View child, int index) {
    super.addView(child, index);
    if (index == -1) {
      views.add(child);
    } else {
      views.add(index, child);
    }
  }


  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);


  }



  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    if (views.isEmpty()) {
      return;
    }



    int L = UIUtil.dip2px (getContext(),30);

    int R = right -  UIUtil.dip2px (getContext(),80);


    int v_x_w = (R - L)/views.size();

    int currentY = UIUtil.height/3;

    for (int i = 0; i < views.size(); i++) {
      View view = views.get(i);
      view.layout(L+v_x_w*i, currentY, L+v_x_w*i + view.getMeasuredWidth(),
          currentY + view.getMeasuredHeight());
    }
  }


  @Override
  public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
    BaseInputConnection fic = new BaseInputConnection(this, false);
    outAttrs.actionLabel = null;
    outAttrs.inputType = InputType.TYPE_NULL;
    outAttrs.imeOptions = EditorInfo.IME_ACTION_NEXT;
    return fic;
  }

  @Override
  public boolean onCheckIsTextEditor() {
    return true;
  }



  public void setChar(char letter) {

    final String fileName = "Mobilo/" + letter + ".json";
    if (compositionMap.containsKey(fileName)) {
      addComposition(compositionMap.get(fileName));
    } else {
      LottieComposition.Factory.fromAssetFileName(getContext(), fileName,
              new OnCompositionLoadedListener() {
                @Override
                public void onCompositionLoaded(LottieComposition composition) {
                  compositionMap.put(fileName, composition);
                  addComposition(composition);
                }
              });
    }

  }


  private void addComposition(LottieComposition composition) {
    LottieAnimationView lottieAnimationView = new LottieAnimationView(getContext());

    NLog.i("uiuitil.width:%d",UIUtil.width);
    int wp = MeasureSpec.makeMeasureSpec( (UIUtil.width)/3,MeasureSpec.EXACTLY );
    lottieAnimationView.setLayoutParams(new LayoutParams(wp,wp));
    lottieAnimationView.setComposition(composition);
    //lottieAnimationView.setBackgroundColor(0x88ff0000);
    addView(lottieAnimationView);


    lottieAnimationView.playAnimation();

  }



}
