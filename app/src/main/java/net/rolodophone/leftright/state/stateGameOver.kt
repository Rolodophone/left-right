package net.rolodophone.leftright.state

import net.rolodophone.leftright.components.gameOverOverlay
import net.rolodophone.leftright.components.player
import net.rolodophone.leftright.components.road
import net.rolodophone.leftright.components.weather

object stateGameOver : State {

    override fun reset() {
        gameOverOverlay.reset()
    }

    override fun update() {
        gameOverOverlay.update()
    }

    override fun draw() {
        road.draw()
        player.draw()
        weather.draw()
        gameOverOverlay.draw()
    }
}