package net.thesparrows.peter.phonetrack

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_WIFI


class WiFiStateReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == "android.net.conn.CONNECTIVITY_CHANGE") {
            val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            val hasWifi = capabilities?.hasTransport(TRANSPORT_WIFI) == true

            val service: TrackService = context as TrackService
            service.setRunning(!hasWifi)
        }
    }

}