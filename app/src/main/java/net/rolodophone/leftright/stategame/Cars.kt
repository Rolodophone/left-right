package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.main.bitmapPaint
import net.rolodophone.leftright.main.canvas
import net.rolodophone.leftright.main.fps
import net.rolodophone.leftright.main.w
import net.rolodophone.leftright.resources.Bitmaps

abstract class Car(private val imgGroup: Bitmaps.Car, private var speed: Float, road: Road) : Obstacle(road, w(90), w(180)) {
    override var img = imgGroup.clean
    override val z = 4

    override val deathType = DeathType.CAR

    private var spinSpeed = 0f
    private var deceleration = 0f
    private var rotation = 0f

    var isCrashed = false

    override fun onTouch(otherObject: Object) {
        when (otherObject) {
            is Obstacle, is Player -> {
                if (!isCrashed) {
                    img = imgGroup.hit
                    road.ctx.sounds.playHit()
                    isCrashed = true
                }
            }
            is Oil -> {
                road.ctx.sounds.playOil()

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

class Car1(road: Road) : Car(road.ctx.bitmaps.car1, w(250), road) {
    override val companion = Companion
    companion object : SpawnableCompanion(15, { road -> Car1(road)})
}

class Car2(road: Road) : Car(road.ctx.bitmaps.car2, w(300), road) {
    override val companion = Companion
    companion object : SpawnableCompanion(20, { road -> Car2(road)})
}

class Car3(road: Road) : Car(road.ctx.bitmaps.car3, w(350), road) {
    override val companion = Companion
    companion object : SpawnableCompanion(25, { road -> Car3(road)})
}

class Car4(road: Road) : Car(road.ctx.bitmaps.car4, w(400), road) {
    override val companion = Companion
    companion object : SpawnableCompanion(30, { road -> Car4(road)})
}

class Car5(road: Road) : Car(road.ctx.bitmaps.car5, w(450), road) {
    override val companion = Companion
    companion object : SpawnableCompanion(35, { road -> Car5(road)})
}

class Car6(road: Road) : Car(road.ctx.bitmaps.car6, w(650), road) {
    override val companion = Companion
    companion object : SpawnableCompanion(40, { road -> Car6(road)})
}
