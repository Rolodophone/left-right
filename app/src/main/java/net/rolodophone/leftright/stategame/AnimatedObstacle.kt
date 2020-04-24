package net.rolodophone.leftright.stategame

import android.graphics.Bitmap
import android.os.SystemClock

abstract class AnimatedObstacle(state: StateGame, w: Float, h: Float, private val imgList: List<Bitmap>, lane: Int = state.road.randomLane(true)) : Obstacle(state, w, h, imgList[0], lane) {
    abstract val frameDuration: Int

    var timeSpriteLastChanged = 0L
    var imgNum = 0

    var animationPaused = false

    override fun update() {
        super.update()

        if (!animationPaused) {
            val currentTime = SystemClock.elapsedRealtime()

            if (currentTime - timeSpriteLastChanged > frameDuration) {
                imgNum = (imgNum + 1) % imgList.size
                img = imgList[imgNum]

                timeSpriteLastChanged = currentTime
            }
        }
    }
}