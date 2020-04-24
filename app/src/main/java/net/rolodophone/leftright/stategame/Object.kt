package net.rolodophone.leftright.stategame

import android.graphics.Bitmap
import android.graphics.RectF
import androidx.annotation.CallSuper
import net.rolodophone.leftright.main.*

abstract class Object(final override val state: StateGame, val w: Float, val h: Float, var lane: Int = state.road.randomLane(false)) : Component {

    abstract val companion: ObjectCompanion
    abstract class ObjectCompanion(val averageSpawnMetres: Int, val constructor: (state: StateGame) -> Object) {
        fun spawn(state: StateGame) {
            val new = constructor(state)
            if (new.lane != -1) state.road.objects.add(new)
        }
    }


    open val dim = RectF(
        state.road.centerOfLane(lane) - w / 2,
        -h,
        state.road.centerOfLane(lane) + w / 2,
        0f
    ).scaled(9/10f)

    val imgDim
        get() = dim.scaled(10/9f)

    abstract var img: Bitmap
    abstract val z: Int


    val hasTouched = mutableListOf<Object>()

    fun isTouching(otherObject: Object) = this.dim.intersects(otherObject.dim.left, otherObject.dim.top, otherObject.dim.right, otherObject.dim.bottom)

    open fun onTouch(otherObject: Object) {}


    @CallSuper
    override fun update() {
        //move item down
        dim.offset(0f, state.road.cameraSpeed / fps)

        //mark offscreen item for deletion
        if (imgDim.isOffscreen()) state.road.itemsToDel.add(this)

        //update lane if dim got moved by collisions etc
        for (i in 0 until state.road.numLanes) {
            if (dim.centerX() < ((width) / state.road.numLanes) * (i+1)) {
                lane = i
                break
            }
        }
    }


    fun handleCollisions() {
        //remove objects that have been deleted from hasTouched
        hasTouched.retainAll { it in state.road.objects }

        //perform onTouch()
        for (otherObject in state.road.objects.minus(this)) {
            if (isTouching(otherObject) && otherObject !in hasTouched) {
                onTouch(otherObject)
                hasTouched.add(otherObject)
            }
        }
    }


    override fun draw() {
        canvas.drawBitmap(img, null, imgDim, bitmapPaint)
    }
}