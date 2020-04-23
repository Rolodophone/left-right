package net.rolodophone.leftright.main

import net.rolodophone.leftright.button.Button

abstract class State(val ctx: MainActivity) {

    val buttons = mutableListOf<Button.ButtonHandler>()
    abstract val numThingsToLoad: Int

    abstract fun update()
    abstract fun draw()
}