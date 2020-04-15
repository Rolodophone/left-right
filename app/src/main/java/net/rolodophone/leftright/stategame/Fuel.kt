package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.main.w

class Fuel(state: StateGame) : Object(state, w(45), w(51.4285714286f)) {
    override val companion = Companion
    companion object : Object.ObjectCompanion(15, { Fuel(it) })

    override var img = state.bitmaps.fuel
    override val z = 3

    init {
        if (state.player.fuel > 100f) lane = -1
    }
}