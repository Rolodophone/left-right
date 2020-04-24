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
    val topSpeed = when (state.area) {
        0 -> 2f
        else -> 1.2f
    }

    var fuel = 50f
    var distance = 0f
    var coins = 0

    lateinit var causeOfDeath: DeathType

    var goingL = false
    var goingR = false
    var targetX = 0f

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
            die(otherObject.deathType)
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

        if (!isDead && !isDoingVictory) {
            //handle fuel
            fuel -= 2f / fps
            if (fuel <= 0f) die(DeathType.FUEL)

            //increase distance travelled
            distance += ySpeedMps / fps

            //speed up over time (except at top speed)
            if (spinSpeed == 0f) {
                ySpeedMps += 0.3f / fps
                if (ySpeedMps > topSpeed) ySpeedMps = topSpeed
            }

            //handle switching lane
            if (goingL) {
                dim.offset(-xSpeed / fps, 0f)

                if (dim.left <= targetX) {
                    goingL = false
                    dim.offsetTo(targetX, dim.top)
                }
            } else if (goingR) {
                dim.offset(xSpeed / fps, 0f)

                if (dim.left >= targetX) {
                    goingR = false
                    dim.offsetTo(targetX, dim.top)
                }
            }
        }
    }


    private fun die(deathType: DeathType) {
        causeOfDeath = deathType
        state.state = StateGame.State.GAME_OVER
        acceleration = -w(500)
        isDead = true
    }


    fun victory() {
        isDoingVictory = true
        state.sounds.playVictory()
        state.sounds.playVroom()
        state.road.cameraSpeed = speed
        acceleration = w(500)
    }


    fun turnLeft() {
        if (!isDoingVictory) {

            if (lane != 0) {
                goingL = true
                targetX = laneXs[lane - 1]
                state.sounds.playTap()
            }

            goingR = false
        }
    }


    fun turnRight() {
        //if the player turns in between lanes, set the lane to the lane it would have gone to
        if (!isDoingVictory) {

            if (lane != state.road.numLanes - 1) {
                goingR = true
                targetX = laneXs[lane + 1]
                state.sounds.playTap()
            }

            goingL = false
        }
    }
}