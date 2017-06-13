package com.dym.alarm.flows;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

/**
 * Created by Administrator on 2016/9/20.
 * <p/>
 * <p/>
 * <element
 * id="032" name="清空数据" iconpath=":/image/114.png"  descript="清空数据 ： 全局 或 box	内数据  " c_android="com.northking.bpo.flows.SetNull"	 c_ios="SetNull">
 * <property name="keys" title="数据变量(,相隔)" type="0"  args="" value="" />
 * <property name="descript" title="描述" type="0" args="" value="清空数据" />
 * <p/>
 * <p/>
 * </element>
 */
public class SetNull extends FlowPoint {
    @Override
    public void run(FlowBox flowBox) throws Exception {
        String keys = params.get("keys");

        String[] ks = keys.split(",");
        for (int i = 0; i < ks.length; i++) {
            flowBox.remove(ks[i]);
        }

        flowBox.notifyFlowContinue();
    }
}
