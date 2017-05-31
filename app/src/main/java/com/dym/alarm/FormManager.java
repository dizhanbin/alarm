package com.dym.alarm;

import android.content.Context;


import com.dym.alarm.common.Event;

import java.util.List;
import java.util.Vector;

/**
 * Created by dzb on 16/5/18.
 */
public class FormManager {


    //public Map<String,FormData> forms = new HashMap<String,FormData>();


    public Vector<Form> stack= new Vector<Form>();





    //public FormData first;

    public FormManager(final Context context){


        /*
        new Thread(){

            public void run(){

                try {
                    load(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
        */

    }

    /*
    private void load(final Context context) throws Exception {



        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();

        parser.setInput(context.getResources().getAssets().open("flows/forms.xml"),"utf-8");

        int eventType ;
        String tagname;

        while( (eventType = parser.getEventType()) != XmlPullParser.END_DOCUMENT )
        {

            tagname = parser.getName();
            switch (eventType)
            {
                case XmlPullParser.START_TAG:
                {

                    switch (tagname)
                    {
                        case "form":
                        {
                            FormData fd = new FormData();
                            fd.event = parser.getAttributeValue(null,"event");
                            fd.classname = parser.getAttributeValue(null,"class");
                            fd.main = parser.getAttributeValue(null,"type")!=null;
                            fd.group = parser.getAttributeValue(null,"group");
                            if( fd.group == null )
                                fd.group = fd.event;

                            forms.put(fd.event,fd);
                            if( fd.main ) {
                                first = fd;

                                Event e = Event.valueOf(first.event);
                                MessageCenter.sendMessage(e,null);

                            }
                            break;
                        }


                    }

                }
                break;

            }
            parser.next();
        }


    }
*/

    public void onFormAppear(Form form){


        boolean fromback = stack.contains(form);
        stack.remove(form);
        stack.add(form);
        //form.setFormtype(Form.FormType.FORM_TOP);
        form.onPush(fromback);





    }
    public void onFormDisapear(Form form){

        if( form.getFormtype() != Form.FormType.FORM_ONLY )
        {
            stack.remove(form);
            form.onPop();
        }

    }
    public Form getLastForm(){

        if( stack.size()>1 )
            return stack.get(stack.size()-2);
        return  null;

    }
    public Form getCurrentForm()
    {
        if( stack.size() > 0 )
            return stack.get(stack.size()-1);
        return null;

    }


    public List<Form> getStack() {
        return stack;
    }

    public void onMessage(Event event, Object value) {



        if( stack.size() == 0 ) return;

        Form form = getCurrentForm();

        boolean result = form.onMessage(event,value);

        if( !result )
        for(int i=stack.size()-1;i>-1;i--){

            Form wf = stack.get(i);
            if( wf == form ) continue;
            result = wf.onMessage(event,value);
            if( result ){
                return;
            }


        }

    }

    public Form getForm(Class  classz) {


            for(int i=0;i<stack.size();i++)
            {
                if( stack.get(i).getClass() == classz )
                {
                    return stack.get(i);
                }
            }
            return null;

    }


    public  Form pushto(Class classz){


        Form form = getForm(classz);

        if( form == null )
            return null;
        int index = stack.indexOf(form);


        if( index >= stack.size()-2)
            return form;
        for(int i=stack.size()-2;i>index;i--)
        {
            Form form_i = stack.remove(i);
           // form_i.onPop();
            form_i.onDestroy(true);

        }
        return form;


    }


    public void clearForms(){


        synchronized (stack) {
            Form currentForm = getCurrentForm();

            stack.remove(currentForm);


            stack.clear();

            stack.add(currentForm);

        }


    }
}
