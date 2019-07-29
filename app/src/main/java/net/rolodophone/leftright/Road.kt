package net.rolodophone.leftright

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Road(context: Context) : GameObject {

    var tileYs = mutableListOf<Int>()
    var img: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.road)
    val tileW = width
    val tileH = width * (76/128)


    init {
        img = Bitmap.createScaledBitmap(img, tileW, tileH, false)

        for (y in 0..height step tileH) {
            tileYs.add(y)
        }
    }


    override fun update() {
        for (i in 0..tileYs.lastIndex) {
            tileYs[i] += player.speed
        }
    }


    override fun draw() {
        for (tileY in tileYs) canvas.drawBitmap(img, 0f, tileY.toFloat(), paint)
    }
}