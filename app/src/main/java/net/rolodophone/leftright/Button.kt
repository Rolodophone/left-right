package net.rolodophone.leftright

import android.graphics.RectF

open class Button(val dim: RectF, val condition: () -> Boolean, var draw: () -> Unit, var onClick: () -> Unit) {
    open fun handleClick(x: Float, y: Float): Boolean {
        if (condition() && x in dim.left..dim.right && y in dim.top..dim.bottom) {
            onClick()
            return true
        } else return false
    }
}