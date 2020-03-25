package net.rolodophone.leftright.components

import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import net.rolodophone.leftright.button.Button
import net.rolodophone.leftright.button.ButtonText
import net.rolodophone.leftright.main.*
import net.rolodophone.leftright.main.MainView.c
import net.rolodophone.leftright.resources.sounds
import net.rolodophone.leftright.state.stateGame

class PausedOverlay {
    var resume = object : Button(
        c,
        RectF(
            w(90), halfHeight + w(5),
            w(270), halfHeight + w(49)
        ), {
            sounds.playSelect()
            state = stateGame
        }
    ) {
        override fun draw() {
            super.draw()

            val path = Path()
            path.moveTo(w(87), halfHeight + w(5))
            path.lineTo(w(87), halfHeight + w(49))
            path.lineTo(w(120), halfHeight + w(27))
            path.close()

            canvas.drawPath(path, whitePaint)
            whitePaint.textSize = w(40)
            whitePaint.textAlign = Paint.Align.LEFT
            canvas.drawText("Resume",
                w(132), halfHeight + w(39),
                whitePaint
            )
        }
    }
    val btnShowDebug = ButtonText(
        "debug",
        Paint.Align.RIGHT,
        c,
        RectF(
            w(200), height - w(35),
            w(348), height - w(10)
        )
    ) {
        c.statusBar.showDebug = true
    }
    val btnHideDebug = ButtonText(
        "debug",
        Paint.Align.RIGHT,
        c,
        RectF(
            w(200), height - w(35),
            w(348), height - w(10)
        )
    ) {
        c.statusBar.showDebug = false
    }


    fun draw() {
        //dim rest of screen
        canvas.drawRect(0f, 0f,
            width,
            height,
            dimmerPaint
        )

        //draw paused icon
        canvas.drawRect(
            w(90),
            halfHeight - w(190),
            w(150),
            halfHeight - w(10),
            whitePaint
        )
        canvas.drawRect(
            w(210),
            halfHeight - w(190),
            w(270),
            halfHeight - w(10),
            whitePaint
        )

        resume.draw()
        if (c.statusBar.showDebug) btnHideDebug.draw() else btnShowDebug.draw()
    }
}