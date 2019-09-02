package net.rolodophone.leftright

import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.util.Log

var appOpen = true
var width = 0f
var height = 0f
var halfWidth = 0f
var halfHeight = 0f
var unit = 0f

const val isDebug = true

class MainActivity : Activity() {

    private lateinit var mainView: MainView
    private lateinit var thread: Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("Activity", "onCreate()")
        super.onCreate(savedInstanceState)

        val dim = Point()
        windowManager.defaultDisplay.getSize(dim)
        width = dim.x.toFloat()
        height = dim.y.toFloat()
        halfWidth = width / 2f
        halfHeight = height / 2f
        unit = width / 360f
        Log.i("Activity", "width: $width height: $height")

        mainView = MainView(this)

        setContentView(mainView)

        mainView.setup()
    }


    override fun onStart() {
        Log.i("Activity", "onStart()")
        super.onStart()

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



    override fun onRestart() {
        Log.i("Activity", "onRestart()")
        super.onRestart()

    }


    override fun onStop() {
        Log.i("Activity", "onStop()")
        super.onStop()

        appOpen = false
        Log.i("Activity", "Joining status thread")
        thread.join()
    }
    

    //called once when the app starts
    override fun onResume() {
        Log.i("Activity", "onResume()")
        super.onResume()

        mainView.resume()
    }
}