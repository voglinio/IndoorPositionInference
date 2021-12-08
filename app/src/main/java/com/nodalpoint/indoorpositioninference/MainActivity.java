package com.nodalpoint.indoorpositioninference;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import com.nodalpoint.indoorpositioninference.ui.home.MyReceiver;
import androidx.core.app.ActivityCompat;
import android.content.IntentFilter;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;

import org.tensorflow.lite.Interpreter;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    private WifiManager wifiManager;
    private MyReceiver broadcastReceiver;
    private final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1;
    @Getter
    public List<String> wifiResults = new ArrayList<String>();
    @Getter
    public List<WifiRec> wifRecsList = new ArrayList<>();
    public  long offset;
    public MediaPlayer mediaPlayer;
    public MediaPlayer mediaPlayerIntro;
    public Interpreter tflite;
    public TreeMap<String, Integer> wifiDict = new TreeMap<String, Integer>();
    public ArrayList<String> bssids = new ArrayList<String>();


    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor=this.getAssets().openFd("test_variable_v2.tflite");
        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startOffset=fileDescriptor.getStartOffset();
        long declareLength=fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declareLength);
    }

    private void LoadWiFis(){
        BufferedReader reader_wifi_dict;

        //TreeMap<String, Integer> wifiDict = new TreeMap<String, Integer>();
        try {
            reader_wifi_dict = new BufferedReader(new InputStreamReader(this.getAssets().open("wifi_dict.txt")));
            String line = reader_wifi_dict.readLine();
            int i = 1;
            while (line != null) {
                String [] elems = line.split(",");

                wifiDict.put(elems[0], Integer.parseInt(elems[1]));
                line = reader_wifi_dict.readLine();
                i = i + 1;
            }
            reader_wifi_dict.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        // Read BSSIDs
        BufferedReader reader_bssid;
        try {
            reader_bssid = new BufferedReader(new InputStreamReader(this.getAssets().open("bssid_50_clean_throttling_v2.txt")));
            String line = reader_bssid.readLine();
            String [] elems = line.split(",");
            for (String elem : elems){
                bssids.add(elem);
            }
            System.out.println("-->" +  bssids.size());
            Collections.sort(bssids);
            System.out.println("-->" +  bssids.toString());
            reader_bssid.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /***
         * Load TfLite Model
         */
        try {
            tflite = new Interpreter(loadModelFile());
            System.out.println("Model load" + tflite.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        /***
         * Load Wifi dictionaries!
         *     TreeMap<String, Integer> wifiDict = new TreeMap<String, Integer>();
         *     ArrayList<String> bssids = new ArrayList<String>();
         */
        LoadWiFis();

        //mediaPlayer =  MediaPlayer.create(this, R.raw.hehe);
        //mediaPlayerIntro =  MediaPlayer.create(this, R.raw.intro);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this, new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
        }
        offset = (java.lang.System.currentTimeMillis() - android.os.SystemClock.elapsedRealtime());


        System.out.println("-->" + (java.lang.System.currentTimeMillis() - android.os.SystemClock.elapsedRealtime()));
        configureWifiManager();


        setContentView(R.layout.activity_main);

        androidx.appcompat.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.logo_layout, null);
        actionbar.setCustomView(view);

    }


    public void scanWifi() {
        //System.out.println("scanWifi");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(MainActivity.this, "location turned off", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
            } else {
                //Toast.makeText(MainActivity.this, "location turned on", Toast.LENGTH_SHORT).show();
                registerReceiver(broadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                boolean started = wifiManager.startScan();
                System.out.println("location turned on " + started);

            }
        } else {
            System.out.println("scanning");
            //Toast.makeText(MainActivity.this, "scanning", Toast.LENGTH_SHORT).show();
            registerReceiver(broadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wifiManager.startScan();
        }
    }

    private void configureWifiManager() {
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            //Toast.makeText(this, "wifi is disabled...Please enable it.", Toast.LENGTH_LONG).show();
            System.out.println("wifi is disabled...Please enable it!");
            wifiManager.setWifiEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }


    @Override
    protected void onPostResume() {
        System.out.println("onPostResume");

        super.onPostResume();
        broadcastReceiver = new MyReceiver(wifiManager, getWifiResults(), offset, getWifRecsList(), bssids);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

//    @Override
//
//    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case MY_PERMISSIONS_ACCESS_COARSE_LOCATION:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    System.out.println("Permission granted");
//                    wifiManager.startScan();
//                } else {
//                    System.out.println("Permission NOT granted");
//                     return;
//                }
//                break;
//        }
//
//
//    }

}