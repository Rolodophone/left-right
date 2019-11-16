package net.rolodophone.leftright

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.WindowManager


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
                if (gui.showDebug && fps < 30f) fps = 30f
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
        dimmerPaint.color = Color.argb(150, 0, 0, 0)


        val activity = context as Activity
        val window = activity.window

        window.statusBarColor = Color.argb(0, 0, 0, 0)

        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN)


        //load bitmaps
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false

        bitmaps.car1.clean = BitmapFactory.decodeResource(context.resources, R.drawable.car1, bitmapOptions)
        bitmaps.car1.hit = BitmapFactory.decodeResource(context.resources, R.drawable.car1_hit, bitmapOptions)
        bitmaps.car2.clean = BitmapFactory.decodeResource(context.resources, R.drawable.car2, bitmapOptions)
        bitmaps.car2.hit = BitmapFactory.decodeResource(context.resources, R.drawable.car2_hit, bitmapOptions)
        bitmaps.car3.clean = BitmapFactory.decodeResource(context.resources, R.drawable.car3, bitmapOptions)
        bitmaps.car3.hit = BitmapFactory.decodeResource(context.resources, R.drawable.car3_hit, bitmapOptions)
        bitmaps.car4.clean = BitmapFactory.decodeResource(context.resources, R.drawable.car4, bitmapOptions)
        bitmaps.car4.hit = BitmapFactory.decodeResource(context.resources, R.drawable.car4_hit, bitmapOptions)
        bitmaps.car5.clean = BitmapFactory.decodeResource(context.resources, R.drawable.car5, bitmapOptions)
        bitmaps.car5.hit = BitmapFactory.decodeResource(context.resources, R.drawable.car5_hit, bitmapOptions)
        bitmaps.car6.clean = BitmapFactory.decodeResource(context.resources, R.drawable.car6, bitmapOptions)
        bitmaps.car6.hit = BitmapFactory.decodeResource(context.resources, R.drawable.car6_hit, bitmapOptions)
        bitmaps.death_msg = BitmapFactory.decodeResource(context.resources, R.drawable.death_msg, bitmapOptions)
        bitmaps.fuel = BitmapFactory.decodeResource(context.resources, R.drawable.fuel, bitmapOptions)
        bitmaps.main_menu = BitmapFactory.decodeResource(context.resources, R.drawable.main_menu, bitmapOptions)
        bitmaps.play_again = BitmapFactory.decodeResource(context.resources, R.drawable.play_again, bitmapOptions)
        bitmaps.cone = BitmapFactory.decodeResource(context.resources, R.drawable.cone, bitmapOptions)
        bitmaps.oil = BitmapFactory.decodeResource(context.resources, R.drawable.oil, bitmapOptions)


        //load sounds
        sounds.hit = sounds.soundPool.load(context, R.raw.hit, 1)
        sounds.select = sounds.soundPool.load(context, R.raw.select, 1)
        sounds.tap = sounds.soundPool.load(context, R.raw.tap, 1)
        sounds.fuel = sounds.soundPool.load(context, R.raw.fuel, 1)
        sounds.oil = sounds.soundPool.load(context, R.raw.oil, 1)


        //initialize state
        state.reset()

        Log.i("View", "</--------INIT--------->")
    }


    @SuppressLint("ClickableViewAccessibility")

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            for (button in gui.buttons) if (button.handleClick(event.x, event.y)) return true
        }

        return false
    }


    fun pause() {
        if (state == stateGame) state = statePaused
        Log.i("View", "Paused")
    }
}