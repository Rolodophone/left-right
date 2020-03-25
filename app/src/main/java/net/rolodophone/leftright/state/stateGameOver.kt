package net.rolodophone.leftright.state

import net.rolodophone.leftright.main.MainView.c

object stateGameOver : State {

    override fun reset() {
        c.gameOverOverlay.reset()
    }

    override fun update() {
        c.gameOverOverlay.update()
    }

    override fun draw() {
        c.road.draw()
        c.player.draw()
        c.weather.draw()
        c.gameOverOverlay.draw()
    }
}