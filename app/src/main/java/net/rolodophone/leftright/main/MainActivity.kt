package net.rolodophone.leftright.main

import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : Activity() {

    private lateinit var mainView: MainView
    private lateinit var thread: Thread

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

        mainView.init()

        setContentView(mainView)
    }


    override fun onStart() {
        Log.i("Activity", "onStart()")
        super.onStart()

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        appOpen = true
        thread = Thread(mainView)
        thread.name = "LeftRightDraw"
        thread.start()
    }
    
    
    override fun onPause() {
        Log.i("Activity", "onPause()")
        super.onPause()

        mainView.pause()
    }



    override fun onStop() {
        Log.i("Activity", "onStop()")
        super.onStop()

        appOpen = false
        Log.i("Activity", "Joining status thread")
        thread.join()
    }
}