package com.nodalpoint.indoorpositioninference.model.uncalibratedSensors;

import android.hardware.Sensor;

public class UncalibratedAccelerometer extends UncalibratedSensor{

    private static final String TYPE_ACCELEROMETER_UNCALIBRATED = "TYPE_ACCELEROMETER_UNCALIBRATED";

    public UncalibratedAccelerometer(Sensor sensor) {
        super(sensor);
        this.setType(TYPE_ACCELEROMETER_UNCALIBRATED);
    }

    @Override
    public String getSensorType() {
        return TYPE_ACCELEROMETER_UNCALIBRATED;
    }
}
