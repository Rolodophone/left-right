package net.rolodophone.leftright

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.WindowManager


lateinit var bitmaps: Map<String, Bitmap>
val buttons = mutableListOf<Button>()

var state: State = stateGame
    set(value) {

        if (value == stateGameOver || value == stateGame && state != statePaused) {
            value.reset()
        }

        field = value
    }

var fps = Float.POSITIVE_INFINITY
var canvas = Canvas()

var whitePaint = Paint()
var dimmerPaint = Paint()

lateinit var player: Player
lateinit var road: Road

const val isDebug = false


class MainView(context: Context) : SurfaceView(context), Runnable {

    override fun run() {
        while (appOpen) {
            val initialTime = SystemClock.elapsedRealtime()

            if (holder.surface.isValid) {

                //update
                state.update()

                //draw
                val c = holder.lockCanvas()
                if (c != null) {
                    canvas = c

                    state.draw()

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

        whitePaint.color = Color.rgb(255, 255, 255)
        whitePaint.isAntiAlias = true
        dimmerPaint.color = Color.argb(100, 0, 0, 0)


        val activity = context as Activity
        val window = activity.window

        window.statusBarColor = Color.argb(0, 0, 0, 0)

        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN)


        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false

        bitmaps = mapOf(
            "car1" to BitmapFactory.decodeResource(context.resources, R.drawable.car1, bitmapOptions),
            "death_msg" to BitmapFactory.decodeResource(context.resources, R.drawable.death_msg, bitmapOptions),
            "fuel" to BitmapFactory.decodeResource(context.resources, R.drawable.fuel, bitmapOptions),
            "main_menu" to BitmapFactory.decodeResource(context.resources, R.drawable.main_menu, bitmapOptions),
            "play_again" to BitmapFactory.decodeResource(context.resources, R.drawable.play_again, bitmapOptions),
            "cone" to BitmapFactory.decodeResource(context.resources, R.drawable.cone, bitmapOptions)
        )

        state.reset()

        Log.i("View", "</--------INIT--------->")
    }


    @SuppressLint("ClickableViewAccessibility")

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            for (button in buttons) if (button.handleClick(event.x, event.y)) return true
        }

        return false
    }


    fun pause() {
        if (state == stateGame) state = statePaused
        Log.i("View", "Paused")
    }
}