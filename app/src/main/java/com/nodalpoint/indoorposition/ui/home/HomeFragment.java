package com.nodalpoint.indoorposition.ui.home;

import android.content.Context;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageView mCustomImage;
    private Checkpoint currentCheckpoint;
    private int currentCheckpointIndex = 0;
    private List<Route> availableRoutes;
    private Route selectedRoute;
    private SensorManager sensorManager;
    private String filename;
    private FileWriter writer;

    private static boolean DEBUG = false;

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
        availableRoutes = setupRoutes();
        // this should be replaced by choice list
        selectedRoute = availableRoutes.get(0);
        currentCheckpointIndex = 0;
        currentCheckpoint = selectedRoute.getCheckpoints().get(currentCheckpointIndex);

        // Setup Managers
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        setupSensors();

        TextView currentCheckpointTextView = (TextView) root.findViewById(R.id.checkpoint);
        currentCheckpointTextView.setText("-");
        Button checkpointButton = (Button) root.findViewById(R.id.record_button);
        checkpointButton.setTypeface(checkpointButton.getTypeface(), Typeface.BOLD);
        checkpointButton.setEnabled(false);
        checkpointButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentCheckpointIndex ++;
                currentCheckpoint = selectedRoute.getCheckpoints().get(currentCheckpointIndex);
                System.out.println("CHECKPOINT " + currentCheckpoint.getName());
                if(currentCheckpointIndex + 2 > selectedRoute.getCheckpoints().size()) {
                    checkpointButton.setEnabled(false);
                    currentCheckpointTextView.setText("Checkpoint " + currentCheckpoint.getName() + " End of Route");
                } else {
                    currentCheckpointTextView.setText("Checkpoint " + currentCheckpoint.getName());
                }
            }
        });

        mCustomImage = (ImageView) root.findViewById(R.id.floorPlan);


        FloatingActionButton startSessionBtn = (FloatingActionButton) root.findViewById(R.id.start_session);
        FloatingActionButton stopSessionBtn = (FloatingActionButton) root.findViewById(R.id.stop_session);
        stopSessionBtn.setEnabled(false);


        startSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Start Record Session");
                Context context = getActivity().getApplicationContext();
                try {
                    filename = new SimpleDateFormat("'record'_yyyy_MM_dd_HH_mm_ss'.txt'").format(new Date());
                    File file = new File(context.getExternalFilesDir(null), filename);;

                    writer = new FileWriter(file);

                    String manufacturer = Build.MANUFACTURER;
                    String model = Build.MODEL;
                    String base_os = Build.VERSION.INCREMENTAL;
                    int sdk = Build.VERSION.PREVIEW_SDK_INT;
                    long time= System.currentTimeMillis();
                    writer.write("# startTime: " + time + "\n");
                    writer.write("# SiteID:Poole House\tSiteName:Interior\tFloorId:0\tFloorName:0 \n");
                    writer.write("# Manufacturer:" + manufacturer + "\tModel:" + model + "\tVersion: "+ base_os + "\tSDK: " + sdk + "\n");
                    for (CalibratedSensor sensor : calibratedSensors) {
                        writer.write("# type: " +sensor.getSensor().getType() +
                                 "\tname : " + sensor.getSensor().getName() +
                                "\tversion: " + sensor.getSensor().getVersion() +
                                "\tvendor: " + sensor.getSensor().getVendor() +
                                "\tresolution: " + sensor.getSensor().getResolution() +
                                "\tpower: " + sensor.getSensor().getPower() +
                                "\tmaximumRange: " + sensor.getSensor().getMaximumRange() + "\n"
                        );
                    };

                    for (UncalibratedSensor sensor : uncalibratedSensors) {
                        writer.write("# type: " +sensor.getSensor().getType() +
                                "\tname : " + sensor.getSensor().getName() +
                                "\tversion: " + sensor.getSensor().getVersion() +
                                "\tvendor: " + sensor.getSensor().getVendor() +
                                "\tresolution: " + sensor.getSensor().getResolution() +
                                "\tpower: " + sensor.getSensor().getPower() +
                                "\tmaximumRange: " + sensor.getSensor().getMaximumRange() + "\n"
                        );
                    };
                    writer.flush();


                } catch (IOException e) {
                    System.err.println("Cannot open file " + filename + "to write");
                }

                currentCheckpointTextView.setText(currentCheckpoint.getName());
                startSessionBtn.setEnabled(false);
                checkpointButton.setEnabled(true);
                stopSessionBtn.setEnabled(true);
                sensorHandler.postDelayed(sensorsRunnable, sensorHandlerDelay);
                wifiBLeHandler.postDelayed(wifiBLeRunnable, wifiBLeHandlerDelay);
            }
        });

        stopSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Stop Record Session");

                Context context = getActivity().getApplicationContext();
                try {

                    if (writer != null) {
                        writer.flush();
                        writer.close();
                    }

                } catch (IOException e) {
                    System.err.println("Cannot close file " + filename + " to write");
                }


                resetCheckpoint(currentCheckpointTextView);
                startSessionBtn.setEnabled(true);
                checkpointButton.setEnabled(false);
                stopSessionBtn.setEnabled(false);
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

            if (DEBUG) System.out.println(time + " " + sensor.getType() + " AXIS X: " + sensor.getAxisX() + " AXIS Y: " + sensor.getAxisY() + " AXIS Z: " + sensor.getAxisZ() + " ACCURACY: " + sensor.getAccuracy());
            try {
                writer.write(time + "\t" + sensor.getType() + "\t" + sensor.getAxisX() + "\t" + sensor.getAxisY() + "\t" + sensor.getAxisZ() + " \t" + sensor.getAccuracy() + "\n");
                writer.flush();
            }catch (IOException e) {
                System.err.println("Cannot write to file " + filename);
            }

        });
        System.out.println(" ---------------------------------- ");
        this.uncalibratedSensors.forEach(sensor -> {
            if (DEBUG) System.out.println(time + " " + sensor.getType() + " AXIS X: " + sensor.getAxisX() + " AXIS Y: " + sensor.getAxisY()
                    + " AXIS Z: " + sensor.getAxisZ() + " ACCURACY: " + sensor.getAccuracy()
                    + " BIAS X: " + sensor.getBiasX() +  "BIAS Y: " + sensor.getBiasY() + " BIAS Z: " + sensor.getBiasZ());

            try {
                writer.write(time + "\t" + sensor.getType() + "\t" + sensor.getAxisX() + "\t" + sensor.getAxisY() +
                          "\t" + sensor.getAxisZ()  + "\t" + sensor.getBiasX() +
                          "\t" + sensor.getBiasY() + "\t" + sensor.getBiasZ() +
                          "\t" + sensor.getAccuracy() + "\n");
                writer.flush();
            }catch (IOException e) {
                System.err.println("Cannot write to file " + filename);
            }

        });
        System.out.println(" ---------------------------------- ");
    }

    private List<Route> setupRoutes() {
        Map<Integer, Checkpoint> availableCheckpoints = Checkpoint.generatePoints();
        List<Route> routes = new ArrayList<>();
        List<Checkpoint> route1Checkpoints = new ArrayList<Checkpoint>(){
            {add(availableCheckpoints.get(0));}
            {add(availableCheckpoints.get(1));}
            {add(availableCheckpoints.get(2));}
            {add(availableCheckpoints.get(3));}
            {add(availableCheckpoints.get(4));}
            {add(availableCheckpoints.get(5));}
            {add(availableCheckpoints.get(6));}
            {add(availableCheckpoints.get(7));}
            {add(availableCheckpoints.get(8));}
            {add(availableCheckpoints.get(9));}
            {add(availableCheckpoints.get(10));}
        };
        routes.add(new Route(route1Checkpoints));



        return routes;
    }

    private void resetCheckpoint(TextView currentCheckpointTextView) {
        currentCheckpointIndex = 0;
        currentCheckpointTextView.setText("-");
    }
}