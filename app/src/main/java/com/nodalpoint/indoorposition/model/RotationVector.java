package com.nodalpoint.indoorposition.model;

import android.hardware.Sensor;

public class RotationVector extends CalibratedSensor{

    private static final String TYPE_ROTATION_VECTOR = "TYPE_ROTATION_VECTOR";

    public RotationVector(Sensor sensor) {
        super(sensor);
        this.setType(TYPE_ROTATION_VECTOR);
    }

    @Override
    public String getSensorType() {
        return TYPE_ROTATION_VECTOR;
    }
}
