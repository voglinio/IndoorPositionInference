package com.nodalpoint.indoorposition.model;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import lombok.Getter;

@Getter
public class MagneticField implements CalibratedSensor{

    private Sensor magneticField;
    private float magneticFieldAxisX;
    private float magneticFieldAxisY;
    private float magneticFieldAxisZ;
    private int magneticFieldAccuracy;

    public MagneticField(Sensor magneticField) {
        this.magneticField = magneticField;
    }

    @Override
    public float getAxisX() {
        return getMagneticFieldAxisX();
    }

    @Override
    public float getAxisY() {
        return getMagneticFieldAxisY();
    }

    @Override
    public float getAxisZ() {
        return getMagneticFieldAxisZ();
    }

    @Override
    public float getAccuracy() {
        return getMagneticFieldAccuracy();
    }

    @Override
    public void updateAxisValues(float[] values) {
        magneticFieldAxisX = values[0];
        magneticFieldAxisY = values[1];
        magneticFieldAxisZ = values[2];
    }

    @Override
    public void updateAccuracy(int accuracy) {
        magneticFieldAccuracy = accuracy;
    }

    @Override
    public SensorEventListener createSensorListener() {
        SensorEventListener magneticFieldSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                updateAxisValues(sensorEvent.values);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                updateAccuracy(i);
            }
        };
        return magneticFieldSensorListener;
    }
}
