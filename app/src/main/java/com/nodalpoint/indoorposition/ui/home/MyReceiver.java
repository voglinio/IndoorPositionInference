package com.nodalpoint.indoorposition.ui.home;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import com.nodalpoint.indoorposition.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MyReceiver extends BroadcastReceiver {
    private final WifiManager wifiManager;
    private List<ScanResult> scanResults;
    private final List<String> results;// = new ArrayList<String>();
    private long offset;
    public long time;

    public MyReceiver(WifiManager wifiManager, List<String> results, long offset) {
        this.wifiManager = wifiManager;
        this.results = results;
        this.offset = offset;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onReceive(Context context, Intent intent) {
        results.clear();
        long time= System.currentTimeMillis();
        long off = this.offset;

        scanResults = wifiManager.getScanResults();
        for (ScanResult scanResult : scanResults) {

            results.add("TYPE_WIFI" + "\t" +  scanResult.BSSID + "\t"  + scanResult.SSID + "\t" + scanResult.level + "\t" + scanResult.frequency + "\t" + ((long)(off  + scanResult.timestamp/1000.0)) );
            //System.out.println("--->" + scanResult.BSSID +  " "  + scanResult.frequency + " " + scanResult.level + " " + scanResult.timestamp + " " +  ((long)(off  + scanResult.timestamp/1000.0)) );
        }
        //arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, results);
        //listView.setAdapter(arrayAdapter);
    }
}
