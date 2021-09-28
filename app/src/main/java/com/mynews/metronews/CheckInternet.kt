package com.mynews.metronews

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi


class CheckInternet(val context: Context) {

    @RequiresApi(Build.VERSION_CODES.M)
    @TargetApi(Build.VERSION_CODES.M)
    fun isNetworkOnline1(): Boolean {
        var isOnline: Boolean = false
        try {
            val manager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities: NetworkCapabilities =
                manager.getNetworkCapabilities(manager.activeNetwork)!!  // need ACCESS_NETWORK_STATE permission
            isOnline =
                capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return isOnline
    }



}