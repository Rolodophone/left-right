package net.rolodophone.leftright

import android.graphics.RectF

open class Button(val dim: RectF, val activeState: State, val draw: () -> Unit, val onClick: () -> Unit) {
    fun handleClick(x: Float, y: Float): Boolean {
        if (state == activeState && x in dim.left..dim.right && y in dim.top..dim.bottom) {
            onClick()
            return true
        } else return false
    }
}