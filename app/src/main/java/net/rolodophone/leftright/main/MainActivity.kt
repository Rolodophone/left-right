package net.rolodophone.leftright.main

import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.View
import net.rolodophone.leftright.components.*
import net.rolodophone.leftright.resources.Bitmaps
import net.rolodophone.leftright.resources.Music
import net.rolodophone.leftright.resources.Sounds
import net.rolodophone.leftright.state.*

class MainActivity : Activity() {

    lateinit var buttons: Buttons
    lateinit var gameOverlay: GameOverlay
    lateinit var gameOverOverlay: GameOverOverlay
    lateinit var grid: Grid
    lateinit var pausedOverlay: PausedOverlay
    lateinit var player: Player
    lateinit var road: Road
    lateinit var statusBar: StatusBar
    lateinit var weather: Weather

    lateinit var sounds: Sounds
    lateinit var music: Music
    lateinit var bitmaps: Bitmaps

    val stateLoading = StateLoading(this)
    lateinit var stateGameOver: StateGameOver
    lateinit var statePaused: StatePaused
    lateinit var stateGame: StateGame

    var state: State = stateLoading
        set(value) {

            if (value == stateGameOver || value == stateGame && state != statePaused) {
                value.reset()
            }

            field = value
        }

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