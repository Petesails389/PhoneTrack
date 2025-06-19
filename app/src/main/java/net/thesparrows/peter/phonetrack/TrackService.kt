package net.thesparrows.peter.phonetrack

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class TrackService: Service() {

    private val wifiStateReceiver = WiFiStateReceiver()
    private var active = false
    private lateinit var notification: Notification

    fun setRunning(nowRunning: Boolean) {
        active = nowRunning
        updateNotification()
        Log.d("Debug", active.toString())
    }

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
        updateNotification()
        startForeground(1,notification)

        //subscribe to wifi state changes
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        val receiverFlags = ContextCompat.RECEIVER_EXPORTED
        ContextCompat.registerReceiver(this, wifiStateReceiver, intentFilter, receiverFlags)
    }

    private fun updateNotification() {
        notification = NotificationCompat.Builder(this, "tracking_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText(if(active) {
                "Profile is running - Phone track is active."
            } else {
                "Profile is running - Phone track is paused."
            })
            .setContentInfo("Profile - Some Profile here")
            .setOnlyAlertOnce(true)
            .build()
        val notificationManager = this.getSystemService(NotificationManager::class.java)
        notificationManager.notify(1,notification)
    }

    enum class Actions {
        START, STOP
    }
}