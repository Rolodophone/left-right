package net.rolodophone.leftright.stategame.road

import net.rolodophone.leftright.main.fps
import net.rolodophone.leftright.main.w
import net.rolodophone.leftright.resources.Bitmaps
import net.rolodophone.leftright.stategame.DeathType

abstract class Car(private val imgGroup: Bitmaps.Car, private var speed: Float, val road: Road) : Item(road) {
    override var img = imgGroup.clean
    override val isObstacle = true
    override val dim = rectFFromDim(w(87.5f), w(175))
    override val z = 4

    private var spinSpeed = 0f
    private var deceleration = 0f

    var isCrashed = false
        set(value) {
            img = imgGroup.hit
            road.ctx.sounds.playHit()
            field = value
        }

    override fun onTouch() {
        road.state.player.die(DeathType.CAR, this)
    }

    override fun update() {
        super.update()

        if (!isCrashed) {
            //move upwards
            dim.offset(0f, -speed / fps)

            //crash if touching another obstacle
            for (item in road.items.filter { it.isObstacle && it != this}) if (isTouching(item.dim)) {
                isCrashed = true
                if (item is Car) item.isCrashed = true
            }
        }

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

    fun oil() {
        road.ctx.sounds.playOil()

        //one full turn every second
        spinSpeed = 720f
        //lose half your speed over 5 seconds
        deceleration = (speed / 2f) / 5f
        //reset rotation
        rotation = 0f
    }
}

class Car1(road: Road) : Car(road.ctx.bitmaps.car1, w(50), road) {
    override val companion = Companion
    companion object : Item.Companion() {
        override val averageSpawnMetres = 15
        override fun new(road: Road) { road.items.add(Car1(road)) }
    }
}

class Car2(road: Road) : Car(road.ctx.bitmaps.car2, w(100), road) {
    override val companion = Companion
    companion object : Item.Companion() {
        override val averageSpawnMetres = 20
        override fun new(road: Road) { road.items.add(Car2(road)) }
    }
}

class Car3(road: Road) : Car(road.ctx.bitmaps.car3, w(150), road) {
    override val companion = Companion
    companion object : Item.Companion() {
        override val averageSpawnMetres = 25
        override fun new(road: Road) { road.items.add(Car3(road)) }
    }
}

class Car4(road: Road) : Car(road.ctx.bitmaps.car4, w(200), road) {
    override val companion = Companion
    companion object : Item.Companion() {
        override val averageSpawnMetres = 30
        override fun new(road: Road) { road.items.add(Car4(road)) }
    }
}

class Car5(road: Road) : Car(road.ctx.bitmaps.car5, w(250), road) {
    override val companion = Companion
    companion object : Item.Companion() {
        override val averageSpawnMetres = 35
        override fun new(road: Road) { road.items.add(Car5(road)) }
    }
}

class Car6(road: Road) : Car(road.ctx.bitmaps.car6, w(300), road) {
    override val companion = Companion
    companion object : Item.Companion() {
        override val averageSpawnMetres = 40
        override fun new(road: Road) { road.items.add(Car6(road)) }
    }
}
