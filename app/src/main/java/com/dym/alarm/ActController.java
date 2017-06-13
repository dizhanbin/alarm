package com.dym.alarm;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AnimRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dym.alarm.common.Event;
import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowBoxs;
import com.dym.alarm.common.FlowFactory;
import com.dym.alarm.common.FormFactory;
import com.dym.alarm.common.IToDo;
import com.dym.alarm.common.MessageCenter;
import com.dym.alarm.common.NLog;
import com.dym.alarm.common.Strings;
import com.dym.alarm.common.UIUtil;
import com.dym.alarm.datacenter.EventBusiness;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by dzb on 16/5/17.
 */
public class ActController extends AppCompatActivity implements IToDo, IController {


    public static ActController instance;
    FrameLayout container;




    //FrameLayout navbar;

    long thread_main;
    long event_last_time;
    Object event_last_value;


    FormManager form_manager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(params);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
         requestWindowFeature(Window.FEATURE_NO_TITLE);


        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/r-r.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());



        setContentView(R.layout.activity_act_controller);
        MessageCenter.register(this);
        container = (FrameLayout) findViewById(R.id.view_continer);

        thread_main = Thread.currentThread().getId();

        NLog.i("Main thread id:%d", thread_main);

        form_manager = new FormManager(this);
        MessageCenter.sendMessage(Event.FORM_FLASH);



        instance = this;

        //Thread.currentThread().setPriority(10);
    }



    Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {

            MessageCenter.sendMessage(Event.values()[msg.arg1], msg.obj);

        }
    };


    List<Event> run_in_thread = new ArrayList<>();


    Handler handler_remove = new Handler() {

        @Override
        public void handleMessage(Message msg) {


            if( msg == null || msg.what == 1000 ){
                is_form_changing = false;
                NLog.i("reset is_form_changing...");
            }
            else if( msg.obj instanceof  Form )
            try {


                Form form = (Form) msg.obj;

                FragmentManager manager = getSupportFragmentManager();

                Fragment fragment = manager.findFragmentByTag(form.getFormEvent().name());




                if (fragment != null ) {
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.remove(fragment);
                    trans.commitAllowingStateLoss();
                }


            } catch (Exception e) {
                NLog.e(e);
            }


        }
    };


    void dealFormEvent(Class formclass,final Event event,final Object value){



    }


    boolean is_form_changing;

    @Override
    public void todo(final Event event, final Object value) {


        if (!run_in_thread.contains(event))
            if (Thread.currentThread().getId() != thread_main) {

                Message msg = handler.obtainMessage();
                msg.arg1 = event.ordinal();
                msg.obj = value;
                handler.sendMessage(msg);
                return;

         }

        NLog.i("Act-> event:%s  is_form_changing:%b", event.toString(),is_form_changing);

         if(EventBusiness.request(event,value))
             return;


        Class formclass = FormFactory.getForm(event);

        int timer_config = 200;

        if (formclass != null) {

            if( is_form_changing )
            {
                NLog.i("is_from_changing  return;");
                return;
            }


            is_form_changing = true;
            try {


                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();

                Fragment fragment = form_manager.getForm(formclass);

                if (fragment == null) {

                    Form form = null;
                    try {
                        form = (Form) Class.forName(formclass.getName()).newInstance();
                        form.setFormEvent(event);
                        form.onValue(value);

                    } catch (Exception e) {
                        NLog.e(e);
                    }


                    Form current = form_manager.getCurrentForm();


                    boolean animation_run = false;



                        @AnimRes int exitanim = current != null ? current.getAnimExit() : 0;
                        @AnimRes int enteranim = form.getAnimEnter(false);


                        if (exitanim != 0 || enteranim != 0) {
                            //  exitanim = R.anim.fade_50;
                            //  enteranim = R.anim.fade_50;
                            animation_run = true;
                            if (true || exitanim == 0)
                                exitanim = R.anim.fade_out;
                            else if (enteranim == 0)
                                enteranim = R.anim.fade_out;
                            trans.setCustomAnimations(enteranim, exitanim);

                            NLog.i("form %s enter anim is_slide_in_bottom:%b exit is_fade_out:%b", form, enteranim == R.anim.slide_in_bottom, exitanim == R.anim.fade_out);

                        }




                    // trans.remove(form);
                    try {
                        if (manager.findFragmentByTag(form.getFormEvent().name()) == null )// && !form.isAdded() )
                            trans.add(R.id.view_continer, form, form.getFormEvent().name());


                    } catch (Exception e) {
                        NLog.e(e);
                        //return;
                    }


                    if (current != null) {
                        // trans.remove(current);
                        Message msgremove = handler_remove.obtainMessage();
                        msgremove.obj = current;
                        msgremove.what = 100;
                        handler_remove.sendMessageDelayed(msgremove, !animation_run ? 50 : timer_config);

                    }


                    // form_manager.onFormAppear(form);
                    // MessageCenter.sendMessage(Event.REQ_NAVBAR_INIT, form);


                } else {

                    Form current = form_manager.getCurrentForm();
                    Form form = (Form) fragment;
                    form.setFormEvent(event);
                    form.onValue(value);

                    if( current == form )
                    {


                        trans.commitAllowingStateLoss();
                        manager.executePendingTransactions();

                        return;
                    }



                        @AnimRes int exitanim = current != null ? current.getAnimExit() : 0;
                        @AnimRes int enteranim = form.getAnimEnter(true);

                        if (exitanim != 0 || enteranim != 0) {
                            //exitanim = R.anim.fade_50;
                            //enteranim = R.anim.fade_50;
                            if (exitanim == 0)
                                exitanim = R.anim.fade_out;
                            else if (enteranim == 0)
                                enteranim = R.anim.fade_out;

                            trans.setCustomAnimations(enteranim, exitanim);


                            NLog.i("form %s show anim is_slide_in_bottom:%b exit is_fade_out:%b", form, enteranim == R.anim.slide_in_bottom, exitanim == R.anim.fade_out);

                        }


                    //trans.replace(R.id.container, form, event.name());

                    try {

                        if (manager.findFragmentByTag(form.getFormEvent().name()) == null )// && !form.isAdded() )
                            trans.add(R.id.view_continer, form, form.getFormEvent().name());

                    } catch (Exception e) {
                        NLog.e(e);
                    }
                    trans.remove(current);


                }


                trans.commitAllowingStateLoss();
                manager.executePendingTransactions();





                //printforms();
            }catch (Exception e){

                NLog.e(e);

            }
            finally {

                handler_remove.sendEmptyMessageDelayed(1000,timer_config);

            }


            return;

        }
        else {



            FlowBox box = FlowBox.getBox(event);
            if (box == null) {
                box = FlowFactory.parse(this, FlowBoxs.getBox(event));
                if (box != null) {
                    box.run(value);
                    return;
                }
            } else {
                box.execute(value);
                return;
            }

        }


        switch (event) {



            case REQ_FORM_BACK: {

                if( is_form_changing ) {
                    NLog.i("form is changing...");
                    return;
                }

                Form current = form_manager.getCurrentForm();
                if (current.onMessage(Event.REQ_FORM_BACK, value)) {
                    return;
                } else {





                    Form form = form_manager.getLastForm();
                    if (form != null && form_manager.getCurrentForm().isCanBack()) {
                        MessageCenter.sendMessage(form.getFormEvent());
                        return;
                    }
                }

            }
            return;
            case REQ_TOAST: {

                boolean need_filter = false;
                long current = System.currentTimeMillis();
                if (event_last_time > 0) {
                    need_filter = current - event_last_time < 2500;
                    if (need_filter && value.equals(event_last_value)) {
                        NLog.i("Toast repeat.");
                        return;
                    }
                }
                event_last_time = current;
                event_last_value = value;

                if (value instanceof String) {
                    if (((String) value).startsWith("R:")) {
                        int msg = Strings.getStringByKey((String) value);
                        if (msg > 0)
                            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(this, (String) value, Toast.LENGTH_LONG).show();
                } else if (value instanceof Integer)
                    Toast.makeText(this, (Integer) value, Toast.LENGTH_LONG).show();


            }
            return;
            case REQ_WAITTING_SHOW: {



            }
            return;
            case REQ_WAITTING_HIDE: {

            }
            return;
            case REQ_DIALOG_SURE: {



            }
            return;

            case REQ_PUSH_TO_AND_CLEAR_ALL: {
                try {

                    String[] forms = value.toString().split(",");

                    for (String fstr : forms) {
                        Class classz = Class.forName("com.fotoable.photoplus.forms." + fstr);
                        Form form = form_manager.pushto(classz);
                        if (form != null) {
                            MessageCenter.sendMessage(form.getFormEvent());
                            return;
                        }
                    }


                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            }
            return;
            case REQ_FROM_CLEAR: {
                form_manager.clearForms();
            }
            return;



        }

        try {
            form_manager.onMessage(event, value);
        }catch (Exception e){
            NLog.e(e);
        }


    }

    @Override
    public boolean isValidate() {
        return true;//!isDestroyed();
    }

    public void onClick(View view) {

        Form form = form_manager.getCurrentForm();
        if (form != null && form.isVisible()) {
            form.onClick(view);
        }

    }

    private void printforms() {


        NLog.i("-------------forms in manager...");

        for (Form form : form_manager.stack) {
            NLog.i(form.toString());

        }
        NLog.i("-------------forms-------------------");


    }





    @Override
    public void onFormAppear(Form form) {

        form_manager.onFormAppear(form);


        if (form.isScreen()) {
            //form.getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
            //form.getView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_FULLSCREEN );
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(params);

            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {


            WindowManager.LayoutParams params = getWindow().getAttributes();

            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            params.flags &= (~WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().setAttributes(params);

            //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }

    }

    @Override
    public void onFormDestory(Form form) {

    }

    @Override
    public void onFormDisappear(Form form) {

        form_manager.onFormDisapear(form);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        switch (keyCode){

            case  KeyEvent.KEYCODE_BACK:
                if (form_manager.getStack().size() > 1 ) {
                    MessageCenter.sendMessage(Event.REQ_FORM_BACK);
                    return true;
                }
                else {

                    if( form_manager.getStack().size() == 1 ){
                        if( form_manager.getCurrentForm().onMessage(Event.REQ_FORM_BACK,null) )
                            return true;
                    }
                    //printforms();
                    break;
                }

            case KeyEvent.KEYCODE_MENU:

                printforms();
                break;

        }

        return super.onKeyDown(keyCode,event);
    }


    final int state_normal = 0;
    final int state_resume = 1;
    final int state_restart = 2;
    final int state_pauseed = 3;
    final int state_stopped = 4;

    int m_live_state;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        UIUtil.toInit(this);
    }

    @Override
    protected void onPause() {
        NLog.i("ActController..onPause.");
        m_live_state = state_pauseed;

        super.onPause();


    }

    @Override
    protected void onStop() {
        NLog.i("ActController..onStop.");
        Form form = form_manager.getCurrentForm();
        m_live_state = state_stopped;

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        NLog.i("ActController destory");
        MessageCenter.unregister();

        super.onDestroy();
    }


    @Override
    protected void onRestart() {


        super.onRestart();
    }


    boolean had_start;

    @Override
    protected void onResume() {


        NLog.i("ActController..onResume.");
        super.onResume();


        m_live_state = state_normal;


    }


    @Override
    public void onUserInteraction() {


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Form form = form_manager.getCurrentForm();
        if (form != null) {
            form.onPermissionResoult(requestCode, permissions, grantResults);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        NLog.i("onActivityResult requestcode:%d ->%s", requestCode, data);


        Form form = form_manager.getCurrentForm();
        if (form != null) {

            form.onActivityResult(requestCode, resultCode, data);
        }

    }

    public int getFormCount() {
        return form_manager.getStack().size();
    }


    private void setExcludeRecent(boolean exclude) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            try {
                ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.AppTask> tasks = mActivityManager.getAppTasks();
                NLog.i("apptask : size:%d", tasks.size());
                for (ActivityManager.AppTask task : tasks) {

                    task.setExcludeFromRecents(exclude);

                }


            } catch (Exception e) {
            }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
