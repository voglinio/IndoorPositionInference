package com.nodalpoint.indoorposition.model.uncalibratedSensors;

import android.hardware.SensorEventListener;

public interface UncalibratedSensor {

    float getAxisX();
    float getAxisY();
    float getAxisZ();
    float getBiasX();
    float getBiasY();
    float getBiasZ();
    float getAccuracy();
    void updateAxisValues(float[] values);
    void updateAccuracy(int accuracy);
    SensorEventListener createSensorListener();
}
