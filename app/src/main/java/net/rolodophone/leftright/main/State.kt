package net.rolodophone.leftright.main

import net.rolodophone.leftright.button.Button

abstract class State(val ctx: MainActivity) {

    val buttons = mutableListOf<Button>()
    abstract val numThingsToLoad: Int

    open fun update() {}
    open fun draw() {}
}