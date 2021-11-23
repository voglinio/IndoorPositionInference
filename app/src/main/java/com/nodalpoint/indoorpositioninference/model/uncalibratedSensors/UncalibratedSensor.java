package com.nodalpoint.indoorpositioninference.model.uncalibratedSensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import lombok.Getter;

@Getter
public abstract class UncalibratedSensor {

    private final Sensor sensor;
    private float axisX;
    private float axisY;
    private float axisZ;
    private float biasX;
    private float biasY;
    private float biasZ;
    private int accuracy;
    private String type;

    public UncalibratedSensor(Sensor sensor) {
        this.sensor = sensor;
    }


    public void updateAxisValues(float[] values) {
        axisX = values[0];
        axisY = values[1];
        axisZ = values[2];
        biasX = values[3];
        biasY = values[4];
        biasZ = values[5];
    }


    public void updateAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public SensorEventListener createSensorListener() {
        SensorEventListener sensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                updateAxisValues(sensorEvent.values);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                updateAccuracy(i);
            }
        };
        return sensorListener;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Sensor getSensor() { return this.sensor; }
    abstract String getSensorType();
}
