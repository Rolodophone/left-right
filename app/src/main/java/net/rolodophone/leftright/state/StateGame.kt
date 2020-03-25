package net.rolodophone.leftright.state

import net.rolodophone.leftright.main.MainActivity

class StateGame(override val ctx: MainActivity) : State {
    override fun reset() {
        ctx.player.reset()
        ctx.road.reset()
    }

    override fun update() {
        ctx.road.update()
        ctx.player.update()
    }

    override fun draw() {
        ctx.road.draw()
        ctx.player.draw()
        ctx.weather.draw()
        ctx.statusBar.draw()
        ctx.gameOverlay.draw()
    }
}