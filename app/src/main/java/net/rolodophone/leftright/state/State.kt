package net.rolodophone.leftright.state

import net.rolodophone.leftright.main.MainActivity

interface State {
    val ctx: MainActivity
    fun reset()
    fun update()
    fun draw()
}