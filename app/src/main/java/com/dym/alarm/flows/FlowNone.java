package com.dym.alarm.flows;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

/**
 * Created by dizhanbin on 16/10/17.
 */

public class FlowNone extends FlowPoint {
    @Override
    public void run(FlowBox flowBox) throws Exception {



        flowBox.notifyFlowContinue();
    }
}
