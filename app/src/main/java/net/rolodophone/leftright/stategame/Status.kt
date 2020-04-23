package net.rolodophone.leftright.stategame

import android.graphics.Paint
import android.graphics.RectF
import android.os.SystemClock
import net.rolodophone.leftright.button.ButtonText
import net.rolodophone.leftright.main.*

class Status(override val state: StateGame) : Component {
    var showDebug = false

    var prevTime = SystemClock.elapsedRealtime()
    var viewFps = fps.toInt()

    inner class DebugButton(text: String, index: Int, onClick: () -> Unit) //index is 1 upwards
        : ButtonText(
            text,
            Paint.Align.RIGHT,
            state,
            RectF(
                w(200),
                statusBarHeight + w(index * 30),
                w(353),
                statusBarHeight + w(index * 30 + 25)
            ),
            onClick
        )

    val unlimitedFuelOn = DebugButton("unlimited fuel on", 1) { state.player.fuel = Float.POSITIVE_INFINITY }
    val unlimitedFuelOff = DebugButton("unlimited fuel off", 1) { state.player.fuel = 50f }
    val frenzyOn = DebugButton("frenzy on", 2) { state.road.isFrenzy = true }
    val frenzyOff = DebugButton("frenzy off", 2) { state.road.isFrenzy = false }
    val skipToFinish = DebugButton("skip to finish", 3) { state.music.skipToFinish() }

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
        skipToFinish.update()
    }


    override fun draw() {
        whitePaint.textSize = w(22)


        //draw fuel meter
        canvas.drawBitmap(state.bitmaps.fuel, null, fuelDim, bitmapPaint)

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
            skipToFinish.draw()
        }
    }
}