package net.rolodophone.leftright

import android.graphics.Bitmap
import android.graphics.RectF

object player {
    private val xSpeed = w(1800)

    //The x coordinate of the left side of the car, if it was in the Nth lane
    private var laneXs = mutableListOf<Float>()

    init {
        for (i in 0 until road.numLanes) {
            laneXs.add(centerOfLane(i) - w(45))
        }
    }


    private lateinit var img: Bitmap
    lateinit var dim: RectF
    var ySpeed = 0f
    var fuel = 0f
    var distance = 0f
    var coins = 0
    lateinit var causeOfDeath: DeathType
    var goingL = false
    var goingR = false
    var lane = 1


    fun reset() {
        img = bitmaps.car1

        dim = RectF(
            w(135),
            height - w(90),
            w(225),
            height + w(90)
        )

        ySpeed = w(360)
        fuel = 50f
        distance = 0f
        coins = 0
        causeOfDeath = DeathType.NONE

        goingL = false
        goingR = false

        //what lane the car is in. 0 represents left most lane and so on
        lane = 1
    }


    fun update() {
        fuel -= 2f / fps
        if (fuel <= 0f) die(DeathType.FUEL, null)

        distance += (ySpeed / width * 4) / fps

        //speed up over time
        ySpeed += w(3) / fps

        if (goingL) {
            dim.offset(-xSpeed / fps, 0f)

            if (dim.left <= laneXs[lane - 1]) {
                goingL = false
                dim.offsetTo(laneXs[lane - 1], dim.top)
                lane -= 1
            }
        } else if (goingR) {
            dim.offset(xSpeed / fps, 0f)

            if (dim.left >= laneXs[lane + 1]) {
                goingR = false
                dim.offsetTo(laneXs[lane + 1], dim.top)
                lane += 1
            }
        }
    }

    fun draw() {
        canvas.drawBitmap(img, null, dim, whitePaint)
    }


    fun die(deathType: DeathType, item: ItemType.Item?) {
        causeOfDeath = deathType

        if (item != null) {
            img = when {
                goingL -> bitmaps.car1_hit_l
                goingR -> bitmaps.car1_hit_r
                else -> bitmaps.car1_hit_m
            }
        }

        state = stateGameOver
    }
}