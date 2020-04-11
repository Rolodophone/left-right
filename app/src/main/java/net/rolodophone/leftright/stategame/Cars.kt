package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.main.bitmapPaint
import net.rolodophone.leftright.main.canvas
import net.rolodophone.leftright.main.fps
import net.rolodophone.leftright.main.w
import net.rolodophone.leftright.resources.Bitmaps

abstract class Car(private val imgGroup: Bitmaps.Car, var speed: Float, state: StateGame) : Obstacle(state, w(90), w(180)) {
    override var img = imgGroup.clean
    override val z = 4

    override val deathType = DeathType.CAR

    var spinSpeed = 0f
    private var deceleration = 0f
    var rotation = 0f

    var isCrashed = false

    override fun onTouch(otherObject: Object) {
        if (otherObject is Obstacle) {
            if (!isCrashed) {
                img = imgGroup.hit
                state.sounds.playHit()
                isCrashed = true
            }
        }

        when (otherObject) {
            is Oil -> {
                state.sounds.playOil()

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
        if (!isCrashed) dim.offset(0f, -speed / fps)

        //handle oil
        if (spinSpeed != 0f) {
            rotation += spinSpeed / fps
            spinSpeed -= 144 / fps
            speed -= deceleration / fps

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
