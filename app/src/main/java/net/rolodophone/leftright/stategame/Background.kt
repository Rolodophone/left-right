package net.rolodophone.leftright.stategame

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
        canvas.drawRGB(111, 111, 111)

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