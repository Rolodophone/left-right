package net.rolodophone.leftright.main

import net.rolodophone.leftright.button.Button

abstract class State(val ctx: MainActivity) {

    val buttons = mutableListOf<Button.ButtonHandler>()
    val strictButtons = mutableListOf<Button.ButtonHandler>() // these buttons are only pressed in onSingleTapUp (so pressing them and dragging won't activate them)
    abstract val numThingsToLoad: Int

    abstract fun update()
    abstract fun draw()
}