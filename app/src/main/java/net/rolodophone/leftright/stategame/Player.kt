package net.rolodophone.leftright.stategame

import android.graphics.RectF
import net.rolodophone.leftright.main.*

class Player(state: StateGame) : Object(state, RectF(
    w(135),
    height - w(90),
    w(225),
    height + w(90)
)) {
    override var img = state.bitmaps.car1.clean
    override val w = w(90)
    override val h = w(180)
    override var lane = 1

    //The x coordinate of the left side of the car, if it was in the Nth lane
    private var laneXs = mutableListOf<Float>()

    init {
        for (i in 0 until state.road.numLanes) {
            laneXs.add(state.road.centerOfLane(i) - w(35))
        }
    }

    val xSpeed = w(1800)
    var ySpeed = 0f

    var fuel = 50f
    var distance = 0f
    var coins = 0

    lateinit var causeOfDeath: DeathType

    var goingL = false
    var goingR = false

    var spinSpeed = 0f
    var rotation = 0f
    var deceleration = 0f

    //helper variable to use ySpeed in m/s
    var ySpeedMps
        get() = (ySpeed / width)
        set(value) {
            ySpeed = (value) * width
        }


    override fun onTouch(otherObject: Object) {
        when (otherObject) {
            is Fuel -> {
                state.player.fuel += 50f
                state.road.itemsToDel.add(otherObject)
                state.sounds.playFuel()
            }
            is Oil -> {
                state.sounds.playOil()

                //one full turn every second
                spinSpeed = 720f
                //lose half your speed over 5 seconds
                deceleration = (ySpeed / 2f) / 5f
                //reset rotation
                rotation = 0f
            }
            is Coin -> {
                state.player.coins += 1
                state.road.itemsToDel.add(otherObject)
                state.sounds.playCoin()
            }
        }

        if (otherObject is Obstacle) {
            state.sounds.playHit()
            img = state.bitmaps.car1.hit
            die(otherObject.deathType)
        }
    }


    override fun update() {
        super.update()

        //handle fuel
        fuel -= 2f / fps
        if (fuel <= 0f) die(DeathType.FUEL)

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
        canvas.drawBitmap(img, null, imgDim, bitmapPaint)
        canvas.restore()
    }


    private fun die(deathType: DeathType) {
        causeOfDeath = deathType
        state.state = StateGame.State.GAME_OVER
    }


    fun turnLeft() {
        //if the player turns in between lanes, set the lane to the lane it would have gone to
        if (goingR) {
            lane++
        }

        if (lane != 0) {
            goingL = true
            state.sounds.playTap()
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
            state.sounds.playTap()
        }

        goingL = false
    }
}