package com.dym.alarm.datacenter;

import com.dym.alarm.common.Event;
import com.dym.alarm.common.MessageCenter;
import com.dym.alarm.common.NLog;

/**
 * Created by dizhanbin on 17/5/20.
 */

public class DataRequest {



    public static void request(Event req_event,Event rep_event,Object params){



        Request request_imp = null;
        switch (req_event)
        {

            case REQ_ALARM_LIST:
                request_imp = new ReqAlarmList();
                break;
            case REQ_ALARM_SAVE:

                request_imp = new ReqAlarmSave();
                break;



        }

        if( request_imp != null )
            execute(request_imp,params,rep_event);


    }



    public static void execute(final Request request, final Object params, final Event rep_event){

        NLog.i("DataRequest execute:%s",request);
        new Thread(){
            public void run(){

                request.run(params, new ResponseListener() {
                    @Override
                    public void callback(int type, Object value) {
                        if( type == RE_OK  )
                        {
                            MessageCenter.sendMessage(rep_event,value);
                        }

                    }
                });

            }
        }.start();

    }


}
