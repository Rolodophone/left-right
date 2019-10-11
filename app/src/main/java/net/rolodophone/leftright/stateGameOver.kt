package net.rolodophone.leftright

object stateGameOver : State {
    override fun reset() {
        gui.gameOver = gui.GameOver()
    }

    override fun update() {
        gui.gameOver.update()
    }

    override fun draw() {
        road.draw()
        player.draw()
        gui.gameOver.draw()
        if (isDebug) gui.debug.draw()
    }
}