package com.nodalpoint.indoorposition.model.uncalibratedSensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import lombok.Getter;

@Getter
public class UncalibratedMagneticField implements UncalibratedSensor{
    private final Sensor uncalibratedMagneticField;
    private float uncalibratedMagneticFieldAxisX;
    private float uncalibratedMagneticFieldAxisY;
    private float uncalibratedMagneticFieldAxisZ;
    private float uncalibratedMagneticFieldBiasX;
    private float uncalibratedMagneticFieldBiasY;
    private float uncalibratedMagneticFieldBiasZ;
    private int uncalibratedMagneticFieldAccuracy;

    public UncalibratedMagneticField(Sensor uncalibratedMagneticField) {
        this.uncalibratedMagneticField = uncalibratedMagneticField;
    }

    @Override
    public float getAxisX() {
        return getUncalibratedMagneticFieldAxisX();
    }

    @Override
    public float getAxisY() {
        return getUncalibratedMagneticFieldAxisY();
    }

    @Override
    public float getAxisZ() {
        return getUncalibratedMagneticFieldAxisZ();
    }

    @Override
    public float getBiasX() {
        return getUncalibratedMagneticFieldBiasX();
    }

    @Override
    public float getBiasY() {
        return getUncalibratedMagneticFieldBiasY();
    }

    @Override
    public float getBiasZ() {
        return getUncalibratedMagneticFieldBiasZ();
    }

    @Override
    public float getAccuracy() {
        return getUncalibratedMagneticFieldAccuracy();
    }

    @Override
    public void updateAxisValues(float[] values) {
        uncalibratedMagneticFieldAxisX = values[0];
        uncalibratedMagneticFieldAxisY = values[1];
        uncalibratedMagneticFieldAxisZ = values[2];
        uncalibratedMagneticFieldBiasX = values[3];
        uncalibratedMagneticFieldBiasY = values[4];
        uncalibratedMagneticFieldBiasZ = values[5];
    }

    @Override
    public void updateAccuracy(int accuracy) {
        uncalibratedMagneticFieldAccuracy = accuracy;
    }

    @Override
    public SensorEventListener createSensorListener() {
        SensorEventListener uncalibratedMagneticFieldSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                updateAxisValues(sensorEvent.values);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                updateAccuracy(i);
            }
        };
        return uncalibratedMagneticFieldSensorListener;
    }
}
