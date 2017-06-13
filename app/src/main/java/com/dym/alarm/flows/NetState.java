package com.dym.alarm.flows;

import com.dym.alarm.RP;
import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

/**
 * Created by dizhanbin on 16/11/15.

 <property name="state" title="是否可用" type="0" args="" value=""/>
 <property name="descript" title="描述" type="0" args="" value="网络状态"/>
 */

public class NetState extends FlowPoint {




    final String key_state = "state";



    @Override
    public void run(FlowBox flowBox) throws Exception {


        int result = 0;
        if( !RP.NetState.isValide() )
            result = 0;
        else if( RP.NetState.isWifi() )
            result = 2;
        else if( RP.NetState.isGsm() )
            result = 1;
        flowBox.setValue(params.get(key_state),result);
        flowBox.notifyFlowContinue();

    }
}
