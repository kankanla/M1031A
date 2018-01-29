package com.kankanla.m1031a;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;

import java.util.List;

/**
 * Created by kankanla on 2018/01/15.
 * 練習のテスト項目
 */

public class TempSensors implements SensorEventListener {
    protected Context context;
    private SensorManager mSensorManager;
    private Sensor mPressure;

    public TempSensors(Context context) {
        this.context = context;
    }


    public void getSen() {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sensors = mSensorManager.getDynamicSensorList(Sensor.TYPE_ALL);
        } else {
            sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        }
        for (Sensor x : sensors) {
            System.out.println(x.getName());
        }
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            System.out.println(mPressure.isWakeUpSensor());
            System.out.println("LOLLIPOP");
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
