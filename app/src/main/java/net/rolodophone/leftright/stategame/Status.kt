package net.rolodophone.leftright.stategame

import android.graphics.Paint
import android.graphics.RectF
import android.os.SystemClock
import net.rolodophone.leftright.button.ButtonText
import net.rolodophone.leftright.main.*

class Status(override val ctx: MainActivity, override val state: StateGame) : Component {
    var showDebug = false

    var prevTime = SystemClock.elapsedRealtime()
    var viewFps = fps.toInt()

    val unlimitedFuelOn = ButtonText(
        "unlimited fuel on", Paint.Align.RIGHT, ctx, state, RectF(
            w(200),
            statusBarHeight + w(30),
            w(353),
            statusBarHeight + w(55)
        )
    ) {
        state.player.fuel = Float.POSITIVE_INFINITY
    }
    val unlimitedFuelOff = ButtonText(
        "unlimited fuel off", Paint.Align.RIGHT, ctx, state, RectF(
            w(200),
            statusBarHeight + w(30),
            w(353),
            statusBarHeight + w(55)
        )
    ) {
        state.player.fuel = 50f
    }
    val frenzyOn = ButtonText(
        "frenzy on",
        Paint.Align.RIGHT,
        ctx,
        state,
        RectF(
            w(200),
            statusBarHeight + w(60),
            w(353),
            statusBarHeight + w(85)
        )
    ) {
        state.road.isFrenzy = true
    }
    val frenzyOff = ButtonText(
        "frenzy off",
        Paint.Align.RIGHT,
        ctx,
        state,
        RectF(
            w(200),
            statusBarHeight + w(60),
            w(353),
            statusBarHeight + w(85)
        )
    ) {
        state.road.isFrenzy = false
    }

    val fuelDim = RectF(
        w(333),
        w(4) + statusBarHeight,
        w(355),
        w(29.1428571429f) + statusBarHeight
    )

    override fun update() {
        unlimitedFuelOn.update()
        unlimitedFuelOff.update()
        frenzyOff.update()
        frenzyOn.update()
    }


    override fun draw() {
        whitePaint.textSize = w(22)


        //draw fuel meter
        canvas.drawBitmap(ctx.bitmaps.fuel, null, fuelDim, bitmapPaint)

        whitePaint.textAlign = Paint.Align.RIGHT
        canvas.drawText(
            if (state.player.fuel == Float.POSITIVE_INFINITY) "∞"
            else state.player.fuel.toInt().toString(),
            w(329),
            w(25) + statusBarHeight,
            whitePaint
        )

        //draw distance
        whitePaint.textAlign = Paint.Align.LEFT
        canvas.drawText(
            state.player.distance.toInt().toString() + "m",
            w(7),
            w(25) + statusBarHeight,
            whitePaint
        )

        if (showDebug) {
            //draw fps
            if (SystemClock.elapsedRealtime() > prevTime + 500) {
                viewFps = fps.toInt()
                prevTime = SystemClock.elapsedRealtime()
            }
            whitePaint.textAlign = Paint.Align.LEFT
            whitePaint.textSize = w(22)
            canvas.drawText("FPS: $viewFps",
                w(5), w(55) + statusBarHeight,
                whitePaint
            )

            //draw buttons
            if (state.player.fuel == Float.POSITIVE_INFINITY) unlimitedFuelOff.draw() else unlimitedFuelOn.draw()
            if (state.road.isFrenzy) frenzyOff.draw() else frenzyOn.draw()
        }
    }
}