package com.nodalpoint.indoorpositioninference.ui.home;

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
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nodalpoint.indoorpositioninference.MainActivity;
import com.nodalpoint.indoorpositioninference.R;
import com.nodalpoint.indoorpositioninference.model.*;
import com.nodalpoint.indoorpositioninference.model.uncalibratedSensors.UncalibratedAccelerometer;
import com.nodalpoint.indoorpositioninference.model.uncalibratedSensors.UncalibratedGyroscope;
import com.nodalpoint.indoorpositioninference.model.uncalibratedSensors.UncalibratedMagneticField;
import com.nodalpoint.indoorpositioninference.model.uncalibratedSensors.UncalibratedSensor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Spinner startingCheckpointSpinner;
    private Checkpoint currentCheckpoint;
    private TextView currentCheckpointTextView;
    private TextView locationTextView;

    private List<Checkpoint> checkpoints;
    private SensorManager sensorManager;
    private String filename;
    private FileWriter writer;
    private ListView wifiList;
    private LinearLayout linearLayout;
    private Map<Integer, Checkpoint> availableCheckpoints;
    private boolean recordSession = false;

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
            if (DEBUG) System.out.println("WIFI - BLE Data");

            ((MainActivity)getActivity()).scanWifi();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (DEBUG) System.out.println("------------------- wifi start -------------------");
            long time =  System.currentTimeMillis();
            Date date =new Date(time);

            for (String s : ((MainActivity)getActivity()).wifiResults){
                System.out.println(date.toString() + "\t"  + s);
                try{
                    writer.write(time + "\t" + s + "\n");
                    writer.flush();
                }catch (IOException e) {
                    System.err.println("Cannot open file " + filename + "to write");
                }
            }
            ArrayAdapter<String> wifiArray = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, ((MainActivity)getActivity()).getWifiResults());
            wifiList.setAdapter(wifiArray);
            if (DEBUG) System.out.println("------------------- wifi end   -------------------");

            wifiBLeHandler.postDelayed(this, wifiBLeHandlerDelay);
        }
    };



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        // Setup spinner
        startingCheckpointSpinner = (Spinner) root.findViewById(R.id.starting_checkpoint_spinner);
        linearLayout = (LinearLayout) root.findViewById(R.id.neighbours);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            checkpoints = setupCheckpoints();
        }
        locationTextView = (TextView) root.findViewById(R.id.location);
         locationTextView.setText("MAlaka");

