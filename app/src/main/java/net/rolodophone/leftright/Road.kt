package net.rolodophone.leftright

import android.content.Context

class Road(context: Context) {
    companion object {
        val NUM_LANES = 3
    }

    val lineW = w(3)
    val lineH = w(100)
    val lineGap = w(40)

    var topLineBottom = 0f

    var fuels = ItemType(
        context,
        R.drawable.fuel,
        w(45),
        w(45) * 8f / 7f,
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
        w(45),
        w(45),
        10
    ) {
        for (item in it.list) if (item.dim.bottom >= player.dim.top && item.dim.top < player.dim.bottom && item.lane == player.lane) {
            player.die("Crashed into cone")
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
                    whitePaint
                )
            }

            y += lineH + lineGap
        }


        //draw items
        for (item in items) item.draw()
    }
}