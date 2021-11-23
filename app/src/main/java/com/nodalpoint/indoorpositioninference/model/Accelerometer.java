package com.nodalpoint.indoorpositioninference.model;

import android.hardware.Sensor;

public class Accelerometer extends CalibratedSensor{

    private static final String TYPE_ACCELEROMETER = "TYPE_ACCELEROMETER";

    public Accelerometer(Sensor sensor) {
        super(sensor);
        this.setType(TYPE_ACCELEROMETER);
    }

    @Override
    public String getSensorType() {
        return TYPE_ACCELEROMETER;
    }
}
