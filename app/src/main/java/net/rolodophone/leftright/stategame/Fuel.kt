package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.main.w

class Fuel(road: Road) : Spawnable(road, w(45), w(51.4285714286f)) {
    override val companion = Companion
    companion object : Spawnable.SpawnableCompanion(15, {road -> Fuel(road)})

    override var img = road.ctx.bitmaps.fuel
    override val z = 3
}