//        startingCheckpointSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                currentCheckpoint = checkpoints.get(position);
//                currentCheckpointTextView.setText("Last Checkpoint " + currentCheckpoint.getName());
//                setupNeighbours(currentCheckpoint);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        // Setup Managers
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        setupSensors();

        wifiList = root.findViewById(R.id.wifi_list);

        currentCheckpointTextView = (TextView) root.findViewById(R.id.checkpoint);
        currentCheckpointTextView.setText("-");



        FloatingActionButton startSessionBtn = (FloatingActionButton) root.findViewById(R.id.start_session);
        FloatingActionButton stopSessionBtn = (FloatingActionButton) root.findViewById(R.id.stop_session);
        stopSessionBtn.setEnabled(false);
        startSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Start Record Session");
                recordSession = true;
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

                currentCheckpointTextView.setText("Checkpoint " + currentCheckpoint.getName());
                startSessionBtn.setEnabled(false);
                stopSessionBtn.setEnabled(true);
                startingCheckpointSpinner.setEnabled(false);
                sensorHandler.postDelayed(sensorsRunnable, sensorHandlerDelay);
                wifiBLeHandler.postDelayed(wifiBLeRunnable, wifiBLeHandlerDelay);
            }
        });
        stopSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Stop Record Session");
                recordSession = false;
                Context context = getActivity().getApplicationContext();
                try {

                    if (writer != null) {
                        writer.flush();
                        writer.close();
                    }

                } catch (IOException e) {
                    System.err.println("Cannot close file " + filename + " to write");
                }

                ArrayAdapter<String> wifiArray = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, new ArrayList<String>());
                wifiList.setAdapter(wifiArray);
                resetCheckpoint(currentCheckpointTextView);
                startSessionBtn.setEnabled(true);
                stopSessionBtn.setEnabled(false);
                startingCheckpointSpinner.setEnabled(true);
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
        UncalibratedSensor uncalibratedMagneticField = new UncalibratedMagneticField(uncalibratedMagneticFieldSensor);
        SensorEventListener uncalibratedMagneticFieldSensorListener = uncalibratedMagneticField.createSensorListener();
        sensorManager.registerListener(uncalibratedMagneticFieldSensorListener, uncalibratedMagneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL);
        this.uncalibratedSensors.add(uncalibratedMagneticField);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void printSensorsValues() {
        long time= System.currentTimeMillis();
        if (DEBUG) System.out.println(" ---------------------------------- ");
        this.calibratedSensors.forEach(sensor -> {

            if (DEBUG) System.out.println(time + " " + sensor.getType() + " AXIS X: " + sensor.getAxisX() + " AXIS Y: " + sensor.getAxisY() + " AXIS Z: " + sensor.getAxisZ() + " ACCURACY: " + sensor.getAccuracy());
            try {
                writer.write(time + "\t" + sensor.getType() + "\t" + sensor.getAxisX() + "\t" + sensor.getAxisY() + "\t" + sensor.getAxisZ() + " \t" + sensor.getAccuracy() + "\n");
                writer.flush();
            }catch (IOException e) {
                System.err.println("Cannot write to file " + filename);
            }

        });
        if (DEBUG) System.out.println(" ---------------------------------- ");
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
        if (DEBUG) System.out.println(" ---------------------------------- ");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Checkpoint> setupCheckpoints() {
        availableCheckpoints = Checkpoint.generatePoints();
        checkpoints = new ArrayList<>(availableCheckpoints.values());
        ArrayAdapter<String> startingCheckpointAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, checkpoints.stream().map(Checkpoint::getName).collect(Collectors.toList()));
        startingCheckpointAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startingCheckpointSpinner.setAdapter(startingCheckpointAdapter);
        return checkpoints;
    }

    private void resetCheckpoint(TextView currentCheckpointTextView) {
        currentCheckpointTextView.setText("-");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupNeighbours(Checkpoint checkpoint){
        List<Checkpoint> neighbours = checkpoint.getNeighbours();
        linearLayout.removeAllViews();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int rowsCount = neighbours.size() % 3 > 0 ?  (neighbours.size() / 3) + 1 : (neighbours.size() / 3);
        for (int i=0; i<rowsCount; i++){
            LinearLayout rowLayout = new LinearLayout(getContext());
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            int rowSize = ((rowsCount == i+1) & (neighbours.size() % 3 != 0)) ? neighbours.size() % 3 : 3;
            for (int j=0; j < rowSize; j++) {
                LinearLayout btnLayout = new LinearLayout(getContext());
                btnLayout.setOrientation(LinearLayout.VERTICAL);
                btnLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.33333f));
                Checkpoint chk = neighbours.get(j + 3*i);
                Button btn = new Button(getContext());
                btn.setText(chk.getName());
                btn.setWidth(250);
                btn.setHeight(250);
                btn.setTextSize(25);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!recordSession) { return; }
                        //((MainActivity)getActivity()).mediaPlayer.start();

                        currentCheckpoint = availableCheckpoints.get(chk.getId());
                        currentCheckpointTextView.setText("Last Checkpoint " + currentCheckpoint.getName());
                        System.out.println("CHECKPOINT " + currentCheckpoint.getName());
                        long time= System.currentTimeMillis();
                    try{
                        writer.write(time + "\t" + "TYPE_CHECKPOINT" + "\t" + currentCheckpoint.getName() + "\n");
                        writer.flush();
                    }catch (IOException e) {
                        System.err.println("Cannot open file " + filename + "to write");
                    }
                        setupNeighbours(currentCheckpoint);
                    }
                });
                btnLayout.addView(btn);
                lp.setMargins(20,10,20,10);
                rowLayout.addView(btnLayout);
            }
            linearLayout.addView(rowLayout, lp);
        }

    }
}