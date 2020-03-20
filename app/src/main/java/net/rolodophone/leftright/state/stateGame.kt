package net.rolodophone.leftright.state

import net.rolodophone.leftright.components.*

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
        statusBar.draw()
        gameOverlay.draw()
    }
}