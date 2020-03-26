package net.rolodophone.leftright.resources

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import net.rolodophone.leftright.R
import java.util.concurrent.CountDownLatch

class Music(ctx: Context) {
    //this is used to pause the app until the music is loaded
    val countDownLatch = CountDownLatch(ctx.resources.getInteger(R.integer.num_big_resources))

    val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(10)
        .setAudioAttributes(
            AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build())
        .build()

    init {
        soundPool.setOnLoadCompleteListener { _, _, _ ->
            countDownLatch.countDown()
            Log.d("music", "${countDownLatch.count} big resources left to load")
        }
    }

    var game = 0

    private fun playMusic(music: Int) {
        soundPool.play(music, 1f, 1f, 1, -1, 1f)
    }

    fun playGame() = playMusic(game)

    fun stop() = soundPool.autoPause()
}