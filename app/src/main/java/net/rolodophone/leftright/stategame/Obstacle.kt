package net.rolodophone.leftright.stategame

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.RectF
import androidx.annotation.CallSuper
import net.rolodophone.leftright.main.*

abstract class Obstacle(state: StateGame, w: Float, h: Float, final override var img: Bitmap) : Object(state, w, h, state.road.randomLane(true)) {
    inner class Particle(x: Float, y: Float, colour: Int) {
        private val width = w(gaussianRandomFloat(8f, 1f))
        private var timeLeft = 1f
        private val timeMoving = w(gaussianRandomFloat(2f, .5f))
        private val xSpeed = w(gaussianRandomFloat(0f, 50f))
        private val ySpeed = w(gaussianRandomFloat(0f, 50f))
        private val dim = RectF(
            x - width/2,
            y - width/2,
            x + width/2,
            y + width/2
        )
        private val paint = Paint()

        init {
            paint.color = colour
        }


        fun update() {
            dim.offset(0f, state.player.speed / fps)

            if (timeLeft > 0) {
                timeLeft -= timeMoving / fps
                dim.offset(xSpeed/fps, ySpeed/fps)
            }

            if (dim.isOffscreen()) particlesToDel.add(this)
        }

        fun draw() {
            canvas.drawRect(dim, paint)
        }
    }


    data class ParticleType(val averageNumber: Float, val colour: Int)


    private val particles = mutableListOf<Particle>()
    private val particlesToDel = mutableListOf<Particle>()

    private val particleTypes: List<ParticleType>

    abstract val deathType: DeathType
    abstract var weight: Float //higher means it moves less when hit

    var hitSpeedX = 0f //for moving slightly when hit
    var hitSpeedY = 0f
    var decelerationX = 0f //for only moving for a small amount of time
    var decelerationY = 0f

    init {
        val colours = mutableListOf<Int>()

        for (x in 0 until img.width) for (y in 0 until img.height) {
            val colour = img.getPixel(x, y)
            if (colour != 0) colours.add(colour)
        }

        val newParticleTypes = mutableListOf<ParticleType>()
        for (colour in colours.toSet()) {
            newParticleTypes.add(ParticleType(colours.count{ it == colour } / 10f, colour))
        }

        particleTypes = newParticleTypes
    }


    @CallSuper
    override fun onTouch(otherObject: Object) {
        if (otherObject is Obstacle) {
            val intersection = RectF(dim)
            intersection.intersect(otherObject.dim)
            explode(intersection)

            onHit(otherObject)
        }
    }


    open fun onHit(otherObstacle: Obstacle) {
        if (this.dim.top > otherObstacle.dim.top) {
            hitSpeedX = 0f
            hitSpeedY = 0f
        }
        else {
            if (otherObstacle is Car) {
                hitSpeedY = -otherObstacle.speed / weight

                if (otherObstacle is Player) {
                    if (otherObstacle.goingL) hitSpeedX = -otherObstacle.xSpeed / weight
                    else if (otherObstacle.goingR) hitSpeedX = otherObstacle.xSpeed / weight
                }

                hitSpeedX += w(gaussianRandomFloat(0f, 10f)) / weight

                decelerationX = hitSpeedX
                decelerationY = hitSpeedY
            }
        }
    }


    override fun update() {
        super.update()

        for (particle in particles) particle.update()
        for (particle in particlesToDel) particles.remove(particle)

        dim.offset(hitSpeedX / fps, hitSpeedY / fps)

        if (decelerationX != 0f && decelerationY != 0f) {
            if (hitSpeedX > 0f) {
                hitSpeedX -= decelerationX / fps
                if (hitSpeedX < 0f) {
                    hitSpeedX = 0f
                    decelerationX = 0f
                }
            }
            else {
                hitSpeedX -= decelerationX / fps
                if (hitSpeedX > 0f) {
                    hitSpeedX = 0f
                    decelerationX = 0f
                }
            }

            if (hitSpeedY > 0f) {
                hitSpeedY -= decelerationY / fps
                if (hitSpeedY < 0f) {
                    hitSpeedY = 0f
                    decelerationY = 0f
                }
            }
            else {
                hitSpeedY -= decelerationY / fps
                if (hitSpeedY > 0f) {
                    hitSpeedY = 0f
                    decelerationY = 0f
                }
            }
        }
    }


    override fun draw() {
        super.draw()
        drawParticles()
    }


    fun drawParticles() {
        for (particle in particles) particle.draw()
    }


    private fun explode(area: RectF) {
        for (particleType in particleTypes) {
            repeat(gaussianRandomInt(particleType.averageNumber, particleType.averageNumber / 7)) {

                val x = randomFloat(area.left, area.right)
                val y = randomFloat(area.top, area.bottom)
                particles.add(Particle(x, y, particleType.colour))
            }
        }
    }
}