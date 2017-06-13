package com.dym.alarm.flows;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

import java.util.List;

/**
 * Created by dizhanbin on 16/10/29.
 <property name="permissioin" title="权限" type="0" args="" value=""/>
 */

public class PermAlert extends FlowPoint {


    final String key_permissioin = "permissioin";

    @Override
    public void run(FlowBox flowBox) throws Exception {



        Object obj = flowBox.getValue(params.get(key_permissioin));

        if( obj instanceof  String ) {

            String permission = flowBox.getVarString(params.get(key_permissioin));
            String[] parray = permission.split(",");
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) flowBox.getContext(),
                    parray[0])) {
                ActivityCompat.requestPermissions((Activity) flowBox.getContext(),
                        parray,
                        100
                );

            }
        }
        else if( obj instanceof List)
        {


            List ps = (List) obj;

            String[] parray = new String[ps.size()];

            for(int i=0;i<ps.size();i++)
            {
                parray[i] = ps.get(i).toString();
            }

            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) flowBox.getContext(),
                    parray[0])) {
                ActivityCompat.requestPermissions((Activity) flowBox.getContext(),
                        parray,
                        100
                );
            }

        }

        flowBox.notifyFlowContinue();
    }
}
