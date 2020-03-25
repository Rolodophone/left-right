package net.rolodophone.leftright.state

import net.rolodophone.leftright.main.MainActivity

class StatePaused(override val ctx: MainActivity) : State {
    override fun reset() {
    }

    override fun update() {
    }

    override fun draw() {
        ctx.road.draw()
        ctx.player.draw()
        ctx.weather.draw()
        ctx.statusBar.draw()
        ctx.pausedOverlay.draw()
    }
}