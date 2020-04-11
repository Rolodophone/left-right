package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.main.w

class Oil(state: StateGame) : Spawnable(state, w(135), w(135)) {
    override val companion = Companion
    companion object : Spawnable.SpawnableCompanion(80, { Oil(it) })

    override var img = state.bitmaps.oil
    override val z = 1
}