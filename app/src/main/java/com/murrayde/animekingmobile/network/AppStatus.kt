package com.murrayde.animekingmobile.network

import android.content.Context
import android.net.ConnectivityManager
import timber.log.Timber

class AppStatus {
    var connectivityManager: ConnectivityManager? = null
    var connected = false
    val isOnline: Boolean
        get() {
            try {
                connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connectivityManager!!.activeNetworkInfo
                connected = networkInfo != null && networkInfo.isAvailable &&
                        networkInfo.isConnected
                return connected
            } catch (e: Exception) {
                println("CheckConnectivity Exception: " + e.message)
                Timber.e("Network exception : $e")
            }
            return connected
        }

    companion object {
        private val instance = AppStatus()
        var context: Context? = null
        fun getInstance(ctx: Context): AppStatus {
            context = ctx.applicationContext
            return instance
        }
    }
}