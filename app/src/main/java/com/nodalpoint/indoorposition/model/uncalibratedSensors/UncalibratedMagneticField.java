package com.nodalpoint.indoorposition.model.uncalibratedSensors;

import android.hardware.Sensor;

public class UncalibratedMagneticField extends UncalibratedSensor{

    private static final String TYPE_MAGNETIC_FIELD_UNCALIBRATED = "TYPE_MAGNETIC_FIELD_UNCALIBRATED";

    public UncalibratedMagneticField(Sensor sensor) {
        super(sensor);
        this.setType(TYPE_MAGNETIC_FIELD_UNCALIBRATED);
    }

    @Override
    public String getSensorType() {
        return TYPE_MAGNETIC_FIELD_UNCALIBRATED;
    }
}
