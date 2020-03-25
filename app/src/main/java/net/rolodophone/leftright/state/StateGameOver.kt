package net.rolodophone.leftright.state

import net.rolodophone.leftright.main.MainActivity

class StateGameOver(override val ctx: MainActivity) : State {

    override fun reset() {
        ctx.gameOverOverlay.reset()
    }

    override fun update() {
        ctx.gameOverOverlay.update()
    }

    override fun draw() {
        ctx.road.draw()
        ctx.player.draw()
        ctx.weather.draw()
        ctx.gameOverOverlay.draw()
    }
}