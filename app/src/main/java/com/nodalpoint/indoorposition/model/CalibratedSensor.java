package com.nodalpoint.indoorposition.model;

import android.hardware.SensorEventListener;

public interface CalibratedSensor {

    float getAxisX();
    float getAxisY();
    float getAxisZ();
    float getAccuracy();
    void updateAxisValues(float[] values);
    void updateAccuracy(int accuracy);
    SensorEventListener createSensorListener();
}
