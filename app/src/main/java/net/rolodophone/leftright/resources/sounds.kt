package net.rolodophone.leftright.resources

import android.media.SoundPool

object sounds {
    val soundPool = SoundPool.Builder().setMaxStreams(10).build()

    var hit = 0
    var select = 0
    var tap = 0
    var fuel = 0
    var oil = 0
    var coin = 0

    fun playHit() = soundPool.play(hit, 1f, 1f, 1, 0, 1f)
    fun playSelect() = soundPool.play(select, 1f, 1f, 1, 0, 1f)
    fun playTap() = soundPool.play(tap, 0.2f, 0.2f, 1, 0, 1f)
    fun playFuel() = soundPool.play(fuel, 1f, 1f, 1, 0, 1f)
    fun playOil() = soundPool.play(oil, 1f, 1f, 1, 0, 1f)
    fun playCoin() = soundPool.play(coin, 1f, 1f, 1, 0, 1f)
}