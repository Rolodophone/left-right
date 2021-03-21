package net.rolodophone.leftright.button

import android.graphics.Bitmap
import android.graphics.RectF
import net.rolodophone.leftright.main.State
import net.rolodophone.leftright.main.canvas
import net.rolodophone.leftright.main.whitePaint

class ButtonBitmap(val bitmap: Bitmap, state: State, dim: RectF, isStrict: Boolean = true, onClick: () -> Unit) : Button(state, dim, listOf(), isStrict, onClick) {

    override fun draw() {
        super.draw()
        canvas.drawBitmap(bitmap, null, dim, whitePaint)
    }
}