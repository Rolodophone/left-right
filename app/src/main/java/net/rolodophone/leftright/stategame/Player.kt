package net.rolodophone.leftright.stategame

import android.graphics.RectF
import net.rolodophone.leftright.main.*
import net.rolodophone.leftright.stategame.road.Car
import net.rolodophone.leftright.stategame.road.Item

class Player(override val ctx: MainActivity, override val state: StateGame) : Component {

    //The x coordinate of the left side of the car, if it was in the Nth lane
    private var laneXs = mutableListOf<Float>()

    init {
        for (i in 0 until state.road.numLanes) {
            laneXs.add(state.road.centerOfLane(i) - w(35))
        }
    }


    private var img = ctx.bitmaps.car1.clean

    var dim = RectF(
        w(145),
        height - w(70),
        w(215),
        height + w(70)
    )

    //the image dim (the visible dimensions of the car) is always 1/4 larger than dim
    // (meaning the hitbox, dim, is always 20% smaller than imgDim
    var imgDim: RectF
        get() = dim.scaled(10/8f)
        set(value) {
            dim = value.scaled(8/10f)
        }

    val xSpeed = w(1800)
    var ySpeed = 0f
    var fuel = 50f
    var distance = 0f
    var coins = 0
    var causeOfDeath = DeathType.NONE
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


    override fun update() {
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

    override fun draw() {
        canvas.save()
        canvas.rotate(rotation, dim.centerX(), dim.centerY())
        canvas.drawBitmap(
            img, null,
            imgDim,
            bitmapPaint
        )
        canvas.restore()
    }


    fun die(deathType: DeathType, item: Item?) {
        causeOfDeath = deathType

        if (item != null) {
            ctx.sounds.playHit()
            img = ctx.bitmaps.car1.hit
            if (item is Car) item.isCrashed = true
        }

        state.state = StateGame.State.GAME_OVER
    }


    fun oil() {
        ctx.sounds.playOil()

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

        if (lane != state.road.numLanes - 1) {
            goingR = true
            ctx.sounds.playTap()
        }

        goingL = false
    }
}