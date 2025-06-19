package net.thesparrows.peter.phonetrack

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class WiFiStateReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == "android.net.conn.CONNECTIVITY_CHANGE" || intent.action == "android.net.wifi.WIFI_STATE_CHANGED") {
            Log.d("Debug", "ReceivedBroadcast")
        }
    }

}