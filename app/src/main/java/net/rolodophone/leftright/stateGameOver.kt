package net.rolodophone.leftright

object stateGameOver : State {

    override fun reset() {
        gui.gameOver.reset()
    }

    override fun update() {
        gui.gameOver.update()
    }

    override fun draw() {
        road.draw()
        player.draw()
        weather.draw()
        gui.gameOver.draw()
    }
}