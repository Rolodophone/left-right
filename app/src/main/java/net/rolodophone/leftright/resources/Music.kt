package net.rolodophone.leftright.resources

import android.content.Context
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.Util
import net.rolodophone.leftright.R

class Music(ctx: Context) {
    val player = SimpleExoPlayer.Builder(ctx).build()

    private val rawDataSource = RawResourceDataSource(ctx)
    init {
        rawDataSource.open(DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.just_nasty)))
    }

    private val dataSourceFactory = DefaultDataSourceFactory(ctx, Util.getUserAgent(ctx, ctx.resources.getString(R.string.app_name)))


    private val game: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(rawDataSource.uri)


    private fun playMusic(music: MediaSource) {
        player.prepare(music)
        player.playWhenReady = true
        player.repeatMode = SimpleExoPlayer.REPEAT_MODE_ALL
    }

    fun playGame() = playMusic(game)

    fun stop() {
        player.playWhenReady = false
    }
}