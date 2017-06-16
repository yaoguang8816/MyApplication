package com.yg.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yg.MyApplication;

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
