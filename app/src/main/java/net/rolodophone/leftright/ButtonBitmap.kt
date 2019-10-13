package net.rolodophone.leftright

import android.graphics.Bitmap
import android.graphics.RectF

class ButtonBitmap(val bitmap: Bitmap, dim: RectF, condition: () -> Boolean, onClick: () -> Unit) : Button(dim, condition, {
    canvas.drawBitmap(bitmap, null, dim, whitePaint)
}, onClick)