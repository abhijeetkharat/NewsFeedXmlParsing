package com.abhi.feedxmlparsing.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by abhijeet on 1/2/18.
 */

public class ConnectionDetector {


    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] nInfo=cm.getAllNetworkInfo();
        for(NetworkInfo info:nInfo){
            if("WIFI".equalsIgnoreCase(info.getTypeName())&& info.isConnected()){
                return true;
            }
            if("MOBILE".equalsIgnoreCase(info.getTypeName())&& info.isConnected()){
                    return true;
            }
        }

       return false;
    }


}
