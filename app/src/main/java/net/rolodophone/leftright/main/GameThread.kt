package net.rolodophone.leftright.main

import android.os.Looper

class GameThread(private val mainView: MainView): Thread() {
    override fun run() {
        Looper.prepare()
        mainView.run()
    }
}