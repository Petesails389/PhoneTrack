package net.thesparrows.peter.phonetrack

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class TrackService: Service() {
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

    private fun start() {
        val notification = NotificationCompat.Builder(this, "tracking_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText("Phone track is running")
            .setContentInfo("Profile - Some Profile here")
            .build()
        startForeground(1,notification)
    }

    enum class Actions {
        START, STOP
    }
}