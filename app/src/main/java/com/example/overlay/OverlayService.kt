package com.example.overlay

import android.app.Service
import android.content.Intent
import android.graphics.*
import android.os.IBinder
import android.view.*

class OverlayService : Service() {
    private lateinit var view: OverlayView

    override fun onCreate() {
        super.onCreate()
        view = OverlayView(this)
        val lp = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT)
        (getSystemService(WINDOW_SERVICE) as WindowManager).addView(view, lp)
    }
    override fun onBind(i: Intent?) = null
}

class OverlayView(c: android.content.Context) : View(c) {
    private val paint = Paint().apply {
        style = Paint.Style.STROKE; strokeWidth = 10f; color = Color.RED
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Kotak dummy di tengah layar buat ngetes overlay jalan
        canvas.drawRect(300f, 300f, 600f, 600f, paint) 
    }
}
