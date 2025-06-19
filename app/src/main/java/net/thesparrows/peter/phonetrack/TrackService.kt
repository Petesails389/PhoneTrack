package net.thesparrows.peter.phonetrack

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class TrackService: Service() {

    private val wifiStateReceiver = WiFiStateReceiver()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()

        this.unregisterReceiver(wifiStateReceiver)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "tracking_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText("Phone track is running")
            .setContentInfo("Profile - Some Profile here")
            .build()
        startForeground(1,notification)

        //subscribe to wifi state changes
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED")
        val receiverFlags = ContextCompat.RECEIVER_EXPORTED
        ContextCompat.registerReceiver(this, wifiStateReceiver, intentFilter, receiverFlags)

    }

    enum class Actions {
        START, STOP
    }
}