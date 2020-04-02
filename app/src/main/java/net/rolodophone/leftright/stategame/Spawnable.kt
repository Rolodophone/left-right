package net.rolodophone.leftright.stategame

import android.graphics.RectF
import net.rolodophone.leftright.main.fps
import net.rolodophone.leftright.main.height

abstract class Spawnable(road: Road, override val w: Float, override val h: Float, override var lane: Int = road.randomLane()) : Object(road, RectF(
    road.centerOfLane(lane) - w / 2,
    -h,
    road.centerOfLane(lane) + w / 2,
    0f
)) {
    abstract val companion: SpawnableCompanion
    abstract class SpawnableCompanion(val averageSpawnMetres: Int, val constructor: (road: Road) -> Spawnable) {
        fun spawn(road: Road) {
            val new = constructor(road)
            if (new.lane != -1) road.objects.add(new)
        }
    }

    abstract val z: Int


    override fun update() {
        super.update()

        //move item down
        dim.offset(0f, road.state.player.ySpeed / fps)

        //mark offscreen item for deletion
        if (imgDim.top > height) road.itemsToDel.add(this)
    }
}