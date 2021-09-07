package com.nodalpoint.indoorposition.model;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import lombok.Getter;

@Getter
public class Gyroscope implements CalibratedSensor{
    private Sensor gyroscope;
    private float gyroscopeAxisX;
    private float gyroscopeAxisY;
    private float gyroscopeAxisZ;
    private int gyroscopeAccuracy;


    public Gyroscope(Sensor gyroscope) {
        this.gyroscope = gyroscope;
    }

    @Override
    public float getAxisX() {
        return getGyroscopeAxisX();
    }

    @Override
    public float getAxisY() {
        return getGyroscopeAxisY();
    }

    @Override
    public float getAxisZ() {
        return getGyroscopeAxisZ();
    }

    @Override
    public float getAccuracy() {
        return getGyroscopeAccuracy();
    }

    @Override
    public void updateAxisValues(float[] values) {
        gyroscopeAxisX = values[0];
        gyroscopeAxisY = values[1];
        gyroscopeAxisZ = values[2];
    }

    @Override
    public void updateAccuracy(int accuracy) {
        this.gyroscopeAccuracy = accuracy;
    }

    @Override
    public SensorEventListener createSensorListener() {
        SensorEventListener gyroscopeSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                updateAxisValues(sensorEvent.values);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                updateAccuracy(i);
            }
        };
        return gyroscopeSensorListener;
    }
}
