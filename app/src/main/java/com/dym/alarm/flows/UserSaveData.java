package com.dym.alarm.flows;

import android.content.SharedPreferences;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;
import com.dym.alarm.common.NLog;

/**
 * Created by dzb on 16/9/8.
 *
 *
 * 可 ， 相隔
 <property name="key" title="key" type="0"  args="" value="" />
 <property name="value" title="数据" type="0"  args="" value="" />
 <property name="descript" title="描述" type="0" args="" value="保存本地数据" />
 *
 */
public class UserSaveData extends FlowPoint {


    final static String key_key = "key";
    final static String key_value = "value";

    @Override
    public void run(FlowBox flowBox) throws Exception {

        String[] keys = this.params.get(key_key).split(",");
        String[] values = this.params.get(key_value).split(",");

        SharedPreferences sp = UserData.getPreference(flowBox.getContext());

        SharedPreferences.Editor editor = sp.edit();
        for(int i=0;i<keys.length;i++)
        {
            if( "newalarm".equals(keys[i]) )
            {
                NLog.i("save newalarm");
                int count = Integer.parseInt( sp.getString("newalarm","0") );
                count++;
                editor.putString(keys[i],String.valueOf(count) );
            }
            else
                editor.putString( keys[i], flowBox.getVarString(  values[i] ) );
        }
        editor.commit();

        flowBox.notifyFlowContinue();
    }
}
