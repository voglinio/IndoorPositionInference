package com.nodalpoint.indoorposition.ui.home;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyReceiver extends BroadcastReceiver {
    private WifiManager wifiManager;
    private List<ScanResult> scanResults;
    private ArrayAdapter arrayAdapter;
    private ArrayList results = new ArrayList<String>();

    public MyReceiver(WifiManager wifiManager) {
        this.wifiManager = wifiManager;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        results.clear();
        scanResults = wifiManager.getScanResults();
        for (ScanResult scanResult : scanResults) {
            results.add(scanResult.SSID + scanResult.capabilities);
            System.out.println(scanResult.SSID + scanResult.capabilities);
        }
        //arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, results);
        //listView.setAdapter(arrayAdapter);
    }
}
