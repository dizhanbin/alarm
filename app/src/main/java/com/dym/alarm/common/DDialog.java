package com.dym.alarm.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;


import com.dym.alarm.R;

import java.util.Timer;
import java.util.Vector;

/**
 * TODO: document your custom view class.
 */
public class DDialog extends Dialog {



    public int tag;
    public Timer timer;

    public View contentView;

    public DDialog(Context context) {
        super(context);
    }

    public DDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public View findViewById(int id){

        if( contentView != null )
            return contentView.findViewById(id);
        else
            return null;
    }


    public void setRunable(Runnable runable) {
        new Thread(runable).start();
    }



    public static class ButtonListener{
        private  int viewid;
        private  OnClickListener listener;

        public ButtonListener(int viewid, OnClickListener listener){
            this.viewid = viewid;
            this.listener = listener;
        }
    }
    public static interface ViewInitListener{
        public void ViewInit(View view);
    }
   public static  DDialog ddialog;


    @Override
    public void dismiss() {

        try {
            if (DDialog.ddialog.timer != null)
                DDialog.ddialog.timer.cancel();
            DDialog.ddialog.timer = null;
            super.dismiss();
            DDialog.ddialog = null;
        }catch (Exception e){
            NLog.e(e);
        }

    }

    public static void  fdimiss(){

        try {
            if (DDialog.ddialog != null && DDialog.ddialog.isShowing()) {

                DDialog.ddialog.dismiss();


            }
        }catch (Exception e){
            NLog.e(e);
        }
    }


    public static class Builder {
        private Context context;
        private int layout;
        ViewInitListener viewListener;
        OnDismissListener onDismissListener;

        private Vector<ButtonListener> buttons = new Vector<>();


        private boolean cancelable = false;

        private int tag;


        public Builder setTag(int t){

            tag = t;
            return this;
        }



        public Builder setCancelable(boolean can){
            cancelable = can;
            return this;
        }

        public Builder setInitListener(ViewInitListener listener){

            viewListener = listener;
            return this;
        }

        public Builder setOnDimissListener(OnDismissListener l){
            onDismissListener = l;
            return this;
        }


        public Builder(Context context) {
            this.context = context;
        }


        public Builder addButtonListener(int viewid, OnClickListener listener){
            buttons.add(new ButtonListener(viewid,listener) );
            return this;
        }

        public Builder setContentView(int layout){

            this.layout = layout;
            return this;
        }




        public DDialog create() {


            fdimiss();

            // instantiate the dialog with the custom Theme
            final DDialog dialog = new DDialog(context, R.style.DDialog);

            View contentView = View.inflate(context, layout, null);


            for(final ButtonListener bl : buttons){
                contentView.findViewById(bl.viewid).setOnClickListener(

                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                NLog.i("DDialog click button:%s",v);
                                bl.listener.onClick( dialog,bl.viewid );
                            }
                        }
                );
            }


            if( onDismissListener != null )
                dialog.setOnDismissListener(onDismissListener);

            dialog.setCancelable(this.cancelable);


            if( viewListener != null )
                viewListener.ViewInit(contentView);

            dialog.setContentView(contentView);

            Window dialogWindow = dialog.getWindow();
            WindowManager m = ((Activity)context).getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            p.height = LayoutParams.MATCH_PARENT; // 高度设置为屏幕的0.6，根据实际情况调整
            p.width = LayoutParams.MATCH_PARENT; // 宽度设置为屏幕的0.65，根据实际情况调整
            p.gravity = Gravity.CENTER;


            // dialogWindow.setGravity(Gravity.CENTER);

            dialogWindow.setAttributes(p);




            DDialog.ddialog = dialog;

            ddialog.tag = tag;
            dialog.contentView = contentView;


            return dialog;
        }

    }


}
