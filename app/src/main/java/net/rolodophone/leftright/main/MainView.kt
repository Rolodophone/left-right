package net.rolodophone.leftright.main

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.WindowManager
import net.rolodophone.leftright.R
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


        //initialize resources
        ctx.sounds = Sounds(ctx)
        ctx.music = Music(ctx)
        ctx.bitmaps = Bitmaps(ctx)


        //load bitmaps
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false

        ctx.bitmaps.car1 = BitmapFactory.decodeResource(ctx.resources, R.drawable.car1, bitmapOptions)
        ctx.bitmaps.car1_hit = BitmapFactory.decodeResource(ctx.resources, R.drawable.car1_hit, bitmapOptions)
        ctx.bitmaps.car2 = BitmapFactory.decodeResource(ctx.resources, R.drawable.car2, bitmapOptions)
        ctx.bitmaps.car2_hit = BitmapFactory.decodeResource(ctx.resources, R.drawable.car2_hit, bitmapOptions)
        ctx.bitmaps.car3 = BitmapFactory.decodeResource(ctx.resources, R.drawable.car3, bitmapOptions)
        ctx.bitmaps.car3_hit = BitmapFactory.decodeResource(ctx.resources, R.drawable.car3_hit, bitmapOptions)
        ctx.bitmaps.car4 = BitmapFactory.decodeResource(ctx.resources, R.drawable.car4, bitmapOptions)
        ctx.bitmaps.car4_hit = BitmapFactory.decodeResource(ctx.resources, R.drawable.car4_hit, bitmapOptions)
        ctx.bitmaps.car5 = BitmapFactory.decodeResource(ctx.resources, R.drawable.car5, bitmapOptions)
        ctx.bitmaps.car5_hit = BitmapFactory.decodeResource(ctx.resources, R.drawable.car5_hit, bitmapOptions)
        ctx.bitmaps.car6 = BitmapFactory.decodeResource(ctx.resources, R.drawable.car6, bitmapOptions)
        ctx.bitmaps.car6_hit = BitmapFactory.decodeResource(ctx.resources, R.drawable.car6_hit, bitmapOptions)
        ctx.bitmaps.death_msg = BitmapFactory.decodeResource(ctx.resources, R.drawable.death_msg, bitmapOptions)
        ctx.bitmaps.fuel = BitmapFactory.decodeResource(ctx.resources, R.drawable.fuel, bitmapOptions)
        ctx.bitmaps.main_menu = BitmapFactory.decodeResource(ctx.resources, R.drawable.main_menu, bitmapOptions)
        ctx.bitmaps.play_again = BitmapFactory.decodeResource(ctx.resources, R.drawable.play_again, bitmapOptions)
        ctx.bitmaps.cone = BitmapFactory.decodeResource(ctx.resources, R.drawable.cone, bitmapOptions)
        ctx.bitmaps.oil = BitmapFactory.decodeResource(ctx.resources, R.drawable.oil, bitmapOptions)
        ctx.bitmaps.coin = BitmapFactory.decodeResource(ctx.resources, R.drawable.coin, bitmapOptions)
        ctx.bitmaps.coinShining = listOf(
            BitmapFactory.decodeResource(ctx.resources, R.drawable.coin0, bitmapOptions),
            BitmapFactory.decodeResource(ctx.resources, R.drawable.coin1, bitmapOptions),
            BitmapFactory.decodeResource(ctx.resources, R.drawable.coin2, bitmapOptions),
            BitmapFactory.decodeResource(ctx.resources, R.drawable.coin3, bitmapOptions),
            BitmapFactory.decodeResource(ctx.resources, R.drawable.coin4, bitmapOptions),
            BitmapFactory.decodeResource(ctx.resources, R.drawable.coin5, bitmapOptions),
            BitmapFactory.decodeResource(ctx.resources, R.drawable.coin6, bitmapOptions),
            BitmapFactory.decodeResource(ctx.resources, R.drawable.coin7, bitmapOptions)
        )


        //load sounds
        ctx.sounds.hit = ctx.sounds.soundPool.load(ctx, R.raw.hit, 1)
        ctx.sounds.select = ctx.sounds.soundPool.load(ctx, R.raw.select, 1)
        ctx.sounds.tap = ctx.sounds.soundPool.load(ctx, R.raw.tap, 1)
        ctx.sounds.fuel = ctx.sounds.soundPool.load(ctx, R.raw.fuel, 1)
        ctx.sounds.oil = ctx.sounds.soundPool.load(ctx, R.raw.oil, 1)
        ctx.sounds.coin = ctx.sounds.soundPool.load(ctx, R.raw.coin, 1)


        //load music
        ctx.music.game = ctx.music.soundPool.load(ctx, R.raw.just_nasty, 1)


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


        //wait until resource loading is finished
        Log.d("temp", "count down latch: ${ctx.music.countDownLatch.count}")
        ctx.music.countDownLatch.await()


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