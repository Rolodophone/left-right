package net.rolodophone.leftright.components

import android.graphics.RectF
import net.rolodophone.leftright.button.Button
import net.rolodophone.leftright.main.*
import net.rolodophone.leftright.main.MainView.c
import net.rolodophone.leftright.resources.sounds
import net.rolodophone.leftright.state.statePaused

class GameOverlay {
    val pause = object : Button(
        c,
        RectF(
            w(5), height - w(50),
            w(50), height - w(5)
        ),
        {
            sounds.playSelect()
            state = statePaused
        }
    ) {
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

    val leftButton = Button(c, RectF(0f, 0f, halfWidth - 1f, height)) {
        val degree = c.player.rotation % 360f
        if (degree < 90f || degree > 270f) c.player.turnLeft()
        else c.player.turnRight()
    }

    val rightButton = Button(c, RectF(halfWidth, 0f, width, height)) {
        val degree = c.player.rotation % 360f
        if (degree < 90f || degree > 270f) c.player.turnRight()
        else c.player.turnLeft()
    }


    fun draw() {
        pause.draw()
        leftButton.draw()
        rightButton.draw()
    }
}