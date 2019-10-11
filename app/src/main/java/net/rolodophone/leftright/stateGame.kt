package net.rolodophone.leftright

object stateGame : State {
    override fun reset() {
        player = Player()
        road = Road()
    }

    override fun update() {
        road.update()
        player.update()
    }

    override fun draw() {
        road.draw()
        player.draw()
        gui.status.draw()
        gui.game.draw()
        if (isDebug) gui.debug.draw()
    }
}