package com.example.baijunfeng.myapplication.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.baijunfeng.myapplication.MyApplication;

/**
 * Created by baijunfeng on 17/6/13.
 */

public class NetworkStatus {

    public boolean isWifiConnected() {
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }

        return false;
    }
}
