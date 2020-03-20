package net.rolodophone.leftright.components

import android.graphics.RectF
import net.rolodophone.leftright.button.Button
import net.rolodophone.leftright.main.*
import net.rolodophone.leftright.resources.sounds
import net.rolodophone.leftright.state.statePaused

object gameOverlay {
    val pause = object : Button() {
        override val dim = RectF(
            w(5), height - w(50),
            w(50), height - w(5)
        )
        override val onClick = {
            sounds.playSelect()
            state = statePaused
        }

        override fun draw() {
            super.draw()

            canvas.drawRect(
                w(5),
            height - w(50),
                w(20),
            height - w(5),
                whitePaint
            )
            canvas.drawRect(
                w(35),
                height - w(50),
                w(50),
                height - w(5),
                whitePaint
            )
        }
    }

    val leftButton = object : Button() {
        override val dim = RectF(0f, 0f, halfWidth - 1f, height)
        override val onClick = {
            val degree = player.rotation % 360f
            if (degree < 90f || degree > 270f) player.turnLeft()
            else player.turnRight()
        }
    }

    val rightButton = object : Button() {
        override val dim = RectF(halfWidth, 0f, width, height)
        override val onClick = {
            val degree = player.rotation % 360f
            if (degree < 90f || degree > 270f) player.turnRight()
            else player.turnLeft()
        }
    }


    fun draw() {
        pause.draw()
        leftButton.draw()
        rightButton.draw()
    }
}