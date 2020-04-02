package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.main.w

class Oil(road: Road) : Spawnable(road, w(135), w(135)) {
    override val companion = Companion
    companion object : Spawnable.SpawnableCompanion(80, { road -> Oil(road) })

    override var img = road.ctx.bitmaps.oil
    override val z = 1
}