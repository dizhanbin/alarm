package com.dym.alarm.flows;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.util.List;

/**
 * Created by 01 on 2016/9/28.
 * <p>
 * <property name="data" title="数组" value="#uploadfiles"/>
 * <property name="obj" title="对象" value="$file"/>
 */
public class ArrayDel extends FlowPoint {
    @Override
    public void run(FlowBox flowBox) throws Exception {
        List data = (List) flowBox.getValue(params.get("data"));
        Object obj = flowBox.getValue(params.get("obj"));

        if (data == null) {

            flowBox.notifyFlowContinue();
            return;

        }

        synchronized (data) {


            if (obj == null) {
                flowBox.notifyFlowContinue();
                return;
            } else if (obj instanceof List) {
                data.removeAll((List) obj);
            } else {
                data.remove(obj);

            }

        }
        flowBox.notifyFlowContinue();


    }
}
