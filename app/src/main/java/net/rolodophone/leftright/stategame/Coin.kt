package net.rolodophone.leftright.stategame

import android.os.SystemClock
import net.rolodophone.leftright.main.fps
import net.rolodophone.leftright.main.w

class Coin(state: StateGame) : AnimatedObject(state, w(45), w(45), state.bitmaps.coin) {
    override val companion = Companion
    companion object : Object.ObjectCompanion(3, { Coin(it) })

    override val z = 3
    override val frameDuration = 70

    init {
        dim.offset(-w(5), 0f)
        animationPaused = true
    }

    var animationStage = 0f
    var spawnTime = SystemClock.elapsedRealtime()
    var animationHasStarted = false

    override fun update() {
        super.update()

        // move left and right
        if (animationStage % 2 <= 1) dim.offset(w(20) / fps, 0f) else dim.offset(w(-20) / fps, 0f)
        animationStage += 2f / fps

        if (animationHasStarted && imgNum == 0) animationPaused = true

        if (!animationHasStarted && SystemClock.elapsedRealtime() - spawnTime > 100) {
            animationPaused = false
            animationHasStarted = true
        }
    }
}