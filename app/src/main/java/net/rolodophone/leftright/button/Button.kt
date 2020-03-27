package net.rolodophone.leftright.button

import android.graphics.RectF
import androidx.annotation.CallSuper
import net.rolodophone.leftright.main.MainActivity

open class Button(open val ctx: MainActivity, open val dim: RectF, open val onClick: () -> Unit) {
    private var drawnThisFrame = false
    var visible = false


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