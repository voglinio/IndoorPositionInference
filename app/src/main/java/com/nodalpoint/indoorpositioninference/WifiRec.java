package com.nodalpoint.indoorpositioninference;

public class WifiRec {


    public String bssid;
    public String  ssid;
    public float strength;
    public long timestamp;

    public WifiRec(long tim, String b, String s, float str){
        timestamp = tim;
        bssid = b;
        ssid = s;
        strength = str;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        return "com.nodalpoint.indoorpositioninference.WifiRec{" +
                "bssid='" + bssid + '\'' +
                ", ssid='" + ssid + '\'' +
                ", strength=" + strength +
                ", timestamp=" + timestamp +
                '}';
    }
}
