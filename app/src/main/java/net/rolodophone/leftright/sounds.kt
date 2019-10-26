package net.rolodophone.leftright

import android.media.SoundPool

object sounds {
    val soundPool = SoundPool.Builder().setMaxStreams(3).build()

    var hit: Int = 0
    var select: Int = 0
    var tap: Int = 0
    var fuel: Int = 0

    fun playHit() = soundPool.play(hit, 1f, 1f, 1, 0, 1f)
    fun playSelect() = soundPool.play(select, 1f, 1f, 1, 0, 1f)
    fun playTap() = soundPool.play(tap, 0.2f, 0.2f, 1, 0, 1f)
    fun playFuel() = soundPool.play(fuel, 1f, 1f, 1, 0, 1f)
}