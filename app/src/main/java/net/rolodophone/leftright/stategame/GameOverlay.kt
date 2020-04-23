package net.rolodophone.leftright.stategame

import android.graphics.RectF
import net.rolodophone.leftright.button.Button
import net.rolodophone.leftright.main.*

class GameOverlay(override val state: StateGame) : Component {
    val pause = object : Button(
        state,
        RectF(
            w(5), height - w(50),
            w(50), height - w(5)
        ),
        listOf(),
        true,
        {
            state.sounds.playSelect()
            state.state = StateGame.State.PAUSED
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

    val leftButton = Button(state, RectF(0f, 0f, halfWidth - 1f, height), listOf(pause.dim), false) {
        val degree = state.player.rotation % 360f
        if (degree < 90f || degree > 270f) state.player.turnLeft()
        else state.player.turnRight()
    }

    val rightButton = Button(state, RectF(halfWidth, 0f, width, height), listOf(pause.dim), false) {
        val degree = state.player.rotation % 360f
        if (degree < 90f || degree > 270f) state.player.turnRight()
        else state.player.turnLeft()
    }

    override fun update() {
        pause.update()
        leftButton.update()
        rightButton.update()
    }


    override fun draw() {
        pause.draw()
        leftButton.draw()
        rightButton.draw()
    }
}