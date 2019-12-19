package com.example.froukje.countingbirds;

/**
 * Created by Kurok on 4/3/2018.
 */

public class DataReader {

    private String strIp;

    public String getStrIp(){
        // ****************
        // ----------------
        // VUL HET IP VAN DE COMPUTER IN
        // ----------------
        strIp = "http://145.49.70.24:28479";
        // ----------------
        // ****************
        //String strURL = strIp+":28479/weidevogelsBE/webresources/appweidevogels."+tabel;
        return  strIp;

    }
}
