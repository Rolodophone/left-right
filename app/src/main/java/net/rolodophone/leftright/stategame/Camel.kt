package net.rolodophone.leftright.stategame

import android.graphics.RectF
import net.rolodophone.leftright.main.*
import kotlin.random.Random

class Camel(state: StateGame) : AnimatedObstacle(state, w(170), w(170 * (23/31f)), state.bitmaps.camel) {
    override val companion = Companion
    companion object : ObjectCompanion(15, { Camel(it) })

    override val frameDuration = 333
    override val deathType = DeathType.CAMEL
    override var weight = 0.5f
    override val z = 5

    private val goingRight = Random.nextBoolean()
    private val xSpeed = if (goingRight) w(80) else -w(80)

    override val dim: RectF

    init {
        val spawnHeight = (0..halfHeight.toInt()).random().toFloat()
        dim =
            if (goingRight) RectF(
                -w,
                spawnHeight - h,
                0f,
                spawnHeight
            )
            else RectF(
                width,
                spawnHeight - h,
                width + w,
                spawnHeight
            )

        lane =
            if (goingRight) 0
            else state.road.numLanes - 1
    }


    override fun update() {
        super.update()

        dim.offset(xSpeed / fps, 0f)
    }

    override fun draw() {
        canvas.save()
        if (goingRight) canvas.scale(-1f, 1f, dim.centerX(), 0f)
        canvas.drawBitmap(img, null, dim, bitmapPaint)
        canvas.restore()
        drawParticles()
    }
}