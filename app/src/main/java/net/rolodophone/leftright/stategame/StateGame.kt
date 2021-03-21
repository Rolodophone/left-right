package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.main.MainActivity
import net.rolodophone.leftright.main.State

class StateGame(ctx: MainActivity, val area: Int) : State(ctx) {
    override val numThingsToLoad = 1

    val bitmaps = ctx.bitmaps
    val sounds = ctx.sounds
    val music = ctx.music

    enum class State {UNPAUSED, PAUSED, GAME_OVER}

    var state = State.UNPAUSED
        private set

    val road = Road(this)
    val player = Player(this)
    val status = Status(this)
    val gameOverlay = GameOverlay(this)
    val pausedOverlay = PausedOverlay(this)
    val gameOverOverlay = GameOverOverlay(this)

    init {
        road.objects.add(player)

        //load music
        music.prepare(0)
    }

    override fun update() {
        status.update()
        gameOverlay.update()
        pausedOverlay.update()
        gameOverOverlay.update()

        if (state != State.PAUSED) {
            road.update()

            if (state == State.GAME_OVER) {
                gameOverOverlay.updateMsg()
            }
        }
    }

    override fun draw() {
        road.draw()

        when (state) {
            State.UNPAUSED -> {
                status.draw()
                gameOverlay.draw()
            }
            State.PAUSED -> {
                status.draw()
                pausedOverlay.draw()
            }
            State.GAME_OVER -> {
                gameOverOverlay.draw()
            }
        }
    }

    fun unpauseGame() {
        state = State.UNPAUSED
        music.resume()
    }

    fun pauseGame() {
        state = State.PAUSED
        music.pause()
    }

    fun endGame() {
        state = State.GAME_OVER
        music.pause()
        gameOverOverlay.prepare()
    }

    fun onMusicReady() {
        if (state == State.UNPAUSED) music.resume()
    }
}