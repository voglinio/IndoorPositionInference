package com.nodalpoint.indoorpositioninference.ui.home;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.nodalpoint.indoorpositioninference.WifiRec;

import java.util.ArrayList;
import java.util.List;

public class MyReceiver extends BroadcastReceiver {
    private final WifiManager wifiManager;
    private List<ScanResult> scanResults;
    private final List<WifiRec> wifiRecs; // long tim, String b, String s, float str
    private final List<String> results;// = new ArrayList<String>();
    private ArrayList<String> bssids;
    private long offset;
    public long time;

    public MyReceiver(WifiManager wifiManager, List<String> results, long offset,
                      List<WifiRec> wifiRecs,  ArrayList<String> bssids ) {
        System.out.println("Receiver: bssids len = " + bssids.size());
        this.wifiManager = wifiManager;
        this.results = results;
        this.offset = offset;
        this.wifiRecs = wifiRecs;
        this.bssids = bssids;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onReceive(Context context, Intent intent) {
        results.clear();
        wifiRecs.clear();
        long time= System.currentTimeMillis();
        long off = this.offset;

        scanResults = wifiManager.getScanResults();
        for (ScanResult scanResult : scanResults) {

            results.add("TYPE_WIFI" + "\t" +  scanResult.BSSID + "\t"  + scanResult.SSID + "\t" + scanResult.level + "\t" + scanResult.frequency + "\t" + ((long)(off  + scanResult.timestamp/1000.0)) );
            //System.out.println("--->" + scanResult.BSSID +  " "  + scanResult.frequency + " " + scanResult.level + " " + scanResult.timestamp + " " +  ((long)(off  + scanResult.timestamp/1000.0)) );
            if (this.bssids.contains(scanResult.BSSID)) {
                wifiRecs.add(new WifiRec(((long) (off + scanResult.timestamp / 1000.0)), scanResult.BSSID, scanResult.SSID, (float) scanResult.level));
            }else{
                System.err.println("---------> Nope nope " + scanResult.BSSID);
            }
        }
        //arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, results);
        //listView.setAdapter(arrayAdapter);
    }
}
