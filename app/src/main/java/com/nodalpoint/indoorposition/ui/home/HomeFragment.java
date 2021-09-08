package com.nodalpoint.indoorposition.ui.home;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nodalpoint.indoorposition.R;
import com.nodalpoint.indoorposition.model.*;
import com.nodalpoint.indoorposition.model.uncalibratedSensors.UncalibratedAccelerometer;
import com.nodalpoint.indoorposition.model.uncalibratedSensors.UncalibratedGyroscope;
import com.nodalpoint.indoorposition.model.uncalibratedSensors.UncalibratedSensor;
import android.net.wifi.WifiManager;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageView mCustomImage;
    private SensorManager sensorManager;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Setup Managers
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        // Gyroscope
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        CalibratedSensor gyroscope = new Gyroscope(gyroscopeSensor);
        SensorEventListener gyroscopeSensorListener = gyroscope.createSensorListener();
        sensorManager.registerListener(gyroscopeSensorListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Rotation Vector
        Sensor rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        CalibratedSensor rotationVector = new RotationVector(rotationVectorSensor);
        SensorEventListener rotationVectorSensorListener = rotationVector.createSensorListener();
        sensorManager.registerListener(rotationVectorSensorListener, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Accelerometer
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        CalibratedSensor accelerometer = new Accelerometer(accelerometerSensor);
        SensorEventListener accelerometerSensorListener = accelerometer.createSensorListener();
        sensorManager.registerListener(accelerometerSensorListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Geomagnetic
        Sensor magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        CalibratedSensor magneticField = new MagneticField(magneticFieldSensor);
        SensorEventListener magneticFieldSensorListener = magneticField.createSensorListener();
        sensorManager.registerListener(magneticFieldSensorListener, magneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Uncalibrated Accelerometer
        Sensor uncalibratedAccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);
        UncalibratedSensor uncalibratedAccelerometer = new UncalibratedAccelerometer(uncalibratedAccelerometerSensor);
        SensorEventListener uncalibratedAccelerometerSensorListener = uncalibratedAccelerometer.createSensorListener();
        sensorManager.registerListener(uncalibratedAccelerometerSensorListener, uncalibratedAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Uncalibrated GYROSCOPE
        Sensor uncalibratedGyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        UncalibratedSensor uncalibratedGyroscope = new UncalibratedGyroscope(uncalibratedGyroscopeSensor);
        SensorEventListener uncalibratedGyroscopeSensorListener = uncalibratedGyroscope.createSensorListener();
        sensorManager.registerListener(uncalibratedGyroscopeSensorListener, uncalibratedGyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Uncalibrated MAGNETIC FIELD
        Sensor uncalibratedMagneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
        UncalibratedSensor uncalibratedMagneticField = new UncalibratedGyroscope(uncalibratedMagneticFieldSensor);
        SensorEventListener uncalibratedMagneticFieldSensorListener = uncalibratedMagneticField.createSensorListener();
        sensorManager.registerListener(uncalibratedMagneticFieldSensorListener, uncalibratedMagneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Button button = (Button) root.findViewById(R.id.record_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                System.out.println("GYROSCOPE AXIS X: " + gyroscope.getAxisX());
                System.out.println("GYROSCOPE AXIS Y: " + gyroscope.getAxisY());
                System.out.println("GYROSCOPE AXIS Z: " + gyroscope.getAxisZ());
                System.out.println("GYROSCOPE ACCURACY: " + gyroscope.getAccuracy());
                System.out.println("ROTATION VECTOR AXIS X: " + rotationVector.getAxisX());
                System.out.println("ROTATION VECTOR AXIS X: " + rotationVector.getAxisY());
                System.out.println("ROTATION VECTOR AXIS X: " + rotationVector.getAxisZ());
                System.out.println("ACCELEROMETER AXIS X: " + accelerometer.getAxisX());
                System.out.println("ACCELEROMETER AXIS Y: " + accelerometer.getAxisY());
                System.out.println("ACCELEROMETER AXIS Z: " + accelerometer.getAxisZ());
                System.out.println("ACCELEROMETER ACCURACY: " + accelerometer.getAccuracy());
                System.out.println("MAGNETIC FIELD AXIS X: " + magneticField.getAxisX());
                System.out.println("MAGNETIC FIELD AXIS Y: " + magneticField.getAxisY());
                System.out.println("MAGNETIC FIELD AXIS Z: " + magneticField.getAxisZ());
                System.out.println("MAGNETIC FIELD ACCURACY: " + magneticField.getAccuracy());
                System.out.println("Uncalibrated ACCELEROMETER AXIS X: " + uncalibratedAccelerometer.getAxisX());
                System.out.println("Uncalibrated ACCELEROMETER AXIS Y: " + uncalibratedAccelerometer.getAxisY());
                System.out.println("Uncalibrated ACCELEROMETER AXIS Z: " + uncalibratedAccelerometer.getAxisZ());
                System.out.println("Uncalibrated ACCELEROMETER BIAS X: " + uncalibratedAccelerometer.getBiasX());
                System.out.println("Uncalibrated ACCELEROMETER BIAS Y: " + uncalibratedAccelerometer.getBiasY());
                System.out.println("Uncalibrated ACCELEROMETER BIAS Z: " + uncalibratedAccelerometer.getBiasZ());
                System.out.println("Uncalibrated ACCELEROMETER ACCURACY: " + uncalibratedAccelerometer.getAccuracy());
                System.out.println("Uncalibrated GYROSCOPE AXIS X: " + uncalibratedGyroscope.getAxisX());
                System.out.println("Uncalibrated GYROSCOPE AXIS Y: " + uncalibratedGyroscope.getAxisY());
                System.out.println("Uncalibrated GYROSCOPE AXIS Z: " + uncalibratedGyroscope.getAxisZ());
                System.out.println("Uncalibrated GYROSCOPE BIAS X: " + uncalibratedGyroscope.getBiasX());
                System.out.println("Uncalibrated GYROSCOPE BIAS Y: " + uncalibratedGyroscope.getBiasY());
                System.out.println("Uncalibrated GYROSCOPE BIAS Z: " + uncalibratedGyroscope.getBiasZ());
                System.out.println("Uncalibrated GYROSCOPE ACCURACY: " + uncalibratedGyroscope.getAccuracy());
                System.out.println("Uncalibrated MAGNETIC FIELD AXIS X: " + uncalibratedMagneticField.getAxisX());
                System.out.println("Uncalibrated MAGNETIC FIELD AXIS Y: " + uncalibratedMagneticField.getAxisY());
                System.out.println("Uncalibrated MAGNETIC FIELD AXIS Z: " + uncalibratedMagneticField.getAxisZ());
                System.out.println("Uncalibrated MAGNETIC FIELD BIAS X: " + uncalibratedMagneticField.getBiasX());
                System.out.println("Uncalibrated MAGNETIC FIELD BIAS Y: " + uncalibratedMagneticField.getBiasY());
                System.out.println("Uncalibrated MAGNETIC FIELD BIAS Z: " + uncalibratedMagneticField.getBiasZ());
                System.out.println("Uncalibrated MAGNETIC FIELD ACCURACY: " + uncalibratedMagneticField.getAccuracy());
            }
        });

        mCustomImage = (ImageView) root.findViewById(R.id.floorPlan);


        FloatingActionButton startSessionBtn = (FloatingActionButton) root.findViewById(R.id.start_session);
        FloatingActionButton stopSessionBtn = (FloatingActionButton) root.findViewById(R.id.stop_session);




        startSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Start Record Session");
            }
        });

        stopSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Stop Record Session");
            }
        });

        return root;

    }


}