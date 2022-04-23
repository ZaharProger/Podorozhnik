package com.podorozhnik.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionChecker {
    public static boolean checkConnection(Context context){
        boolean isConnected = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE ||
                    activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET || activeNetwork.getType() == ConnectivityManager.TYPE_VPN)
                isConnected = true;
        }

        return isConnected;
    }
}
