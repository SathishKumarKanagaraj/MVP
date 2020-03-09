package com.example.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public final class Utils {

    /**
     * Default Constructor
     */
    private Utils() {

    }

    /**
     * To check the internet connectivity and show error message if network not available.
     *
     * @param context Contains the context
     * @return boolean True if internet is available false
     */
    public static boolean checkNetworkAndShowDialog(Context context) {
        if (!checkNetConnection(context)) {
            Toast.makeText(context,"Check Internet Connectivity",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Checking internet state.
     *
     * @param context Activity context
     * @return boolean True if internet is enabled else false
     */
    public static boolean checkNetConnection(Context context) {
        ConnectivityManager miManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo miInfo = miManager.getActiveNetworkInfo();
        boolean networkStatus = false;

        //Checking the network connection is in wifi or mobile data
        if (miInfo != null && miInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            networkStatus = true;
        } else if (miInfo != null && miInfo.getType() == ConnectivityManager.TYPE_MOBILE &&
                miInfo.isConnectedOrConnecting())
            networkStatus = true;
        return networkStatus;
    }



}
