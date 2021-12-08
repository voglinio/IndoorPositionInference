package com.nodalpoint.indoorpositioninference;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class WifiEncoding {
    List<WifiRec> wifis;
    int nfeaturesEncoding = 60;
    int nfeaturesStrength = 60;
    double [] meansStrength = {-51.8159744 ,  -53.65372574,  -54.69721005,  -57.28261323,
            -60.02574271,  -61.21192514,  -63.99325123,  -65.25074793,
            -66.18200793,  -67.63661031,  -68.70347179,  -69.62172128,
            -70.85869338,  -71.69143533,  -72.49718222,  -73.33973422,
            -73.97773603,  -74.59834412,  -75.22688374,  -75.97043067,
            -76.88993251,  -77.45335003,  -78.44618382,  -79.19397481,
            -80.1191818 ,  -82.20635915,  -83.84693523,  -86.6114242 ,
            -89.88909761,  -94.80289432,  -99.66402282, -104.94232241,
            -113.90642176, -125.32519307, -137.4891811 , -152.18054686,
            -170.58004592, -191.05934739, -213.22013498, -236.31767898,
            -259.78257845, -283.86808599, -311.10679747, -339.45828985,
            -364.14262854, -392.679051  , -423.78856189, -455.01071453,
            -485.44792319, -514.82216656, -547.08898629, -579.58095039,
            -615.84853545, -649.58575106, -683.41918876, -719.31823558,
            -750.64906422, -781.340917  , -810.29005775, -838.20246295,
            -866.05795589, -888.39428094, -908.5590343 , -922.14297641,
            -936.6610311 , -947.37862659, -955.18416475, -964.18861755,
            -971.73318027, -978.38753218, -981.75175677, -985.99457316,
            -987.96883045, -990.81994017, -992.72448341, -994.1837473 ,
            -995.5773325 , -996.27635149, -996.65762193, -997.29068392,
            -998.05002435, -998.43032074, -998.68329507, -998.93654769,
            -998.93654769, -998.93661727, -998.93661727, -999.        ,
            -999.        , -999.        , -999.        , -999.        ,
            -999.        , -999.        , -999.        , -999.        ,
            -999.        , -999.        , -999.        , -999.       };

    double [] stdStrength = {   6.17812461,   6.21386286,   6.27839412,   5.76539907,
            6.23449151,   6.29445424,   5.32799739,   5.31517144,
            5.32683195,  16.36285189,  19.68305215,  22.51124212,
            28.3570873 ,  30.365661  ,  32.24671937,  34.0152438 ,
            33.98637376,  33.9595081 ,  33.93050257,  35.60814007,
            39.52034817,  39.49401146,  44.38966155,  46.30536795,
            49.94059007,  62.89232473,  70.71678381,  84.17646068,
            98.0596832 , 116.64655717, 132.259292  , 147.3863156 ,
            170.4963494 , 195.78017838, 218.97556142, 243.44795916,
            270.11211331, 295.73400264, 319.77955705, 341.56510716,
            360.87157356, 378.18447699, 395.15661745, 410.2089591 ,
            421.27931869, 432.03321691, 441.41418313, 448.48378458,
            453.22312744, 455.80588068, 456.4972557 , 454.91538111,
            450.43059844, 443.54902369, 433.94503807, 420.58667635,
            405.97831004, 388.70234187, 369.37534053, 347.48148373,
            321.74729132, 297.6396588 , 272.51595707, 253.26707012,
            230.07796339, 210.68476541, 194.97047955, 174.68430454,
            155.26746757, 135.51809961, 124.1901478 , 108.11929937,
            99.650064  ,  85.95653464,  75.35399653,  66.05816098,
            55.73469453,  49.72121005,  46.10753553,  39.40103411,
            29.39108733,  22.75876575,  16.97728982,   7.6068704 ,
            7.6068704 ,   7.59852953,   7.59852953,   0.        ,
            0.        ,   0.        ,   0.        ,   0.        ,
            0.        ,   0.        ,   0.        ,   0.        ,
            0.        ,   0.        ,   0.        ,   0.       };


    public List<WifiRec> getWifis() {
        return wifis;
    }

    public void setWifis(List<WifiRec> wifis) {
        this.wifis = wifis;
    }

    public WifiRec existInWifis(String bssid) {
        for(WifiRec w : this.wifis){
            if (w.getBssid().equals(bssid)){
                return w;
            }
        }
        return null;
    }
    public WifiEncoding(List<WifiRec> wifi) {
        wifis = new ArrayList<WifiRec>();
        for (WifiRec w : wifi){
            wifis.add(w);
        }
        System.out.println("Read " + wifis.size() + " wifi elements ");
    }
    public WifiEncoding(List<WifiRec> wifi, int n1, int n2) {
        wifis = new ArrayList<WifiRec>();
        for (WifiRec w : wifi){
            wifis.add(w);
        }
        nfeaturesEncoding = n1;
        nfeaturesStrength = n2;
    }

//    <-       60 theseis               ->.<-    60 theseis.           ->
//            [33, 34, x, x, 145, ...., ][-47, -47, -47, -57, -65, ....]
//            ||   |
//    means                        \/
//            [m1,    m2, m3, m4, ..., m60]
//            [s1+1, s2+1,     ]
//            (-47 - m1)/(s1+1)
    public float [] encode(ArrayList<String> bssids, TreeMap<String, Integer> wifiDict ){
        List<Pair<String, Float>> feat = new ArrayList<Pair<String, Float>>();
        //System.out.println("bssids ->" + bssids.size());
        for (String bssid : bssids){
            float w;
            WifiRec wi = existInWifis(bssid);
            if (wi == null){
                w = (float)-999.0;
            }else{
                w = wi.strength;
            }
            Pair<String, Float> p = new Pair<String, Float>(bssid, (float) w);
            feat.add(p);
        }

        Collections.sort(feat, new Comparator<Pair<String, Float>>() {
            @Override
            public int compare(final Pair<String, Float> o1, final Pair<String, Float> o2) {
                // TODO: implement your logic here
                if (o1.second > o2.second){
                    return -1;
                }else if (o1.second < o2.second){
                    return 1;
                }else{  // o1.second == o2.second

                    return -(o1.first.compareTo(o2.first));
                }
            }
        });
        //System.out.println("===================================");
        float [] res = new float[2*nfeaturesEncoding];
        int i = 0;
        for (Pair<String, Float> elem : feat){
            if (i == nfeaturesEncoding){
                break;
            }
            //System.out.println(i + " " + elem.first + " " +  wifiDict.get(elem.first)  + " " + elem.second) ; //+ ((elem.second-meansStrength[i])/(1+stdStrength[i] )) + " mean " + meansStrength[i] + " std " + stdStrength[i] );
            res[i] = (float)wifiDict.get(elem.first);
            res[i+nfeaturesEncoding]  = (float)((elem.second-meansStrength[i])/(1+stdStrength[i] ));
            //System.out.println(i + " - " + (i+nfeaturesEncoding));

            i = i + 1;
        }
        //System.out.println("===================================");

        System.out.println(Arrays.toString(res));


        return res;
    }
}
