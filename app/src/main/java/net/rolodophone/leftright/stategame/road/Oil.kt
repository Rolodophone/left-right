package net.rolodophone.leftright.stategame.road

import net.rolodophone.leftright.main.w

class Oil(val road: Road) : Item(road) {
    override val Factory = Companion
    companion object : Item.ItemFactory() {
        override val averageSpawnMetres = 80
        override fun constructor(road: Road) = Oil(road)
    }

    override var img = road.ctx.bitmaps.oil
    override val isObstacle = false
    override val dim = rectFFromDim(w(135), w(135))
    override val z = 1

    override fun onTouch() {
        road.state.player.oil()
        isDisabled = true
    }

    override fun update() {
        super.update()
        if (!isDisabled) {
            for (item in road.items.minus(this)) {
                if (isTouching(item.dim) && item is Car) {
                    item.oil()
                    isDisabled = true
                }
            }
        }
    }
}