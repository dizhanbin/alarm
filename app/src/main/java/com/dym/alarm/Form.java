package com.dym.alarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.dym.alarm.common.Event;
import com.dym.alarm.common.MessageCenter;
import com.dym.alarm.common.NLog;

import java.lang.reflect.Field;

/**
 * Created by dzb on 16/5/18.
 */
public class Form extends Fragment {
    private Event formEvent;
    public View mView;

    public void onValue(Object value)
    {



    }

    public void setView(View v){
        mView = v;
    }

    public void setFormEvent(Event formEvent) {
        this.formEvent = formEvent;
    }
    public Event getFormEvent(){

        return this.formEvent;

    }

    public boolean isCanBack() {
        return true;
    }

    public void onPermissionResoult(int requestCode, String[] permissions, int[] grantResults) {




    }


    public static enum FormType{

        FORM_TOP,
        FORM_ONLY,

    }



    public FormType getFormtype() {
        return formtype;
    }

    public void setFormtype(FormType formtype) {
        this.formtype = formtype;
    }

    FormType formtype = FormType.FORM_TOP;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setAllowEnterTransitionOverlap(true);
        setAllowReturnTransitionOverlap(true);
    }




    public int getAnimEnter(boolean fromback){

        if( fromback )
            return R.anim.fade_out;
        else
            return R.anim.slide_in_bottom;

    }
    public int getAnimExit(){

        if( formtype == FormType.FORM_ONLY )
            return R.anim.fade_out;
        else
            return R.anim.slide_out_bottom;

    }


    public void onClick(View view){



    }

    int color_status = RP.CK.K_NORMAL_COLOR;// ActController.instance.getResources().getColor(R.color.navbar);

    public void setStatusColor(int color){
        color_status = color;
    }
    public int getStatusColor(){
        return color_status;
    }

    @Override
    public void onResume() {
        super.onResume();

        RP.Statusbar.setStatusbarColor(getStatusColor());


        if( isAutoRotate() )
            ActController.instance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        else
            ActController.instance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ((IController)getActivity()).onFormAppear(this);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        onDestroy(false);

    }
    public void onDestroy(boolean fromuser)
    {

        Activity act = getActivity();
        if(act != null )
            ((IController)getActivity()).onFormDestory(this);

    }

    @Override
    public void onDestroyView() {

        Activity act = getActivity();
        if(act != null )
            ((IController)getActivity()).onFormDisappear(this);
        super.onDestroyView();
    }

    public void sendMessage(Event event, Object value)
    {
        MessageCenter.sendMessage(event,value);
    }
    public void sendMessage(Event event)
    {
        MessageCenter.sendMessage(event,null);
    }

    public boolean onMessage(Event event,Object value){


        return false;
    }

    public String toString(){

        return String.format("class :%s isVisible:%b formtype:%s",getClass().toString(),isVisible(),formtype.toString() );

    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {

            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");

            childFragmentManager.setAccessible(true);

            childFragmentManager.set(this, null);



        } catch (Exception e) {
            NLog.e(e);
        }

    }


    public boolean isScreen(){

        return false;
    }


    public void onPush(boolean fromback){



    }
    public void onPop(){

    }

    @Override
    public void onStop() {
        super.onStop();
        hideSoftInput();
    }

    boolean needLogin = true;
    public boolean isNeedLoginOnInBack(){

        return needLogin;
    };

    public void setNeedLoginOnInBack(boolean v){

        needLogin = v;

    }

    @Override
    public Context getContext() {
        Context context = super.getContext();
        if( context == null )
            return ActController.instance;
        else
            return context;
    }
    public String getContextString(int rid){

        return getContext().getResources().getString(rid);
    }


    public View ViewInject(View parent,int id){
        return  parent.findViewById(id);
    }
    public View ViewInject(int id){

        return mView.findViewById(id);
    }


    public boolean isSecurityForm(){

        return false;
    }


    public void hideSoftInput(){

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if( imm != null && imm.isActive() ) {
            Activity activity = getActivity();
            if( activity != null )
                imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    public void onActivityResultForm(int requestCode, int resultCode, Intent data){

    }

    public void showSoftInput(View view){


        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        // InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
    }

    public void click(String tag){


    }
    public void click(String tag,String v){



    }

    public void mainThreadExecuteDelay(Runnable runnable,long time){

        new Handler(Looper.getMainLooper()).postDelayed(runnable,time);

    }


    public boolean isAutoRotate(){
        return false;
    }


    public Object getRootParentTag(int parentid,View view){


        if( view.getId() == parentid )
            return view.getTag();
        View parent = (View)view.getParent();
        while(parent != null){

            if( parent.getId() == parentid )
                return parent.getTag();
            parent = (View) parent.getParent();

        }
        return null;

    }
    public void log(String format,Object ...args){

        NLog.log(getClass().getSimpleName(),format,args);
    }

}
