package net.rolodophone.leftright.resources

import android.content.Context
import android.media.SoundPool

class Sounds(ctx: Context) {
    val soundPool: SoundPool = SoundPool.Builder().setMaxStreams(10).build()

    var hit = 0
    var select = 0
    var tap = 0
    var fuel = 0
    var oil = 0
    var coin = 0

    private fun playSound(sound: Int) {
        soundPool.play(sound, 1f, 1f, 1, 0, 1f)
    }

    fun playHit() = playSound(hit)
    fun playSelect() = playSound(select)
    fun playTap() = soundPool.play(tap, 0.2f, 0.2f, 1, 0, 1f)
    fun playFuel() = playSound(fuel)
    fun playOil() = playSound(oil)
    fun playCoin() = playSound(coin)

    fun stop() = soundPool.autoPause()
}