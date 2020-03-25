package net.rolodophone.leftright.state

import net.rolodophone.leftright.main.MainView.c

object stateGame : State {
    override fun reset() {
        c.player.reset()
        c.road.reset()
    }

    override fun update() {
        c.road.update()
        c.player.update()
    }

    override fun draw() {
        c.road.draw()
        c.player.draw()
        c.weather.draw()
        c.statusBar.draw()
        c.gameOverlay.draw()
    }
}