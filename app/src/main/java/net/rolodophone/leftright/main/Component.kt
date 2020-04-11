package net.rolodophone.leftright.main

interface Component {
    val state: State
    fun update()
    fun draw()
}