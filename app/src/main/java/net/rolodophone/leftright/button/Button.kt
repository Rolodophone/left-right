package net.rolodophone.leftright.button

import android.graphics.RectF
import androidx.annotation.CallSuper
import net.rolodophone.leftright.main.State

open class Button(state: State, val dim: RectF, dimExceptions: List<RectF> = listOf(), isStrict: Boolean = true, onClick: () -> Unit) {

    class ButtonHandler(val checkClick: (Float, Float) -> Boolean, val onClick: () -> Unit)

    init {
        val checkClick = { x: Float, y: Float -> visible && dim.contains(x, y) && dimExceptions.none { it.contains(x, y) }}

        if (isStrict) {
            state.strictButtons.add(ButtonHandler(checkClick, onClick))
        }
        else {
            state.buttons.add(ButtonHandler(checkClick, onClick))
        }
    }

    private var drawnLastFrame = false
    var visible = false


    fun update() {
        visible = drawnLastFrame
        drawnLastFrame = false
    }

    @CallSuper
    open fun draw() {
        drawnLastFrame = true
    }
}