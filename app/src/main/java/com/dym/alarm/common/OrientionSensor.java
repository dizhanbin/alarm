package com.dym.alarm.common;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Sean on 2016/11/22.
 */

public  abstract class OrientionSensor implements SensorEventListener {


    private SensorManager sensorManager;
    Sensor sensor_orientation;
    public OrientionSensor(Context context){

        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        sensor_orientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(this,sensor_orientation, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void release(){

        if( sensorManager != null ) {
            sensorManager.unregisterListener(this);
            this.sensorManager = null;
            sensor_orientation = null;

        }

    }

    public void pause(){

    }
    public void resume(){

    }


    int count;
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (sensorManager != null  && count ++ > 5 && event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
//                float x = event.values[SensorManager.DATA_X];
            float y = event.values[SensorManager.DATA_Y];
//                float z = event.values[SensorManager.DATA_Z];
            //屏幕翻转时触发
            if ((y < 180 && y > 150) || (y > -180 && y < -150)) {
                onSensor();
                count = 0 ;
            }
        }
//            else{
//                float x = event.values[SensorManager.DATA_X];
//                float y = event.values[SensorManager.DATA_Y];
//                float z = event.values[SensorManager.DATA_Z];
//            }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    public abstract void onSensor();

}



