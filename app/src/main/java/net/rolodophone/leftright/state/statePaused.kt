package net.rolodophone.leftright.state

import net.rolodophone.leftright.main.MainView.c

object statePaused : State {
    override fun reset() {
    }

    override fun update() {
    }

    override fun draw() {
        c.road.draw()
        c.player.draw()
        c.weather.draw()
        c.statusBar.draw()
        c.pausedOverlay.draw()
    }
}