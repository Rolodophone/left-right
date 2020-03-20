package net.rolodophone.leftright.components

import android.graphics.Color
import android.graphics.Paint
import net.rolodophone.leftright.main.*

object grid {
    val gridPaint = Paint()

    init {
        gridPaint.color = Color.argb(100, 255, 255, 255)
    }

    fun draw() {
        var x = w(20)
        while (x < width) {
            canvas.drawLine(x, 0f, x, height, gridPaint)
            x += w(20)
        }

        x = w(120)
        while (x < width) {
            canvas.drawLine(x, 0f, x, height, whitePaint)
            x += w(120)
        }

        var y = h(20)
        while (y < height) {
            canvas.drawLine(0f, y, width, y, gridPaint)
            y += h(20)
        }

        y = h(120)
        while (y < height) {
            canvas.drawLine(0f, y, width, y, whitePaint)
            y += h(120)
        }
    }
}