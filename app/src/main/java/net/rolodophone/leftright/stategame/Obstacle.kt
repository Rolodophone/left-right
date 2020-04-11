package net.rolodophone.leftright.stategame

abstract class Obstacle(state: StateGame, w: Float, h: Float) : Spawnable(state, w, h) {
    abstract val deathType: DeathType
}