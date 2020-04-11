package net.rolodophone.leftright.main

import net.rolodophone.leftright.button.Button

interface State {
    val ctx: MainActivity

    val buttons: MutableList<Button.ButtonHandler>
    val numThingsToLoad: Int

    fun update()
    fun draw()
}