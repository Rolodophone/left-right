package net.rolodophone.leftright.stategame

abstract class Obstacle(state: StateGame, w: Float, h: Float) : Object(state, w, h) {
    abstract val deathType: DeathType
}