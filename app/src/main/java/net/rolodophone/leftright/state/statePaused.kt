package net.rolodophone.leftright.state

import net.rolodophone.leftright.components.*

object statePaused : State {
    override fun reset() {
    }

    override fun update() {
    }

    override fun draw() {
        road.draw()
        player.draw()
        weather.draw()
        statusBar.draw()
        pausedOverlay.draw()
    }
}