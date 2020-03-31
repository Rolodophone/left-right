package net.rolodophone.leftright.stategame.road

import net.rolodophone.leftright.main.w

class Fuel private constructor(val road: Road) : Item(road) {
    override val Factory = Companion
    companion object : Item.ItemFactory() {
        override val averageSpawnMetres = 15
        override fun constructor(road: Road) = Fuel(road)
    }

    override var img = road.ctx.bitmaps.fuel
    override val isObstacle = false
    override val dim = rectFFromDim(w(45), w(51.4285714286f))
    override val z = 3

    override fun onTouch() {
        road.state.player.fuel += 50f
        road.itemsToDel.add(this)
        road.ctx.sounds.playFuel()
    }
}