package net.rolodophone.leftright.main

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.WindowManager
import net.rolodophone.leftright.components.*
import net.rolodophone.leftright.resources.Bitmaps
import net.rolodophone.leftright.resources.Music
import net.rolodophone.leftright.resources.Sounds
import net.rolodophone.leftright.state.StateGame
import net.rolodophone.leftright.state.StateGameOver
import net.rolodophone.leftright.state.StatePaused


@SuppressLint("ViewConstructor")
class MainView(private val ctx: MainActivity) : SurfaceView(ctx), Runnable {

    override fun run() {
        init()

        while (appOpen) {
            val initialTime = SystemClock.elapsedRealtime()

            if (holder.surface.isValid) {

                //update
                ctx.state.update()
                ctx.buttons.updateButtons()

                //draw
                val c = holder.lockCanvas()
                if (c != null) {
                    canvas = c

                    ctx.state.draw()

                    holder.unlockCanvasAndPost(canvas)
                }

                //calculate fps
                val timeElapsed = SystemClock.elapsedRealtime() - initialTime
                fps = if (timeElapsed == 0L) 2000f else 1000f / timeElapsed
                if (isDebug && fps < 30f) fps = 30f
            }


            else {
                Log.e("View", "Surface invalid")
            }
        }
    }


    fun init() {
        Log.i("View", "<---------INIT--------->")

        holder.setFormat(PixelFormat.RGB_565)

        //initialize paints
        whitePaint.color = Color.rgb(255, 255, 255)
        whitePaint.isAntiAlias = true
        whitePaint.isFilterBitmap = true
        bitmapPaint.isAntiAlias = true
        bitmapPaint.isFilterBitmap = false
        dimmerPaint.color = Color.argb(150, 0, 0, 0)


        //configure window
        val activity = ctx as Activity
        val window = activity.window
        window.statusBarColor = Color.argb(0, 0, 0, 0)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN)


        //load resources
        ctx.sounds = Sounds(ctx)
        ctx.music = Music(ctx)
        ctx.bitmaps = Bitmaps(ctx)


        //initialize components
        ctx.gameOverlay = GameOverlay(ctx)
        ctx.gameOverOverlay = GameOverOverlay(ctx)
        ctx.grid = Grid(ctx)
        ctx.pausedOverlay = PausedOverlay(ctx)
        ctx.road = Road(ctx)
        ctx.player = Player(ctx)
        ctx.statusBar = StatusBar(ctx)
        ctx.weather = Weather(ctx)
        ctx.buttons = Buttons(ctx)


        //initialize states
        ctx.stateGame = StateGame(ctx)
        ctx.stateGameOver = StateGameOver(ctx)
        ctx.statePaused = StatePaused(ctx)


        //start game
        ctx.state = ctx.stateGame

        Log.i("View", "</--------INIT--------->")
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            for (button in ctx.buttons.buttons) if (button.checkClick(event.x, event.y)) {
                button.onClick()
                return true
            }
        }

        return false
    }


    fun pause() {
        Log.i("View", "Paused")
        if (ctx.state == ctx.stateGame) ctx.state = ctx.statePaused
        ctx.music.stop()
        ctx.sounds.stop()
    }
}