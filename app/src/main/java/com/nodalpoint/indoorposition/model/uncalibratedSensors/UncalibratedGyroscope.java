package com.nodalpoint.indoorposition.model.uncalibratedSensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import lombok.Getter;

@Getter
public class UncalibratedGyroscope implements UncalibratedSensor{

    private final Sensor uncalibratedGyroscope;
    private float uncalibratedGyroscopeAxisX;
    private float uncalibratedGyroscopeAxisY;
    private float uncalibratedGyroscopeAxisZ;
    private float uncalibratedGyroscopeBiasX;
    private float uncalibratedGyroscopeBiasY;
    private float uncalibratedGyroscopeBiasZ;
    private int uncalibratedGyroscopeAccuracy;

    public UncalibratedGyroscope(Sensor uncalibratedGyroscope) {
        this.uncalibratedGyroscope = uncalibratedGyroscope;
    }


    @Override
    public float getAxisX() {
        return getUncalibratedGyroscopeAxisX();
    }

    @Override
    public float getAxisY() {
        return getUncalibratedGyroscopeAxisY();
    }

    @Override
    public float getAxisZ() {
        return getUncalibratedGyroscopeAxisZ();
    }

    @Override
    public float getBiasX() {
        return getUncalibratedGyroscopeBiasX();
    }

    @Override
    public float getBiasY() {
        return getUncalibratedGyroscopeBiasY();
    }

    @Override
    public float getBiasZ() {
        return getUncalibratedGyroscopeBiasZ();
    }

    @Override
    public float getAccuracy() {
        return getUncalibratedGyroscopeAccuracy();
    }

    @Override
    public void updateAxisValues(float[] values) {
        uncalibratedGyroscopeAxisX = values[0];
        uncalibratedGyroscopeAxisY = values[1];
        uncalibratedGyroscopeAxisZ = values[2];
        uncalibratedGyroscopeBiasX = values[3];
        uncalibratedGyroscopeBiasY = values[4];
        uncalibratedGyroscopeBiasZ = values[5];
    }

    @Override
    public void updateAccuracy(int accuracy) {
         uncalibratedGyroscopeAccuracy = accuracy;
    }

    @Override
    public SensorEventListener createSensorListener() {
        SensorEventListener uncalibratedGyroscopeSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                updateAxisValues(sensorEvent.values);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                updateAccuracy(i);
            }
        };
        return uncalibratedGyroscopeSensorListener;
    }
}
