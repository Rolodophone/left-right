package net.rolodophone.leftright.stategame

import android.graphics.Paint
import android.graphics.RectF
import android.os.SystemClock
import net.rolodophone.leftright.button.ButtonText
import net.rolodophone.leftright.main.*

class StatusBar(override val ctx: MainActivity, override val state: StateGame) : Component {
    var showDebug = false

    val debug = Debug()

    inner class Debug {
        var prevTime = SystemClock.elapsedRealtime()
        var viewFps = fps.toInt()

        val moreFuel = ButtonText(
            "more fuel", Paint.Align.RIGHT, ctx, state, RectF(
                w(200),
                statusBarHeight + w(30),
                w(353),
                statusBarHeight + w(55)
            )
        ) {
            state.player.fuel += 1000
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

        fun update() {
            moreFuel.update()
            frenzyOff.update()
            frenzyOn.update()
        }


        fun draw() {
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
            moreFuel.draw()
            if (state.road.isFrenzy) frenzyOff.draw() else frenzyOn.draw()
        }
    }

    val fuelDim = RectF(
        w(333),
        w(4) + statusBarHeight,
        w(355),
        w(29.1428571429f) + statusBarHeight
    )

    override fun update() {
        debug.update()
    }


    override fun draw() {
        whitePaint.textSize = w(22)


        //draw fuel meter
        canvas.drawBitmap(ctx.bitmaps.fuel, null, fuelDim, bitmapPaint)

        whitePaint.textAlign = Paint.Align.RIGHT
        canvas.drawText(
            state.player.fuel.toInt().toString(),
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

        if (showDebug) debug.draw()
    }
}