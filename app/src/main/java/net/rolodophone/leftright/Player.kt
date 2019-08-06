package net.rolodophone.leftright

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Player(context: Context) : GameObject {
    private var img: Bitmap
    var ySpeed = height / 2f
    var xSpeed = width * 5f
    var fuel = 50f

    private val w = width / 4f
    private val h = w * 2f
    private val x = (width - w) / 2f
    private val y = height - (h / 2f)
    var dim = RectF(
        x,
        y,
        x + w,
        y + h
    )

    var goingL = false
    var goingR = false

    //what lane the car is in. 0 represents left most lane and so on
    var lane: Int

    //The x coordinate of the left side of the car, if it was in the Nth lane
    private var laneXs = mutableListOf<Float>()

    init {
        val opts = BitmapFactory.Options()
        opts.inScaled = false
        img = BitmapFactory.decodeResource(context.resources, R.drawable.car1, opts)

        for (i in 1..road.numLanes) {
            laneXs.add((2f * width * i - width - road.numLanes * w) / (2f * road.numLanes))
        }

        lane = 1
    }


    override fun update() {
        fuel -= 2f / fps

        if (goingL) {
            dim.offset(-xSpeed / fps, 0f)

            if (dim.left <= laneXs[lane - 1]) {
                goingL = false
                dim.offsetTo(laneXs[lane - 1], dim.top)
                lane = lane - 1
            }
        } else if (goingR) {
            dim.offset(xSpeed / fps, 0f)

            if (dim.left >= laneXs[lane + 1]) {
                goingR = false
                dim.offsetTo(laneXs[lane + 1], dim.top)
                lane = lane + 1
            }
        }
    }
    override fun draw() {
        canvas.drawBitmap(img, null, dim, paint)
    }
}