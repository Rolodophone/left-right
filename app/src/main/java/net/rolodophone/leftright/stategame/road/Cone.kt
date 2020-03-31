package net.rolodophone.leftright.stategame.road

import net.rolodophone.leftright.main.w
import net.rolodophone.leftright.stategame.DeathType

class Cone(val road: Road) : Item(road) {
    override val Factory = Companion
    companion object : Item.ItemFactory() {
        override val averageSpawnMetres = 60
        override fun constructor(road: Road) = Cone(road)
    }

    override var img = road.ctx.bitmaps.cone
    override val isObstacle = true
    override val dim = rectFFromDim(w(45), w(51.4285714286f))
    override val z = 2

    override fun onTouch() {
        road.state.player.die(DeathType.CONE, this)
    }
}