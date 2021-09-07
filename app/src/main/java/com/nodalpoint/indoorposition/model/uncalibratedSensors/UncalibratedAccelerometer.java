package com.nodalpoint.indoorposition.model.uncalibratedSensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import lombok.Getter;

@Getter
public class UncalibratedAccelerometer implements UncalibratedSensor{

    private final Sensor uncalibratedAccelerometer;
    private float uncalibratedAccelerometerAxisX;
    private float uncalibratedAccelerometerAxisY;
    private float uncalibratedAccelerometerAxisZ;
    private float uncalibratedAccelerometerBiasX;
    private float uncalibratedAccelerometerBiasY;
    private float uncalibratedAccelerometerBiasZ;
    private int uncalibratedAccelerometerAccuracy;

    public UncalibratedAccelerometer(Sensor uncalibratedAccelerometer) {
        this.uncalibratedAccelerometer = uncalibratedAccelerometer;
    }

    @Override
    public float getAxisX() {
        return getUncalibratedAccelerometerAxisX();
    }

    @Override
    public float getAxisY() {
        return getUncalibratedAccelerometerAxisY();
    }

    @Override
    public float getAxisZ() {
        return getUncalibratedAccelerometerAxisZ();
    }

    @Override
    public float getBiasX() {
        return getUncalibratedAccelerometerBiasX();
    }

    @Override
    public float getBiasY() {
        return getUncalibratedAccelerometerBiasY();
    }

    @Override
    public float getBiasZ() {
        return getUncalibratedAccelerometerBiasZ();
    }

    @Override
    public float getAccuracy() {
        return getUncalibratedAccelerometerAccuracy();
    }

    @Override
    public void updateAxisValues(float[] values) {
        uncalibratedAccelerometerAxisX = values[0];
        uncalibratedAccelerometerAxisY = values[1];
        uncalibratedAccelerometerAxisZ = values[2];
        uncalibratedAccelerometerBiasX = values[3];
        uncalibratedAccelerometerBiasY = values[4];
        uncalibratedAccelerometerBiasZ = values[5];
    }

    @Override
    public void updateAccuracy(int accuracy) {
        uncalibratedAccelerometerAccuracy = accuracy;
    }

    @Override
    public SensorEventListener createSensorListener() {
        SensorEventListener uncalibratedAccelerometerSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                updateAxisValues(sensorEvent.values);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                updateAccuracy(i);
            }
        };
        return uncalibratedAccelerometerSensorListener;
    }
}
