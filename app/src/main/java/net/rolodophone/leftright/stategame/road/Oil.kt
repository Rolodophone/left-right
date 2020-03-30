package net.rolodophone.leftright.stategame.road

import net.rolodophone.leftright.main.w

class Oil(val road: Road) : Item(road) {
    override val companion = Companion
    companion object : Item.Companion() {
        override val averageSpawnMetres = 80
        override fun new(road: Road) { road.items.add(Oil(road)) }
    }

    override var img = road.ctx.bitmaps.oil
    override val isObstacle = false
    override val dim = rectFFromDim(w(135), w(135))
    override val z = 1

    override fun onTouch() {
        road.state.player.oil()
        disabled = true
        road.ctx.sounds.playOil()
    }
}