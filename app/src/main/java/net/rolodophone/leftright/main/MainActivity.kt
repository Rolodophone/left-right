package net.rolodophone.leftright.main

import android.app.Activity
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.core.view.GestureDetectorCompat
import net.rolodophone.leftright.resources.Bitmaps
import net.rolodophone.leftright.resources.Music
import net.rolodophone.leftright.resources.Sounds
import net.rolodophone.leftright.stateareas.StateAreas

class MainActivity : Activity() {

    lateinit var grid: Grid

    lateinit var sounds: Sounds
    lateinit var music: Music
    lateinit var bitmaps: Bitmaps

    lateinit var state: State

    private lateinit var mainView: MainView
    private lateinit var thread: Thread

    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("Activity", "onCreate()")
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        val dim = Point()
        windowManager.defaultDisplay.getRealSize(dim)

        //get status bar height
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        width = dim.x.toFloat()
        height = dim.y.toFloat()
        halfWidth = width / 2f
        halfHeight = height / 2f
        wUnit = width / 360f
        hUnit = height / 360f
        Log.i("Activity", "width: $width height: $height")

        mainView = MainView(this)

        setContentView(mainView)

        gestureDetector = GestureDetectorCompat(this, MyGestureListener())

        init()
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (gestureDetector.onTouchEvent(event)) return true

        state.let {
            if (it is StateAreas) {
                if (event.action == MotionEvent.ACTION_UP) {
                    it.stopSeek()
                    return true
                }

                if (event.action == MotionEvent.ACTION_MOVE) {
                    it.seek(event.x)
                    return true
                }
            }
        }

        return super.onTouchEvent(event)
    }


    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        var skipNextUp = false

        override fun onDown(event: MotionEvent): Boolean {
            for (button in state.buttons) {
                if (button.wasClicked(event.x, event.y, fingerWasReleased = false)) {
                    button.onClick()
                    skipNextUp = true
                    return true
                }
            }

            state.let { if (it is StateAreas) {
                it.startSeek(event.x)
                return true
            }}

            return false
        }

        override fun onSingleTapUp(event: MotionEvent): Boolean {
            if (!skipNextUp) {
                for (button in state.buttons) {
                    if (button.wasClicked(event.x, event.y, fingerWasReleased = true)) {
                        button.onClick()
                        return true
                    }
                }
            }
            else {
                skipNextUp = false
            }


            return false
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            state.let {
                if (it is StateAreas) {

                    if (velocityX < 0f) it.flingLeft()
                    else it.flingRight()

                    return true
                }
            }
            return false
        }
    }


    fun init() {
        Log.i("Initialization", "<---------INIT--------->")

        mainView.holder.setFormat(PixelFormat.RGB_565)

        //initialize paints
        whitePaint.color = Color.rgb(255, 255, 255)
        whitePaint.isAntiAlias = true
        whitePaint.isFilterBitmap = true
        bitmapPaint.isAntiAlias = true
        bitmapPaint.isFilterBitmap = false
        dimmerPaint.color = Color.argb(150, 0, 0, 0)

        //load resources
        sounds = Sounds(this)
        music = Music(this)
        bitmaps = Bitmaps(this)

        //initialize grid
        grid = Grid(this)

        state = StateAreas(this)

        Log.i("Initialization", "</--------INIT--------->")
    }


    override fun onStart() {
        super.onStart()

        //configure window
        if (Build.VERSION.SDK_INT >= 21) window.statusBarColor = Color.argb(0, 0, 0, 0)
        if (Build.VERSION.SDK_INT >= 18) window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN)
        window.decorView.systemUiVisibility = when  {
            Build.VERSION.SDK_INT >= 19 -> View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            else                        -> View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }


        appOpen = true
        thread = Thread(mainView)
        thread.name = "LeftRightDraw"
        thread.start()
    }
    
    
    override fun onPause() {
        super.onPause()
        mainView.pause()
    }


    override fun onResume() {
        super.onResume()
        mainView.resume()
    }


    override fun onStop() {
        super.onStop()

        appOpen = false
        thread.join()
    }
}