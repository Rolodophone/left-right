package net.rolodophone.leftright

object stateGameOver : State {
    var lengthMoved = 0f

    override fun reset() {
        lengthMoved = 0f
        gui.gameOver.reset()
    }

    override fun update() {
        gui.gameOver.update()

        if (lengthMoved < w(12)) {
            road.update()
            lengthMoved += player.ySpeed / fps
        }
    }

    override fun draw() {
        road.draw()
        road.drawItems()
        player.draw()
        gui.gameOver.draw()
        if (isDebug) gui.debug.draw()
    }
}