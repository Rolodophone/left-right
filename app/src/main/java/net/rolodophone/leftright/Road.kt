package net.rolodophone.leftright

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import android.util.Log

class Road(context: Context) : GameObject {

    var tileYs = mutableListOf<Float>()
    var img: Bitmap
    val tileW = width
    val tileH = width * (76f / 128f)


    init {
        val opts = BitmapFactory.Options()
        opts.inScaled = false
        img = BitmapFactory.decodeResource(context.resources, R.drawable.road, opts)

        var y = height - tileH
        while (y >= -2 * tileH) {
            tileYs.add(y)
            y -= tileH
        }
        Log.v("Road", "Initial tileYs: $tileYs")
    }


    override fun update() {
        //add player speed to each tileY to make the background move
        for (i in 0..tileYs.lastIndex) {
            tileYs[i] += player.speed / fps
        }

        //add and remove from tileYs so that the list doesn't run out
        val add = mutableListOf<Float>()
        for (tileY in tileYs) {
            if (tileY > height) {
                add.add(tileYs.last() - tileH)
            } else {
                break
            }
        }
        for (a in add) {
            tileYs.add(a)
            tileYs.removeAt(0)
        }
        Log.v("Road", "tileYs: $tileYs")
    }


    override fun draw() {
        for (tileY in tileYs) canvas.drawBitmap(img, null, RectF(0f, tileY, tileW, tileY + tileH), paint)
    }
}