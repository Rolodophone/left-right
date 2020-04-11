package net.rolodophone.leftright.stategame

import android.graphics.RectF
import net.rolodophone.leftright.main.fps
import net.rolodophone.leftright.main.height

abstract class Spawnable(state: StateGame, override val w: Float, override val h: Float, override var lane: Int = state.road.randomLane()) : Object(state, RectF(
    state.road.centerOfLane(lane) - w / 2,
    -h,
    state.road.centerOfLane(lane) + w / 2,
    0f
)) {
    abstract val companion: SpawnableCompanion
    abstract class SpawnableCompanion(val averageSpawnMetres: Int, val constructor: (state: StateGame) -> Spawnable) {
        fun spawn(state: StateGame) {
            val new = constructor(state)
            if (new.lane != -1) state.road.objects.add(new)
        }
    }

    abstract val z: Int


    override fun update() {
        super.update()

        //move item down
        dim.offset(0f, state.player.ySpeed / fps)

        //mark offscreen item for deletion
        if (imgDim.top > height) state.road.itemsToDel.add(this)
    }
}