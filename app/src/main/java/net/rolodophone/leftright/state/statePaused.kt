package net.rolodophone.leftright.state

import net.rolodophone.leftright.components.gui
import net.rolodophone.leftright.components.player
import net.rolodophone.leftright.components.road
import net.rolodophone.leftright.components.weather

object statePaused : State {
    override fun reset() {
    }

    override fun update() {
    }

    override fun draw() {
        road.draw()
        player.draw()
        weather.draw()
        gui.status.draw()
        gui.paused.draw()
    }
}