package com.sakana.beenhere.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by caesare on 9/27/13.
 */
public class BLNetwork {

    private Context c;

    public BLNetwork(Context context){
        c = context;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
