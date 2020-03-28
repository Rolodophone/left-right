package net.rolodophone.leftright.resources

import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.Util
import net.rolodophone.leftright.R
import net.rolodophone.leftright.main.MainActivity

class Music(ctx: MainActivity) {
    companion object {
        const val NUM_SONGS = 1
    }


    private val player = SimpleExoPlayer.Builder(ctx).build()


    private val rawDataSource = RawResourceDataSource(ctx)
    init {
        rawDataSource.open(DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.just_nasty)))
    }
    private val dataSourceFactory = DefaultDataSourceFactory(ctx, Util.getUserAgent(ctx, ctx.resources.getString(R.string.app_name)))

    private val game: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(rawDataSource.uri)


    init {
        player.addListener(object : Player.EventListener {
            override fun onLoadingChanged(isLoading: Boolean) {
                if (!isLoading) ctx.musicLoadingLatch.countDown()
            }
        })
    }


    private fun playMusic(music: MediaSource) {
        player.prepare(music)
        player.playWhenReady = true
    }

    fun playGame() = playMusic(game)

    fun stop() {
        player.playWhenReady = false
    }

    fun resume() {
        player.playWhenReady = true
    }
}