package net.rolodophone.leftright.stateloading

import net.rolodophone.leftright.main.MainActivity
import net.rolodophone.leftright.main.State

class StateLoading(ctx: MainActivity, private val nextState: State) : State(ctx) {
    override val numThingsToLoad = 0

    var loadingCountDown = nextState.numThingsToLoad
        set(value) {
            if (value <= 0) ctx.state = nextState
            field = value
        }

    override fun update() {}
    override fun draw() {}
}