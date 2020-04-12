package net.rolodophone.leftright.stategame

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.RectF
import androidx.annotation.CallSuper
import net.rolodophone.leftright.main.*

abstract class Obstacle(state: StateGame, w: Float, h: Float, final override var img: Bitmap) : Object(state, w, h, state.road.randomLane(true)) {
    abstract val deathType: DeathType


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