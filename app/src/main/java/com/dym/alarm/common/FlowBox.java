package com.dym.alarm.common;

import android.content.Context;


import com.dym.alarm.BuildConfig;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;



public class FlowBox {

    static {

        global_values = new HashMap<String, Object>();
        //parseProjectinfos();
    }

    private boolean isStaticv;//是否常驻内存



    static  Map<Event,FlowBox> global_boxs = new HashMap<>();

    static Map<String, Object> global_values;




    Map<String, Object> static_values = new HashMap<>();

    Map<String, FlowPoint> points = new HashMap<String, FlowPoint>();


    private Event eventValue;
    private FlowPoint point_root;
    private FlowPoint point_current;
    private Context context;

    private boolean debug = true;


    private String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }






    public static FlowBox getBox(Event event)
    {


        synchronized (global_boxs)
        {
            if( global_boxs.containsKey(event) )
            {

                return global_boxs.get(event);

            }
            return null;

        }

    }

    public FlowBox(Context context) {

        this.context = context;

    }

    public Context getContext() {

        return this.context;

    }

    public void setRoot(FlowPoint r) {

        this.point_root = r;

    }

    public void setDebug(boolean f) {

        debug = f;
    }


    public void run() {

        run(null);

    }

    public Object getFlowParam() {


        return value;

    }

    Object value;
    long timestart;


    public void execute(final Object value)
    {


        if( isFinished ) {

            isFinished = false;
            this.value = value;
            point_current = point_root;
            try {
                point_current.run(FlowBox.this);
            } catch (Exception e) {
                error();
            }

        }
        else
            log("flow is running not execute again");


    }

    public FlowPoint getStaticPoint(){

       return  point_root.getStaticPoint(this);

    }

    public void run(final Object value) {
        this.value = value;
        point_current = point_root;



        if( isStatic() )
        {

            synchronized (global_boxs)
            {

                if( !global_boxs.containsKey(eventValue) )
                {
                    global_boxs.put(eventValue,this);

                }

            }
        }




        timestart = System.currentTimeMillis();
        new Thread(null, null, "flowbox", 1024 * 1024 * 2) {
            public void run() {

                try {
                    point_current.run(FlowBox.this);
                } catch (Exception e) {
                    //e.printStackTrace();
                   NLog.e(e);
                    error();

                }

            }
        }.start();


    }

    int flow_index;

    public void notifyFlowContinue()  {


        if (point_current == null || point_current.isEnd()) {

            onFinish();

        } else {

            point_current = point_current.getChild(FlowBox.this);



            if (point_current == null)
                onFinish();
            else {

                try {
                    if( BuildConfig.DEBUG )
                        log("point[%d] %s :%s", flow_index++,point_current.getDescript(), point_current.getClass().getSimpleName());
                    point_current.run(this);
                }catch (Exception e){
                    NLog.e(e);
                    error();
                }
            }

        }

    }

    public void log(String str, Object... args) {

        NLog.i(title + "->" + str, args);

    }


    boolean isFinished = false;

    public void onFinish() {

        log("flow is over. duration:%d",(System.currentTimeMillis()-timestart));

        isFinished = true;


        flow_index = 0;
        synchronized (static_values) {
            static_values.clear();
        }

        synchronized (global_boxs) {
            if (global_boxs.containsKey(eventValue)) {
                global_boxs.remove(eventValue);
            }
        }

    }


    public void addPoint(FlowPoint flowpoint) {

        points.put(flowpoint.getId(), flowpoint);

    }

    public FlowPoint getPoint(String fromid) {
        return points.get(fromid);
    }

    public void setEvent(String event) {

        this.eventValue = Event.valueOf(event);
    }


    public void setValue(String s, Object s1) {

        if (s == null || s.length() == 0)
            return;

        if (s.charAt(0) == '$') {
            synchronized (static_values){
                static_values.put(s, s1);
            }
        }
        else if (s.charAt(0) == '#')
            synchronized (global_values) {
                global_values.put(s, s1);
            }
    }

    public Object getValue(String k) {

        if( k == null || k.length() == 0 )
            return k;
        if (k.charAt(0) == '$')
            return static_values.get(k);
        else if (k.charAt(0) == '#')
            return global_values.get(k);
        return k;

    }


    public void remove(String key) {


        if (key == null || key.length() == 0) {
            return;
        }
        if (key.charAt(0) == '$') {
            synchronized (static_values) {
                static_values.remove(key);
            }
        } else if (key.charAt(0) == '#') {
            synchronized (global_values) {
                global_values.remove(key);
            }
        }


    }

    public String getVarString(String korv) {

        if (isVar(korv)) {
            Object r = getValue(korv);
            if (r == null)
                return null;
            else
                return r.toString();
        } else
            return korv;

    }

    public static  boolean isContainGlobal(String k){
        return global_values.containsKey(k);
    }

    public static Object getGlobalValue(String k) {

        return global_values.get(k);

    }

    public static boolean isOK(String k,Object v){

        return ( global_values.containsKey(k) && v != null && v.equals(global_values.get(k)));

    }

    public static void clearGlobalValues(){

        try {
            NLog.i("global clearGlobalValues");
            for (String k : global_values.keySet()) {
                NLog.i("global value-> key:%s v:%s", k, global_values.get(k));
            }
        }catch (Exception e){}

        global_values.clear();
    }


    public void error() {

        MessageCenter.sendMessage(Event.REQ_WAITTING_HIDE);
        log(">>flow exec error.check flow log...");
        onFinish();

    }


    public boolean isVar(String str) {
        return str != null && str.length() > 0 && (str.charAt(0) == '$' || str.charAt(0) == '#');
    }

    public void setStatic(boolean b) {

        isStaticv = b;

    }

    public  boolean isStatic(){
        return  isStaticv;
    }

    public static void setGlobalValue(String key, Object value) {

        if( key != null )
        if (key.charAt(0) == '#') {
            synchronized (global_values) {
                global_values.put(key, value);
            }
        }

    }
    public static void removeGlobal(String key){
        if( key != null )
            synchronized (global_values){

                global_values.remove(key);
            }
    }

    public final static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^[0-9]*$");
        else
            return false;
    }
}
