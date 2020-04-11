package net.rolodophone.leftright.stategame

import android.os.SystemClock
import net.rolodophone.leftright.main.fps
import net.rolodophone.leftright.main.w

class Coin(state: StateGame) : Object(state, w(45), w(45)) {
    override val companion = Companion
    companion object : Object.ObjectCompanion(3, { Coin(it) })

    override var img = state.bitmaps.coin
    override val z = 3

    init {
        dim.offset(-w(5), 0f)
    }

    var animationStage = 0f
    var timeSpriteLastChanged = 0L
    var shineNum = -2

    override fun update() {
        super.update()

        if (animationStage % 2 <= 1) dim.offset(w(20) / fps, 0f) else dim.offset(
            w(
                -20
            ) / fps, 0f)

        if (SystemClock.elapsedRealtime() - timeSpriteLastChanged > 70) {
            if (shineNum <= 6) {
                shineNum += 1
                if (shineNum >= 0) img = state.bitmaps.coinShining[shineNum]
                timeSpriteLastChanged = SystemClock.elapsedRealtime()
            } else img = state.bitmaps.coin
        }

        animationStage += 2f / fps
    }
}