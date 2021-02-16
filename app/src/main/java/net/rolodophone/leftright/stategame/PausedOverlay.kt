package net.rolodophone.leftright.stategame

import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import net.rolodophone.leftright.button.Button
import net.rolodophone.leftright.button.ButtonText
import net.rolodophone.leftright.main.*

class PausedOverlay(override val state: StateGame) : Component {
    var resume = object : Button(
        RectF(w(90), halfHeight + w(5), w(270), halfHeight + w(49)),
        TriggerType.UP,
        {
            state.sounds.playSelect()
            state.unpauseGame()
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
            canvas.drawText("Resume", w(132), halfHeight + w(39), whitePaint)
        }
    }
    val btnShowDebug = ButtonText(
        "debug",
        Paint.Align.RIGHT,
        RectF(w(200), height - w(35), w(348), height - w(10)),
        Button.TriggerType.UP
    ) {
        state.status.showDebug = true
    }


    override fun draw() {
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

        //draw buttons
        resume.draw()

        if (state.status.showDebug) {
            btnShowDebug.visible = true
        }
        else {
            btnShowDebug.visible = false
            btnShowDebug.draw()
        }
    }
}