package net.rolodophone.leftright

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Player(context: Context) : GameObject {
    var img: Bitmap
    var speed = 2000f

    private val w = width / 4f
    private val h = w * 2f
    private val x = (width - w) / 2f
    private val y = height - h - (height / 8f)
    var dim = RectF(
        x,
        y,
        x + w,
        y + h
    )

    init {
        val opts = BitmapFactory.Options()
        opts.inScaled = false
        img = BitmapFactory.decodeResource(context.resources, R.drawable.car1, opts)
    }


    override fun update() {}
    override fun draw() {
        canvas.drawBitmap(img, null, dim, paint)
    }
}