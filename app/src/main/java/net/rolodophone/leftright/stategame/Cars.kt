package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.main.*
import net.rolodophone.leftright.resources.Bitmaps

abstract class Car(private val imgGroup: Bitmaps.Car, var speed: Float, state: StateGame) : Obstacle(state, w(90), w(180), imgGroup.clean) {
    override val z = 4

    override val deathType = DeathType.CAR

    var spinSpeed = 0f
    private var deceleration = 0f
    var rotation = 0f

    var isCrashed = false

    var isOiling = false

    override fun onTouch(otherObject: Object) {
        super.onTouch(otherObject)

        if (otherObject is Obstacle) {
            state.sounds.playHit()
            isCrashed = true
            spinSpeed = gaussianRandomFloat(0f, 50f)

            if (otherObject.dim.top < this.dim.top) {
                img = imgGroup.hit //only show the crashed at the top sprite if the crash is at the top
                speed = 0f
            }
            else {
                speed += w(300)
                deceleration = w(500)
            }
        }

        when (otherObject) {
            is Oil -> {
                state.sounds.playOil()
                isOiling = true

                //one full turn every second
                spinSpeed = 720f
                //lose half your speed over 5 seconds
                deceleration = (speed / 2f) / 5f
                //reset rotation
                rotation = 0f
            }
        }
    }

    override fun update() {
        super.update()

        //move upwards
        dim.offset(0f, -speed / fps)

        speed -= deceleration / fps
        if (speed < 0f) speed = 0f

        //handle oil
        if (spinSpeed != 0f) {
            rotation += spinSpeed / fps

            spinSpeed -= 144 / fps
            if (spinSpeed < 0f) spinSpeed = 0f

            if (isOiling && spinSpeed == 0f) {
                rotation = 0f
                isOiling = false
            }
        }
    }

    override fun draw() {
        canvas.save()
        canvas.rotate(rotation, dim.centerX(), dim.centerY())
        canvas.drawBitmap(img, null, imgDim, bitmapPaint)
        canvas.restore()

        drawParticles()
    }
}

class Car1(state: StateGame) : Car(state.bitmaps.car1, w(250), state) {
    override val companion = Companion
    companion object : ObjectCompanion(15, { Car1(it) })
}

class Car2(state: StateGame) : Car(state.bitmaps.car2, w(300), state) {
    override val companion = Companion
    companion object : ObjectCompanion(20, { Car2(it) })
}

class Car3(state: StateGame) : Car(state.bitmaps.car3, w(350), state) {
    override val companion = Companion
    companion object : ObjectCompanion(25, { Car3(it) })
}

class Car4(state: StateGame) : Car(state.bitmaps.car4, w(400), state) {
    override val companion = Companion
    companion object : ObjectCompanion(30, { Car4(it) })
}

class Car5(state: StateGame) : Car(state.bitmaps.car5, w(450), state) {
    override val companion = Companion
    companion object : ObjectCompanion(35, { Car5(it) })
}

class Car6(state: StateGame) : Car(state.bitmaps.car6, w(650), state) {
    override val companion = Companion
    companion object : ObjectCompanion(40, { Car6(it) })
}
