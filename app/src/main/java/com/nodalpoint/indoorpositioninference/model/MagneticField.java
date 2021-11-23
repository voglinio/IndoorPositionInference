package com.nodalpoint.indoorpositioninference.model;

import android.hardware.Sensor;

public class MagneticField extends CalibratedSensor{

    private static final String TYPE_MAGNETIC_FIELD = "TYPE_MAGNETIC_FIELD";

    public MagneticField(Sensor sensor) {
        super(sensor);
        this.setType(TYPE_MAGNETIC_FIELD);
    }

    @Override
    public String getSensorType() {
        return TYPE_MAGNETIC_FIELD;
    }
}
