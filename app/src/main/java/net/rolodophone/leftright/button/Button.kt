package net.rolodophone.leftright.button

import android.graphics.RectF
import android.support.annotation.CallSuper

abstract class Button {
    private var drawnThisFrame = false
    var visible = false

    abstract val dim: RectF
    abstract val onClick: () -> Unit

    fun update() {
        visible = drawnThisFrame
        drawnThisFrame = false
    }

    @CallSuper
    open fun draw() {
        drawnThisFrame = true
    }

    fun checkClick(x: Float, y: Float): Boolean {
        return visible && x in dim.left..dim.right && y in dim.top..dim.bottom
    }
}