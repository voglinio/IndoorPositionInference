package com.nodalpoint.indoorposition.ui.home;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nodalpoint.indoorposition.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MyReceiver extends BroadcastReceiver {
    private WifiManager wifiManager;
    private List<ScanResult> scanResults;
    private ArrayAdapter arrayAdapter;
    private ArrayList results;// = new ArrayList<String>();

    public MyReceiver(WifiManager wifiManager, ArrayList results) {

        this.wifiManager = wifiManager;
        this.results = results;

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        results.clear();
        long time= System.currentTimeMillis();

        scanResults = wifiManager.getScanResults();
        for (ScanResult scanResult : scanResults) {
            results.add(time + "\t" + "TYPE_WIFI" + "\t" +  scanResult.BSSID + "\t"  + scanResult.SSID + "\t" + scanResult.level + "\t" + scanResult.frequency + "\t" + scanResult.timestamp);
            //System.out.println(scanResult.BSSID + " "  + scanResult.SSID + " " + scanResult.frequency + " " + scanResult.level + " " + scanResult.timestamp);
        }
        //arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, results);
        //listView.setAdapter(arrayAdapter);
    }
}
