package net.rolodophone.leftright

import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.util.Log

var appOpen = true
var width: Float = 0f
var height: Float = 0f

class MainActivity : Activity() {

    private lateinit var mainView: MainView
    private lateinit var thread: Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dim = Point()
        windowManager.defaultDisplay.getSize(dim)
        width = dim.x.toFloat()
        height = dim.y.toFloat()

        mainView = MainView(this)

        setContentView(mainView)

        thread = Thread(mainView)
        Log.i("Activity", "Starting game thread")
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

        appOpen = true
        Log.i("Activity", "Starting game thread")
        thread.start()

    }


    override fun onStop() {
        Log.i("Activity", "onStop()")
        super.onStop()

        appOpen = false
        Log.i("Activity", "Joining game thread")
        thread.join()
    }
    

    //called once when the app starts
    override fun onResume() {
        Log.i("Activity", "onResume()")
        super.onResume()

        mainView.resume()
    }
}