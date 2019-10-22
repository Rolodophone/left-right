package net.rolodophone.leftright

object stateGame : State {
    override fun reset() {
        player.reset()
        road.reset()
    }

    override fun update() {
        road.update()
        player.update()
//        gui.game.update()
    }

    override fun draw() {
        road.draw()
        road.drawItems()
        player.draw()
        gui.status.draw()
        gui.game.draw()
        if (isDebug) gui.debug.draw()
    }
}