package com.nodalpoint.indoorposition.model;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import lombok.Getter;

@Getter
public class Accelerometer implements CalibratedSensor{

    private Sensor accelerometer;
    private float accelerometerAxisX;
    private float accelerometerAxisY;
    private float accelerometerAxisZ;
    private int accelerometerAccuracy;


    public Accelerometer(Sensor accelerometer) {
        this.accelerometer = accelerometer;
    }

    @Override
    public float getAxisX() {
        return getAccelerometerAxisX();
    }

    @Override
    public float getAxisY() {
        return getAccelerometerAxisY();
    }

    @Override
    public float getAxisZ() {
        return getAccelerometerAxisZ();
    }

    @Override
    public float getAccuracy() {
        return getAccelerometerAccuracy();
    }

    @Override
    public void updateAxisValues(float[] values) {
        accelerometerAxisX = values[0];
        accelerometerAxisY = values[1];
        accelerometerAxisZ = values[2];
    }

    @Override
    public void updateAccuracy(int accuracy) {
        accelerometerAccuracy = accuracy;
    }

    @Override
    public SensorEventListener createSensorListener() {
        SensorEventListener accelerometerSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                updateAxisValues(sensorEvent.values);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                updateAccuracy(i);
            }
        };
        return accelerometerSensorListener;
    }
}
