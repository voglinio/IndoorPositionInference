package com.nodalpoint.indoorposition.ui.home;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.nodalpoint.indoorposition.MainActivity;
import com.nodalpoint.indoorposition.R;
import com.nodalpoint.indoorposition.model.*;
import com.nodalpoint.indoorposition.model.uncalibratedSensors.UncalibratedAccelerometer;
import com.nodalpoint.indoorposition.model.uncalibratedSensors.UncalibratedGyroscope;
import com.nodalpoint.indoorposition.model.uncalibratedSensors.UncalibratedSensor;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageView mCustomImage;
    private SensorManager sensorManager;
    // Sensors
    private List<CalibratedSensor> calibratedSensors = new ArrayList<>();
    private List<UncalibratedSensor> uncalibratedSensors = new ArrayList<>();
    private Handler sensorHandler = new Handler();
    final int sensorHandlerDelay = 50;
    private final Runnable sensorsRunnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            printSensorsValues();
            sensorHandler.postDelayed(this, sensorHandlerDelay);
        }
    };

    private Handler wifiBLeHandler = new Handler();
    final int wifiBLeHandlerDelay = 2000;
    private final Runnable wifiBLeRunnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {

            // Put your code here
            System.out.println("WIFI - BLE Data");
            ((MainActivity)getActivity()).scanWifi();
            System.out.println("------------------- wifi start -------------------");
            for (String s : ((MainActivity)getActivity()).wifiResults){
                System.out.println(s);
            }
            System.out.println("------------------- wifi end   -------------------");

            wifiBLeHandler.postDelayed(this, wifiBLeHandlerDelay);
        }
    };



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Setup Managers
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        setupSensors();
        Button button = (Button) root.findViewById(R.id.record_button);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {
                printSensorsValues();
            }
        });

        mCustomImage = (ImageView) root.findViewById(R.id.floorPlan);


        FloatingActionButton startSessionBtn = (FloatingActionButton) root.findViewById(R.id.start_session);
        FloatingActionButton stopSessionBtn = (FloatingActionButton) root.findViewById(R.id.stop_session);




        startSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Start Record Session");
                sensorHandler.postDelayed(sensorsRunnable, sensorHandlerDelay);
                wifiBLeHandler.postDelayed(wifiBLeRunnable, wifiBLeHandlerDelay);
            }
        });

        stopSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Stop Record Session");
                sensorHandler.removeCallbacks(sensorsRunnable);
                wifiBLeHandler.removeCallbacks(wifiBLeRunnable);
            }
        });

        return root;

    }


    private void setupSensors() {
        this.uncalibratedSensors = new ArrayList<>();
        this.calibratedSensors = new ArrayList<>();
        // Gyroscope
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        CalibratedSensor gyroscope = new Gyroscope(gyroscopeSensor);
        SensorEventListener gyroscopeSensorListener = gyroscope.createSensorListener();
        sensorManager.registerListener(gyroscopeSensorListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        this.calibratedSensors.add(gyroscope);
        // Rotation Vector
        Sensor rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        CalibratedSensor rotationVector = new RotationVector(rotationVectorSensor);
        SensorEventListener rotationVectorSensorListener = rotationVector.createSensorListener();
        sensorManager.registerListener(rotationVectorSensorListener, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        this.calibratedSensors.add(rotationVector);
        // Accelerometer
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        CalibratedSensor accelerometer = new Accelerometer(accelerometerSensor);
        SensorEventListener accelerometerSensorListener = accelerometer.createSensorListener();
        sensorManager.registerListener(accelerometerSensorListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        this.calibratedSensors.add(accelerometer);
        // Geomagnetic
        Sensor magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        CalibratedSensor magneticField = new MagneticField(magneticFieldSensor);
        SensorEventListener magneticFieldSensorListener = magneticField.createSensorListener();
        sensorManager.registerListener(magneticFieldSensorListener, magneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL);
        this.calibratedSensors.add(magneticField);
        // Uncalibrated Accelerometer
        Sensor uncalibratedAccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);
        UncalibratedSensor uncalibratedAccelerometer = new UncalibratedAccelerometer(uncalibratedAccelerometerSensor);
        SensorEventListener uncalibratedAccelerometerSensorListener = uncalibratedAccelerometer.createSensorListener();
        sensorManager.registerListener(uncalibratedAccelerometerSensorListener, uncalibratedAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        this.uncalibratedSensors.add(uncalibratedAccelerometer);
        // Uncalibrated GYROSCOPE
        Sensor uncalibratedGyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        UncalibratedSensor uncalibratedGyroscope = new UncalibratedGyroscope(uncalibratedGyroscopeSensor);
        SensorEventListener uncalibratedGyroscopeSensorListener = uncalibratedGyroscope.createSensorListener();
        sensorManager.registerListener(uncalibratedGyroscopeSensorListener, uncalibratedGyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        this.uncalibratedSensors.add(uncalibratedGyroscope);
        // Uncalibrated MAGNETIC FIELD
        Sensor uncalibratedMagneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
        UncalibratedSensor uncalibratedMagneticField = new UncalibratedGyroscope(uncalibratedMagneticFieldSensor);
        SensorEventListener uncalibratedMagneticFieldSensorListener = uncalibratedMagneticField.createSensorListener();
        sensorManager.registerListener(uncalibratedMagneticFieldSensorListener, uncalibratedMagneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL);
        this.uncalibratedSensors.add(uncalibratedMagneticField);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void printSensorsValues() {
        long time= System.currentTimeMillis();
        System.out.println(" ---------------------------------- ");
        this.calibratedSensors.forEach(sensor -> {
            System.out.println(time + " " + sensor.getType() + " AXIS X: " + sensor.getAxisX() + " AXIS Y: " + sensor.getAxisY() + " AXIS Z: " + sensor.getAxisZ() + " ACCURACY: " + sensor.getAccuracy());
        });
        System.out.println(" ---------------------------------- ");
        this.uncalibratedSensors.forEach(sensor -> {
            System.out.println(time + " " + sensor.getType() + " AXIS X: " + sensor.getAxisX() + " AXIS Y: " + sensor.getAxisY()
                    + " AXIS Z: " + sensor.getAxisZ() + " ACCURACY: " + sensor.getAccuracy()
                    + " BIAS X: " + sensor.getBiasX() +  "BIAS Y: " + sensor.getBiasY() + " BIAS Z: " + sensor.getBiasZ());

        });
        System.out.println(" ---------------------------------- ");
    }
}