package com.nodalpoint.indoorpositioninference;

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
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private WifiManager wifiManager;
    private MyReceiver broadcastReceiver;
    private final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1;
    @Getter
    public List<String> wifiResults = new ArrayList<String>();
    public  long offset;
    public MediaPlayer mediaPlayer;
    public MediaPlayer mediaPlayerIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        broadcastReceiver = new MyReceiver(wifiManager, getWifiResults(), offset);
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