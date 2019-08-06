package net.rolodophone.leftright

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Fuel(val lane: Int) {
    companion object {
        //average seconds between spawning
        const val spawnRate = 10

        val w = player.dim.width() * (1f / 2f)
        val h = w * (16f / 14f)

        val fuelsToDel = mutableListOf<Fuel>()

        lateinit var img: Bitmap

        fun init(context: Context) {
            val opts = BitmapFactory.Options()
            opts.inScaled = false
            img = BitmapFactory.decodeResource(context.resources, R.drawable.fuel, opts)
        }
    }

    private val x = centerOfLane(lane) - w / 2f
    val dim = RectF(
        centerOfLane(lane) - w / 2f,
        -h,
        centerOfLane(lane) - w / 2f + w,
        -h + h
    )


    fun update() {
        dim.offset(0f, player.ySpeed / fps)

        if (dim.top >= height) {
            fuelsToDel.add(this)
        }

        if (this.dim.bottom >= player.dim.top && this.lane == player.lane) {
            player.fuel += 50f
            Fuel.fuelsToDel.add(this)
        }
    }


    fun draw() {
        canvas.drawBitmap(img, null, dim, paint)
    }
}