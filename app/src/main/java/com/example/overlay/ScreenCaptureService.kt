package com.example.overlay

import android.app.*
import android.content.Intent
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.media.ImageReader
import android.media.projection.MediaProjectionManager
import android.os.*
import androidx.core.app.NotificationCompat

class ScreenCaptureService : Service() {
    override fun onStartCommand(intent: Intent?, f: Int, id: Int): Int {
        val code = intent!!.getIntExtra("resultCode", 0)
        val data = intent.getParcelableExtra<Intent>("data")!!
        val mpm = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        val proj = mpm.getMediaProjection(code, data)

        val dm = getSystemService(DISPLAY_SERVICE) as DisplayManager
        val metrics = resources.displayMetrics
        val reader = ImageReader.newInstance(metrics.widthPixels, metrics.heightPixels, PixelFormat.RGBA_8888, 2)
        
        proj.createVirtualDisplay("cap", metrics.widthPixels, metrics.heightPixels, metrics.densityDpi,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, reader.surface, null, null)

        val n = NotificationCompat.Builder(this, "cap")
            .setContentTitle("Capture aktif").setSmallIcon(android.R.drawable.ic_menu_camera).build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(NotificationChannel("cap","cap",NotificationManager.IMPORTANCE_LOW))
        }
        startForeground(1, n)
        return START_STICKY
    }
    override fun onBind(i: Intent?) = null
}
