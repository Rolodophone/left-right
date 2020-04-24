package net.rolodophone.leftright.stategame

import android.graphics.Color
import net.rolodophone.leftright.main.*

class Background(override val state: StateGame) : Component {
    private val lineW = w(3)
    private val lineH = w(100)
    private val lineGap = w(40)

    var topLineBottom = 0f

    override fun update() {
        topLineBottom += state.road.cameraSpeed / fps
        topLineBottom %= lineH + lineGap
    }

    override fun draw() {
        //draw background
        when (state.area) {
            0 -> paint.color = Color.rgb(111, 111, 111)
            1 -> paint.color = Color.rgb(239, 227, 134)
        }
        canvas.drawRect(0f, 0f, width, height, paint)

        //draw lines
        var y = topLineBottom
        while (y <= height + lineH) {

            for (i in 1 until state.road.numLanes) {
                canvas.drawRect(
                    ((width * i) / state.road.numLanes) - (lineW / 2),
                    y - lineH,
                    ((width * i) / state.road.numLanes) + (lineW / 2),
                    y,
                    whitePaint
                )
            }

            y += lineH + lineGap
        }
    }
}