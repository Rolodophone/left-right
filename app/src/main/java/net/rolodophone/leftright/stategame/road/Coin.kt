package net.rolodophone.leftright.stategame.road

import android.os.SystemClock
import net.rolodophone.leftright.main.fps
import net.rolodophone.leftright.main.w

class Coin(val road: Road) : Item(road) {
    override val Factory = Companion
    companion object : Item.ItemFactory() {
        override val averageSpawnMetres = 3
        override fun constructor(road: Road) = Coin(road)
    }

    override var img = road.ctx.bitmaps.coin
    override val isObstacle = false
    override val dim = rectFFromDim(w(45), w(45))
    override val z = 3

    init {
        dim.offset(-w(5), 0f)
    }

    var animationStage = 0f
    var timeSpriteLastChanged = 0L
    var shineNum = -2

    override fun onTouch() {
        road.state.player.coins += 1
        road.ctx.sounds.playCoin()
        road.itemsToDel.add(this)
    }

    override fun update() {
        super.update()

        if (animationStage % 2 <= 1) dim.offset(w(20) / fps, 0f) else dim.offset(
            w(
                -20
            ) / fps, 0f)

        if (SystemClock.elapsedRealtime() - timeSpriteLastChanged > 70) {
            if (shineNum <= 6) {
                shineNum += 1
                if (shineNum >= 0) img = road.ctx.bitmaps.coinShining[shineNum]
                timeSpriteLastChanged = SystemClock.elapsedRealtime()
            } else img = road.ctx.bitmaps.coin
        }

        animationStage += 2f / fps
    }
}