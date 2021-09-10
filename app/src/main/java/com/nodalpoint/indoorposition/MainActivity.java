package com.nodalpoint.indoorposition;

import android.app.Fragment;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nodalpoint.indoorposition.ui.home.HomeFragment;
import com.nodalpoint.indoorposition.ui.home.MyReceiver;
import androidx.core.app.ActivityCompat;
import android.content.IntentFilter;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private WifiManager wifiManager;
    private MyReceiver broadcastReceiver;
    private final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1;
    public ArrayList<String> wifiResults = new ArrayList<String>();
    public  long offset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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

//        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(
//                    MainActivity.this, new String[]{
//                            Manifest.permission.ACCESS_COARSE_LOCATION
//                    }, MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
//
//
//        } else {
//            scanWifi();
//        }
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
                System.out.println("location turned on");
                registerReceiver(broadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                wifiManager.startScan();
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
        broadcastReceiver = new MyReceiver(wifiManager, wifiResults);
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