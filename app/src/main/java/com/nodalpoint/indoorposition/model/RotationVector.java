package com.nodalpoint.indoorposition.model;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RotationVector implements CalibratedSensor{
    private Sensor rotationVector;
    private float rotationVectorAxisX;
    private float rotationVectorAxisY;
    private float rotationVectorAxisZ;
    private int rotationVectorAccuracy;


    public RotationVector(Sensor rotationVector) {
        this.rotationVector = rotationVector;
    }

    @Override
    public float getAxisX() {
        return getRotationVectorAxisX();
    }

    @Override
    public float getAxisY() {
        return getRotationVectorAxisY();
    }

    @Override
    public float getAxisZ() {
        return getRotationVectorAxisZ();
    }

    @Override
    public float getAccuracy() {
        return getRotationVectorAccuracy();
    }

    public void updateAxisValues(float[] values) {
        rotationVectorAxisX = values[0];
        rotationVectorAxisY = values[1];
        rotationVectorAxisZ = values[2];
    }

    public void updateAccuracy(int accuracy) {
        this.rotationVectorAccuracy = accuracy;
    }

    public SensorEventListener createSensorListener() {
        SensorEventListener rotationVectorSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                updateAxisValues(sensorEvent.values);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                updateAccuracy(i);
            }
        };
        return rotationVectorSensorListener;
    }
}
