package net.rolodophone.leftright

object stateGameOver : State {
    var lengthMoved = 0f
    var guiGameOver = gui.GameOver()

    override fun reset() {
        guiGameOver = gui.GameOver()
    }

    override fun update() {
        guiGameOver.update()

        if (lengthMoved < w(12)) {
            road.update()
            lengthMoved += player.ySpeed / fps
        }
    }

    override fun draw() {
        road.draw()
        road.drawItems()
        player.draw()
        guiGameOver.draw()
        if (isDebug) gui.debug.draw()
    }
}