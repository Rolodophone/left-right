package net.rolodophone.leftright.stateloading

import net.rolodophone.leftright.button.Button
import net.rolodophone.leftright.main.MainActivity
import net.rolodophone.leftright.main.State

class StateLoading(override val ctx: MainActivity) : State {
    override val buttons = mutableListOf<Button.ButtonHandler>()
    override fun update() {}
    override fun draw() {}
}