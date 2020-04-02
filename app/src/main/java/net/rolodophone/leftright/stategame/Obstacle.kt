package net.rolodophone.leftright.stategame

abstract class Obstacle(road: Road, w: Float, h: Float) : Spawnable(road, w, h) {
    abstract val deathType: DeathType
}