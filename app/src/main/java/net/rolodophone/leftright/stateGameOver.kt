package net.rolodophone.leftright

object stateGameOver : State {
    var moved = false

    override fun reset() {
        moved = false
        gui.gameOver.reset()
    }

    override fun update() {
        gui.gameOver.update()

        if (!moved) {
            road.update()
            moved = true
        }
    }

    override fun draw() {
        road.draw()
        road.draw()
        player.draw()
        weather.draw()
        gui.gameOver.draw()
        if (isDebug) gui.debug.draw()
    }
}