package com.dym.alarm.flows;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

/**
 * Created by dizhanbin on 16/10/27.
 <property name="uuid" title="赋值给" type="0"  args="" value="$uuid" />

 <property name="descript" title="描述" type="0" args="" value="生成UUID" />
 */

public class UUIDCreate extends FlowPoint {

    static final String key_uuid = "uuid";


    @Override
    public void run(FlowBox flowBox) throws Exception {

        String uuid = newUUID();
        flowBox.setValue(params.get(key_uuid),uuid);
        flowBox.log("uuid:%s",uuid);
        flowBox.notifyFlowContinue();

    }
    public static String newUUID(){

        return java.util.UUID.randomUUID().toString().replaceAll("-","");
    }
}
