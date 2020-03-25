package net.rolodophone.leftright.components

import android.graphics.RectF
import net.rolodophone.leftright.button.Button
import net.rolodophone.leftright.main.*
import net.rolodophone.leftright.resources.sounds

class GameOverlay(override val ctx: MainActivity) : Component {
    val pause = object : Button(
        ctx,
        RectF(
            w(5), height - w(50),
            w(50), height - w(5)
        ),
        {
            sounds.playSelect()
            ctx.state = ctx.statePaused
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

    val leftButton = Button(ctx, RectF(0f, 0f, halfWidth - 1f, height)) {
        val degree = ctx.player.rotation % 360f
        if (degree < 90f || degree > 270f) ctx.player.turnLeft()
        else ctx.player.turnRight()
    }

    val rightButton = Button(ctx, RectF(halfWidth, 0f, width, height)) {
        val degree = ctx.player.rotation % 360f
        if (degree < 90f || degree > 270f) ctx.player.turnRight()
        else ctx.player.turnLeft()
    }


    fun draw() {
        pause.draw()
        leftButton.draw()
        rightButton.draw()
    }
}