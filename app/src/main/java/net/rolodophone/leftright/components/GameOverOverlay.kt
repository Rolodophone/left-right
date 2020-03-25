package net.rolodophone.leftright.components

import android.content.Context
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import net.rolodophone.leftright.button.ButtonBitmap
import net.rolodophone.leftright.main.*
import net.rolodophone.leftright.resources.bitmaps
import net.rolodophone.leftright.resources.sounds
import kotlin.random.Random

class GameOverOverlay(override val ctx: MainActivity) : Component {
    private val deathMsgDim = RectF(
        w(30),
        h(40),
        w(330),
        h(40) + w(72.972972973f)
    )
    private val deathMsgPaint = Paint(bitmapPaint)

    val playAgain = ButtonBitmap(
        bitmaps.play_again, ctx, RectF(
            w(220),
            h(250),
            w(300), h(250) + w(80)
        )
    ) {
        sounds.playSelect()
        ctx.state = ctx.stateGame
    }
    val mainMenu = ButtonBitmap(
        bitmaps.main_menu, ctx, RectF(
            w(60),
            h(250),
            w(140), h(250) + w(80)
        )
    ) {
        sounds.playSelect()
        //state = stateMain
    }

    private val comments = mapOf(
        DeathType.NONE to listOf(
            listOf("Better luck next time!"),
            listOf("Oops, I forgot my seat belt..."),
            listOf("Death can be fatal..."),

            listOf("Magic!")
        ),

        DeathType.CONE to listOf(
            listOf("Better luck next time!"),
            listOf("Oops, I forgot my seat belt..."),
            listOf("Death can be fatal..."),

            listOf("Who filled this cone", "with concrete?"),
            listOf("You hit a cone!")
        ),

        DeathType.FUEL to listOf(
            listOf("Better luck next time!"),
            listOf("Oops, I forgot my seat belt..."),
            listOf("Death can be fatal..."),

            listOf("No one ever told me this", "car explodes when it runs", "out of fuel!"),
            listOf("You ran out of fuel!"),
            listOf("Hmm, the accelerator pedal", "doesn't seem to be working")
        ),

        DeathType.CAR to listOf(
            listOf("Better luck next time!"),
            listOf("Oops, I forgot my seat belt..."),
            listOf("Death can be fatal...")
        )
    )


    private var alpha = 0f
    private var rotation = 0f
    private var maxRotation = 0f
    private var scale = 0f

    lateinit var comment: List<String>
    var highscore = 0
    var score = 0
    var newHighscore = false


    fun reset() {
        alpha = 0f
        rotation = 0f
        maxRotation = -20f + Random.nextFloat() * 40f
        scale = 0f

        comment = comments.getValue(ctx.player.causeOfDeath).random()
        score = ctx.player.distance.toInt() + ctx.player.fuel.toInt() + ctx.player.coins
//        try {
            highscore = ctx.openFileInput("highscore").bufferedReader().useLines { lines -> lines.fold("") { some, text -> "$some\n$text" } }.toInt()
//        } catch  {
//
//        }

        if (score > highscore) {
            newHighscore = true
            ctx.openFileOutput("highscore", Context.MODE_PRIVATE).use {
                it.write(score.toString().toByteArray())
            }
        }

    }


    fun update() {
        //updateButtons wasted image
        alpha += 2040f / fps
        rotation += (maxRotation * 8f) / fps
        scale += 8f / fps

        if (alpha > 255f) alpha = 255f
        if (maxRotation > 0f && rotation > maxRotation || maxRotation < 0f && rotation < maxRotation) rotation =
            maxRotation
        if (scale > 1f) scale = 1f

        deathMsgPaint.alpha = alpha.toInt()
    }


    fun draw() {
        //dim rest of screen
        canvas.drawRect(0f, 0f,
            width,
            height,
            dimmerPaint
        )


        //draw wasted image
        canvas.save()
        canvas.rotate(
            rotation,
            halfWidth,
            h(60)
        )
        canvas.drawBitmap(
            bitmaps.death_msg, null, deathMsgDim.scaled(scale),
            deathMsgPaint
        )
        canvas.restore()

        //draw comment
        whitePaint.textAlign = Paint.Align.CENTER
        whitePaint.typeface = Typeface.DEFAULT_BOLD
        for ((i, line) in comment.withIndex()) canvas.drawText(line,
            halfWidth, h(120) + w(25 * i),
            whitePaint
        )
        whitePaint.typeface = Typeface.DEFAULT

        //draw stats
        whitePaint.textAlign = Paint.Align.LEFT
        canvas.drawText("Distance travelled:",
            w(30),
            h(160),
            whitePaint
        )
        canvas.drawText("Fuel remaining:",
            w(30), h(160) + w(30),
            whitePaint
        )
        canvas.drawText("Coins collected:",
            w(30), h(160) + w(60),
            whitePaint
        )
        canvas.drawText("Total score:",
            w(30), h(160) + w(100),
            whitePaint
        )
        canvas.drawText("Highscore:",
            w(30), h(160) + w(130),
            whitePaint
        )

        whitePaint.textAlign = Paint.Align.RIGHT
        canvas.drawText(
            ctx.player.distance.toInt().toString(),
            w(330),
            h(160),
            whitePaint
        )
        canvas.drawText(
            ctx.player.fuel.toInt().toString(),
            w(330), h(160) + w(30),
            whitePaint
        )
        canvas.drawText(
            ctx.player.coins.toString(),
            w(330), h(160) + w(60),
            whitePaint
        )
        canvas.drawText(
            (score).toString(),
            w(330), h(160) + w(100),
            whitePaint
        )
        canvas.drawText(
            highscore.toString(),
            w(330), h(160) + w(130),
            whitePaint
        )

        whitePaint.textAlign = Paint.Align.CENTER
        canvas.drawText(
            "New highscore!",
            halfWidth, h(160) + w(160),
            whitePaint
        )

        whitePaint.strokeWidth = 3f
        canvas.drawLine(
            w(30), h(160) + w(72),
            w(330), h(160) + w(72),
            whitePaint
        )
        whitePaint.strokeWidth = 1f

        //draw buttons
        playAgain.draw()
        mainMenu.draw()
    }
}