package net.rolodophone.leftright

import android.content.Context

class Road(context: Context) {
    companion object {
        val NUM_LANES = 3
    }

    val lineW = !3
    val lineH = !100
    val lineGap = !40

    var topLineBottom = 0f

    var fuels = ItemType(
        context,
        R.drawable.fuel,
        !45,
        !45 * 8f / 7f,
        10
    ) {
        for (item in it.list) if (item.dim.bottom >= player.dim.top && item.dim.top < player.dim.bottom && item.lane == player.lane) {
            player.fuel += 50f
            it.toDel.add(item)
        }
    }

    var cones = ItemType(
        context,
        R.drawable.traffic_cone,
        !45,
        !45,
        10
    ) {
        for (item in it.list) if (item.dim.bottom >= player.dim.top && item.dim.top < player.dim.bottom && item.lane == player.lane) {
            gameOver("Crashed into cone")
        }
    }

    val items = mutableListOf(fuels, cones)


    fun update() {
        topLineBottom += player.ySpeed / fps
        topLineBottom %= lineH + lineGap

        for (item in items) item.update()
    }


    fun draw() {
        //draw background
        canvas.drawRGB(111, 111, 111)

        //draw lines
        var y = topLineBottom
        while (y <= height + lineH) {

            for (i in 1 until NUM_LANES) {
                canvas.drawRect(
                    ((width * i) / NUM_LANES) - (lineW / 2),
                    y - lineH,
                    ((width * i) / NUM_LANES) + (lineW / 2),
                    y,
                    pWhite
                )
            }

            y += lineH + lineGap
        }


        //draw items
        for (item in items) item.draw()
    }
}