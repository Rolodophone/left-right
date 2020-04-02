package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.main.*

class Background(val road: Road) {
    private val lineW = w(3)
    private val lineH = w(100)
    private val lineGap = w(40)

    var topLineBottom = 0f

    fun update() {
        topLineBottom += road.state.player.ySpeed / fps
        topLineBottom %= lineH + lineGap
    }

    fun draw() {
        //draw background
        canvas.drawRGB(111, 111, 111)

        //draw lines
        var y = topLineBottom
        while (y <= height + lineH) {

            for (i in 1 until road.numLanes) {
                canvas.drawRect(
                    ((width * i) / road.numLanes) - (lineW / 2),
                    y - lineH,
                    ((width * i) / road.numLanes) + (lineW / 2),
                    y,
                    whitePaint
                )
            }

            y += lineH + lineGap
        }
    }
}