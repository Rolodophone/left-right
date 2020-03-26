package net.rolodophone.leftright.components

import android.graphics.Bitmap
import android.graphics.RectF
import net.rolodophone.leftright.main.*

class Player(override val ctx: MainActivity) : Component {
    private val xSpeed = w(1800)

    //The x coordinate of the left side of the car, if it was in the Nth lane
    private var laneXs = mutableListOf<Float>()

    init {
        for (i in 0 until ctx.road.numLanes) {
            laneXs.add(ctx.road.centerOfLane(i) - w(35))
        }
    }


    private lateinit var img: Bitmap
    lateinit var dim: RectF

    //the image dim (the visible dimensions of the car) is always 1/4 larger than dim
    // (meaning the hitbox, dim, is always 20% smaller than imgDim
    var imgDim: RectF
        get() = dim.scaled(10/8f)
        set(value) {
            dim = value.scaled(8/10f)
        }

    var ySpeed = 0f
    var fuel = 0f
    var distance = 0f
    var coins = 0
    lateinit var causeOfDeath: DeathType
    var goingL = false
    var goingR = false
    var spinSpeed = 0f
    var rotation = 0f
    var deceleration = 0f
    var lane = 1

    //helper variable to use ySpeed in m/s
    var ySpeedMps
        get() = (ySpeed / width)
        set(value) {
            ySpeed = (value) * width
        }


    fun reset() {
        img = ctx.bitmaps.car1

        dim = RectF(
            w(145),
            height - w(70),
            w(215),
            height + w(70)
        )

        ySpeedMps = 0f
        fuel = 50f
        distance = 0f
        coins = 0
        causeOfDeath = DeathType.NONE

        goingL = false
        goingR = false

        //for the oil item
        spinSpeed = 0f
        rotation = 0f
        deceleration = 0f

        //what lane the car is in. 0 represents left most lane and so on
        lane = 1
    }


    fun update() {
        //handle fuel
        fuel -= 2f / fps
        if (fuel <= 0f) die(DeathType.FUEL, null)

        //increase distance travelled
        distance += ySpeedMps / fps

        //speed up over time (except at top speed)
        if (spinSpeed == 0f) {
            ySpeedMps += 0.3f / fps
            if (ySpeedMps > 2f) ySpeedMps = 2f
        }

        //handle switching lane
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

        //handle oil
        if (spinSpeed != 0f) {
            rotation += spinSpeed / fps
            spinSpeed -= 144 / fps
            ySpeed -= deceleration / fps

            if (spinSpeed < 0f) {
                spinSpeed = 0f
                rotation = 0f
            }
        }
    }

    fun draw() {
        canvas.save()
        canvas.rotate(rotation, dim.centerX(), dim.centerY())
        canvas.drawBitmap(
            img, null,
            imgDim,
            bitmapPaint
        )
        canvas.restore()
    }


    fun die(deathType: DeathType, item: Road.Item?) {
        causeOfDeath = deathType

        if (item != null) {
            ctx.sounds.playHit()

            img = ctx.bitmaps.car1_hit

            when (item) {
                is Road.Car1 -> item.img = ctx.bitmaps.car1_hit
                is Road.Car2 -> item.img = ctx.bitmaps.car2_hit
                is Road.Car3 -> item.img = ctx.bitmaps.car3_hit
                is Road.Car4 -> item.img = ctx.bitmaps.car4_hit
                is Road.Car5 -> item.img = ctx.bitmaps.car5_hit
                is Road.Car6 -> item.img = ctx.bitmaps.car6_hit
            }
        }

        ctx.state = ctx.stateGameOver
    }


    fun oil() {
        //one full turn every second
        spinSpeed = 720f
        //lose half your speed over 5 seconds
        deceleration = (ySpeed / 2f) / 5f
        //reset rotation
        rotation = 0f
    }


    fun turnLeft() {
        //if the player turns in between lanes, set the lane to the lane it would have gone to
        if (goingR) {
            lane++
        }

        if (lane != 0) {
            goingL = true
            ctx.sounds.playTap()
        }

        goingR = false
    }


    fun turnRight() {
        //if the player turns in between lanes, set the lane to the lane it would have gone to
        if (goingL) {
            lane--
        }

        if (lane != ctx.road.numLanes - 1) {
            goingR = true
            ctx.sounds.playTap()
        }

        goingL = false
    }
}