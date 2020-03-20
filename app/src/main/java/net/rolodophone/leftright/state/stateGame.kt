package net.rolodophone.leftright.state

import net.rolodophone.leftright.components.gui
import net.rolodophone.leftright.components.player
import net.rolodophone.leftright.components.road
import net.rolodophone.leftright.components.weather

object stateGame : State {
    override fun reset() {
        player.reset()
        road.reset()
    }

    override fun update() {
        road.update()
        player.update()
    }

    override fun draw() {
        road.draw()
        player.draw()
        weather.draw()
        gui.status.draw()
        gui.game.draw()
    }
}