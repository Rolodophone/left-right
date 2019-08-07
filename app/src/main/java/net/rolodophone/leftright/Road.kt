package net.rolodophone.leftright

import android.content.Context
import android.graphics.Color

class Road(context: Context) {
    companion object {
        const val NUM_LANES = 3
    }
    
    val lineW = width / 128f
    val lineH = height / 6f
    val lineGap = height / 10f

    var topLineBottom = 0f

    var fuels = ItemType(
        context,
        R.drawable.fuel,
        player.dim.width() * (1f / 2f),
        player.dim.width() * (4f / 7f),
        10
    ) {
        for (item in it.list) if (item.dim.bottom >= player.dim.top && item.lane == player.lane) {
            player.fuel += 50f
            it.toDel.add(item)
        }
    }

    var cones = ItemType(
        context, R.drawable.traffic_cone,
        player.dim.width() * (3f / 4f),
        player.dim.width() * (3f / 4f),
        10
    ) {
        for (item in it.list) if (item.dim.bottom >= player.dim.top && item.lane == player.lane) {
            gui.gameOver()
        }
    }


    fun update() {
        topLineBottom += player.ySpeed / fps
        topLineBottom %= lineH + lineGap

        fuels.update()
    }


    fun draw() {
        //draw background
        canvas.drawRGB(111, 111, 111)

        //draw lines
        paint.color = Color.rgb(255, 255, 255)
        var y = topLineBottom
        while (y <= height + lineH) {

            for (i in 1 until NUM_LANES) {
                canvas.drawRect(
                    ((width * i) / NUM_LANES) - (lineW / 2),
                    y - lineH,
                    ((width * i) / NUM_LANES) + (lineW / 2),
                    y,
                    paint
                )
            }

            y += lineH + lineGap
        }


        //draw fuels
        fuels.draw()
    }
}