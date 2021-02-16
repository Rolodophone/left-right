package net.rolodophone.leftright.button

import android.graphics.Bitmap
import android.graphics.RectF
import net.rolodophone.leftright.main.canvas
import net.rolodophone.leftright.main.whitePaint

class ButtonBitmap(val bitmap: Bitmap, dim: RectF, triggerType: TriggerType, onClick: () -> Unit): Button(dim, triggerType, onClick) {
    override fun draw() {
        canvas.drawBitmap(bitmap, null, dim, whitePaint)
    }
}