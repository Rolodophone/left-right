package net.rolodophone.leftright.stategame

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.RectF
import androidx.annotation.CallSuper
import net.rolodophone.leftright.main.*
import kotlin.math.pow
import kotlin.math.sqrt

abstract class Obstacle(state: StateGame, w: Float, h: Float, final override var img: Bitmap) : Object(state, w, h, state.road.randomLane(true)) {
    abstract val deathType: DeathType


    inner class Particle(x: Float, y: Float, gradient: Float, colour: Int) {
        private val width = w(gaussianRandomFloat(8f, 1f))
        private var timeLeft = 1f
        private val timeMoving = w(gaussianRandomFloat(5f, 2f))
        private val dim = RectF(
            x - width/2,
            y - width/2,
            x + width/2,
            y + width/2
        )
        private val paint = Paint()
        private val xSpeed: Float
        private val ySpeed: Float

        init {
            val lateralSpeed = w(gaussianRandomFloat(0f, 150f))
            val forwardSpeed = w(gaussianRandomFloat(0f, 30f))

            if (gradient == Float.NEGATIVE_INFINITY || gradient == Float.POSITIVE_INFINITY) {
                xSpeed = forwardSpeed
                ySpeed = lateralSpeed
            }
            else {
                //use Pythagoras to work out x and y speeds based on lateral and forward speeds
                val tmpFrac = sqrt(gradient.pow(2) + 1) / (gradient.pow(2) + 1)
                xSpeed = (lateralSpeed * tmpFrac) + (forwardSpeed * tmpFrac * gradient)
                ySpeed = (lateralSpeed * tmpFrac * gradient) + (forwardSpeed * tmpFrac)
            }

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

    init {
        val colours = mutableListOf<Int>()

        for (x in 0 until img.width) for (y in 0 until img.height) {
            val colour = img.getPixel(x, y)
            if (colour != 0) colours.add(colour)
        }

        val newParticleTypes = mutableListOf<ParticleType>()
        for (colour in colours.toSet()) {
            newParticleTypes.add(ParticleType(colours.count{ it == colour } / 5f, colour))
        }

        particleTypes = newParticleTypes
    }


    @CallSuper
    override fun onTouch(otherObject: Object) {
        if (otherObject is Obstacle) {

            val intersection = RectF(dim)
            intersection.intersect(otherObject.dim)
            val x = intersection.centerX()
            val y = intersection.centerY()

            val gradient = (dim.centerX() - otherObject.dim.centerX()) / (otherObject.dim.centerY() - dim.centerY())

            explode(x, y, gradient)
        }
    }


    override fun update() {
        super.update()
        for (particle in particles) particle.update()
        for (particle in particlesToDel) particles.remove(particle)
    }


    override fun draw() {
        super.draw()
        drawParticles()
    }


    fun drawParticles() {
        for (particle in particles) particle.draw()
    }


    private fun explode(x: Float, y: Float, gradient: Float) {
        for (particleType in particleTypes) {
            repeat(gaussianRandomInt(particleType.averageNumber, particleType.averageNumber / 7)) {
                particles.add(Particle(x, y, gradient, particleType.colour))
            }
        }
    }
}