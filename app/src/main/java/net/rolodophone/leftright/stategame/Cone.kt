package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.main.w

class Cone(road: Road) : Obstacle(road, w(45), w(51.4285714286f)) {
    override val companion = Companion
    companion object : SpawnableCompanion(60, { road: Road ->  Cone(road) })

    override var img = road.ctx.bitmaps.cone
    override val z = 2

    override val deathType = DeathType.CONE
}