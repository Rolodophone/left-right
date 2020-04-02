package net.rolodophone.leftright.stategame

import android.graphics.Bitmap
import android.graphics.RectF
import androidx.annotation.CallSuper
import net.rolodophone.leftright.main.Component
import net.rolodophone.leftright.main.bitmapPaint
import net.rolodophone.leftright.main.canvas
import net.rolodophone.leftright.main.scaled

abstract class Object(val road: Road, visibleDim: RectF) : Component {
    override val ctx = road.ctx
    override val state = road.state

    abstract val img: Bitmap
    abstract val w: Float
    abstract val h: Float
    abstract var lane: Int

    val dim = visibleDim.scaled(9/10f)

    val imgDim
        get() = dim.scaled(10/9f)

    private val hasTouched = mutableListOf<Object>()


    fun isTouching(otherObject: Object) = this.dim.intersects(otherObject.dim.left, otherObject.dim.top, otherObject.dim.right, otherObject.dim.bottom)

    open fun onTouch(otherObject: Object) {}

    @CallSuper
    override fun update() {
        //remove objects that have been deleted from hasTouched
        hasTouched.retainAll { it in road.objects }

        //perform onTouch()
        for (otherObject in road.objects.minus(this)) {
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