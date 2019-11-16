package net.rolodophone.leftright

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
        if (gui.showDebug) gui.debug.draw()
    }
}