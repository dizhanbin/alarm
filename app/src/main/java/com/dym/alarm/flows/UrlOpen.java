package com.dym.alarm.flows;

import android.content.Intent;
import android.net.Uri;

import com.dym.alarm.common.FlowBox;
import com.dym.alarm.common.FlowPoint;

/**
 * Created by dzb on 16/9/20.
 * <property name="url" title="地址" value="$downloadLink"/>
 <property name="descript" title="描述" value="打开浏览器"/>
 */
public class UrlOpen extends FlowPoint {
    @Override
    public void run(FlowBox flowBox) throws Exception {


        String url = flowBox.getVarString(params.get("url"));


        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        flowBox.getContext().startActivity(intent);

    }
}
