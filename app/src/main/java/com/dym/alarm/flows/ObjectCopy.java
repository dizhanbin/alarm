package com.dym.alarm.flows;


import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dzb on 16/7/7.
 *
 *  <property key="from"  value="$http_result"/>
    <property key="to"  value="$json_result"/>
    <property key="class"  value="com.my17.model."/>
    <property key="conditon"  value="a=b,c=d"/>



 *
 *
 */
public class ObjectCopy extends FlowPoint {

    final static String key_from = "from";
    final static String key_to = "to";
    final static String key_class = "class";
    final static String key_conditon = "condition";

    @Override
    public void run(FlowBox flowBox) throws Exception {

        Object obj = flowBox.getValue( params.get(key_from) );



        String classname = params.get(key_class);
        flowBox.log("JSONParse class:%s ",classname);


        if( classname.indexOf('.')<0 )
            classname ="com.my17.client.models."+classname;

        Class classz = Class.forName( classname );

        Field[] fields = classz.getDeclaredFields();

        if( obj instanceof Collection )
        {

            List result = new ArrayList();

            Collection cols = (Collection) obj;
            for(Object object : cols )
            {
                Object v = classz.newInstance();
                copy(fields,object,v,params.get(key_conditon));
                result.add(v);

            }
            flowBox.setValue(params.get(key_to),result);

        }
        flowBox.notifyFlowContinue();



    }


    Field findFieldByName(Field[] fields,String name){

        for(Field f : fields)
        {
            if( f.getName().equals(name) )
                return f;

        }
        return null;

    };

    void copy(Field[] to_fields,Object from,Object to,String conditions) throws IllegalAccessException {

        String[] cs = conditions.split(",");
        Field[]  from_fields = from.getClass().getDeclaredFields();

        for(String k:cs)
        {
            int index = k.indexOf('=');
            String f_f = k.substring(0,index);
            String f_t = k.substring(index+1);

            Field ff = findFieldByName(from_fields,f_f);
            Field ft = findFieldByName(to_fields,f_t);

            ff.setAccessible(true);
            ft.setAccessible(true);

            if( ff != null && ft != null )
            {
                ft.set(to,ff.get(from));
            }

        }


    };
}
