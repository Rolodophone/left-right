package net.rolodophone.leftright.stategame

import android.graphics.RectF
import net.rolodophone.leftright.main.*

class Player(state: StateGame) : Car(state.bitmaps.car1, 0f, state) {
    override val companion = Companion
    companion object : Object.ObjectCompanion(-1, { Player(it) })

    init { lane = 1 }

    //move player so it's at the bottom not at the top when it spawns
    override val dim = RectF(
        state.road.centerOfLane(lane) - w / 2,
        height - h/2,
        state.road.centerOfLane(lane) + w / 2,
        height + h/2
    ).scaled(9/10f)

    override val z = 10

    //The x coordinate of the left side of the hitbox of the car, if it was in the Nth lane
    private var laneXs = List(state.road.numLanes) { state.road.centerOfLane(it) - dim.width()/2f }

    val xSpeed = w(1800)

    var fuel = 50f
    var distance = 0f
    var coins = 0

    lateinit var causeOfDeath: DeathType

    var goingL = false
    var goingR = false

    var isDoingVictory = false

    //helper variable to use speed in m/s
    var ySpeedMps
        get() = (speed / width)
        set(value) {
            speed = (value) * width
        }


    override fun onTouch(otherObject: Object) {
        super.onTouch(otherObject)

        if (otherObject is Obstacle) {
            //if (isDoingVictory) otherObject.is
            /*else*/ die(otherObject.deathType)
        }

        when (otherObject) {
            is Fuel -> {
                state.player.fuel += 50f
                state.road.itemsToDel.add(otherObject)
                state.sounds.playFuel()
            }
            is Coin -> {
                state.player.coins += 1
                state.road.itemsToDel.add(otherObject)
                state.sounds.playCoin()
            }
        }
    }


    override fun update() {
        super.update()

        if (!isCrashed) {
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
        }
    }


    private fun die(deathType: DeathType) {
        causeOfDeath = deathType
        state.state = StateGame.State.GAME_OVER
    }


    fun victory() {
        isDoingVictory = true
        state.sounds.playVictory()
        state.sounds.playVroom()
        state.road.cameraSpeed = speed
        acceleration = w(500)
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