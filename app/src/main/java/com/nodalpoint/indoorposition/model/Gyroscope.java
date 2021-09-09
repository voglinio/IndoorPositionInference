package com.nodalpoint.indoorposition.model;

import android.hardware.Sensor;

public class Gyroscope extends CalibratedSensor{

    private static final String TYPE_GYROSCOPE = "TYPE_GYROSCOPE";

    public Gyroscope(Sensor gyroscopeSensor) {
        super(gyroscopeSensor);
        this.setType(TYPE_GYROSCOPE);
    }

    @Override
    public String getSensorType() {
        return TYPE_GYROSCOPE;
    }
}
