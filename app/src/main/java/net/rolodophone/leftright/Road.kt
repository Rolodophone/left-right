package net.rolodophone.leftright

import android.content.Context
import android.graphics.Color

class Road(context: Context) : GameObject {

    val numLines = 2
    val lineW = width / 128f
    val lineH = height / 6f
    val lineGap = height / 10f

    var topLineBottom = 0f


    override fun update() {
        topLineBottom += player.speed / fps
        topLineBottom %= lineH + lineGap
    }


    override fun draw() {
        canvas.drawRGB(111, 111, 111)

        paint.color = Color.rgb(255, 255, 255)

        var y = topLineBottom
        while (y <= height + lineH) {

            for (i in 1..numLines) {
                canvas.drawRect(
                    ((width * i) / (numLines + 1)) - (lineW / 2),
                    y - lineH,
                    ((width * i) / (numLines + 1)) + (lineW / 2),
                    y,
                    paint
                )
            }

            y += lineH + lineGap
        }
    }
}