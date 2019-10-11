package net.rolodophone.leftright

import android.graphics.Bitmap
import android.graphics.RectF

class BitmapButton(val bitmap: Bitmap, dim: RectF, activeState: State, onClick: () -> Unit) : Button(dim, activeState, {
    canvas.drawBitmap(bitmap, null, dim, whitePaint)
}, onClick)