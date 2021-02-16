package net.rolodophone.leftright.stategame

import android.graphics.RectF
import net.rolodophone.leftright.button.Button
import net.rolodophone.leftright.main.*

class GameOverlay(override val state: StateGame) : Component {
    val pauseButton = object : Button(
        RectF(w(5), height - w(50), w(50), height - w(5)),
        TriggerType.UP,
        {
            state.sounds.playSelect()
            state.pauseGame()
        }
    ) {
        override fun draw() {
            super.draw()

            canvas.drawRect(w(5), height - w(50), w(20), height - w(5), whitePaint)
            canvas.drawRect(w(35), height - w(50), w(50), height - w(5), whitePaint)
        }
    }

    init {
        state.buttons.add(pauseButton)

        state.buttons.add(Button(RectF(0f, 0f, halfWidth - 1f, height), Button.TriggerType.DOWN) {
            val degree = state.player.rotation % 360f
            if (degree < 90f || degree > 270f) state.player.turnLeft()
            else state.player.turnRight()
        })

        state.buttons.add(Button(RectF(halfWidth, 0f, width, height), Button.TriggerType.DOWN) {
            val degree = state.player.rotation % 360f
            if (degree < 90f || degree > 270f) state.player.turnRight()
            else state.player.turnLeft()
        })
    }

    fun draw() {
        pauseButton.draw()
    }
}