package net.rolodophone.leftright.button

import android.graphics.RectF

open class Button(
    var dim: RectF,
    var triggerType: TriggerType,
    var onClick: () -> Unit
) {
    enum class TriggerType {
        UP, DOWN
    }

    var visible = true

    open fun draw() {}

    fun wasClicked(x: Float, y: Float, triggerType: TriggerType): Boolean {
        return triggerType == this.triggerType &&
                visible &&
                dim.contains(x, y)
    }
}