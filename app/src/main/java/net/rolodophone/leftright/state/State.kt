package net.rolodophone.leftright.state

interface State {
    fun reset()
    fun update()
    fun draw()
}