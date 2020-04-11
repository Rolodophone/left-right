package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.main.w

class Cone(state: StateGame) : Obstacle(state, w(45), w(51.4285714286f)) {
    override val companion = Companion
    companion object : ObjectCompanion(60, { Cone(it) })

    override var img = state.bitmaps.cone
    override val z = 2

    override val deathType = DeathType.CONE
}