package net.rolodophone.leftright

interface State {
    fun reset()
    fun update()
    fun draw()
}