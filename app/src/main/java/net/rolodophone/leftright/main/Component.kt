package net.rolodophone.leftright.main

interface Component {
    val ctx: MainActivity
    val state: State
    fun update()
    fun draw()
}