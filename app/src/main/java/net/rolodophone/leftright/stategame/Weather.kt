package net.rolodophone.leftright.stategame

import android.graphics.Color
import android.graphics.Paint
import net.rolodophone.leftright.main.Component
import net.rolodophone.leftright.main.MainActivity
import net.rolodophone.leftright.main.w

class Weather(override val ctx: MainActivity, override val state: StateGame) : Component {
    private val rainPaint = Paint()

    init {
        rainPaint.strokeWidth = w(1)
        rainPaint.strokeCap = Paint.Cap.ROUND
        rainPaint.color = Color.argb(150, 5, 100, 15)
    }

    override fun update() {}

    override fun draw() {
        //canvas.drawLine(w(100), w(100), w(90), w(90), rainPaint)
        //canvas.drawARGB(80, 5, 100, 15)
    }
}