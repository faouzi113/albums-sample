package com.faouzibidi.albums.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
/**
 * this class is used like a herlper to get network state
 */
class NetworkHelper{

    companion object {

        /**
         * this method check the device internet connection and returns a boolean which
         * signify that the device is connected to a network either on mobile data or wifi
         *
         * @return true if the device is connected to cellular or wifi network
         *
         * @see ConnectivityManager
         */
        fun isConnected(context: Context) : Boolean{
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            /*
                since the application is built for minSdk 19 we should check connection
                for old versions of devices using deprecated method
             */
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities=connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                networkCapabilities!=null &&
                        (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            } else {
                if(connectivityManager.activeNetworkInfo != null){
                    val networkInfo = connectivityManager.activeNetworkInfo!!.type
                    networkInfo == ConnectivityManager.TYPE_WIFI || networkInfo == ConnectivityManager.TYPE_MOBILE
                }else{
                    false
                }
            }

        }
    }
}