package com.nodalpoint.indoorpositioninference.model.uncalibratedSensors;

import android.hardware.Sensor;

public class UncalibratedGyroscope extends UncalibratedSensor{

    private static final String TYPE_GYROSCOPE_UNCALIBRATED = "TYPE_GYROSCOPE_UNCALIBRATED";

    public UncalibratedGyroscope(Sensor sensor) {
        super(sensor);
        this.setType(TYPE_GYROSCOPE_UNCALIBRATED);
    }

    @Override
    public String getSensorType() {
        return TYPE_GYROSCOPE_UNCALIBRATED;
    }
}
