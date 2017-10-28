package com.example.mykola.spe;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by HOME-PC on 26.11.2016.
 */

public class ConnectionHelper {

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
}

//todo зробити адекватну провірку інтернету
//    public static boolean isNetwork(Context context){
//        try {
//            URL url = new URL("http://www.google.com");
//            HttpURLConnection urlc = (HttpURLConnection) url .openConnection();
//            urlc.setRequestProperty("User-Agent", "Test");
//            urlc.setRequestProperty("Connection", "close");
//            urlc.setConnectTimeout(3000); // This is time limit if the
//            // connection time limit
//            try {
//                urlc.connect();
//                Log.e("TAG", " urlc ----------" + urlc.getResponseCode());
//                if (urlc.getResponseCode() == 200) {
//                    return true;
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//    } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
