package net.rolodophone.leftright.stategame.road

import android.graphics.Bitmap
import android.graphics.RectF
import androidx.annotation.CallSuper
import net.rolodophone.leftright.main.*

abstract class Item(private val road: Road) {
    abstract val Factory: ItemFactory
    abstract class ItemFactory {
        abstract val averageSpawnMetres: Int
        abstract fun constructor(road: Road): Item
        fun new(road: Road) {
            val new = constructor(road)
            if (new.lane != -1) road.items.add(new)
        }
    }

    var isDisabled = false

    open var rotation = 0f
    open val lane = randomLane()

    abstract val isObstacle: Boolean
    abstract var img: Bitmap
    abstract val dim: RectF
    abstract val z: Int

    fun rectFFromDim(width: Float, height: Float): RectF {
        return RectF(
            road.centerOfLane(lane) - width / 2,
            -height,
            road.centerOfLane(lane) + width / 2,
            0f
        )
    }

    private fun randomLane(): Int {
        val remainingLanes = mutableSetOf<Int>()
        remainingLanes.addAll((0 until road.numLanes).filter { checkValidLane(it) })

        return if (remainingLanes.isEmpty()) -1
        else remainingLanes.random()
    }

    private fun checkValidLane(laneToCheck: Int): Boolean {

        //too close to another item
        for (item in road.items) if (item.lane == laneToCheck && item.dim.top < w(5)) {
            return false
        }

        //making it impossible for player to pass (an obstacle in each lane)

        val remainingLanes = mutableSetOf<Int>()
        remainingLanes.addAll(0 until road.numLanes)
        remainingLanes.remove(laneToCheck)

        for (item in road.items) {
            if (item.isObstacle) remainingLanes.remove(item.lane)
        }

        if (remainingLanes.isEmpty()) return false

        return true
    }

    fun isTouching(dim: RectF) = this.dim.intersects(dim.left, dim.top, dim.right, dim.bottom)

    abstract fun onTouch()

    @CallSuper
    open fun update() {
        //move item down
        dim.offset(0f, road.state.player.ySpeed / fps)

        //mark offscreen item for deletion
        if (dim.top > height) road.itemsToDel.add(this)

        //perform onTouch()
        if (isTouching(road.state.player.dim) && !isDisabled) onTouch()
    }

    fun draw() {
        canvas.save()
        canvas.rotate(rotation, dim.centerX(), dim.centerY())
        canvas.drawBitmap(img, null, dim, bitmapPaint)
        canvas.restore()
    }
}